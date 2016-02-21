package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SkillSpeedUp extends Skill {
	
	public SkillSpeedUp() {
		super("speed_up", 1024, 16, 60 * 20);
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
		return true;
	}
}