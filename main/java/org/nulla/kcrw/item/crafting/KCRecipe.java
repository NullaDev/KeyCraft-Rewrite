package org.nulla.kcrw.item.crafting;

import java.util.ArrayList;

import org.nulla.kcrw.KCItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;

public class KCRecipe {
	
	protected ItemStack[] craftItems = new ItemStack[3];
	protected int productAmount;
	protected int auroraRequired;

	public KCRecipe(ItemStack[] itemstacks, int amount, int aurora) {
		for (int i = 0; i < itemstacks.length; i++)
			this.craftItems[i] = itemstacks[i];
		this.productAmount = amount;
		this.auroraRequired = aurora;
	}
	
	public ItemStack getCraftItemStack(int i) {
		return this.craftItems[i];
	}
	
	public Item getCraftItem(int i) {
		if (getCraftItemStack(i) != null)
			return this.craftItems[i].getItem();
		else
			return null;
	}
	
	public int getCraftItemAmount(int pos) {
		if (getCraftItemStack(pos) != null)
			return this.craftItems[pos].stackSize;
		else
			return 0;
	}
	
	public int getProductAmount() {
		return productAmount;
	}
	
	public int getAuroraRequired() {
		return auroraRequired;
	}
	
	public static Item getCraftItemFromNumber(int number) {
		for (Item i : getAllItems()) {
			if (Item.getIdFromItem(i) == number)
				return i;
		}
		return null;
	}
	
	public static ArrayList<Item> getItemCraftable(EntityPlayer player) {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(KCItems.peach_juice);
		items.add(KCItems.music_player);
		items.add(KCItems.aurora_iron_ingot);
		items.add(KCItems.steel_blade);		
		items.add(KCItems.hand_sonic);
		items.add(KCItems.javelin);
		items.add(KCItems.miracle_ribbon);
		return items;
	}
	
	public static ArrayList<Item> getAllItems() {
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(KCItems.peach_juice);
		items.add(KCItems.music_player);
		items.add(KCItems.aurora_iron_ingot);
		items.add(KCItems.steel_blade);
		items.add(KCItems.hand_sonic);
		items.add(KCItems.javelin);
		items.add(KCItems.miracle_ribbon);
		return items;
	}

}
