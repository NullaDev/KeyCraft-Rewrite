package org.nulla.kcrw;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;

import org.nulla.kcrw.event.HandlerChatCheating;
import org.nulla.kcrw.skill.SkillNetwork;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.*;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;

@Mod(modid = KeyCraft_Rewrite.MODID, name = KeyCraft_Rewrite.MODNAME, version = KeyCraft_Rewrite.VERSION)
public class KeyCraft_Rewrite {
	
	public static final String MODID = "kcrw";
	public static final String MODNAME = "KeyCraft Rewrite Ver.";
    public static final String VERSION = "Demo20160323";
    
    @SidedProxy(clientSide = "org.nulla.kcrw.KCClientProxy",
            	serverSide = "org.nulla.kcrw.KCCommonProxy")
    public static KCCommonProxy proxy;
 
    @Instance(MODID)
    public static KeyCraft_Rewrite instance;
    
    public static CreativeTabs KCCreativeTab = new KCCreativeTab("kcrw");
    
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit(event);
    	KCItems.InitItems();
    	KCBlocks.InitBlocks();
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event) {
    	proxy.init(event);
    	MinecraftForge.EVENT_BUS.register(new HandlerChatCheating());
		// 注册网络事件
		SkillNetwork.getInstance().init();
		KCNetwork.getInstance().init();	
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    }

}
