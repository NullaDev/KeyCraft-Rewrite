package org.nulla.kcrw.event;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.client.gui.SkillLearningHelper;
import org.nulla.kcrw.skill.SkillsRw;
import org.nulla.nullacore.api.skill.Skill;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerCraftKCItem {
	
	@SubscribeEvent
    public void onPlayerCraftKCItem(EventPlayerCraftKCItem event) {
		Skill skill = null;
		if (event.kcitem == KCItems.baseball) {
			skill = SkillsRw.BaseballShooting;
		} else if (event.kcitem == KCItems.javelin) {
			skill = SkillsRw.LouisJavelin;
		} else if (event.kcitem == KCItems.miracle_ribbon) {
			skill = SkillsRw.RibbonTouch;
		}
		
		if (skill == null) {
			return;
		}
		
		SkillLearningHelper.findSkill(event.crafter, skill, true);
		
	}

}
