package org.nulla.kcrw.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.nulla.kcrw.potion.KCPotion;
import org.nulla.kcrw.potion.PotionUtils;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class HandlerTick_KCPotion {
	
	/** ÿtick��������potionʣ��ʱ�� */
	@SubscribeEvent
	public void PotionTick(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		HashMap potions = PotionUtils.getPotion(player);
		Iterator iter = potions.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			KCPotion key = (KCPotion) entry.getKey();
			int val = (Integer) entry.getValue() - 1;
			potions.replace(key, val);
		}
		PotionUtils.addPotion(player, potions);
	}

}
