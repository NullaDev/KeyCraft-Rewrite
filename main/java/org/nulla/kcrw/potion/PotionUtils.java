package org.nulla.kcrw.potion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

public class PotionUtils {

	public static final ArrayList<KCPotion> AllPotions = new ArrayList<KCPotion>();
	
	public static void addPotion(EntityPlayer player, KCPotion potion, int ticks) {
		String name = "potion_" + potion.mName;
		if (player.getEntityData().hasKey(name)) {
			int restTicks = player.getEntityData().getInteger(name);
			player.getEntityData().setInteger("potion_" + potion.mName, Math.max(ticks, restTicks));
		} else {
			player.getEntityData().setInteger("potion_" + potion.mName, ticks);
		}
	}
	
	public static void addPotion(EntityPlayer player, HashMap potions) {
		Iterator iter = potions.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			KCPotion key = (KCPotion) entry.getKey();
			int val = (Integer) entry.getValue();
			addPotion(player, key, val);
		}
	}
	
	public static HashMap getPotion(EntityPlayer player) {
		HashMap<KCPotion, Integer> potions = new HashMap<KCPotion, Integer>();
		for (KCPotion i : AllPotions) {
			String name = "potion_" + i.mName;
			if (player.getEntityData().hasKey(name)) {
				int restTicks = player.getEntityData().getInteger(name);
				if (restTicks != 0) {
					potions.put(i, restTicks);
				}
			}
		}
		return potions;
	}
	
}
