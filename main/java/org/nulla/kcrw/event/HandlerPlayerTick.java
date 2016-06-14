package org.nulla.kcrw.event;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.UUID;

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
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
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
		
		if (SkillsRw.AuroraRepair.trigSkill(player)) {
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
			if (SkillsRw.AuroraRegeneration.trigSkill(player)) {
				player.addPotionEffect(new PotionEffect(KCPotions.auroraRegeneration.id, 20));
			}
		}
		
	}
	
	@SubscribeEvent
	public void a(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		
		if (player.worldObj.isRemote) {
			return;
		}

		IAttributeInstance attr = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
		double value = attr.getBaseValue();
		UUID uuid = UUID.randomUUID();
		AttributeModifier m = new AttributeModifier(uuid, "speed_up_final", 0.2, 1);
		System.out.println(value);
		
		if (SkillsRw.SpeedUpFinal.trigSkill(player) && !SkillsRw.SpeedUpFinal.isOpen(player)) {
			attr.applyModifier(m);
			SkillsRw.SpeedUpFinal.setOpen(player, true);
			System.out.println("on");
		} else if (!SkillsRw.SpeedUpFinal.trigSkill(player) && SkillsRw.SpeedUpFinal.isOpen(player)) {
			if (attr.getModifier(uuid) != null)
				attr.removeModifier(m);
			SkillsRw.SpeedUpFinal.setOpen(player, false);
			System.out.println("off");
		}
	}

}
