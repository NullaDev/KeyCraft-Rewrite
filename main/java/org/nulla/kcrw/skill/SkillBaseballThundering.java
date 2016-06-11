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
 * 这个技能原本叫做“ライジングニャットボール”，出自《Little Busters!》游戏中枣铃的扔球必杀技。
 * 如果直接翻译成中文的话的话，应该叫做ライジング（升）ニャット（猫）ボール（球），其实和雷没有什么关系啦~
 * 然而由于澄空汉化组的误翻，导致“雷神喵喵球”这个翻译反而成了知名度最高的称呼。。
 * 所以大概就这样吧。
 */
public class SkillBaseballThundering extends Skill {
	
	public SkillBaseballThundering(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/thundering_baseball.png");
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
		
		if (!player.capabilities.isCreativeMode) {
			if (KCUtils.getNumberOfItemInPlayer(player, KCItems.baseball) >= 1) {
				KCUtils.minusNumberOfItemInPlayer(player, KCItems.baseball, 1);
			} else {
				return false;
			}
		}

		player.worldObj.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F));

		player.worldObj.spawnEntityInWorld(new EntityBaseball(player.worldObj, player, "thundering"));
		
		return true;
	}
}