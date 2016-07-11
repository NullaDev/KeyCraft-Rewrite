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
 * 这个技能的中文翻译叫做“下坠球”，也是出自《Little Busters!》游戏中枣铃的扔球必杀技。
 * “下坠球”这个词其实是出自棒球术语“シンカー”，指的是一种快速下坠的棒球，比较难接。
 * 所以我（为了省事）直接让棒球的重力加速度随时间递增了【笑。
 */
public class SkillBaseballFalling extends Skill {
	
	public SkillBaseballFalling(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/falling_baseball.png");
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

		player.worldObj.spawnEntityInWorld(new EntityBaseball(player.worldObj, player, "falling"));
		
		return true;
	}
}