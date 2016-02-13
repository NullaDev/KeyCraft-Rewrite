package org.nulla.kcrw;

import org.nulla.kcrw.block.*;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class KCBlocks {
	
	public static Block KotoriWorkshop;
	
	public static void InitBlocks() {
		KotoriWorkshop = new BlockKotoriWorkshop()
			.setBlockName("KotoriWorkshop")
			.setBlockTextureName("kcrw:kotori_workshop")
			.setHardness(1.5F)
			.setResistance(10.0F);
    	GameRegistry.registerBlock(KotoriWorkshop, "KotoriWorkshop");
    }

}
