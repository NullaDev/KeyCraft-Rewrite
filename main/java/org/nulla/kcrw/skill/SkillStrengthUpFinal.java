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
	
	public UUID getAttributeUUID(EntityPlayer player) {
		final String name = "AttributeUUID" + this.mName;
		final NBTTagCompound nbt = player.getEntityData();
		if (!nbt.hasKey(name))
			return null;
		if (nbt.getString(name) == "")
			return null;
		return UUID.fromString(nbt.getString(name));
	}
	
	public void setAttributeUUID(EntityPlayer player, UUID uuid) {
		final String name = "AttributeUUID" + this.mName;
		final NBTTagCompound nbt = player.getEntityData();
		if (uuid == null) {
			nbt.removeTag(name);
		} else {
			nbt.setString(name, uuid.toString());
		}
	}
	
}
