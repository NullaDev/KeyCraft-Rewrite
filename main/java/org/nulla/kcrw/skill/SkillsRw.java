package org.nulla.kcrw.skill;

import java.util.ArrayList;

import org.nulla.nullacore.api.skill.Skill;

/** 声明所有技能 */
public class SkillsRw {
	
	public static SkillAntiFire AntiFire;
	public static SkillAntiPoison AntiPoison;
	public static SkillAuroraAttack AuroraAttack;
	public static SkillAuroraBlade AuroraBlade;
	public static SkillAuroraBlast AuroraBlast;
	public static SkillAuroraRegeneration AuroraRegeneration;
	public static SkillAuroraRepair AuroraRepair;
	public static SkillAuroraShield AuroraShield;
	public static SkillBaseballExplosive BaseballExplosive;
	public static SkillBaseballRolling BaseballRolling;
	public static SkillBaseballThundering BaseballThundering;
	public static SkillSpeedUp SpeedUp;
	public static SkillStrengthUp StrengthUp;
	public static SkillVibrationWave VibrationWave;
	public static SkillVisionUp VisionUp;

	public static void initSkills() {
		AntiFire = new SkillAntiFire("anti_fire", 1024, 16, 60 * 20);
		AntiPoison = new SkillAntiPoison("anti_poison", 1024, 16, 60 * 20);
		AuroraAttack = new SkillAuroraAttack("aurora_attack", 512, 1, 1 * 20);
		AuroraBlade = new SkillAuroraBlade("aurora_blade", 1024, 16, 120 * 20);
		AuroraBlast = new SkillAuroraBlast("aurora_blast", 1024, 32, 3 * 20);
		AuroraRegeneration = new SkillAuroraRegeneration("aurora_blast", 1024, 0, 1 * 20);
		AuroraRepair = new SkillAuroraRepair("aurora_repair", 128, 1, 1 * 20);
		AuroraShield = new SkillAuroraShield("aurora_shield", 1024, 20, 60 * 20);
		BaseballExplosive = new SkillBaseballExplosive("explosive_baseball", 128, 2, 2 * 20);
		BaseballRolling = new SkillBaseballRolling("rolling_baseball", 128, 2, 2 * 20);
		BaseballThundering = new SkillBaseballThundering("thundering_baseball", 128, 2, 2 * 20);
		SpeedUp = new SkillSpeedUp("speed_up", 1024, 16, 60 * 20);
		StrengthUp = new SkillStrengthUp("strength_up", 1024, 16, 60 * 20);
		VibrationWave = new SkillVibrationWave("vibration_wave", 1024, 8, 10 * 20);
		VisionUp = new SkillVisionUp("vision_up", 1024, 16, 60 * 20);
	}
	
}
