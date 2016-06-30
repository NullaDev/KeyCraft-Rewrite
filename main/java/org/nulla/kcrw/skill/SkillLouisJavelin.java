package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.kcrw.entity.EntityJavelin;
import org.nulla.nullacore.api.skill.Skill;

public class SkillLouisJavelin extends Skill{

	public SkillLouisJavelin(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}

	@Override
	protected boolean onUse(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		if (!player.capabilities.isCreativeMode) {
			if (KCUtils.getNumberOfItemInPlayer(player, KCItems.javelin) >= 1) {
				KCUtils.minusNumberOfItemInPlayer(player, KCItems.javelin, 1);
			} else {
				return false;
			}
		}

		player.worldObj.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F));

		player.worldObj.spawnEntityInWorld(new EntityJavelin(player.worldObj, player, true));
		
		return true;
	}

}