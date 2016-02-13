package org.nulla.kcrw;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.nulla.kcrw.skill.SkillNetwork;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;

public class KCCommonProxy implements IGuiHandler {

	public void preInit(FMLPreInitializationEvent event) {
		
	}
	 
	public void init(FMLInitializationEvent event) {
		// 注册网络事件
		FMLCommonHandler.instance().bus().register(new SkillNetwork.SendSyncPacket());
		SkillNetwork.channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(SkillNetwork.CHANNEL_STRING);
		SkillNetwork.channel.register(new SkillNetwork());
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
