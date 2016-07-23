package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.nullacore.api.skill.Skill;

/**
 * 这个技能在罚抄原作里出现了n多次，出自班长的能力超震动，通过使剑快速震动造成高额伤害。
 * 但是剑会飞速掉耐久啊！很浪费的喂！就算精制钢剑耐久高也禁不起这么用啊喂！
 */
public class SkillVibrationBlade extends Skill {

	public SkillVibrationBlade(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/vibration_blade.png");
	}

	@Override
	protected boolean onUse(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		ItemStack held = player.getHeldItem();
		if (held == null) {
			return false;
		} else if (held.getItem() == KCItems.steel_blade) {
			player.setCurrentItemOrArmor(0, new ItemStack(KCItems.steel_blade_vibrating, 1, held.getItemDamage()));
			if (!player.worldObj.isRemote) {
				Random rand = new Random();
				int exp = rand.nextInt(10) + 1;
				modifyExperience(player, exp);
			}
			return true;	
    	} else if (held.getItem() instanceof ItemSword) {
			player.setCurrentItemOrArmor(0, null);
			if (!player.worldObj.isRemote) {
				Random rand = new Random();
				int exp = rand.nextInt(10) + 1;
				modifyExperience(player, exp);
			}
			return true;	
    	}
		return false;
	}

}
