package org.nulla.kcrw;

import java.lang.reflect.Field;

import net.minecraft.util.ResourceLocation;

public class KCResources {
	
	public static ResourceLocation gui_kotori_workshop = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/gui/gui_kotori_workshop.png");
	
	public static ResourceLocation aurora_strip_outside = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/aurora_strip_outside.png");
	public static ResourceLocation aurora_strip_inside = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/aurora_strip_inside.png");
	
	public static ResourceLocation icon_end = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/end.png");
	public static ResourceLocation icon_craft = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/craft.png");

	public static ResourceLocation item_peach_juice = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/items/peach_juice.png");
	public static ResourceLocation item_music_player = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/items/music_player.png");

	public static ResourceLocation skill_empty_slot = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/empty_slot.png");
	public static ResourceLocation skill_passive_on = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/passive_on.png");
	public static ResourceLocation skill_passive_off = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/passive_off.png");
	public static ResourceLocation skill_empty_exp = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/empty_exp.png");
	
	public static ResourceLocation music_pump_it = new ResourceLocation(KeyCraft_Rewrite.MODID, "music.test");
	public static ResourceLocation music_tori_no_uta = new ResourceLocation(KeyCraft_Rewrite.MODID, "music.tori_no_uta");
	
	public static ResourceLocation sound_aurora = new ResourceLocation(KeyCraft_Rewrite.MODID, "sound.aurora");
	
	public static ResourceLocation potion_poisonResistance = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/potions/poison_resistance.png");
	
	public static ResourceLocation[] locations = {icon_end, icon_craft, item_peach_juice, item_music_player};
	
	public static ResourceLocation getLocationFromName(String name) {
		for (ResourceLocation i: locations) {
			if (i.getResourcePath().contains(name))
				return i;
		}
		return null;
	}
}
