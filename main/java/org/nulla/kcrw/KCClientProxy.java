package org.nulla.kcrw;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.nulla.kcrw.event.HandlerKeyInput;
import org.nulla.kcrw.event.HandlerLivingAttack;
import org.nulla.kcrw.event.HandlerLivingDeath;
import org.nulla.kcrw.event.HandlerPlayerTick;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

/* 注册只在客户端发生的事件 */
public class KCClientProxy extends KCCommonProxy {
	
	public static final KeyBinding kbSwitchSkill = new KeyBinding("kcrw.key.skill3", Keyboard.KEY_V, "kcrw.key.keytitle");
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		Display.setTitle("Welcome to KeyCraft's World!");
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		// 注册GUI、用户输入事件
		FMLCommonHandler.instance().bus().register(new HandlerKeyInput());
		FMLCommonHandler.instance().bus().register(new HandlerPlayerTick());
		MinecraftForge.EVENT_BUS.register(new HandlerLivingAttack());
		MinecraftForge.EVENT_BUS.register(new HandlerLivingDeath());

		
		//注册KeyBinding
		ClientRegistry.registerKeyBinding(kbSwitchSkill);
		
		// 注册渲染器
		KCRenderer.init();
		
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
	}

}
