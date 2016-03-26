package org.nulla.kcrw.potion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.entity.player.EntityPlayer;

/** 还没写同步 */
public class PotionUtils {

	public static void addPotion(EntityPlayer player, KCPotion potion, int level, int ticks) {
		// 以后再优化了..
		int[] currentPotion = getPotion(player), 
				currentPotionLv = getPotionLevel(player),
				curPotionRestTick = getPotionRestTick(player);
		int[] newPotion = new int[currentPotion.length + 1], 
				newPotionLv = new int[currentPotion.length + 1],
				newPotionRestTick = new int[currentPotion.length + 1];
		
		System.out.println(String.format("%d %d %d", currentPotion.length, currentPotionLv.length, curPotionRestTick.length));
		
		for (int i = 0; i < currentPotion.length; i++)
		{
			newPotion[i] = currentPotion[i];
			newPotionLv[i] = currentPotionLv[i];
			newPotionRestTick[i] = curPotionRestTick[i];
		}
		newPotion[currentPotion.length] = potion.mID;
		newPotionLv[currentPotion.length] = level;
		newPotionRestTick[currentPotion.length] = ticks;
		
		player.getEntityData().setIntArray("KCPotions", newPotion);
		player.getEntityData().setIntArray("KCPotionLevels", newPotionLv);
		player.getEntityData().setIntArray("KCPotionRestTicks", newPotionRestTick);
	}

	public static int[] getPotion(EntityPlayer player) {
		return player.getEntityData().getIntArray("KCPotions");
	}
	
	public static void deletePotion(EntityPlayer player, KCPotion potion) {
		int[] currentPotion = getPotion(player), 
				currentPotionLv = getPotionLevel(player),
				curPotionRestTick = getPotionRestTick(player);
		int[] newPotion = new int[currentPotion.length - 1], 
				newPotionLv = new int[currentPotion.length - 1],
				newPotionRestTick = new int[currentPotion.length - 1];
		
		int iNew = 0;
		for (int iCur = 0; iCur < potion.mID; iCur++)
		{
			if (currentPotion[iCur] != potion.mID)
			{
				newPotion[iNew] = currentPotion[iCur];
				newPotionLv[iNew] = currentPotionLv[iCur];
				newPotionRestTick[iNew] = curPotionRestTick[iCur];
				iNew++;
			}
		}
		
		player.getEntityData().setIntArray("KCPotions", newPotion);
		player.getEntityData().setIntArray("KCPotionLevels", newPotionLv);
		player.getEntityData().setIntArray("KCPotionRestTicks", newPotionRestTick);
	}

	public static int[] getPotionLevel(EntityPlayer player) {
		return player.getEntityData().getIntArray("KCPotionLevels");
	}

	public static int[] getPotionRestTick(EntityPlayer player) {
		return player.getEntityData().getIntArray("KCPotionRestTicks");
	}

	public static void modifyPotionRestTick(EntityPlayer player, KCPotion potion, int ticks) {
		int[] potionRestTick = getPotionRestTick(player);
		potionRestTick[potion.mID] += ticks;
		if (potionRestTick[potion.mID] > 0)
			player.getEntityData().setIntArray("KCPotionRestTicks", potionRestTick);
		else
			deletePotion(player, potion);
	}
	
}
