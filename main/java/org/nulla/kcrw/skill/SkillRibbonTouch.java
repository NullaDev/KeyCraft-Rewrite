package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.EntityRibbon;
import org.nulla.nullacore.api.skill.Skill;

public class SkillRibbonTouch extends Skill {
	
	public SkillRibbonTouch(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/ribbon_touch.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return true;
	}

	@Override
	protected boolean onUse(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		if (!player.capabilities.isCreativeMode) {
			if (KCUtils.getNumberOfItemInPlayer(player, KCItems.miracle_ribbon) >= 1) {
				KCUtils.minusNumberOfItemInPlayer(player, KCItems.miracle_ribbon, 1);
			} else {
				return false;
			}
		}
		
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(10) + 1;
			modifyExperience(player, exp);
		}

		player.worldObj.spawnEntityInWorld(new EntityRibbon(player.worldObj, player));
		
		return true;
	}

}
