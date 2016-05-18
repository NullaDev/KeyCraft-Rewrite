package org.nulla.kcrw.skill;

import java.util.ArrayList;

import org.nulla.nullacore.api.skill.Skill;

/** 声明所有技能 */
public class Skills {
	
	public static SkillAntiFire SkillAntiFire ;
	public static SkillAntiPoison SkillAntiPoison;
	public static SkillAuroraAttack SkillAuroraAttack;
	public static SkillAuroraBlade SkillAuroraBlade;
	public static SkillAuroraBlast SkillAuroraBlast;
	public static SkillAuroraRepair SkillAuroraRepair;
	public static SkillBaseballExplosive SkillBaseballExplosive;
	public static SkillBaseballRolling SkillBaseballRolling;
	public static SkillSpeedUp SkillSpeedUp;
	public static SkillStrengthUp SkillStrengthUp;
	public static SkillVisionUp SkillVisionUp;

	public static void initSkills() {
		SkillAntiFire SkillAntiFire = new SkillAntiFire("anti_fire", 1024, 16, 60 * 20);
		SkillAntiPoison SkillAntiPoison = new SkillAntiPoison("anti_poison", 1024, 16, 60 * 20);
		SkillAuroraAttack SkillAuroraAttack = new SkillAuroraAttack("aurora_attack", 512, 1, 1 * 20);
		SkillAuroraBlade SkillAuroraBlade = new SkillAuroraBlade("aurora_blade", 1024, 16, 120 * 20);
		SkillAuroraBlast SkillAuroraBlast = new SkillAuroraBlast("aurora_blast", 1024, 32, 3 * 20);
		SkillAuroraRepair SkillAuroraRepair = new SkillAuroraRepair("aurora_repair", 128, 1, 1 * 20);
		SkillBaseballExplosive SkillBaseballExplosive = new SkillBaseballExplosive("explosive_baseball", 128, 2, 2 * 20);
		SkillBaseballRolling SkillBaseballRolling = new SkillBaseballRolling("rolling_baseball", 128, 2, 2 * 20);
		SkillSpeedUp SkillSpeedUp = new SkillSpeedUp("speed_up", 1024, 16, 60 * 20);
		SkillStrengthUp SkillStrengthUp = new SkillStrengthUp("strength_up", 1024, 16, 60 * 20);
		SkillVisionUp SkillVisionUp = new SkillVisionUp("vision_up", 1024, 16, 60 * 20);
	}
	
}
