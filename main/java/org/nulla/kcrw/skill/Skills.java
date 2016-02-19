package org.nulla.kcrw.skill;

import java.util.ArrayList;

/** 声明所有技能 */
public class Skills
{
	public static final ArrayList<Skill> AllSkills = new ArrayList<Skill>();
	
	public static final TestSkill Test = new TestSkill("Test", 1, 1, 10 * 20);
	
}
