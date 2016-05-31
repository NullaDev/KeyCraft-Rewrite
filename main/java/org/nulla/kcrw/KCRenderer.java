package org.nulla.kcrw;

import net.minecraft.client.renderer.entity.RenderSnowball;

import org.nulla.kcrw.client.renderer.RendererAuroraShield;
import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.kcrw.entity.effect.EntityAuroraShield;

import cpw.mods.fml.client.registry.RenderingRegistry;

public class KCRenderer {
	public static void init() {
		RenderingRegistry.registerEntityRenderingHandler(EntityBaseball.class, new RenderSnowball(KCItems.baseball));//棒球的渲染
		RenderingRegistry.registerEntityRenderingHandler(EntityAuroraShield.class, new RendererAuroraShield());//棒球的渲染
	}
}
