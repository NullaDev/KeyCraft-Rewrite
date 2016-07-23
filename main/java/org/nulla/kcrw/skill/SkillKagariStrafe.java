package org.nulla.kcrw.skill;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.EntityRibbon;
import org.nulla.nullacore.api.skill.Skill;

public class SkillKagariStrafe extends Skill {
	
	public SkillKagariStrafe(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/kagari_strafe.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.RibbonTouch.getExperience(player) >= 512;
	}

	@Override
	protected boolean onUse(EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		if (!player.capabilities.isCreativeMode) {
			if (KCUtils.getNumberOfItemInPlayer(player, KCItems.miracle_ribbon) >= 8) {
				KCUtils.minusNumberOfItemInPlayer(player, KCItems.miracle_ribbon, 8);
			} else {
				return false;
			}
		}
		
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(20) + 1;
			modifyExperience(player, exp);
		}

		double px = player.posX;
		double py = player.posY + player.getEyeHeight();
		double pz = player.posZ;
		
		ArrayList<EntityRibbon> toAdd = new ArrayList<EntityRibbon>();
		
		int num = 16384 / (2048 - this.getExperience(player));
		double ran = 2 * Math.PI * new Random().nextDouble();
		
		for (int i = 0; i < num; i++) {
			double angle = 2 * Math.PI / num * i + ran;
			toAdd.add(new EntityRibbon(player.worldObj, player, px + 2 * Math.cos(angle), py, pz + 2 * Math.sin(angle)));
		}

		for (EntityRibbon i : toAdd) {
			i.resetSpeed();
			player.worldObj.spawnEntityInWorld(i);
		}
	
		return true;
	}

}