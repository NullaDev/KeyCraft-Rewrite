package org.nulla.kcrw;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.network.IGuiHandler;

public class KCCommonProxy implements IGuiHandler {

	public void preInit(FMLPreInitializationEvent event) {
		
	}
	 
	public void init(FMLInitializationEvent event) {
		
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
