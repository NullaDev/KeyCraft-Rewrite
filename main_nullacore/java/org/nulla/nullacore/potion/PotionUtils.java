package org.nulla.nullacore.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.nulla.nullacore.api.potion.NullaPotion;

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
			
			NullaPotion.nextID = Potion.potionTypes.length;
			Potion[] newPotionTypes = new Potion[Potion.potionTypes.length + 32];
			// 复制
			for (int i = 0; i < Potion.potionTypes.length; i++)
				newPotionTypes[i] = Potion.potionTypes[i];
			
			// 修改
			potionTypesField.set(null, newPotionTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
