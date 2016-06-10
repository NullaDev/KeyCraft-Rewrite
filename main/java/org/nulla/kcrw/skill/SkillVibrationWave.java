package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.EntityVibrationWave;
import org.nulla.nullacore.api.skill.Skill;

public class SkillVibrationWave extends Skill {

	public SkillVibrationWave(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/vibration_wave.png");
	}

	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return true;
	}
	
	@Override
	public boolean onUse(final EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		player.worldObj.spawnEntityInWorld(new EntityVibrationWave(player));
		
		// 随机事件只在服务器发生
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(5) + 1;
			modifyExperience(player, exp);
		}
		
		return true;
	}

}
