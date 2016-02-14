package org.nulla.kcrw.event;

import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.skill.Skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerDrawHUD {
	
	@SubscribeEvent
	public void AuroraPointInfo(RenderGameOverlayEvent.Text event) {
		EntityPlayer player = KCUtils.getPlayerCl();
		String name = player.getCommandSenderName();
        if (!Skill.hasInitialized(player)) {
        	String info = ", Your Aurora point is ";
        	int point = Skill.getAuroraPoint(player);
        	event.left.add(0, name + info + point + ".");
        } else {
        	event.left.add(0, "hai mei chu shi hua");
        }
	}

}
