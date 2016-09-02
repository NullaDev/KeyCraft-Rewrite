package org.nulla.kcrw.skill;

import java.util.Random;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.SkillUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

/**
 * 将Aurora在体外凝结成物理上存在的东西，灰太狼招牌技能。
 * 由于新加入了Aurora极光爪，现在的技能名字叫Aurora“solidification（固化）”，而不是以前的Aurora“Blade”。
 * 顺便说一句，Aurora“Blade”应该翻译成极光“刃”而不是“剑”。。
 */
public class SkillAuroraSolidification extends Skill {
	
	public SkillAuroraSolidification(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_solidification.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.AuroraAttack.getExperience(player) >= 128;
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		ItemStack held = player.getHeldItem();
		if (held == null) {
			ItemStack stack = getExperience(player) >= 512 ? new ItemStack(KCItems.aurora_blade, 1) : new ItemStack(KCItems.aurora_claw, 1);
			player.setCurrentItemOrArmor(0, stack);
			if (!player.worldObj.isRemote) {
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.callblade")));
    		} else {
    			Random rand = new Random();
    			int exp = rand.nextInt(20) + 1;
    			modifyExperience(player, exp);
    		}
			return true;
    	}
		return false;
	}
	
	/** 当剑爆了的时候，调用此方法。 */
	public static void onBladeDead(EntityPlayer player, double proportion) {
		if (!player.worldObj.isRemote) {
			if (proportion < 0.99D)
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.recyclerate") + String.format("%.3f", proportion)));
			else
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.breakblade")));
		}
		int time = (int)(10 * 20 * proportion);
		player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, time, 1));
		player.addPotionEffect(new PotionEffect(Potion.confusion.id, time));
		player.addPotionEffect(new PotionEffect(Potion.weakness.id, time, 1));
	}
	
	/** 当剑完全爆了的时候，调用此方法。 */
	public static void breakBlade(EntityPlayer player) {
		onBladeDead(player, 1D);
    }
	
}