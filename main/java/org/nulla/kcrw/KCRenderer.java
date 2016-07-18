package org.nulla.kcrw;

import org.nulla.kcrw.client.model.*;
import org.nulla.kcrw.client.renderer.*;
import org.nulla.kcrw.client.renderer.entity.*;
import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.kcrw.entity.EntityJavelin;
import org.nulla.kcrw.entity.EntityRibbon;
import org.nulla.kcrw.entity.EntityVibrationWave;
import org.nulla.kcrw.entity.effect.EntityAuroraShield;
import org.nulla.kcrw.entity.effect.EntityAuroraStorm;
import org.nulla.kcrw.entity.entityliving.EntityBlackDog;
import org.nulla.kcrw.entity.entityliving.EntityRedDog;

import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.client.renderer.entity.RenderSnowball;

public class KCRenderer {
	public static void init() {
		RenderingRegistry.registerEntityRenderingHandler(EntityBaseball.class, new RenderSnowball(KCItems.baseball));//棒球的渲染
		RenderingRegistry.registerEntityRenderingHandler(EntityJavelin.class, new RendererJavelin());//标枪的渲染
		RenderingRegistry.registerEntityRenderingHandler(EntityAuroraShield.class, new RendererAuroraShield());//盾的渲染
		RenderingRegistry.registerEntityRenderingHandler(EntityVibrationWave.class, new RendererVibrationWave());//震荡波的渲染
		RenderingRegistry.registerEntityRenderingHandler(EntityAuroraStorm.class, new RendererNull());//风暴的渲染
		RenderingRegistry.registerEntityRenderingHandler(EntityRibbon.class, new RendererRibbon());
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackDog.class, new RendererBlackDog(new ModelDog(), 0.7F));
		RenderingRegistry.registerEntityRenderingHandler(EntityRedDog.class, new RendererRedDog(new ModelDog(), 0.7F));

	}
}
