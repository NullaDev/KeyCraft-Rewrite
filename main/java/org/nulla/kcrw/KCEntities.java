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

import cpw.mods.fml.common.registry.EntityRegistry;

public class KCEntities {
	public static void initEntities(Object ModObject)
	{

		int modID = 1;
    	EntityRegistry.registerModEntity(EntityAuroraBlast.class, "AuroraBlast", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityAuroraShield.class, "AuroraShield", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityAuroraStorm.class, "AuroraStorm", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityBaseball.class, "Baseball", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityJavelin.class, "Javelin", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityRibbon.class, "Ribbon", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityVibrationWave.class, "VibrationWave", modID++, ModObject, 128, 1, true);
    	
    	//EntityLiving
    	EntityRegistry.registerModEntity(EntityBlackDog.class, "BlackDog", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityRedDog.class, "RedDog", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityTreeGuard.class, "TreeGuard", modID++, ModObject, 128, 1, true);
    	EntityRegistry.registerModEntity(EntityTreeProducer.class, "TreeGuard", modID++, ModObject, 128, 1, true);
	}
}
