package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;

public class SkillPassive extends Skill {

	public SkillPassive(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}

	@Override
	protected boolean onUse(EntityPlayer player) {
		return false;
	}
	
	@Override
	public boolean isPassive() {
		return true;
	}
	
	public boolean onTrigger(EntityPlayer player) {
		return false;
	}

}
