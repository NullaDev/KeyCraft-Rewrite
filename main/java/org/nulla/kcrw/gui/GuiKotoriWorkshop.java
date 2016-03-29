package org.nulla.kcrw.gui;

import org.nulla.kcrw.*;
import org.nulla.kcrw.gui.part.*;
import org.nulla.kcrw.item.*;
import org.nulla.kcrw.item.crafting.KCRecipe;
import org.nulla.kcrw.skill.SkillNetwork;
import org.nulla.kcrw.skill.SkillUtils;

import net.minecraft.client.gui.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.common.MinecraftForge;

public class GuiKotoriWorkshop extends KCGuiBase {
	
	private EntityPlayer crafter;
	
	private String currentState = "CRAFT";

	private GuiButtonImage btnChooseCraft;
	private GuiButtonImage btnChooseDecompose;
	private GuiButtonImage btnChooseReturn;
	private GuiButtonImage btnEnsureCraft;
	private GuiButtonImage btnCraft[] = new GuiButtonImage[99];
	
	private KCItemBase currentCraftItem = null;
	 
    public GuiKotoriWorkshop(GuiScreen parent, EntityPlayer player) {
         super(parent);
         crafter = player;
    }

    @Override
    public void initGui() {
		buttonList.add(btnChooseCraft = new GuiButtonImage(128, (int)(width * 0.32 - 16), (int)(height * 0.13 - 16), 32, 32, KCResources.btn_craft, false));
		buttonList.add(btnChooseDecompose = new GuiButtonImage(129, (int)(width * 0.40 - 16), (int)(height * 0.13 - 16), 32, 32, KCResources.btn_decompose, false));
		buttonList.add(btnChooseReturn = new GuiButtonImage(130, (int)(width * 0.48 - 16), (int)(height * 0.13 - 16), 32, 32, KCResources.btn_return, false));

    	if (currentCraftItem != null) {
    		buttonList.add(btnEnsureCraft = new GuiButtonImage(256, (int)(width * 0.42 + 10), (int)(height * 0.2 + 145), 16, 16, KCResources.btn_ensure, false));
    	}
        if (currentState.equals("CRAFT")) {
        	addCraftButton();
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        
        mc.renderEngine.bindTexture(KCResources.gui_kotori_workshop);
        KCUtils.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1280, 1200, width, height, 1280, 1200);
    	
        super.drawScreen(par1, par2, par3);
                
        //绘制合成界面
        if (currentState.equals("CRAFT")) {
        	//绘制边框
            int shang = (int)(height * 0.2);
            int xia = (int)(height * 0.85);
            int zhong = (int)(width * 0.42);
            
            //这是一条竖线
            drawRect(zhong, shang, zhong + 1, xia, 0xFF000000);      
            KCUtils.initDrawerState();
            
        	if (currentCraftItem != null) {
        		
        		fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("kcrw.gui.currentCraftItem") + ":", zhong + 10, shang + 5, 0xAFAFAF);
        		fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal(currentCraftItem.getUnlocalizedName()+".name"), zhong + 10, shang + 15, 0x7FFFBF);
        		fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("kcrw.gui.currentCraftItemAmount") + ": " + currentCraftItem.getRecipe().getProductAmount(), zhong + 10, shang + 35, 0xAFAFAF);
        		fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("kcrw.gui.currentCraftItemInBag") + ": " + KCUtils.getNumberOfItemInPlayer(crafter, currentCraftItem), zhong + 10, shang + 55, 0xAFAFAF);
        		fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("kcrw.gui.needs") + ":", zhong + 10, shang + 75, 0xAFAFAF);
        		
        		for (int i = 0; i < 3; i++) {
            		ItemStack craftItemStack[] = new ItemStack[3];
        			craftItemStack[i] = currentCraftItem.getRecipe().getCraftItemStack(i);
        			if (craftItemStack[i] != null) {
        				int number = KCUtils.getNumberOfItemInPlayer(crafter, craftItemStack[i].getItem());
        				String info = "";
        				info += craftItemStack[i].getDisplayName();
        				info += ": ";
        				info += number;
        				info += " / ";
        				info += craftItemStack[i].stackSize;
        				int color = isEnough(i)? 0x7FFFBF : 0xFF0000;
        				fontRendererObj.drawStringWithShadow(info, zhong + 10, shang + 85 + 10 * i, color);
        			} else {
        				String info = "----: -- / --";
        				fontRendererObj.drawStringWithShadow(info, zhong + 10, shang + 85 + 10 * i, 0xAFAFAF);
        			}
        		}
        		
        		int aurora = SkillUtils.getAuroraPoint(crafter);
        		int aurora_required = currentCraftItem.getRecipe().getAuroraRequired();
        		int color = aurora > aurora_required ? 0x7FFFBF : 0xFF00000;
        		String info = "Aurora: " +  aurora_required + " / " + aurora;
				fontRendererObj.drawStringWithShadow(info, zhong + 10, shang + 125, color);

        		KCUtils.initDrawerState();
        	}
        }
    }
    
    @Override
	protected void actionPerformed(GuiButtonImage button) {
    	if (button.equals(btnChooseCraft)) {
    		currentState = "CRAFT";
    	} else if (button.equals(btnChooseDecompose)) {
    		currentState = "DECOMPOSE";
    	} else if (button.equals(btnChooseReturn)) {
    		mc.displayGuiScreen(getParentScreen());
    	} else if (button.equals(btnEnsureCraft)) {
			if (isEnough(0) && isEnough(1) && isEnough(2)) {
				craft(currentCraftItem, crafter);
			}				
    	} else {
    		for (int i = 0; i < 99; i++) {
    			if (button.equals(btnCraft[i])) {
    				currentCraftItem = (KCItemBase) KCRecipe.getCraftItemFromNumber(i);
    			}
    		}
    	}
		refresh();
	}
    
    private void addCraftButton() {
    	buttonList.add(btnCraft[0] = new GuiButtonImage(0, (int)(width * 0.15 ), (int)(height * 0.4), 32, 32, KCResources.item_peach_juice, true));
    	buttonList.add(btnCraft[1] = new GuiButtonImage(1, (int)(width * 0.15 + 36), (int)(height * 0.4), 32, 32, KCResources.item_music_player, true));
    	buttonList.add(btnCraft[2] = new GuiButtonImage(2, (int)(width * 0.15 + 72), (int)(height * 0.4), 32, 32, KCResources.item_aurora_iron_ingot, true));
    }
    
    private boolean isEnough(int i) {
		ItemStack craftItemStack = currentCraftItem.getRecipe().getCraftItemStack(i);

		if (craftItemStack == null)
			return true;
		
    	if (craftItemStack.stackSize <= KCUtils.getNumberOfItemInPlayer(crafter, craftItemStack.getItem()))
    		return true;
    	else
    		return false;
    	
    }
    
    /** 如果在客户端会发同步包 */
    public static void craft(KCItemBase output, EntityPlayer player) {
    	if (output == null)
    		return;
    	
    	if (player.worldObj.isRemote)
        	KCNetwork.Channel.sendToServer(KCNetwork.createCraftPacket(output));
    	
    	for (int i = 0; i < 3; i++) {
    		ItemStack craftItemStack[] = new ItemStack[3];
			craftItemStack[i] = output.getRecipe().getCraftItemStack(i);
			if (craftItemStack[i] != null) {
				KCUtils.minusNumberOfItemInPlayer(player, craftItemStack[i].getItem(), craftItemStack[i].stackSize);
			}
    	}
    	SkillUtils.modifyAuroraPoint(player, -1 * output.getRecipe().getAuroraRequired());
    	ItemStack stack = new ItemStack(output, output.getRecipe().getProductAmount());
    	player.inventory.addItemStackToInventory(stack);
    }
    
    public static void craft(Item output, EntityPlayer player) {
    	if (!(output instanceof KCItemBase))
    		return;
    	craft((KCItemBase)output, player);
    }

}
