package org.nulla.kcrw.item;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.item.crafting.KCRecipe;

import net.minecraft.item.Item;

public abstract class KCItemBase extends Item {
	
	protected KCRecipe craftRecipe;
	
	public KCItemBase() {
		this.setCreativeTab(KeyCraft_Rewrite.KCCreativeTab);
		KCItems.items.add(this);
	}

	public KCItemBase setRecipe(KCRecipe recipe) {
		this.craftRecipe = recipe;
		return this;
	}
	
	public KCRecipe getRecipe() {
		return this.craftRecipe;
	}
	
}
