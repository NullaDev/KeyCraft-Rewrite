package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KCResources;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.potion.Potions;

public class SkillAntiPoison extends Skill {
	
	public SkillAntiPoison(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/anti_poison.png");
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		int time = 20 * 30 * 2048 / (2048 - getExperience(player));
		player.addPotionEffect(new PotionEffect(Potions.poisonResistance.id, time));
		// 随机事件只在服务器发生
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(10) + 1;
			modifyExperience(player, exp);
		}
		player.worldObj.playSoundAtEntity(player, KCResources.sound_aurora.toString(), 1.0f, 1.0f);
		return true;
	}
}