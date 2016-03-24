package org.nulla.kcrw.gui;

import org.nulla.kcrw.KCResources;
import org.nulla.kcrw.gui.part.GuiButtonImage;
import org.nulla.kcrw.item.KCItemBase;
import org.nulla.kcrw.item.crafting.KCRecipe;
import org.nulla.kcrw.skill.Skill;
import org.nulla.kcrw.skill.SkillUtils;
import org.nulla.kcrw.skill.Skills;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSwitchSkill extends GuiScreen {
	
	private GuiScreen parentScreen;
	private EntityPlayer skillOwner;
	
	private GuiButtonImage btnCurrentSkillSlot[] = new GuiButtonImage[4];
	private GuiButtonImage btnOptionalSkill[];
	private Skill[] optionalSkill;
	
	private int currentState = -1;
		 
    public GuiSwitchSkill(GuiScreen parent, EntityPlayer player) {
         parentScreen = parent;
         skillOwner = player;
    }

    @Override
    public void initGui() {
    	Skill[] skillsInSlot = new Skill[4];
    	for (int i = 0; i < 4; i++) {
    		skillsInSlot[i] = SkillUtils.getSkillInSlot(skillOwner, i);
    		if (skillsInSlot[i] != null) {
    	    	buttonList.add(btnCurrentSkillSlot[i] = new GuiButtonImage(i, (int)(width * (0.35 + 0.1 * i) - 16), (int)(height * 0.3), 32, 32, skillsInSlot[i].mIcon));
    		} else {
    	    	buttonList.add(btnCurrentSkillSlot[i] = new GuiButtonImage(i, (int)(width * (0.35 + 0.1 * i) - 16), (int)(height * 0.3), 32, 32, Skill.empty_skill_icon));
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
    	    	buttonList.add(btnOptionalSkill[i] = new GuiButtonImage(i + 100, (int)(width * (0.28 + 0.06 * i)) - 8, (int)(height * 0.6), 16, 16, optionalSkill[i].mIcon));
        	}
        }
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        //drawDefaultBackground();
        
        drawRect((int) (width * 0.2), (int) (height * 0.2), (int) (width * 0.8), (int) (height * 0.8), 0x7F000000);

        //super.drawScreen(par1,par2,par3);
    	
        //»æÖÆButton
        for (int k = 0; k < this.buttonList.size(); ++k) {
            ((GuiButtonImage)this.buttonList.get(k)).drawButton(this.mc, par1, par2);
        }
                
    }
    
    //²»ÊÇOverride
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
