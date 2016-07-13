package org.nulla.kcrw.event;

import org.nulla.kcrw.client.gui.SkillLearningHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerPlayerCraftKCItem {
	
	@SubscribeEvent
    public void onPlayerCraftKCItem(EventPlayerCraftKCItem event) {
		SkillLearningHelper.setPlayerCraftInfo(event.crafter, event.kcitem, true);
	}

}
