package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.nullacore.api.skill.Skill;

/**
 * 妈的就是喷射球啦。。就是速度灰常快的球而且很好中啦。。具体我他妈懒得考证了。。
 */
public class SkillBaseballShooting extends Skill {
	
	public SkillBaseballShooting(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/shooting_baseball.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.StrengthUp.getExperience(player) >= 128;
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		if (!player.capabilities.isCreativeMode) {
			if (KCUtils.getNumberOfItemInPlayer(player, KCItems.baseball) >= 1) {
				KCUtils.minusNumberOfItemInPlayer(player, KCItems.baseball, 1);
			} else {
				return false;
			}
		}
		
		// 随机事件只在服务器发生
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(1000) + 1;
			modifyExperience(player, exp);
		}

		player.worldObj.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F));

		player.worldObj.spawnEntityInWorld(new EntityBaseball(player.worldObj, player, "shooting"));
		
		return true;
	}
}