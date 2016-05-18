package org.nulla.nullacore;

import net.minecraftforge.common.MinecraftForge;

import org.nulla.nullacore.api.audio.MusicHelper;
import org.nulla.nullacore.api.skill.SkillNetwork;
import org.nulla.nullacore.event.HandlerChatCheating;
import org.nulla.nullacore.potion.PotionUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = NullaCore.MODID, name = NullaCore.MODNAME, version = NullaCore.VERSION)
public class NullaCore {
	public static final String MODID = "nullacore";
	public static final String MODNAME = "Nulla MODs' core";
    public static final String VERSION = "Demo20160512";
    
    @SidedProxy(clientSide = "org.nulla.nullacore.ClientProxy",
            	serverSide = "org.nulla.nullacore.CommonProxy")
    public static CommonProxy proxy;
        
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit(event);
		
		PotionUtils.init();
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event) {
    	proxy.init(event);
    	
		// 注册聊天作弊
    	MinecraftForge.EVENT_BUS.register(new HandlerChatCheating());
    	
		// 注册网络事件
		SkillNetwork.getInstance().init();
		
		// 注册音乐tick事件
		FMLCommonHandler.instance().bus().register(new MusicHelper());
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    }

}
