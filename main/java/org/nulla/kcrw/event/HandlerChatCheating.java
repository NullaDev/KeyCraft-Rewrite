package org.nulla.kcrw.event;

import org.nulla.kcrw.skill.Skill;
import org.nulla.kcrw.skill.SkillNetwork;
import org.nulla.kcrw.skill.SkillUtils;
import org.nulla.kcrw.skill.Skills;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraftforge.event.ServerChatEvent;

public class HandlerChatCheating {
	
	@SubscribeEvent
    public void Cheating(ServerChatEvent event) {
		System.out.println("woshisb");
        if(event.message.toLowerCase().equals("kotori")) {
        	event.setCanceled(true);
            EntityPlayerMP player = event.player;
            if (!player.worldObj.isRemote) {
            	SkillUtils.modifyAuroraPoint(player, 1000);
            	player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.cheat.add")));
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
                	i.setSkill(player, true);
        			SkillNetwork.Channel.sendToServer(SkillNetwork.createSyncSkillPacket(player));
        		}
                player.addChatComponentMessage(new ChatComponentText(StatCollector.translateToLocal("kcrw.prompt.cheat.learn")));
            }
        }
    }

}
