package org.nulla.kcrw;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import org.nulla.kcrw.event.HandlerDrawHUD;
import org.nulla.kcrw.skill.SkillNetwork;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.*;

public class KCCommonProxy implements IGuiHandler {

	public void preInit(FMLPreInitializationEvent event) {
    	KCItems.InitItems();
    	KCBlocks.InitBlocks();
	}
	 
	public void init(FMLInitializationEvent event) {
		// 注册网络事件
		SkillNetwork.getInstance().init();
	}
	 
	public void postInit(FMLPostInitializationEvent event) {

	}
	
	@Override 
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { 
		return null;
	}
	
	@Override 
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) { 
		return null;
	}
	
}
