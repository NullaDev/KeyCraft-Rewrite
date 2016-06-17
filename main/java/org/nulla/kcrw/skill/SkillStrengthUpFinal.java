package org.nulla.kcrw.skill;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.nullacore.api.skill.SkillPassive;

public class SkillStrengthUpFinal extends SkillPassive {

	public SkillStrengthUpFinal(String name, int auroraRequired, int auroraCost,
			int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/strength_up_final.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.StrengthUp.getExperience(player) >= 1024;
	}
	
	public boolean getAttributeAdd(EntityPlayer player) {
		final String name = "AttributeHassAdd" + this.mName;
		final NBTTagCompound nbt = player.getEntityData();
		if (!nbt.hasKey(name))
			return false;
		return nbt.getBoolean(name);
	}
	
	public void setAttributeAdd(EntityPlayer player, boolean add) {
		final String name = "AttributeHassAdd" + this.mName;
		final NBTTagCompound nbt = player.getEntityData();
		nbt.setBoolean(name, add);
	}
	
}
