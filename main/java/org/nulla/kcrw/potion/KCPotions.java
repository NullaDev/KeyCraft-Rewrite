package org.nulla.kcrw.potion;

import org.nulla.kcrw.KCResources;

/** 声明所有效果 */
public class KCPotions {

	public static PoisonResistance poisonResistance;
	public static PotionAuroraRegeneration auroraRegeneration;
	
	public static void initPotions() {
		poisonResistance = new PoisonResistance("kcrw.potion.poisonResistance", KCResources.potion_poisonResistance);
		auroraRegeneration = new PotionAuroraRegeneration("kcrw.potion.auroraRegeneration", KCResources.potion_aurora_regeneration);
	}
	
}
