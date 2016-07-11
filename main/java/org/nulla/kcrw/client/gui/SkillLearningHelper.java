package org.nulla.kcrw.client.gui;

import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

import org.nulla.kcrw.skill.SkillsRw;
import org.nulla.nullacore.api.CO2D;
import org.nulla.nullacore.api.skill.Skill;

public class SkillLearningHelper {
	
	private static HashMap<Skill, CO2D> posHelper = new HashMap<Skill, CO2D>();
	
	private static HashMap<Skill, ArrayList<Skill>> PreLearnHelper = new HashMap<Skill, ArrayList<Skill>>();

	
	public static void init() {
		posHelper.clear();
		posHelper.put(SkillsRw.VisionUp, new CO2D(0.15, 0.5));
		posHelper.put(SkillsRw.SpeedUp, new CO2D(0.2, 0.5));
		posHelper.put(SkillsRw.StrengthUp, new CO2D(0.25, 0.5));
		
		PreLearnHelper.put(SkillsRw.SpeedUpFinal, new ArrayList<Skill>() {
			{add(SkillsRw.SpeedUp);}
		});
		PreLearnHelper.put(SkillsRw.StrengthUpFinal, new ArrayList<Skill>() {
			{add(SkillsRw.StrengthUp);}
		});

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
		if (skill.canLearnSkill(player))
			return true;
		if (!PreLearnHelper.containsKey(skill))
			return false;
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

}