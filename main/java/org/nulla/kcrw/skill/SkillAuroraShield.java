package org.nulla.kcrw.skill;

import java.util.Random;
import java.util.UUID;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.effect.EntityAuroraShield;
import org.nulla.nullacore.api.skill.Skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkillAuroraShield extends Skill {
	
	public static String[] damageResistible = {"inFire", "onFire", "lava", "arrow", "mob", "player", "thrown"};
	
	public static boolean isResistible(String damageType) {
		for (String i : damageResistible) {
			if (damageType.equals(i))
				return true;
		}
		return false;
	}

	public SkillAuroraShield(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_shield.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.BloodControl.getExperience(player) >= 128;
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		EntityAuroraShield entity = new EntityAuroraShield(player.worldObj, player, 16 * 2048 / (2048 - getExperience(player)), 60 * 20);
		player.worldObj.spawnEntityInWorld(entity);
		player.getEntityData().setInteger("AuroraShieldID", entity.getEntityId());
		
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(10) + 1;
			modifyExperience(player, exp);
		}
		
		return true;
	}
	
	public static EntityAuroraShield getEntityShield(EntityPlayer player) {
		int id = player.getEntityData().getInteger("AuroraShieldID");
		if (id == 0)
			return null;
		return (EntityAuroraShield)player.worldObj.getEntityByID(id); // 没有UUID取实体的方法？
	}

}
