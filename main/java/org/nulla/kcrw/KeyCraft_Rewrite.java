package org.nulla.kcrw;

import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.kcrw.entity.EntityVibrationWave;
import org.nulla.kcrw.entity.effect.EntityAuroraBlast;
import org.nulla.kcrw.entity.effect.EntityAuroraShield;
import org.nulla.kcrw.potion.KCPotions;
import org.nulla.kcrw.skill.SkillsRw;
import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.Skills;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import net.minecraft.creativetab.CreativeTabs;

@Mod(modid = KeyCraft_Rewrite.MODID, name = KeyCraft_Rewrite.MODNAME, version = KeyCraft_Rewrite.VERSION, 
	dependencies = "required-after:nullacore")
public class KeyCraft_Rewrite {
	
	public static final String MODID = "kcrw";
	public static final String MODNAME = "KeyCraft Rewrite Ver.";
    public static final String VERSION = "Demo20160614";
    
    @SidedProxy(clientSide = "org.nulla.kcrw.KCClientProxy",
            	serverSide = "org.nulla.kcrw.KCCommonProxy")
    public static KCCommonProxy proxy;
    
    public static CreativeTabs KCCreativeTab = new KCCreativeTab("kcrw");
    
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	proxy.preInit(event);
    	
    	// 注册物品、方块
    	KCItems.InitItems();
    	KCBlocks.InitBlocks();
		
		// 注册实体
		int modID = 1;
    	EntityRegistry.registerModEntity(EntityBaseball.class, "Baseball", modID++, this, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityAuroraBlast.class, "AuroraBlast", modID++, this, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityAuroraShield.class, "AuroraShield", modID++, this, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityVibrationWave.class, "VibrationWave", modID++, this, 128, 1, true);
    }
    
    @EventHandler
    public void Init(FMLInitializationEvent event) {
    	proxy.init(event);
    	
		// 注册网络事件
		KCNetwork.getInstance().init();	
    	
		// 注册技能
    	SkillsRw.initSkills();
		
		// 注册效果
		KCPotions.initPotions();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    }

}
