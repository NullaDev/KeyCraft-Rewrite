package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.common.KCDelayedEffect;

public class SkillAuroraBlast extends Skill {
	
	public SkillAuroraBlast(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_blast.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return true;
	}
	
	@Override
	public boolean onUse(final EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		Vec3 skilldict = player.getLookVec();
		for (int i = 0; i < 10; i++) {
			KCDelayedEffect effect = new KCDelayedEffect(10 * i);
			final Double expX = player.posX + i * skilldict.xCoord * 2;
			final Double expY = player.posY + i * skilldict.yCoord * 2;
			final Double expZ = player.posZ + i * skilldict.zCoord * 2;
			final float expS = 2.0F + 0.2F * i;
			effect.setCallback(new KCDelayedEffect.ICallback() {			
				@Override
				public void onTrigger() {
					player.worldObj.createExplosion(player, expX, expY, expZ, expS, true);
				}
			});
		}
		
		return true;
	}
}