package org.nulla.kcrw;

import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.kcrw.entity.EntityJavelin;
import org.nulla.kcrw.entity.EntityRibbon;
import org.nulla.kcrw.entity.EntityVibrationWave;
import org.nulla.kcrw.entity.effect.EntityAuroraBlast;
import org.nulla.kcrw.entity.effect.EntityAuroraShield;
import org.nulla.kcrw.entity.effect.EntityAuroraStorm;
import org.nulla.kcrw.entity.entityliving.EntityBlackDog;
import org.nulla.kcrw.entity.entityliving.EntityRedDog;
import org.nulla.kcrw.entity.entityliving.EntityTreeGuard;
import org.nulla.kcrw.entity.entityliving.EntityTreeProducer;
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
    public static final String VERSION = "TestInGroup";
    
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
    	KCMaterials.init();
		
		// 注册实体
    	KCEntities.initEntities(this);
	
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
