package org.nulla.nullacore.event;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.ServerChatEvent;

import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.SkillNetwork;
import org.nulla.nullacore.api.skill.SkillUtils;
import org.nulla.nullacore.api.skill.Skills;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerChatCheating {
	
	@SubscribeEvent
    public void Cheating(ServerChatEvent event) {
        if(event.message.toLowerCase().equals("kotori")) {
        	event.setCanceled(true);
            EntityPlayerMP player = event.player;
            if (!player.worldObj.isRemote) {
            	SkillUtils.modifyAuroraPoint(player, 1000);
            }
        } else if(event.message.toLowerCase().equals("reset")) {
        	event.setCanceled(true);
            EntityPlayerMP player = event.player;
            if (!player.worldObj.isRemote) {
            	SkillUtils.setAuroraPoint(player, SkillUtils.INITIAL_AURORA_POINT);
                for (Skill i : Skills.AllSkills) {
                	i.setSkill(player, false);
        		}
                SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
            }
        } else if(event.message.toLowerCase().equals("regenerator")) {
        	event.setCanceled(true);
            EntityPlayerMP player = event.player;
            if (!player.worldObj.isRemote) {
    			player.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 0x7FFFFFFF, 94));
				player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 0x7FFFFFFF, 9));
            }
        } else if(event.message.toLowerCase().equals("kagari")) {
        	event.setCanceled(true);
            EntityPlayerMP player = event.player;
            if (!player.worldObj.isRemote) {
                for (Skill i : Skills.AllSkills) {
                	System.out.println(i.mName);
                	i.setSkill(player, true);
        		}
    			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), player);
            }
        }
    }

}
