package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;

public class TestSkill extends Skill {

	public TestSkill(String Name, int AuroraRequired, int AuroraCost, int cd) {
		super(Name, AuroraRequired, AuroraCost, cd);
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		System.out.println("Hello world!");
		return true;
	}

}
