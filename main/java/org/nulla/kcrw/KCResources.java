package org.nulla.kcrw;

import java.lang.reflect.Field;

import net.minecraft.util.ResourceLocation;

public class KCResources {
	
	public static ResourceLocation end = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/end.png");
	public static ResourceLocation baseball = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/baseball.png");

	public static ResourceLocation[] locations = {end, baseball};
	
	public static ResourceLocation getLocationFromName(String name) {
		for (ResourceLocation i: locations) {
			System.out.println(i.getResourcePath());
			if (i.getResourcePath().contains(name))
				return i;
		}
		return null;
	}
}
