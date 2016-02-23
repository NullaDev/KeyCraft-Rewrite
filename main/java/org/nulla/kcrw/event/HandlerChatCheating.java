package org.nulla.kcrw.event;

import org.nulla.kcrw.skill.*;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraftforge.event.ServerChatEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerChatCheating {
	
	@SubscribeEvent
    public void Cheating(ServerChatEvent event) {
        if(event.message.toLowerCase().equals("kotori")) {
        	event.setCanceled(true);
            EntityPlayerMP player = event.player;
            if (!player.worldObj.isRemote) {
            	Skill.modifyAuroraPoint(player, 1000);
            	player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.cheat.add")));
            }
        } else if(event.message.toLowerCase().equals("reset")) {
        	event.setCanceled(true);
            EntityPlayerMP player = event.player;
            if (!player.worldObj.isRemote) {
                Skill.setAuroraPoint(player, Skill.INITIAL_AURORA_POINT);
                for (Skill i : Skills.AllSkills) {
                	Skill.setSkill(player, i, false);
        		}
                SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.cheat.reset")));
            }
        } else if(event.message.toLowerCase().equals("sakuya")) {
        	event.setCanceled(true);
            EntityPlayerMP player = event.player;
            if (!player.worldObj.isRemote) {
    			player.addPotionEffect(new PotionEffect(Potion.field_76434_w.id, 0x7FFFFFFF, 94));
				player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 0x7FFFFFFF, 9));
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.cheat.recover")));
            }
        } else if(event.message.toLowerCase().equals("kagari")) {
        	event.setCanceled(true);
            EntityPlayerMP player = event.player;
            if (!player.worldObj.isRemote) {
                for (Skill i : Skills.AllSkills) {
        			Skill.learnSkill(player, i);
                	//这好像不用发
        		}
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.cheat.learn")));
            }
        }
    }

}
