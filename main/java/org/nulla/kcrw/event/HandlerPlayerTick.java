package org.nulla.kcrw.event;

import org.nulla.kcrw.damage.KCDamageSource;
import org.nulla.kcrw.item.*;
import org.nulla.kcrw.skill.Skills;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.*;

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
		
		if (Skills.SkillAuroraRepair.trigSkill(player)) {
			for (int i = 0; i < 4; i++) {
				if (player.inventory.armorInventory[i] != null) {
					flag.setItemDamage(Math.max(player.inventory.armorInventory[i].getItemDamage() - 16, 0));
					return;
				}
			}
		}
	}

}
