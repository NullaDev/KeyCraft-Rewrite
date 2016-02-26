package org.nulla.kcrw.gui;

import org.nulla.kcrw.*;
import org.nulla.kcrw.gui.part.*;
import org.nulla.kcrw.item.*;
import org.nulla.kcrw.item.crafting.KCRecipe;

import net.minecraft.client.gui.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.common.MinecraftForge;

public class GuiKotoriWorkshop extends GuiScreen {
	
	private GuiScreen parentScreen;
	
	private String currentState = "CRAFT";

	private GuiButtonImage btnEnd;
	private GuiButtonImage btnCraft[] = new GuiButtonImage[99];
	
	private KCItemBase currentCraftItem = null;
	 
    public GuiKotoriWorkshop(GuiScreen parent) {
         parentScreen = parent;
    }

    @Override
    public void initGui() {
    	buttonList.add(btnEnd = new GuiButtonImage(100, (int)(width * 0.05), (int)(height * 0.2 - 31), 32, 32, "end"));
    	addCraftButton();
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        //drawDefaultBackground();
        
        mc.renderEngine.bindTexture(KCResources.bg);
        KCUtils.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1280, 1200, width, height, 1280, 1200);
        //drawRect(0, 0, width, height, 0xFFFFFFFF);

        //super.drawScreen(par1,par2,par3);
                
        //绘制边框
        int shang = (int)(height * 0.2);
        int xia = (int)(height * 0.9);
        int zuo = (int)(width * 0.05);
        int you = (int)(width * 0.95);
        int zhong = (int)(width * 0.5);
        
        drawRect(zuo, shang, you, shang + 1, 0xFF000000);
        drawRect(zuo, shang, zuo + 1, xia, 0xFF000000);
        drawRect(you - 1, shang, you, xia, 0xFF000000);
        drawRect(zuo, xia - 1, you, xia, 0xFF000000);
        drawRect(zhong, shang, zhong + 1, xia, 0xFF000000);
        
        KCUtils.initDrawerState();
    	
        for (int k = 0; k < this.buttonList.size(); ++k) {
            ((GuiButtonImage)this.buttonList.get(k)).drawButton(this.mc, par1, par2);
        }
        
        KCUtils.drawAuroraStrip(width, height);
        
        //绘制合成界面
        if (currentCraftItem != null) {
        	ItemStack craftItemStack[] = new ItemStack[3];
        	for (int i = 0; i < 3; i++)
        		craftItemStack[i] = currentCraftItem.getRecipe().getCraftItemStack(i);
        	
        	fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("kcrw.gui.currentCraftItem") + ":" + currentCraftItem.getUnlocalizedName(), zhong + 5, shang + 10, 0xFFFFFF);
        	fontRendererObj.drawStringWithShadow(StatCollector.translateToLocal("kcrw.gui.needs") + ":", zhong + 5, shang + 20, 0xFFFFFF);
        	for (int i=0; i<3; i++) {
        		if (craftItemStack[i] != null) {
        			int number = KCUtils.getNumberOfItemInPlayer(KCUtils.getPlayerCl(), craftItemStack[i].getItem());
        			String info = "";
        			info += craftItemStack[i].getUnlocalizedName();
        			info += ": ";
        			info += number;
        			info += " / ";
        			info += craftItemStack[i].stackSize;
        			int color = number >= craftItemStack[i].stackSize? 0xFFFFFF : 0xFF0000;
        			fontRendererObj.drawStringWithShadow(info, zhong + 5, shang + 30 + 10 * i, color);
        		}
        	}

        	KCUtils.initDrawerState();
        }
    }
    
    //不是Override
	protected void actionPerformed(GuiButtonImage button) {
		if (button.equals(btnEnd)) {
			mc.displayGuiScreen(parentScreen);
    	} else {
    		for (int i = 0; i < 99; i++) {
    			if (button.equals(btnCraft[i])) {
    				currentCraftItem = (KCItemBase) KCRecipe.getCraftItemFromNumber(i);
    			}
    		}
    	}
	}
    
    @Override
    protected void mouseClicked(int p_73864_1_, int p_73864_2_, int p_73864_3_) {
    	if (p_73864_3_ == 0) {
            for (int l = 0; l < this.buttonList.size(); ++l) {
            	GuiButtonImage guibutton = (GuiButtonImage)this.buttonList.get(l);

                if (guibutton.mousePressed(this.mc, p_73864_1_, p_73864_2_)) {
                	guibutton.func_146113_a(this.mc.getSoundHandler());
                    this.actionPerformed(guibutton);
                }
            }
        }
    }
    
    public void refresh() {
        mc.displayGuiScreen(new GuiKotoriWorkshop(this.parentScreen));
    }
    
    public GuiScreen getParentScreen() {
    	return this.parentScreen;
    }
    
    public GuiScreen getThisScreen() {
    	return mc.currentScreen;
    }
    
    private void addCraftButton() {
    	buttonList.add(btnCraft[0] = new GuiButtonImage(0, (int)(width * 0.3 - 64), (int)(height * 0.3), 32, 32, "peach_juice"));
    }

}
