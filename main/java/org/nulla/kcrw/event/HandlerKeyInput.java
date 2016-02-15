package org.nulla.kcrw.event;

import org.lwjgl.input.Keyboard;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.skill.Skill;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class HandlerKeyInput {
	
	@SubscribeEvent
	public void keyListener(KeyInputEvent event) {
		if (Keyboard.getEventKey() == Keyboard.KEY_R) {
	    	Skill.modifyAuroraPoint(KCUtils.getPlayerCl(), 1000);
	    }
	}

}
