package org.nulla.kcrw.event;

import org.nulla.kcrw.entity.effect.EntityAuroraShield;
import org.nulla.kcrw.potion.KCPotions;
import org.nulla.kcrw.skill.SkillAuroraShield;
import org.nulla.kcrw.skill.SkillsRw;
import org.nulla.nullacore.api.damage.NullaDamageSource;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class HandlerLivingAttackOrHurt {
		
	@SubscribeEvent
	public void AuroraAttack(LivingAttackEvent event) { // HurtEvent客户端不触发
		if (event.source.damageType.equals("aurora")) {
			return;
		}
		Entity entity = event.source.getEntity();
		if (!(entity instanceof EntityPlayer))
			return;
		// @EntityLivingBase.attackEntityFrom
		if (event.entityLiving.isEntityInvulnerable()
			|| event.entityLiving.getHealth() <= 0.0F)
			return;
		
		EntityPlayer player = (EntityPlayer)entity;
		if (event.source.damageType.equals("player")) {
			//这里直接有判断cd和是否习得了，太棒了
			if (SkillsRw.AuroraAttack.trigSkill(player)) {
				event.entityLiving.setLastAttacker(player);
				event.entityLiving.attackEntityFrom(NullaDamageSource.CauseAuroraDamage(player), 20.0F);
			}
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
	
	@SubscribeEvent
	public void ShieldStatement(LivingHurtEvent event) {
		if (event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			EntityAuroraShield shield = SkillAuroraShield.getEntityShield(player);
			if (shield == null)
				return;
			if (!shield.isActive)
				return;
			
			if (SkillAuroraShield.isResistible(event.source.damageType)) {
				float value = Math.min(event.ammount * 0.8F, shield.mShieldValue);
				shield.mShieldValue -= value;
				event.ammount -= value;
			}
			
		}
		
	}

}
