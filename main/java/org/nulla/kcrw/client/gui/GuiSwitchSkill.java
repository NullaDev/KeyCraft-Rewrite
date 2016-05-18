package org.nulla.kcrw.client.gui;

import org.nulla.kcrw.KCUtils;
import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.Skills;
import org.nulla.nullacore.api.skill.SkillUtils;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSwitchSkill extends KCGuiBase {
	
	private EntityPlayer skillOwner;
	
	private GuiButtonImage btnCurrentSkillSlot[] = new GuiButtonImage[4];
	private GuiButtonImage btnOptionalSkill[];
	private Skill[] optionalSkill;
	
	private int currentState = -1;
		 
    public GuiSwitchSkill(GuiScreen parent, EntityPlayer player) {
    	super(parent);
    	skillOwner = player;
    }

    @Override
    public void initGui() {
    	Skill[] skillsInSlot = new Skill[4];
    	for (int i = 0; i < 4; i++) {
    		skillsInSlot[i] = SkillUtils.getSkillInSlot(skillOwner, i);
    		if (skillsInSlot[i] != null) {
    	    	buttonList.add(btnCurrentSkillSlot[i] = new GuiButtonImage(i, (int)(width * (0.35 + 0.1 * i) - 16), (int)(height * 0.3), 32, 32, skillsInSlot[i].mIcon, true));
    		} else {
    	    	buttonList.add(btnCurrentSkillSlot[i] = new GuiButtonImage(i, (int)(width * (0.35 + 0.1 * i) - 16), (int)(height * 0.3), 32, 32, Skill.empty_skill_icon, true));
    		}
    	}
    	   
        if (currentState != -1) {
        	int optionalSkillNumber = 0;
        	optionalSkill = new Skill[99];
        	for (Skill i : Skills.AllSkills) {
        		if (i.hasSkill(skillOwner)) {
        			optionalSkill[optionalSkillNumber++] = i;
        		}
        	}
        	
    		btnOptionalSkill = new GuiButtonImage[optionalSkillNumber];

        	for (int i = 0; i < optionalSkillNumber; i++) {
    	    	buttonList.add(btnOptionalSkill[i] = new GuiButtonImage(i + 100, (int)(width * (0.28 + 0.06 * i)) - 8, (int)(height * 0.6), 16, 16, optionalSkill[i].mIcon, true));
        	}
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        drawRect((int) (width * 0.2), (int) (height * 0.2), (int) (width * 0.8), (int) (height * 0.8), 0x7F000000);
        KCUtils.initDrawerState();      
    	super.drawScreen(par1, par2, par3);
    }
    
    @Override
	protected void actionPerformed(GuiButtonImage button) {
		if (currentState != -1) {
			for (int i = 0; i < btnOptionalSkill.length; i++) {
				if (button.equals(btnOptionalSkill[i])) {
					SkillUtils.setSkillInSlot(skillOwner, currentState, optionalSkill[i], true);
					currentState = -1;
				}
			}
		}
		
		for (int i = 0; i < 4; i++) {
			if (button.equals(btnCurrentSkillSlot[i])) {
				currentState = i;
			}
		}
		refresh();
	}
    
    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}
