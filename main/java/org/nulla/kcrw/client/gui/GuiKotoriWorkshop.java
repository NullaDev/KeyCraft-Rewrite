package org.nulla.kcrw.client.gui;

import java.util.ArrayList;

import org.nulla.kcrw.KCNetwork;
import org.nulla.kcrw.KCResources;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.event.EventPlayerCraftKCItem;
import org.nulla.kcrw.item.KCItemBase;
import org.nulla.kcrw.item.crafting.KCRecipe;
import org.nulla.nullacore.api.skill.SkillUtils;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

public class GuiKotoriWorkshop extends KCGuiBase {
	
	private EntityPlayer crafter;
	
	private String currentState = "ITEM";

	private GuiButtonImage btnChooseItem;
	private GuiButtonImage btnChooseMonster;
	private GuiButtonImage btnChooseReturn;
	private GuiButtonImage btnEnsureCraft;
	private GuiButtonImage btnNextPage;
	private GuiButtonImage btnPreviousPage;
	private ArrayList<GuiButtonImage> btnCraft = new ArrayList<GuiButtonImage>();
	
	private KCItemBase currentCraftItem = null;
	private int currentItemPage = 0;
	 
    public GuiKotoriWorkshop(GuiScreen parent, EntityPlayer player) {
         super(parent);
         crafter = player;
    }

    @Override
    public void initGui() {
    	buttonList.clear();
    	btnCraft.clear();
    	
		buttonList.add(btnChooseItem = new GuiButtonImage(128, (int)(width * 0.32 - 16), (int)(height * 0.13 - 8), 32, 16, KCResources.btn_workshop_empty, false));
		buttonList.add(btnChooseMonster = new GuiButtonImage(129, (int)(width * 0.40 - 16), (int)(height * 0.13 - 8), 32, 16, KCResources.btn_workshop_empty, false));
		buttonList.add(btnChooseReturn = new GuiButtonImage(130, (int)(width * 0.48 - 16), (int)(height * 0.13 - 8), 32, 16, KCResources.btn_workshop_empty, false));

    	if (currentCraftItem != null) {
    		buttonList.add(btnEnsureCraft = new GuiButtonImage(256, (int)(width * 0.42 + 10), (int)(height * 0.2 + 145), 16, 16, KCResources.btn_workshop_ensure, false));
    	}
        if (currentState.equals("ITEM")) {
        	addCraftButton();
        	
        	if (KCRecipe.getItemCraftable(crafter).size() / 9 > currentItemPage) {
        		buttonList.add(btnNextPage = new GuiButtonImage(131, (int)(width * 0.4 - 8), (int)(height * 0.3 - 8), 16, 16, KCResources.btn_workshop_next, false));
        	}
        	
        	if (currentItemPage > 0) {
        		buttonList.add(btnPreviousPage = new GuiButtonImage(132, (int)(width * 0.35 - 8), (int)(height * 0.3 - 8), 16, 16, KCResources.btn_workshop_previous, false));
        	}
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawRect(0, 0, width, height, 0xAF000000);
        KCUtils.initDrawerState();

        mc.renderEngine.bindTexture(KCResources.gui_kotori_workshop);
        KCUtils.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1366, 768, width, height, 1366, 768);
    	
        super.drawScreen(par1, par2, par3);
        
		this.drawCenteredString(fontRendererObj, StatCollector.translateToLocal("kcrw.gui.craftitem"), (int)(width * 0.32), (int)(height * 0.13 - 4), 0xFFFFFF);
		this.drawCenteredString(fontRendererObj, StatCollector.translateToLocal("kcrw.gui.craftmonster"), (int)(width * 0.40), (int)(height * 0.13 - 4), 0xFFFFFF);
		this.drawCenteredString(fontRendererObj, StatCollector.translateToLocal("kcrw.gui.return"), (int)(width * 0.48), (int)(height * 0.13 - 4), 0xFFFFFF);
        KCUtils.initDrawerState();       
		
        //绘制合成界面
        if (currentState.equals("ITEM")) {
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
        } else if (currentState.equals("MONSTER")) {
        	KCUtils.drawStringWithShadow(fontRendererObj, StatCollector.translateToLocal("kcrw.gui.monster"), (int)(width * 0.16), (int)(height * 0.4), 0xFF0000);
            KCUtils.initDrawerState();
        }
    }
    
    @Override
	protected void actionPerformed(GuiButtonImage button) {
    	if (button.equals(btnChooseItem)) {
    		currentState = "ITEM";
    	} else if (button.equals(btnChooseMonster)) {
    		currentState = "MONSTER";
    	} else if (button.equals(btnChooseReturn)) {
    		mc.displayGuiScreen(getParentScreen());
    	} else if (button.equals(btnNextPage)) {
    		currentItemPage++;
    	} else if (button.equals(btnPreviousPage)) {
    		currentItemPage--;
    	} else if (button.equals(btnEnsureCraft)) {
			if (isEnough(0) && isEnough(1) && isEnough(2)) {
				craft(currentCraftItem, crafter);
			}				
    	} else {
    		for (GuiButtonImage i : btnCraft) {
    			if (button.equals(i)) {
    				currentCraftItem = (KCItemBase) Item.getItemById(i.id);
    			}
    		}
    	}
		refresh();
	}
    
    private void addCraftButton() {
    	int pos = 0;
    	ArrayList<Item> list = KCRecipe.getItemCraftable(crafter);
    	for (Item i : list) {
    		if (list.indexOf(i) / 9 != this.currentItemPage) {
    			continue;
    		}
    		int id = Item.getIdFromItem(i);
        	btnCraft.add(new GuiButtonImage(id, (int)(width * 0.15 + pos % 3 * 36), (int)(height * 0.4 + pos / 3 * 36), 32, 32, KCResources.getResourcebyID(id), true));
        	pos++;
    	}
    	buttonList.addAll(btnCraft);
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
    	
    	for (int i = 0; i < 3; i++) {
    		ItemStack craftItemStack[] = new ItemStack[3];
			craftItemStack[i] = output.getRecipe().getCraftItemStack(i);
			if (craftItemStack[i] != null) {
				//如果合成失败因为没有同步东西会还回来
				KCUtils.minusNumberOfItemInPlayer(player, craftItemStack[i].getItem(), craftItemStack[i].stackSize);
			}
    	}
    	
    	//防止合成品放不进玩家背包导致合成失败
    	ItemStack stack = new ItemStack(output, output.getRecipe().getProductAmount());
    	if (player.inventory.addItemStackToInventory(stack)) {
        	SkillUtils.modifyAuroraPoint(player, -1 * output.getRecipe().getAuroraRequired());        	
        	if (player.worldObj.isRemote)
            	KCNetwork.Channel.sendToServer(KCNetwork.createCraftPacket(output));
        	EventPlayerCraftKCItem event = new EventPlayerCraftKCItem(player, output);
    		MinecraftForge.EVENT_BUS.post(event);
    	}
    	
    }
    
    public static void craft(Item output, EntityPlayer player) {
    	if (!(output instanceof KCItemBase))
    		return;
    	craft((KCItemBase)output, player);
    }
    
    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    @Override
    public void refresh() {
    	buttonList.clear();
    	btnCraft.clear();
    	this.initGui();
    }

}
