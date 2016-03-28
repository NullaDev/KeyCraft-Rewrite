package org.nulla.kcrw.event;

import org.nulla.kcrw.damage.KCDamageSource;
import org.nulla.kcrw.potion.KCPotions;
import org.nulla.kcrw.skill.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraftforge.event.entity.living.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingAttack {
		
	@SubscribeEvent
	public void AuroraAttack(LivingHurtEvent event) {
		if (!(event.source.getEntity() instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer) event.source.getEntity();
		if (player.worldObj.isRemote) {
			return;
		}
		if (event.source.damageType.equals("player")) {
			//这里直接有判断cd和是否习得了，太棒了
			if (Skill.useSkill(player, Skills.SkillAuroraAttack.mID))
				event.entityLiving.attackEntityFrom(KCDamageSource.aurora, 20.0F);
		}
		
	}
	
	@SubscribeEvent
	public void PoisonCanceller(LivingAttackEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if (event.source.damageType.equals("magic") && player.isPotionActive(KCPotions.poisonResistance)) {
				event.setCanceled(true);
			}
		}
		
	}

}
