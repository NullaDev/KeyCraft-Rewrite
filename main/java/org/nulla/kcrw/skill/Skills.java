package org.nulla.kcrw.skill;

import java.util.ArrayList;

/** 声明所有技能 */
public class Skills
{
	public static final ArrayList<Skill> AllSkills = new ArrayList<Skill>();
	
	public static final SkillSpeedUp SkillSpeedUp = new SkillSpeedUp("speed_up", 1024, 16, 60 * 20);
	
}
