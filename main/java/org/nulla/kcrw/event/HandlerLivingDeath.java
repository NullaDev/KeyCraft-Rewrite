package org.nulla.kcrw.event;

import org.nulla.kcrw.potion.KCPotions;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerLivingDeath {
	
	@SubscribeEvent
    public void Kill_AuroraRegeneration(LivingDeathEvent event) {
		if (!(event.source.getEntity() instanceof EntityPlayer)) {
			return;
		}
		
		EntityPlayer player = (EntityPlayer)event.source.getEntity();
		if (player.worldObj.isRemote) {
			return;
		}
		int maxHealth = (int) event.entityLiving.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue() / 2;
		player.addPotionEffect(new PotionEffect(KCPotions.auroraRegeneration.id, 20 * maxHealth));

	}

}
