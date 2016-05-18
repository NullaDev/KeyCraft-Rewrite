package org.nulla.kcrw.potion;

import org.nulla.nullacore.api.potion.NullaPotion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class PoisonResistance extends NullaPotion {

	public PoisonResistance(String name, ResourceLocation location) {
		super(name, location);
	}

	@Override
	public void performEffect(EntityLivingBase entity, int level) {

	}

}
