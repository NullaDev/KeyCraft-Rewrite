package org.nulla.kcrw.skill;

import java.util.Random;

import org.nulla.kcrw.KCMusicHelper;
import org.nulla.kcrw.KCResources;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SkillSpeedUp extends Skill {
	
	public SkillSpeedUp(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		int time = 20 * 30 * 2048 / (2048 - getExperience(player));
		player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, time, 1));
		// 随机事件只在服务器发生
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(10) + 1;
			modifyExperience(player, exp);
		}
		KCMusicHelper.playSound(KCResources.sound_aurora);
		return true;
	}
}