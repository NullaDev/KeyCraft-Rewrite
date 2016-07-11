package org.nulla.kcrw.skill;

import java.util.Random;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.nullacore.api.skill.Skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/**
 * 这个技能和同一组的棒球系列技能不同，是我生编硬造出来的技能，而非出自《Little Busters!》。
 * 不过因为落地时能够爆炸造成AOE伤害，还不破坏地形，用起来意外的好用呢。也许这就是MC的世界观吧。
 * 不过我估计枣铃扔出的球也许真的能造成这种破坏力吧【笑。
 */
public class SkillBaseballExplosive extends Skill {
	
	public SkillBaseballExplosive(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/explosive_baseball.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.BaseballShooting.getExperience(player) >= 512;
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

		player.worldObj.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F));

		player.worldObj.spawnEntityInWorld(new EntityBaseball(player.worldObj, player, "explosion"));
		
		return true;
	}
}