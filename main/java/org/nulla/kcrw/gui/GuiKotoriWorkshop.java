package org.nulla.kcrw.gui;

import org.nulla.kcrw.gui.part.*;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.common.MinecraftForge;

public class GuiKotoriWorkshop extends GuiScreen {
	
	private GuiScreen parentScreen;

	private GuiButtonImage btnEnd;
	 
    public GuiKotoriWorkshop(GuiScreen parent) {
         parentScreen = parent;
    }

    @Override
    public void initGui() {
    	buttonList.add(btnEnd = new GuiButtonImage(0, (int)(width * 0.9 - 64), (int)(height*0.1), 64, 64, "end"));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        
        //mc.renderEngine.bindTexture(KCResources.aaaaaa);
        //func_146110_a((int)(width*0.05), (int)(height*0.05), 0, 0, (int)(width*0.9), (int)(height*0.8), (int)(width*0.9), (int)(height*0.8));
        
        drawRect((int)(width*0.1), (int)(height*0.1), (int)(width*0.9), (int)(height*0.8), 0x80FFFFFF);

    	//super.drawScreen(par1,par2,par3);
        for (int k = 0; k < this.buttonList.size(); ++k) {
            ((GuiButtonImage)this.buttonList.get(k)).drawButton(this.mc, par1, par2);
        }
        
    }
    
    //不是Override
	protected void actionPerformed(GuiButtonImage button) {
		if (button.equals(btnEnd)) {
			mc.displayGuiScreen(parentScreen);
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

}
