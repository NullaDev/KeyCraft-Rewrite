package org.nulla.kcrw;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.kcrw.event.*;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

/* 注册只在客户端发生的事件 */
public class KCClientProxy extends KCCommonProxy {
	
	public static final KeyBinding kbSkill1 = new KeyBinding("kcrw.key.skill1", Keyboard.KEY_R, "kcrw.key.keytitle");
	public static final KeyBinding kbSkill2 = new KeyBinding("kcrw.key.skill2", Keyboard.KEY_F, "kcrw.key.keytitle");
	public static final KeyBinding kbSwitchSkill = new KeyBinding("kcrw.key.skill3", Keyboard.KEY_V, "kcrw.key.keytitle");
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		Display.setTitle("Welcome to KeyCraft's World!");
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		// 注册GUI、用户输入事件
    	MinecraftForge.EVENT_BUS.register(new HandlerDrawHUD());
		FMLCommonHandler.instance().bus().register(new HandlerKeyInput());
		FMLCommonHandler.instance().bus().register(new HandlerPlayerTick());
		MinecraftForge.EVENT_BUS.register(new HandlerLivingAttack());
		MinecraftForge.EVENT_BUS.register(new HandlerLivingDeath());

		
		//注册KeyBinding
		ClientRegistry.registerKeyBinding(kbSkill1);
		ClientRegistry.registerKeyBinding(kbSkill2);
		
		// 注册渲染器
		RenderingRegistry.registerEntityRenderingHandler(EntityBaseball.class, new RenderSnowball(KCItems.baseball));
		
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}

}
