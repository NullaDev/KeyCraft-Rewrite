package org.nulla.kcrw.client.gui;

import java.util.ArrayList;

import org.nulla.kcrw.KCResources;
import org.nulla.kcrw.KCUtils;
import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.SkillUtils;
import org.nulla.nullacore.api.skill.Skills;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;

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
    	buttonList.clear();
    	btnSkill.clear();
    	SkillLearningHelper.init();
		buttonList.add(btnChooseReturn = new GuiButtonImage(128, (int)(width * 0.7 - 16), (int)(height * 0.25 - 16), 32, 32, KCResources.btn_end, false));

		if (currentState.equals("CHOOSE")) {
        	addSkillButton();
        } else if (currentSkill != null) {
        	if (!currentSkill.hasSkill(learner))
        		buttonList.add(btnLearnSkill = new GuiButtonImage(256, (int)(width * 0.3) - 32, (int)(height * 0.8) - 8, 64, 16, KCResources.btn_skill_learn, false));
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
        
        if (this.currentState == "LEARN" && currentSkill != null) {
            mc.renderEngine.bindTexture(currentSkill.mIcon);
        	KCUtils.drawScaledCustomSizeModalRect((int)(width * 0.2) - 16, (int)(height * 0.45) - 16, 0, 0, 64, 64, 32, 32, 64, 64);
        	KCUtils.initDrawerState();
        	drawLearnState();
		}
        
        super.drawScreen(par1, par2, par3);
		
    }
    
    private void drawLearnState() {
    	int color;
    	
    	//技能名称
    	String name = StatCollector.translateToLocal("kcrw.skill.name") + ": " + StatCollector.translateToLocal("kcrw.skill." + currentSkill.mName + ".name");
    	this.fontRendererObj.drawStringWithShadow(name, (int)(width * 0.2) + 20, (int)(height * 0.45) - 12, 0xFFFFFF);
    	this.fontRendererObj.drawStringWithShadow("skill: " + currentSkill.mName, (int)(width * 0.2) + 20, (int)(height * 0.45) + 4, 0xFFFFFF);
    	KCUtils.initDrawerState();

    	//技能前置
    	if (currentSkill.hasSkill(learner)) {
    		String info = StatCollector.translateToLocal("kcrw.skill.learned");
    		color = 0x00FF00;
    		this.fontRendererObj.drawStringWithShadow(info, (int)(width * 0.2) - 16, (int)(height * 0.55), color);
    	} else {
    		String info;
    		if (currentSkill.canLearnSkill(learner))
    			info = StatCollector.translateToLocal("kcrw.skill.can_learn");
    		else
    			info = StatCollector.translateToLocal("kcrw.skill.cant_learn");
    		this.fontRendererObj.drawStringWithShadow(info, (int)(width * 0.2) - 16, (int)(height * 0.55), currentSkill.canLearnSkill(learner)? 0x00FF00 : 0xFF0000);
    	}
		KCUtils.initDrawerState();
    	
    	//技能消耗
    	if (currentSkill.hasSkill(learner)) {
    		String info = StatCollector.translateToLocal("kcrw.skill.aurora_cost") + ": " + currentSkill.mAuroraCost + "/" + SkillUtils.getAuroraPoint(learner);
    		color = currentSkill.mAuroraCost <= SkillUtils.getAuroraPoint(learner)? 0x00FF00 : 0xFF0000;
    		fontRendererObj.drawStringWithShadow(info, (int)(width * 0.2) - 16, (int)(height * 0.65), color);
    	} else {
    		String info = StatCollector.translateToLocal("kcrw.skill.aurora_require") + ": " + currentSkill.mAuroraRequired + "/" + SkillUtils.getAuroraPoint(learner);
    		color = currentSkill.mAuroraRequired <= SkillUtils.getAuroraPoint(learner)? 0x00FF00 : 0xFF0000;
    		fontRendererObj.drawStringWithShadow(info, (int)(width * 0.2) - 16, (int)(height * 0.65), color);
    	}
    	KCUtils.initDrawerState();
		
    	//技能熟练度
    	if (currentSkill.hasSkill(learner)) {
    		String info = StatCollector.translateToLocal("kcrw.skill.experience") + ": " + currentSkill.getExperience(learner)/1024F;
    		fontRendererObj.drawStringWithShadow(info, (int)(width * 0.2) - 16, (int)(height * 0.75), 0xFFFFFF);
    	}
    	KCUtils.initDrawerState();
    	
        //绘制一条竖线
    	int shang = (int)(height * 0.35);
        int xia = (int)(height * 0.85);
        int zhong = (int)(width * 0.48);
        
        drawRect(zhong, shang, zhong + 1, xia, 0xFFFFFFFF);      
        KCUtils.initDrawerState();
        
        //绘制技能说明
		String info = StatCollector.translateToLocal("kcrw.skill." + currentSkill.mName + ".info");
		KCUtils.drawStringWithShadow(fontRendererObj, info, (int)(width * 0.5), (int)(height * 0.4), 0xFFFFFF);

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
