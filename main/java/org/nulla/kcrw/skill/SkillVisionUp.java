package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.*;

public class SkillVisionUp extends Skill {
	
	public SkillVisionUp(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		int time = 20 * 30 * 2048 / (2048 - this.getExperience(player));
		player.addPotionEffect(new PotionEffect(Potion.nightVision.id, time));
		Random rand = new Random();
		int exp = rand.nextInt(10) + 1;
		this.modifyExperience(player, exp);
		return true;
	}
}