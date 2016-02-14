package org.nulla.kcrw.event;

import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.skill.Skill;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerDrawHUD {
	
	@SubscribeEvent
	public void drawAuroraPoint(RenderGameOverlayEvent.Pre event) {
		
		System.out.println(KCUtils.getMC().displayWidth +","+ KCUtils.getMC().displayHeight);
		float width = event.resolution.getScaledWidth() * 0.95F;
		float height = event.resolution.getScaledHeight() * 0.05F;
		
		int currentAuroraPoint = Skill.getAuroraPoint(KCUtils.getPlayerCl());
		int MaximumAuroraPoint = Skill.AURORA_POINT_MAXIMUM;
	        
		int length = Math.min(128 *  currentAuroraPoint / MaximumAuroraPoint, 128);
		Gui.drawRect((int)width - 128, (int)height, (int)width, (int)height + 32, 0x7F000000);
		Gui.drawRect((int)width - length, (int)height, (int)width, (int)height + 32, 0x7F00FF00);
	        
		String info = "Aurora: " + Skill.getAuroraPoint(KCUtils.getPlayerCl()) + " / " + Skill.AURORA_POINT_MAXIMUM;
		FontRenderer fontRenderer = KCUtils.getMC().fontRenderer;
		int color = 0xFFFFFF;
		if (currentAuroraPoint <= MaximumAuroraPoint * 0.25) {color = 0xFF0000;}
		else if (currentAuroraPoint <= MaximumAuroraPoint * 0.5) {color = 0xFFFF00;}
		else if (currentAuroraPoint > MaximumAuroraPoint) {color = 0x00FF00;}
		fontRenderer.drawStringWithShadow(info, (int)width - 128, (int)height + 48, color);
		fontRenderer.drawString("", 0, 0, 0xFFFFFF);	//³õÊ¼»¯×²îÑ¼¦       
		KCUtils.getMC().renderEngine.bindTexture(Gui.icons);
		
	}
	
	/*
	@SubscribeEvent
	public void auroraPointInfo(RenderGameOverlayEvent.Text event) {
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
	*/

}
