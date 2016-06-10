package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.nullacore.api.skill.Skill;

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