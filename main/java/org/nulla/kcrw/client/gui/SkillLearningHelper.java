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
	
	private static HashMap<Skill, CO2D> skillPosHelper = new HashMap<Skill, CO2D>();
	
	private static HashMap<Skill, ArrayList<Skill>> PreLearnHelper = new HashMap<Skill, ArrayList<Skill>>();
	
	private static ArrayList<Skill> SkillsFindSpecially = new ArrayList<Skill>();
	
	public static void init() {
		skillPosHelper.clear();
		skillPosHelper.put(SkillsRw.VisionUp, new CO2D(0.25, 0.5));
		skillPosHelper.put(SkillsRw.SpeedUp, new CO2D(0.25, 0.4));
		skillPosHelper.put(SkillsRw.StrengthUp, new CO2D(0.25, 0.6));
		skillPosHelper.put(SkillsRw.SpeedUpFinal, new CO2D(0.2, 0.4));
		skillPosHelper.put(SkillsRw.StrengthUpFinal, new CO2D(0.2, 0.6));
		skillPosHelper.put(SkillsRw.BaseballShooting, new CO2D(0.3, 0.65));
		skillPosHelper.put(SkillsRw.BaseballFalling, new CO2D(0.35, 0.6));
		skillPosHelper.put(SkillsRw.BaseballRolling, new CO2D(0.35, 0.7));
		skillPosHelper.put(SkillsRw.BaseballExplosive, new CO2D(0.3, 0.75));
		skillPosHelper.put(SkillsRw.BaseballThundering, new CO2D(0.25, 0.75));
		skillPosHelper.put(SkillsRw.LouisJavelin, new CO2D(0.2, 0.7));
		skillPosHelper.put(SkillsRw.BloodControl, new CO2D(0.35, 0.5));
		skillPosHelper.put(SkillsRw.AuroraBlade, new CO2D(0.4, 0.6));
		skillPosHelper.put(SkillsRw.AuroraAttack, new CO2D(0.4, 0.5));
		skillPosHelper.put(SkillsRw.FireProtection, new CO2D(0.4, 0.4));
		skillPosHelper.put(SkillsRw.FireImmunity, new CO2D(0.35, 0.4));
		skillPosHelper.put(SkillsRw.PoisonProtection, new CO2D(0.5, 0.4));
		skillPosHelper.put(SkillsRw.PoisonImmunity, new CO2D(0.55, 0.4));
		skillPosHelper.put(SkillsRw.AuroraShield, new CO2D(0.45, 0.5));
		skillPosHelper.put(SkillsRw.AuroraBlast, new CO2D(0.45, 0.6));
		skillPosHelper.put(SkillsRw.AuroraStorm, new CO2D(0.45, 0.7));
		skillPosHelper.put(SkillsRw.AuroraRegeneration, new CO2D(0.5, 0.5));
		skillPosHelper.put(SkillsRw.AuroraRepair, new CO2D(0.55, 0.5));
		skillPosHelper.put(SkillsRw.RibbonTouch, new CO2D(0.8, 0.4));
		skillPosHelper.put(SkillsRw.KagariCannon, new CO2D(0.75, 0.4));
		skillPosHelper.put(SkillsRw.KagariStrafe, new CO2D(0.8, 0.5));
		skillPosHelper.put(SkillsRw.VibrationBlade, new CO2D(0.8, 0.75));
		skillPosHelper.put(SkillsRw.VibrationWave, new CO2D(0.8, 0.65));
		skillPosHelper.put(SkillsRw.HealthFog, new CO2D(0.6, 0.65));

		SkillsFindSpecially.clear();
		SkillsFindSpecially.add(SkillsRw.BaseballShooting);
		SkillsFindSpecially.add(SkillsRw.FireProtection);
		SkillsFindSpecially.add(SkillsRw.HealthFog);
		SkillsFindSpecially.add(SkillsRw.LouisJavelin);
		SkillsFindSpecially.add(SkillsRw.PoisonProtection);
		SkillsFindSpecially.add(SkillsRw.RibbonTouch);

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
			{add(SkillsRw.AuroraAttack);}
		});
		
		PreLearnHelper.put(SkillsRw.AuroraAttack, new ArrayList<Skill>() {
			{add(SkillsRw.BloodControl);}
		});
		
		PreLearnHelper.put(SkillsRw.FireProtection, null);
		PreLearnHelper.put(SkillsRw.PoisonProtection, null);
		PreLearnHelper.put(SkillsRw.RibbonTouch, null);
		PreLearnHelper.put(SkillsRw.VibrationBlade, null);
		
		PreLearnHelper.put(SkillsRw.AuroraShield, new ArrayList<Skill>() {
			{add(SkillsRw.AuroraAttack);add(SkillsRw.FireProtection);add(SkillsRw.PoisonProtection);}
		});
		
		PreLearnHelper.put(SkillsRw.AuroraBlast, new ArrayList<Skill>() {
			{add(SkillsRw.AuroraShield);}
		});
		
		PreLearnHelper.put(SkillsRw.AuroraRegeneration, new ArrayList<Skill>() {
			{add(SkillsRw.AuroraShield);}
		});
		
		PreLearnHelper.put(SkillsRw.AuroraRepair, new ArrayList<Skill>() {
			{add(SkillsRw.AuroraRegeneration);}
		});
		
		PreLearnHelper.put(SkillsRw.AuroraStorm, new ArrayList<Skill>() {
			{add(SkillsRw.AuroraBlast);}
		});
		
		PreLearnHelper.put(SkillsRw.FireImmunity, new ArrayList<Skill>() {
			{add(SkillsRw.FireProtection);}
		});
		
		PreLearnHelper.put(SkillsRw.PoisonImmunity, new ArrayList<Skill>() {
			{add(SkillsRw.PoisonProtection);}
		});
		
		PreLearnHelper.put(SkillsRw.KagariCannon, new ArrayList<Skill>() {
			{add(SkillsRw.RibbonTouch);}
		});
		
		PreLearnHelper.put(SkillsRw.KagariStrafe, new ArrayList<Skill>() {
			{add(SkillsRw.RibbonTouch);}
		});
		
		PreLearnHelper.put(SkillsRw.VibrationBlade, new ArrayList<Skill>() {
			{add(SkillsRw.VibrationWave);}
		});

	}
	
	public static double getSkillPosX(Skill skill) {
		if (skillPosHelper.containsKey(skill))
			return skillPosHelper.get(skill).mx;
		return 0;
	}
	
	public static double getSkillPosY(Skill skill) {
		if (skillPosHelper.containsKey(skill))
			return skillPosHelper.get(skill).my;
		return 0;
	}
	
	public static boolean canFind(Skill skill, EntityPlayer player) {
		if (skill.hasSkill(player))
			return true;
		
		if (SkillsFindSpecially.contains(skill)) {
			if (!getFindInfo(player, skill))
				return false;
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
	
	public static void findSkill(EntityPlayer player, Skill skill, boolean flag) {
		String name = "hasFind" + skill.mID;
		player.getEntityData().setBoolean(name, flag);
	}
	
	public static boolean getFindInfo(EntityPlayer player, Skill skill) {
		if (player.worldObj.isRemote) {
			System.out.println("client");
		} else {
			System.out.println("server");
		}
		String name = "hasFind" + skill.mID;
		if (!player.getEntityData().hasKey(name))
			return false;
		return player.getEntityData().getBoolean(name);
	}

}