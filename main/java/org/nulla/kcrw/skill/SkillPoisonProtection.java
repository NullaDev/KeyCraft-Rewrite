package org.nulla.kcrw.skill;

import java.util.Random;

import org.nulla.kcrw.KCResources;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.potion.KCPotions;
import org.nulla.nullacore.api.skill.Skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class SkillPoisonProtection extends Skill {
	
	public SkillPoisonProtection(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/poison_protection.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return true;
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		if (player.isPotionActive(KCPotions.poisonResistance)) {
			return false;
		}
		int time = 20 * 30 * 2048 / (2048 - getExperience(player));
		player.addPotionEffect(new PotionEffect(KCPotions.poisonResistance.id, time));
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