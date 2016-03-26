package org.nulla.kcrw.event;

import org.nulla.kcrw.damage.KCDamageSource;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingAttack {
	
	@SubscribeEvent
	public void AuroraAttack(LivingHurtEvent event) {
		if (event.source.damageType.equals("player")) {
			event.entity.attackEntityFrom(KCDamageSource.aurora, 2.0F);
		}
		System.out.println(event.source.damageType);
		
	}

}
