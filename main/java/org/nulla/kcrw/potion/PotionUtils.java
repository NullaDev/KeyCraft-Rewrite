package org.nulla.kcrw.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;

public class PotionUtils {
	
	public static void init()
	{
		try {
			Field potionTypesField = Potion.class.getDeclaredField("potionTypes");
			
			// 去掉final
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(potionTypesField, potionTypesField.getModifiers() & ~Modifier.FINAL);
			
			Potion[] newPotionTypes = new Potion[256];
			// 复制
			for (int i = 0; i < 32; i++)
				newPotionTypes[i] = Potion.potionTypes[i];
			
			// 修改
			potionTypesField.set(null, newPotionTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
