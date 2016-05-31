package org.nulla.kcrw.event;

import java.util.Random;

import org.nulla.kcrw.entity.effect.EntityAuroraShield;
import org.nulla.kcrw.item.ItemAuroraArmor;
import org.nulla.kcrw.item.ItemAuroraSword;
import org.nulla.kcrw.item.ItemAuroraTool;
import org.nulla.kcrw.potion.KCPotions;
import org.nulla.kcrw.potion.PotionAuroraRegeneration;
import org.nulla.kcrw.skill.SkillsRw;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class HandlerPlayerTick {
	
	@SubscribeEvent
	public void Skill_AutoRepair(PlayerTickEvent event) {
		if (event.phase == Phase.END) {
			return;
		}
		
		EntityPlayer player = event.player;
		
		if (player.worldObj.isRemote) {
			return;
		}
		
		ItemStack flag = null;
		
		for (int i = 0; i < 4; i++) {
			//防止NullPointerException
			if (player.inventory.armorInventory[i] != null) {
				ItemStack stack = player.inventory.armorInventory[i];
				if (stack.getItemDamage() != 0 && stack.getItem() instanceof ItemAuroraArmor) {
					flag = player.inventory.armorInventory[i];
				}
			}
		}
		
		//好像Armor和Tool的Damage结算方式不一样，明天再说吧。
		if (flag == null) {
			for (int i = 0; i < 36; i++) {
				//防止NullPointerException
				if (player.inventory.mainInventory[i] != null) {
					ItemStack stack = player.inventory.mainInventory[i];
					if (stack.getItemDamage() != 0) {
						if (stack.getItem() instanceof ItemAuroraTool || stack.getItem() instanceof ItemAuroraSword)
							flag = player.inventory.mainInventory[i];
					}
				}
			}
		}
		
		if (flag == null) {
			return;
		}
		
		if (SkillsRw.SkillAuroraRepair.trigSkill(player)) {
			for (int i = 0; i < 4; i++) {
				if (player.inventory.armorInventory[i] != null) {
					flag.setItemDamage(Math.max(player.inventory.armorInventory[i].getItemDamage() - 16, 0));
					return;
				}
			}
		}
	}
	
	@SubscribeEvent
	public void Skill_AuroraRegeneration(PlayerTickEvent event) {
		if (event.phase == Phase.END) {
			return;
		}
		
		EntityPlayer player = event.player;
		
		if (player.worldObj.isRemote) {
			return;
		}
		
		if (player.isPotionActive(KCPotions.auroraRegeneration)) {
			return;
		}
				
		Random ran = new Random();
		if (ran.nextFloat() < 1F / (20 * 120)) {
			if (SkillsRw.SkillAuroraRegeneration.trigSkill(player)) {
				player.addPotionEffect(new PotionEffect(KCPotions.auroraRegeneration.id, 20));
			}
		}
		
	}
	
	@SubscribeEvent
	public void Skill_AuroraShield(PlayerTickEvent event) {
		event.player.worldObj.spawnEntityInWorld(EntityAuroraShield.create(event.player));	
	}

}
