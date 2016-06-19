package org.nulla.kcrw;

import org.nulla.kcrw.block.*;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class KCBlocks {
	
	public static Block KotoriWorkshop;
	
	public static void InitBlocks() {
		KotoriWorkshop = new BlockKotoriWorkshop()
			.setBlockName("KotoriWorkshop")
			.setBlockTextureName("kcrw:kotori_workshop")
			.setHardness(1.5F)
			.setResistance(10.0F);
    	GameRegistry.registerBlock(KotoriWorkshop, "KotoriWorkshop");
    	GameRegistry.addShapedRecipe(new ItemStack(KotoriWorkshop, 1), new Object[] { " A ", "BCB", " B ", 'A', Blocks.crafting_table, 'C', Items.gold_ingot, 'B', Blocks.log });
    }

}
