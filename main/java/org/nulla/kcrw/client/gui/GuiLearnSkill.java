package org.nulla.kcrw.client.gui;

import java.util.ArrayList;

import org.nulla.kcrw.KCResources;
import org.nulla.kcrw.KCUtils;
import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.Skills;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;

public class GuiLearnSkill extends KCGuiBase {

	private EntityPlayer learner;
	
	private String currentState = "CHOOSE";
	private Skill currentSkill = null;

	private GuiButtonImage btnChooseReturn;
	private GuiButtonImage btnLearnSkill;
	private ArrayList<GuiButtonImage> btnSkill = new ArrayList<GuiButtonImage>();
		 
    public GuiLearnSkill(GuiScreen parent, EntityPlayer player) {
         super(parent);
         learner = player;
    }

    @Override
    public void initGui() {
    	SkillLearningHelper.init();
		buttonList.add(btnChooseReturn = new GuiButtonImage(128, (int)(width * 0.48 - 16), (int)(height * 0.13 - 16), 32, 32, KCResources.btn_return, false));

		if (currentState.equals("CHOOSE")) {
        	addSkillButton();
        } else if (currentSkill != null) {
    		buttonList.add(btnLearnSkill = new GuiButtonImage(256, (int)(width * 0.4), (int)(height * 0.7), 32, 32, KCResources.btn_ensure, false));
    	}
    	     
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawRect(0, 0, width, height, 0xAF000000);
        KCUtils.initDrawerState();

        mc.renderEngine.bindTexture(KCResources.gui_learn_skill);
        KCUtils.drawScaledCustomSizeModalRect(0, 0, 0, 0, 1366, 768, width, height, 1366, 768);
        KCUtils.initDrawerState();
                
        if (this.currentState == "CHOOSE") {
        	for (Skill i : Skills.AllSkills) {
        		if (SkillLearningHelper.canFind(i, this.learner) && SkillLearningHelper.getPreSkill(i) != null) {
        			for (Skill j : SkillLearningHelper.getPreSkill(i)) {
        				int x0 = (int) (width * SkillLearningHelper.getSkillPosX(i));
        				int y0 = (int) (height * SkillLearningHelper.getSkillPosY(i));
        				int x1 = (int) (width * SkillLearningHelper.getSkillPosX(j));
        				int y1 = (int) (height * SkillLearningHelper.getSkillPosY(j));
        				KCUtils.drawLine(x0, y0, x1, y1, 0.5F, 1F, 0.5F, 4F);
        		        KCUtils.initDrawerState();
        			}
        		}
        	}
		}
        
        super.drawScreen(par1, par2, par3);
    	                
		if (this.currentState == "LEARN") {

		}
		
    }
    
    @Override
	protected void actionPerformed(GuiButtonImage button) {
    	if (button.equals(btnChooseReturn)) {
    		if (this.currentState == "LEARN") {
    			this.currentState = "CHOOSE";
    		} else {
        		mc.displayGuiScreen(getParentScreen());
    		}
    	} else if (button.equals(btnLearnSkill)) {
			Skill.learnSkill(learner, currentSkill.mID);	
    	} else {
    		for (GuiButtonImage i : btnSkill) {
    			if (button.equals(i)) {
    				this.currentSkill = Skills.getSkill(i.id - 500);
    				this.currentState = "LEARN";
    			}
    		}
    	}
		refresh();
	}
    
    private void addSkillButton() {
    	for (Skill i : Skills.AllSkills) {
    		if (SkillLearningHelper.canFind(i, this.learner)) {
    			btnSkill.add(new GuiButtonImage(i.mID + 500, (int)(width * SkillLearningHelper.getSkillPosX(i)) - 8, (int)(height * SkillLearningHelper.getSkillPosY(i)) - 8, 16, 16, i.mIcon, true));
    		}
    	}
    	buttonList.addAll(btnSkill);
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
    	btnSkill.clear();
    	this.initGui();
    }

}
