package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KCResources;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.nullacore.api.skill.Skill;

public class SkillBloodControl extends Skill {
	
	public SkillBloodControl(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/blood_control.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.SpeedUp.getExperience(player) >= 128
			&& SkillsRw.StrengthUp.getExperience(player) >= 128
			&& SkillsRw.VisionUp.getExperience(player) >= 128;
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		if (player.isPotionActive(Potion.regeneration)) {
			return false;
		}
		int time = 20 * 2 * 2048 / (2048 - getExperience(player));
		player.addPotionEffect(new PotionEffect(Potion.regeneration.id, time, 1));
		player.heal(2F * 2048 / (2048 - getExperience(player)));
		// 随机事件只在服务器发生
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(10) + 1;
			modifyExperience(player, exp);
		}
		player.worldObj.playSoundAtEntity(player, KCResources.sound_aurora.toString(), 1.0f, 1.0f);
		return true;
	}
}