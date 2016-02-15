package org.nulla.kcrw.event;

import org.nulla.kcrw.KCResources;
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
		
		int width = (int) (event.resolution.getScaledWidth() * 0.95F);
		int height = (int) (event.resolution.getScaledHeight() * 0.05F);
		
		int currentAuroraPoint = Skill.getAuroraPoint(KCUtils.getPlayerCl());
		int MaximumAuroraPoint = Skill.AURORA_POINT_MAXIMUM;
	        
		int length = Math.min(105 *  currentAuroraPoint / MaximumAuroraPoint, 105);
		
		KCUtils.getMC().getTextureManager().bindTexture(KCResources.aurora_strip_inside);
		KCUtils.drawScaledCustomSizeModalRect(width - length - 15, height + 1, 630 - 6 * length, 0, 6 * length + 90, 120, length + 15, 18, 720, 120);
		
		KCUtils.getMC().getTextureManager().bindTexture(KCResources.aurora_strip_outside);
		KCUtils.drawScaledCustomSizeModalRect(width - 120, height, 0, 0, 720, 120, 120, 20, 720, 120);
		
		//Gui.drawRect((int)width - 128, (int)height, (int)width, (int)height + 32, 0x7F000000);
		//Gui.drawRect((int)width - length, (int)height, (int)width, (int)height + 32, 0x7F00FF00);
	        
		String info = "Aurora: " + Skill.getAuroraPoint(KCUtils.getPlayerCl()) + " / " + Skill.AURORA_POINT_MAXIMUM;
		FontRenderer fontRenderer = KCUtils.getMC().fontRenderer;
		int color = 0x7FFFBF;
		//if (currentAuroraPoint <= MaximumAuroraPoint * 0.25) {color = 0xFF0000;}
		//else if (currentAuroraPoint <= MaximumAuroraPoint * 0.5) {color = 0xFFFF00;}
		//else if (currentAuroraPoint > MaximumAuroraPoint) {color = 0x00FF00;}
		fontRenderer.drawStringWithShadow(info, (int)width - 105, (int)height - 3, color);
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
