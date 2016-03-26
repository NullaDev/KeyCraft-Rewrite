package org.nulla.kcrw.potion;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.client.Minecraft;

public class PerformPotion {
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event)
	{
		int[] currentPotions = PotionUtils.getPotion(event.player);
		int[] currentPotionLv = PotionUtils.getPotionLevel(event.player);
		for (int i = 0; i < currentPotions.length; i++)
		{
			KCPotion potion = Potions.AllPotions.get(currentPotions[i]);
			potion.onPerform(event.player, currentPotionLv[i]);
			PotionUtils.modifyPotionRestTick(event.player, potion, -1);
		}
	}
}
