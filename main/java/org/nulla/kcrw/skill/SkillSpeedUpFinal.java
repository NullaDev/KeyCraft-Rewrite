package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.nullacore.api.skill.SkillPassive;

public class SkillSpeedUpFinal extends SkillPassive {

	public SkillSpeedUpFinal(String name, int auroraRequired, int auroraCost,
			int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/speed_up_final.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.SpeedUp.getExperience(player) >= 1024;
	}
	
	public boolean isOpen(EntityPlayer player) {
		final String name = "IsOpen" + this.mName;
		final NBTTagCompound nbt = player.getEntityData();
		if (!nbt.hasKey(name))
			return false;
		return nbt.getBoolean(name);
	}
	
	public void setOpen(EntityPlayer player, boolean open) {
		final String name = "IsOpen" + this.mName;
		final NBTTagCompound nbt = player.getEntityData();
		nbt.setBoolean(name, open);
	}
	
}
