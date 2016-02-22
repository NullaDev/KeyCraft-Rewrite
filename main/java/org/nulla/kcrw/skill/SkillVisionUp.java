package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.*;

public class SkillVisionUp extends Skill {
	
	public SkillVisionUp(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		player.addPotionEffect(new PotionEffect(Potion.nightVision.id, 30 * 20, 1));
		return true;
	}
}