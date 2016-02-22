package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class SkillStrengthUp extends Skill {
	
	public SkillStrengthUp(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 30 * 20, 1));
		return true;
	}
}