package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.EntityRibbon;
import org.nulla.nullacore.api.skill.Skill;

public class SkillKagariCannon extends Skill {
	
	public SkillKagariCannon(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/kagari_cannon.png");
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
			if (KCUtils.getNumberOfItemInPlayer(player, KCItems.miracle_ribbon) >= 4) {
				KCUtils.minusNumberOfItemInPlayer(player, KCItems.miracle_ribbon, 4);
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
		int num = 8192 / (2048 - this.getExperience(player));
		for (int i = 0; i < num; i++) {
			Random ran = new Random();
			px += 2 * ran.nextDouble() - 1D;
			py += 2 * ran.nextDouble() - 1D;
			pz += 2 * ran.nextDouble() - 1D;
			EntityRibbon ribbon = new EntityRibbon(player.worldObj, player, px, py, pz);
			ribbon.addVelocity(0.2F * new Random().nextDouble(), 0.2F * new Random().nextDouble(), 0.2F * new Random().nextDouble());
			player.worldObj.spawnEntityInWorld(ribbon);
		}
		
		return true;
	}

}