package org.nulla.kcrw.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

/** 测试用的效果，输出Hello world */
public class PotionTest extends KCPotion {

	public PotionTest(String name, ResourceLocation location) {
		super(name, location);
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level) {
		System.out.println("Hello world!");
	}

}
