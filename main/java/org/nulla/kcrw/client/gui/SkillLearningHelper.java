package org.nulla.kcrw.client.gui;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.item.KCItemBase;
import org.nulla.kcrw.skill.SkillsRw;
import org.nulla.nullacore.api.CO2D;
import org.nulla.nullacore.api.skill.Skill;

public class SkillLearningHelper {
	
	private static HashMap<Skill, CO2D> posHelper = new HashMap<Skill, CO2D>();
	
	private static HashMap<Skill, ArrayList<Skill>> PreLearnHelper = new HashMap<Skill, ArrayList<Skill>>();
	
	private static ArrayList<Skill> SkillsFindSpecially = new ArrayList<Skill>();
	
	public static void init() {
		posHelper.clear();
		posHelper.put(SkillsRw.VisionUp, new CO2D(0.25, 0.5));
		posHelper.put(SkillsRw.SpeedUp, new CO2D(0.25, 0.4));
		posHelper.put(SkillsRw.StrengthUp, new CO2D(0.25, 0.6));
		posHelper.put(SkillsRw.SpeedUpFinal, new CO2D(0.2, 0.4));
		posHelper.put(SkillsRw.StrengthUpFinal, new CO2D(0.2, 0.6));
		posHelper.put(SkillsRw.AuroraBlast, new CO2D(0.7, 0.7));
		posHelper.put(SkillsRw.BaseballShooting, new CO2D(0.3, 0.65));
		posHelper.put(SkillsRw.BaseballFalling, new CO2D(0.4, 0.6));
		posHelper.put(SkillsRw.BaseballRolling, new CO2D(0.4, 0.7));
		posHelper.put(SkillsRw.BaseballExplosive, new CO2D(0.35, 0.75));
		posHelper.put(SkillsRw.BaseballThundering, new CO2D(0.3, 0.75));
		posHelper.put(SkillsRw.LouisJavelin, new CO2D(0.2, 0.8));
		posHelper.put(SkillsRw.BloodControl, new CO2D(0.4, 0.5));
		posHelper.put(SkillsRw.AuroraBlade, new CO2D(0.5, 0.5));
		posHelper.put(SkillsRw.FireProtection, new CO2D(0.5, 0.2));
		posHelper.put(SkillsRw.PoisonProtection, new CO2D(0.5, 0.3));
	
		SkillsFindSpecially.clear();
		SkillsFindSpecially.add(SkillsRw.FireProtection);
		SkillsFindSpecially.add(SkillsRw.PoisonProtection);
		SkillsFindSpecially.add(SkillsRw.HealthFog);

		PreLearnHelper.clear();
		PreLearnHelper.put(SkillsRw.VisionUp, null);
		PreLearnHelper.put(SkillsRw.SpeedUp, null);
		PreLearnHelper.put(SkillsRw.StrengthUp, null);
		PreLearnHelper.put(SkillsRw.SpeedUpFinal, new ArrayList<Skill>() {
			{add(SkillsRw.SpeedUp);}
		});
		
		PreLearnHelper.put(SkillsRw.StrengthUpFinal, new ArrayList<Skill>() {
			{add(SkillsRw.StrengthUp);}
		});
		
		PreLearnHelper.put(SkillsRw.BaseballShooting, new ArrayList<Skill>() {
			{add(SkillsRw.StrengthUp);}
		});
		
		PreLearnHelper.put(SkillsRw.BaseballFalling, new ArrayList<Skill>() {
			{add(SkillsRw.BaseballShooting);}
		});
		
		PreLearnHelper.put(SkillsRw.BaseballRolling, new ArrayList<Skill>() {
			{add(SkillsRw.BaseballShooting);}
		});
		
		PreLearnHelper.put(SkillsRw.BaseballExplosive, new ArrayList<Skill>() {
			{add(SkillsRw.BaseballShooting);}
		});
		
		PreLearnHelper.put(SkillsRw.BaseballThundering, new ArrayList<Skill>() {
			{add(SkillsRw.BaseballShooting);}
		});
		
		PreLearnHelper.put(SkillsRw.LouisJavelin, new ArrayList<Skill>() {
			{add(SkillsRw.BaseballShooting);}
		});
		
		PreLearnHelper.put(SkillsRw.BloodControl, new ArrayList<Skill>() {
			{add(SkillsRw.VisionUp);add(SkillsRw.SpeedUp);add(SkillsRw.StrengthUp);}
		});
		
		PreLearnHelper.put(SkillsRw.AuroraBlade, new ArrayList<Skill>() {
			{add(SkillsRw.BloodControl);}
		});
		
		PreLearnHelper.put(SkillsRw.FireProtection, null);
		PreLearnHelper.put(SkillsRw.PoisonProtection, null);

	}
	
	public static double getSkillPosX(Skill skill) {
		if (posHelper.containsKey(skill))
			return posHelper.get(skill).mx;
		return 0;
	}
	
	public static double getSkillPosY(Skill skill) {
		if (posHelper.containsKey(skill))
			return posHelper.get(skill).my;
		return 0;
	}
	
	public static boolean canFind(Skill skill, EntityPlayer player) {
		if (skill.hasSkill(player))
			return true;
		
		/*
		if (skill.mID == SkillsRw.BaseballShooting.mID) {
			if (!getPlayerCraftInfo(player, KCItems.baseball))
				return false;
		} else if (skill.mID == SkillsRw.RibbonTouch.mID) {
			if (!getPlayerCraftInfo(player, KCItems.miracle_ribbon))
				return false;
		} else if (skill.mID == SkillsRw.VibrationBlade.mID) {
			if (!getPlayerCraftInfo(player, KCItems.steel_blade))
				return false;
		}
		*/
		
		if (SkillsFindSpecially.contains(skill)) {
			if (!getFindInfo(player, skill)) {
				System.out.println(skill.mName + "x");
				return false;
			}
		}
		
		if (skill.canLearnSkill(player))
			return true;
		
		if (!PreLearnHelper.containsKey(skill))
			return false;
		
		if (PreLearnHelper.get(skill) == null)
			return true;
		
		for (Skill i : PreLearnHelper.get(skill)) {
			if (!i.hasSkill(player))
				return false;
		}
		
		return true;
	}
	
	public static ArrayList<Skill> getPreSkill(Skill skill) {
		if (!PreLearnHelper.containsKey(skill))
			return null;
		return PreLearnHelper.get(skill);
	}
	
	public static void setPlayerCraftInfo(EntityPlayer player, Item item, boolean flag) {
		String name = "hasCraftItem" + Item.getIdFromItem(item);
		player.getEntityData().setBoolean(name, flag);
		System.out.println("set");
	}
	
	public static boolean getPlayerCraftInfo(EntityPlayer player, Item item) {
		System.out.println(player.getEntityData().toString());
		String name = "hasCraftItem" + Item.getIdFromItem(item);
		if (!player.getEntityData().hasKey(name))
			return false;
		return player.getEntityData().getBoolean(name);
	}
	
	public static void findSkill(EntityPlayer player, Skill skill, boolean flag) {
		String name = "hasFind" + skill.mID;
		player.getEntityData().setBoolean(name, flag);
	}
	
	public static boolean getFindInfo(EntityPlayer player, Skill skill) {
		String name = "hasFind" + skill.mID;
		if (!player.getEntityData().hasKey(name))
			return false;
		return player.getEntityData().getBoolean(name);
	}

}