package org.nulla.nullacore.api.skill;

import java.util.ArrayList;

/** 存放所有技能，用于批量处理 */
public class Skills {
	
	public static final ArrayList<Skill> AllSkills = new ArrayList<Skill>();
	
	public static Skill getSkill(String skillName) {
		for (Skill i : AllSkills) {
			if (i.mName == skillName)
				return i;
		}
		return null;
	}
	
}
