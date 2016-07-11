package org.nulla.kcrw.skill;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;

import org.nulla.nullacore.api.skill.Skill;

/** 声明所有技能 */
public class SkillsRw {
	
	public static SkillAuroraAttack AuroraAttack;
	public static SkillAuroraBlade AuroraBlade;
	public static SkillAuroraBlast AuroraBlast;
	public static SkillAuroraRegeneration AuroraRegeneration;
	public static SkillAuroraRepair AuroraRepair;
	public static SkillAuroraShield AuroraShield;
	public static SkillAuroraStorm AuroraStorm;
	public static SkillBaseballExplosive BaseballExplosive;
	public static SkillBaseballFalling BaseballFalling;
	public static SkillBaseballRolling BaseballRolling;
	public static SkillBaseballShooting BaseballShooting;
	public static SkillBaseballThundering BaseballThundering;
	public static SkillBloodControl BloodControl;
	public static SkillFireImmunity FireImmunity;
	public static SkillFireProtection FireProtection;
	public static SkillHealthFog HealthFog;
	public static SkillKagariCannon KagariCannon;
	public static SkillKagariStrafe KagariStrafe;
	public static SkillLouisJavelin LouisJavelin;
	public static SkillPoisonImmunity PoisonImmunity;
	public static SkillPoisonProtection PoisonProtection;
	public static SkillRibbonTouch RibbonTouch;
	public static SkillSpeedUp SpeedUp;
	public static SkillSpeedUpFinal SpeedUpFinal;
	public static SkillStrengthUp StrengthUp;
	public static SkillStrengthUpFinal StrengthUpFinal;
	public static SkillVibrationBlade VibrationBlade;
	public static SkillVibrationWave VibrationWave;
	public static SkillVisionUp VisionUp;

	public static void initSkills() {
		AuroraAttack = 			new SkillAuroraAttack("aurora_attack", 512, 1, 1 * 20);
		AuroraBlade = 			new SkillAuroraBlade("aurora_blade", 1024, 16, 1 * 20);
		AuroraBlast = 			new SkillAuroraBlast("aurora_blast", 1024, 32, 3 * 20);
		AuroraRegeneration = 	new SkillAuroraRegeneration("aurora_blast", 1024, 0, 1 * 20);
		AuroraRepair = 			new SkillAuroraRepair("aurora_repair", 128, 1, 1 * 20);
		AuroraShield = 			new SkillAuroraShield("aurora_shield", 1024, 20, 60 * 20);
		AuroraStorm = 			new SkillAuroraStorm("aurora_storm", 1024, 1, 1);
		BaseballExplosive = 	new SkillBaseballExplosive("explosive_baseball", 128, 2, 2 * 20);
		BaseballFalling = 		new SkillBaseballFalling("falling_baseball", 128, 2, 2 * 20);
		BaseballRolling = 		new SkillBaseballRolling("rolling_baseball", 128, 2, 2 * 20);
		BaseballShooting = 		new SkillBaseballShooting("shooting_baseball", 128, 2, 2 * 20);
		BaseballThundering = 	new SkillBaseballThundering("thundering_baseball", 128, 2, 2 * 20);
		BloodControl = 			new SkillBloodControl("blood_control", 512, 8, 10 * 20);
		FireImmunity = 			new SkillFireImmunity("fire_immunity", 1024, 0, 0);
		FireProtection = 		new SkillFireProtection("fire_protection", 1024, 16, 60 * 20);
		HealthFog = 			new SkillHealthFog("health_fog", 512, 8, 3 * 20);
		KagariCannon = 			new SkillKagariCannon("kagari_cannon", 1024, 16, 5 * 20);
		KagariStrafe = 			new SkillKagariStrafe("kagari_strafe", 1024, 16, 5 * 20);
		LouisJavelin = 			new SkillLouisJavelin("louis_javelin", 512, 8, 3 * 20);
		PoisonImmunity = 		new SkillPoisonImmunity("poison_immunity", 1024, 0, 0);
		PoisonProtection = 		new SkillPoisonProtection("poison_protection", 1024, 16, 60 * 20);
		RibbonTouch = 			new SkillRibbonTouch("ribbon_touch", 1024, 16, 5 * 20);
		SpeedUp = 				new SkillSpeedUp("speed_up", 1024, 16, 60 * 20);
		SpeedUpFinal = 			new SkillSpeedUpFinal("speed_up_final", 1024, 0, 0);
		StrengthUp = 			new SkillStrengthUp("strength_up", 1024, 16, 60 * 20);
		StrengthUpFinal = 		new SkillStrengthUpFinal("strength_up_final", 1024, 0, 0);
		VibrationBlade = 		new SkillVibrationBlade("vibration_blade", 256, 8, 3 * 20);
		VibrationWave = 		new SkillVibrationWave("vibration_wave", 1024, 8, 10 * 20);
		VisionUp = 				new SkillVisionUp("vision_up", 1024, 16, 60 * 20);
	}
	
	/** 只对某些技能管用。 */
	public static void setLearnable(Skill skill, EntityPlayer player, boolean flag) {
		player.getEntityData().setBoolean("canLearn" + skill.mName, flag);
	}
	
}
