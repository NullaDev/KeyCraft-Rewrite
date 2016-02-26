package org.nulla.kcrw.block;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.item.KCItemBase;
import org.nulla.kcrw.item.crafting.KCRecipe;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class KCBlockBase extends Block {

	private KCRecipe craftRecipe;

	public KCBlockBase(Material material) {
		super(material);
		this.setCreativeTab(KeyCraft_Rewrite.KCCreativeTab);
	}
	
	public KCBlockBase setRecipe(KCRecipe recipe) {
		this.craftRecipe = recipe;
		return this;
	}
	
	public KCRecipe getRecipe() {
		return this.craftRecipe;
	}

}
