package org.nulla.kcrw.potion;

import java.util.ArrayList;

import org.nulla.kcrw.KCResources;

/** 声明所有效果 */
public class Potions {

	public static PotionTest test;
	public static PoisonResistance poisonResistance;
	
	public static void initPotions()
	{
		test = new PotionTest("kcrw.potion.test", null);
		poisonResistance = new PoisonResistance("kcrw.potion.poisonResistance", KCResources.potion_poisonResistance);
	}
	
}
