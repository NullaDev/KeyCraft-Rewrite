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
 * 这个技能的中文翻译叫做“蝴蝶喵球”，出自《Little Busters!》游戏中枣铃的扔球必杀技。
 * “蝴蝶球”这个词并非原创，而是出自同名的棒球术语，指的是棒球在投手的特殊投掷方法下，因为空气动力学作用而显得轨迹飘忽不定。
 * 因为这个球在原作游戏里我就经常接不住。。所以在满满的恶意下我给这个棒球加了一个连我都控制不了的正弦轨迹。。
 * 这个球我查了一下wiki，似乎是叫做“スライニャー”，但是拿不太准呢~
 */
public class SkillBaseballRolling extends Skill {
	
	public SkillBaseballRolling(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/rolling_baseball.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.BaseballShooting.getExperience(player) >= 256;
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
		
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(10) + 1;
			modifyExperience(player, exp);
		}

		player.worldObj.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F));

		player.worldObj.spawnEntityInWorld(new EntityBaseball(player.worldObj, player, "rolling"));
		
		return true;
	}
}