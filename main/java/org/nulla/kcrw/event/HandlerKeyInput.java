package org.nulla.kcrw.event;

import org.nulla.kcrw.*;
import org.nulla.kcrw.client.gui.GuiSwitchSkill;
import org.nulla.kcrw.skill.*;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class HandlerKeyInput {
	
	@SubscribeEvent
	public void keyListener(KeyInputEvent event) {
		EntityPlayer player = KCUtils.getPlayerCl();
		
		if (KCClientProxy.kbSwitchSkill.isPressed()) {
			KCUtils.getMC().displayGuiScreen(new GuiSwitchSkill(KCUtils.getMC().currentScreen, player));
		}
	}

}
