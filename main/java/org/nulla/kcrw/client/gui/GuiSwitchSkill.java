package org.nulla.kcrw.client.gui;

import java.util.ArrayList;

import org.nulla.kcrw.KCUtils;
import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.SkillPassive;
import org.nulla.nullacore.api.skill.Skills;
import org.nulla.nullacore.api.skill.SkillUtils;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSwitchSkill extends KCGuiBase {
	
	private EntityPlayer skillOwner;
	
	private GuiButtonImage btnCurrentSkillSlot[] = new GuiButtonImage[4];
	private GuiButtonImage btnOptionalSkill[];
	private ArrayList<Skill> optionalSkill = new ArrayList<Skill>();
	
	private int currentState = -1;
		 
    public GuiSwitchSkill(GuiScreen parent, EntityPlayer player) {
    	super(parent);
    	skillOwner = player;
    }

    @Override
    public void initGui() {
    	buttonList.clear();
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
        	for (Skill i : Skills.AllSkills) {
        		if (i.hasSkill(skillOwner)) {
        			optionalSkill.add(i);
        			optionalSkillNumber++;
        		}
        	}
        	
    		btnOptionalSkill = new GuiButtonImage[optionalSkillNumber];

        	for (Skill i : optionalSkill) {
        		int num = optionalSkill.indexOf(i);
    	    	buttonList.add(btnOptionalSkill[num] = new GuiButtonImage(num + 100, (int)(width * (0.28 + 0.06 * (num % 8))) - 8, (int)(height * (0.5 + num / 8 * 0.1)), 16, 16, i.mIcon, true));
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
			for (GuiButtonImage i : btnOptionalSkill) {
				if (button.equals(i)) {
					Skill toChange = SkillUtils.getSkillInSlot(skillOwner, currentState);
					if (toChange instanceof SkillPassive) {
						 if (((SkillPassive) toChange).getIsOn(skillOwner))
							 toChange.useSkill(skillOwner);
					}
					SkillUtils.setSkillInSlot(skillOwner, currentState, optionalSkill.get(i.id - 100), true);
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
    
    @Override
    public void refresh() {
    	buttonList.clear();
    	optionalSkill.clear();
    	this.initGui();
    }

}
