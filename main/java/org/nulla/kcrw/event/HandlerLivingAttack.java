package org.nulla.kcrw.event;

import org.nulla.kcrw.damage.KCDamageSource;
import org.nulla.kcrw.potion.Potions;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingAttack {
	
	@SubscribeEvent
	public void AuroraAttack(LivingHurtEvent event) {
		if (event.source.damageType.equals("player")) {
			event.entityLiving.attackEntityFrom(KCDamageSource.aurora, 2.0F);
			if (event.entityLiving.getHealth() <= 0) {
				event.setCanceled(true);
			}
		}
		
	}
	
	@SubscribeEvent
	public void PoisonCanceller(LivingAttackEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (event.source.damageType.equals("magic") && player.isPotionActive(Potions.poisonResistance)) {
				event.setCanceled(true);
			}
		}
		
	}

}
