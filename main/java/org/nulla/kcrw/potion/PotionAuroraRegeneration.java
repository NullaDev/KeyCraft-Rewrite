package org.nulla.kcrw.potion;

import org.nulla.kcrw.skill.SkillUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class PotionAuroraRegeneration extends KCPotion {

	public PotionAuroraRegeneration(String name, ResourceLocation icon) {
		super(name, icon);
	}
	
	@Override
	public void performEffect(EntityLivingBase entity, int level) {
		if (!(entity instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) entity;
		if (player.worldObj.isRemote) {
			return;
		}
		SkillUtils.modifyAuroraPoint(player, 1);
	}
	
	@Override
	public boolean isReady(int p_76397_1_, int p_76397_2_) {
		int k;
		k = 20 >> p_76397_2_;
		return k > 0 ? p_76397_1_ % k == 0 : true;
	}
	
}
