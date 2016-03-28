package org.nulla.kcrw.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class KCPotion extends Potion {
	
	private static int nextID;
	
	public static void init()
	{
		try {
			Field potionTypesField = Potion.class.getDeclaredField("potionTypes");
			
			// 去掉final
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(potionTypesField, potionTypesField.getModifiers() & ~Modifier.FINAL);
			
			nextID = Potion.potionTypes.length;
			Potion[] newPotionTypes = new Potion[Potion.potionTypes.length + 32];
			// 复制
			for (int i = 0; i < Potion.potionTypes.length; i++)
				newPotionTypes[i] = Potion.potionTypes[i];
			
			// 修改
			potionTypesField.set(null, newPotionTypes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 注册所有效果
		KCPotions.initPotions();
	}
	
	
	public final ResourceLocation mIcon;
		
	public KCPotion(String name, ResourceLocation icon) {
		super(nextID++, false, 0);
		
		this.setPotionName(name);
		this.mIcon = icon;
	}
	
	/** 重载实现效果 */
	@Override
	public void performEffect(EntityLivingBase entity, int level) { }

	@Override
	public boolean isReady(int duration, int level)
	{
		return true;
	}
	
	// 以后实现
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
	{
		mc.getTextureManager().bindTexture(mIcon);
		
		x += 6;
		y += 7;
		final int width = 18, height = 18;
		
		Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(x + width, y + height, 0.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(x + width, y, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(x, y, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
	}

}
