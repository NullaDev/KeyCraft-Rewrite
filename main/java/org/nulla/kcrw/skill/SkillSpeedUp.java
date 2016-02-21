package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SkillSpeedUp extends Skill {
	
	public SkillSpeedUp(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		System.out.println("使用技能加速");
		player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 30 * 20, 1));
		return true;
	}
}