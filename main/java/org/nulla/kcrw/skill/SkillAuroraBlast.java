package org.nulla.kcrw.skill;

import java.util.Random;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.effect.EntityAuroraBlast;
import org.nulla.nullacore.api.skill.Skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class SkillAuroraBlast extends Skill {
	
	public SkillAuroraBlast(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_blast.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.AuroraShield.getExperience(player) >= 128;
	}
	
	@Override
	public boolean onUse(final EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		player.worldObj.spawnEntityInWorld(new EntityAuroraBlast(player));
		
		// 随机事件只在服务器发生
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(20) + 1;
			modifyExperience(player, exp);
		}
		
		return true;
	}
}