package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class SkillPassive extends Skill {

	public SkillPassive(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}
	
	public boolean getIsOn(EntityPlayer player)
	{
		return player.getEntityData().getBoolean("SkillIsOn" + mName);
	}
	
	/** 无同步 */
	protected void setIsOn(EntityPlayer player, boolean isOn)
	{
		player.getEntityData().setBoolean("SkillIsOn" + mName, isOn);
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		boolean isOn = !getIsOn(player);
		setIsOn(player, isOn);
		return isOn;
	}

}
