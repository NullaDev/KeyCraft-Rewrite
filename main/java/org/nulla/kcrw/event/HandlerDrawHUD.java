package org.nulla.kcrw.event;

import org.nulla.kcrw.KCUtils;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class HandlerDrawHUD {
	
	@SubscribeEvent
	public void drawAuroraPoint(RenderGameOverlayEvent.Pre event) {
		
		int width = event.resolution.getScaledWidth();
		int height = event.resolution.getScaledHeight();
		
		KCUtils.drawAuroraStrip(width, height);
	}

}
