package org.nulla.kcrw;

import org.nulla.kcrw.entity.EntityBaseball;

import cpw.mods.fml.common.registry.EntityRegistry;

public class KCEntities {
	
    public static void InitEntities() {
    	int modID = 1;
    	EntityRegistry.registerModEntity(EntityBaseball.class, "baseball", modID++, KeyCraft_Rewrite.class, 128, 1, true);
    }

}
