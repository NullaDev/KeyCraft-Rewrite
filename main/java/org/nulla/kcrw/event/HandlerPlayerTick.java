package org.nulla.kcrw.event;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.UUID;

import org.nulla.kcrw.client.gui.SkillLearningHelper;
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
	public void attributeModifier(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		
		if (player.worldObj.isRemote) {
			return;
		}

		IAttributeInstance attr = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.movementSpeed);
		
		if (SkillsRw.SpeedUpFinal.trigSkill(player) && SkillsRw.SpeedUpFinal.getAttributeUUID(player) == null) {
			UUID uuid = UUID.randomUUID();
			AttributeModifier m = new AttributeModifier(uuid, "speed_up_final", 0.4, 1);
			attr.applyModifier(m);
			SkillsRw.SpeedUpFinal.setAttributeUUID(player, uuid);
			player.stepHeight += 1F;
		} else if (!SkillsRw.SpeedUpFinal.trigSkill(player) && SkillsRw.SpeedUpFinal.getAttributeUUID(player) != null) {
			UUID uuid = SkillsRw.SpeedUpFinal.getAttributeUUID(player);
			if (attr.getModifier(uuid) != null)
				attr.removeModifier(attr.getModifier(uuid));
			SkillsRw.SpeedUpFinal.setAttributeUUID(player, null);
			player.stepHeight -= 1F;
		}
		
		IAttributeInstance attr2 = player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.attackDamage);
		double baseValue = attr2.getBaseValue();

		if (SkillsRw.StrengthUpFinal.trigSkill(player) && !SkillsRw.StrengthUpFinal.getAttributeAdd(player)) {
			attr2.setBaseValue(baseValue + 3);
			SkillsRw.StrengthUpFinal.setAttributeAdd(player, true);
		} else if (!SkillsRw.StrengthUpFinal.trigSkill(player) && SkillsRw.StrengthUpFinal.getAttributeAdd(player)) {
			attr2.setBaseValue(baseValue - 3);
			SkillsRw.StrengthUpFinal.setAttributeAdd(player, false);
		}
	}
	
	@SubscribeEvent
	public void skillFinder(PlayerTickEvent event) {
		EntityPlayer player = event.player;
		
		if (player.worldObj.isRemote) {
			return;
		}
		
		if (player.isPotionActive(Potion.poison)) {
			SkillLearningHelper.findSkill(player, SkillsRw.PoisonProtection, true);
		}
		
		if (player.isBurning()) {
			SkillLearningHelper.findSkill(player, SkillsRw.FireProtection, true);
		}
		
	}

}
