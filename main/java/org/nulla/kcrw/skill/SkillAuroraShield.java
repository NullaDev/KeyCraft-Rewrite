package org.nulla.kcrw.skill;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.effect.EntityAuroraShield;
import org.nulla.nullacore.api.skill.Skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkillAuroraShield extends Skill {

	public SkillAuroraShield(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_shield.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return true;
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		player.worldObj.spawnEntityInWorld(new EntityAuroraShield(player.worldObj, player));
		
		return true;
	}

}