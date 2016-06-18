package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.effect.EntityAuroraStorm;
import org.nulla.nullacore.api.skill.SkillPassive;

public class SkillAuroraStorm extends SkillPassive {

	public SkillAuroraStorm(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_storm.png");
	}
	
	@Override
	protected boolean onUse(EntityPlayer player) {
		boolean isOn = !getIsOn(player);
		if (isOn) {
			EntityAuroraStorm entity = new EntityAuroraStorm(player);
			player.worldObj.spawnEntityInWorld(entity);
			player.getEntityData().setInteger("AuroraStormID", entity.getEntityId());
		} else {
			if (getEntityStorm(player) != null)
				getEntityStorm(player).setDead();
		}
		setIsOn(player, isOn);
		return isOn;
	}
	
	public static EntityAuroraStorm getEntityStorm(EntityPlayer player) {
		int id = player.getEntityData().getInteger("AuroraStormID");
		if (id == 0)
			return null;
		return (EntityAuroraStorm)player.worldObj.getEntityByID(id); // 没有UUID取实体的方法？
	}
}
