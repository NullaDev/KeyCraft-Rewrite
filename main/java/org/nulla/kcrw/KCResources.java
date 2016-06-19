package org.nulla.kcrw;

import java.lang.reflect.Field;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class KCResources {
	
	public static ResourceLocation gui_kotori_workshop = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/gui/gui_kotori_workshop.png");
	
	public static ResourceLocation btn_craft = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/btn_craft.png");
	public static ResourceLocation btn_decompose = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/btn_decompose.png");
	public static ResourceLocation btn_end = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/end.png");
	public static ResourceLocation btn_ensure = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/btn_ensure.png");
	public static ResourceLocation btn_return = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/btn_return.png");

	public static ResourceLocation music_pump_it = new ResourceLocation(KeyCraft_Rewrite.MODID, "music.test");
	public static ResourceLocation music_tori_no_uta = new ResourceLocation(KeyCraft_Rewrite.MODID, "music.tori_no_uta");
	
	public static ResourceLocation sound_aurora = new ResourceLocation(KeyCraft_Rewrite.MODID, "sound.aurora");
	
	public static ResourceLocation potion_aurora_regeneration = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/potions/potion_aurora_regeneration.png");
	public static ResourceLocation potion_poisonResistance = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/potions/poison_resistance.png");
	
	
	public static ResourceLocation getResourcebyID(int id) {
		Item item = Item.getItemById(id);
		String name = item.getUnlocalizedName().replace("item.", "");
		return new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/items/" + name + ".png");
	}
}
