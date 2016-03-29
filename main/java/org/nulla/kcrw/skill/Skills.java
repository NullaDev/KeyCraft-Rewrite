package org.nulla.kcrw.skill;

import java.util.ArrayList;

/** 声明所有技能 */
public class Skills {
	
	public static final ArrayList<Skill> AllSkills = new ArrayList<Skill>();
	
	public static final SkillAntiFire SkillAntiFire = new SkillAntiFire("anti_fire", 1024, 16, 60 * 20);
	public static final SkillAntiPoison SkillAntiPoison = new SkillAntiPoison("anti_poison", 1024, 16, 60 * 20);
	public static final SkillAuroraAttack SkillAuroraAttack = new SkillAuroraAttack("aurora_attack", 512, 1, 1 * 20);
	public static final SkillAuroraBlade SkillAuroraBlade = new SkillAuroraBlade("aurora_blade", 1024, 16, 120 * 20);
	public static final SkillAuroraRepair SkillAuroraRepair = new SkillAuroraRepair("aurora_repair", 128, 1, 1 * 20);
	public static final SkillSpeedUp SkillSpeedUp = new SkillSpeedUp("speed_up", 1024, 16, 60 * 20);
	public static final SkillStrengthUp SkillStrengthUp = new SkillStrengthUp("strength_up", 1024, 16, 60 * 20);
	public static final SkillVisionUp SkillVisionUp = new SkillVisionUp("vision_up", 1024, 16, 60 * 20);

}
