package org.nulla.kcrw.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiKotoriWorkshop extends GuiScreen {
	
	private GuiScreen parentScreen;

	private GuiButton btnReturn;
	 
    public GuiKotoriWorkshop(GuiScreen parent) {
         parentScreen = parent;
    }

    @Override
    public void initGui() {
    	buttonList.add(btnReturn = new GuiButton(0, (int)(width * 0.5 - 32), (int)(height*0.4), 64, 20, ""));
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawDefaultBackground();
        
        //mc.renderEngine.bindTexture(KCResources.aaaaaa);
        //func_146110_a((int)(width*0.05), (int)(height*0.05), 0, 0, (int)(width*0.9), (int)(height*0.8), (int)(width*0.9), (int)(height*0.8));
        
        drawRect((int)(width*0.05), (int)(height*0.05), (int)(width*0.95), (int)(height*0.85), 0x80FFFFFF);

    	super.drawScreen(par1,par2,par3);
        
    }
    
    @Override
	protected void actionPerformed(GuiButton button) {
		if (button.equals(btnReturn)) {
			mc.displayGuiScreen(parentScreen);
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
