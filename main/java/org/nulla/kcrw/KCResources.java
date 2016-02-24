package org.nulla.kcrw;

import java.lang.reflect.Field;

import net.minecraft.util.ResourceLocation;

public class KCResources {
	
	public static ResourceLocation bg = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/bg.png");
	
	public static ResourceLocation aurora_strip_outside = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/aurora_strip_outside.png");
	public static ResourceLocation aurora_strip_inside = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/aurora_strip_inside.png");
	
	public static ResourceLocation icon_end = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/end.png");

	public static ResourceLocation item_peach_juice = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/peach_juice.png");
	
	public static ResourceLocation skill_empty_slot = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/empty_slot.png");
	public static ResourceLocation skill_empty_exp = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/empty_exp.png");
	public static ResourceLocation skill_speed_up = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/speed_up.png");
	public static ResourceLocation skill_strength_up = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/strength_up.png");
	public static ResourceLocation skill_vision_up = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/vision_up.png");
	
	public static ResourceLocation[] locations = {icon_end, item_peach_juice, skill_speed_up, skill_strength_up, skill_vision_up};
	
	public static ResourceLocation getLocationFromName(String name) {
		for (ResourceLocation i: locations) {
			if (i.getResourcePath().contains(name))
				return i;
		}
		return null;
	}
}
