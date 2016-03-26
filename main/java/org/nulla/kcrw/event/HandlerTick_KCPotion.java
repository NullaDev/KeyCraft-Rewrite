package org.nulla.kcrw.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.nulla.kcrw.potion.*;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class HandlerTick_KCPotion {
	
	/** 每tick减少所有potion剩余时间 */
	@SubscribeEvent
	public void PotionTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		int[] potions = PotionUtils.getPotion(player);
		for (int i : potions) {
			KCPotion potion = PotionUtils.getPotionFromID(i);
			PotionUtils.modifyPotionRestTick(player, potion, -1);
		}
	}

}
