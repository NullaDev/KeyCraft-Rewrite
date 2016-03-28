package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SkillPassive extends Skill {
	
	public static boolean isOn = true;

	public SkillPassive(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}
	
	@Override
	public boolean isPassive() {
		return true;
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		return isOn;
	}

}
