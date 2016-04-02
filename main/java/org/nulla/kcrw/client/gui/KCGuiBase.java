package org.nulla.kcrw.client.gui;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.skill.Skill;
import org.nulla.kcrw.skill.SkillUtils;
import org.nulla.kcrw.skill.Skills;

public abstract class KCGuiBase extends GuiScreen {
	
	private GuiScreen parentScreen;
		 
    public KCGuiBase(GuiScreen parent) {
         parentScreen = parent;
    }

    @Override
    public void initGui() {
    	
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
    	for (int k = 0; k < this.buttonList.size(); ++k) {
    		((GuiButtonImage)this.buttonList.get(k)).drawButton(this.mc, par1, par2);
    	}
        KCUtils.initDrawerState();      
    }
    
	protected void actionPerformed(GuiButtonImage button) {

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
    	buttonList.clear();
    	this.initGui();
    }
    
    public GuiScreen getParentScreen() {
    	return this.parentScreen;
    }
    
    public GuiScreen getThisScreen() {
    	return mc.currentScreen;
    }

}
