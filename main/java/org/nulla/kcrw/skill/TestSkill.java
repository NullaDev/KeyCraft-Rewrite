package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;

/** 测试用的技能，使用后输出Hello world */
public class TestSkill extends Skill
{
	public TestSkill(String name, int auroraRequired, int auroraCost, int cd)
	{
		super(name, auroraRequired, auroraCost, cd);
	}
	
	@Override
	public boolean onUse(EntityPlayer player)
	{
		System.out.println("Hello world!");
		return true;
	}

}
