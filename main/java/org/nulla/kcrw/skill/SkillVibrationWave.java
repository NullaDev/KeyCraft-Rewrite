package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.EntityVibrationWave;
import org.nulla.nullacore.api.skill.Skill;

/**
 * 这个技能是妥妥的罚抄原作自带，出处是《Rewrite Harvest festa!》的班长群攻技能“分子振動波”。
 * 既然是群攻又是波索性就把波直接渲染出来啦~然而问题是经常会出现不同步问题，有时候还会被技能重复打击两次。。
 * 然而这些都不是最主要的，更严重的问题是，md为什么波是方的啊喂！！
 */
public class SkillVibrationWave extends Skill {

	public SkillVibrationWave(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/vibration_wave.png");
	}

	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.VibrationBlade.getExperience(player) >= 512;
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
			int exp = rand.nextInt(20) + 1;
			modifyExperience(player, exp);
		}
		
		if (!player.worldObj.isRemote) {
			float r = 4.0f * 2048.0f / (2048.0f - this.getExperience(player));
			for (int x = (int) (player.posX - r); x <= (int) (player.posX + r); x++) {
				for (int y = (int) (player.posY - 1); y <= (int) (player.posY + 1); y++) {
					for (int z = (int) (player.posZ - r); z <= (int) (player.posZ + r); z++) {
						if(player.worldObj.getBlock(x, y, z).getMaterial() == Material.glass) {
							player.worldObj.setBlockToAir(x, y, z);
						}
					}
				}
			}
		}
		
		return true;
	}

}
