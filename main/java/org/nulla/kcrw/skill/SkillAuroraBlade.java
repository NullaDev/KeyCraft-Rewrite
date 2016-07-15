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

public class SkillAuroraBlade extends Skill {
	
	public SkillAuroraBlade(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_blade.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.BloodControl.getExperience(player) >= 128;
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		ItemStack held = player.getHeldItem();
		if (held == null) {
			player.setCurrentItemOrArmor(0, new ItemStack(KCItems.aurora_blade, 1));
			if (!player.worldObj.isRemote) {
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.callblade")));
    		} else {
    			Random rand = new Random();
    			int exp = rand.nextInt(20) + 1;
    			modifyExperience(player, exp);
    		}
    	}
		return false;
	}
	
	/** 当剑爆了的时候，调用此方法。 */
	public static void onBladeDead(EntityPlayer player, double proportion) {
		if (proportion == 0) {
			SkillUtils.modifyAuroraPoint(player, (int) (SkillsRw.AuroraBlade.mAuroraCost * 0.5F));
		} else {
			if (!player.worldObj.isRemote) {
				if (proportion < 0.99D)
					player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.recyclerate") 
							+ String.format("%.3f", proportion)));
				else
					player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.breakblade")));
			}
			int time = (int)(60 * 20 * proportion);
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, time, 1));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, time, 3));
			player.addPotionEffect(new PotionEffect(Potion.confusion.id, time));
			player.addPotionEffect(new PotionEffect(Potion.weakness.id, time, 3));
		}
	}
	
	/** 当剑完全爆了的时候，调用此方法。 */
	public static void breakBlade(EntityPlayer player) {
		onBladeDead(player, 1D);
    }
	
}