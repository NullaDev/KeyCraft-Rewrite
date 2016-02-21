package org.nulla.kcrw;

import java.lang.reflect.Field;

import net.minecraft.util.ResourceLocation;

public class KCResources {
	
	public static ResourceLocation bg = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/bg.png");
	
	public static ResourceLocation aurora_strip_outside = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/aurora_strip_outside.png");
	public static ResourceLocation aurora_strip_inside = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/aurora_strip_inside.png");
	
	public static ResourceLocation icon_end = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/end.png");
	
	public static ResourceLocation item_peach_juice = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/peach_juice.png");

	
	
	public static ResourceLocation[] locations = {icon_end, item_peach_juice};
	
	public static ResourceLocation getLocationFromName(String name) {
		for (ResourceLocation i: locations) {
			System.out.println(i.getResourcePath());
			if (i.getResourcePath().contains(name))
				return i;
		}
		return null;
	}
}
