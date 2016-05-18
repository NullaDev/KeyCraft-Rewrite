package org.nulla.nullacore.api.potion;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public class NullaPotion extends Potion {
	
	/** 在PotionUtils初始化，其他模块不要修改！ */
	public static int nextID;
	
	
	public final ResourceLocation mIcon;
		
	public NullaPotion(String name, ResourceLocation icon) {
		super(nextID++, false, 0);
		
		this.setPotionName(name);
		this.mIcon = icon;
	}
	
	/** 重载实现效果 */
	@Override
	public void performEffect(EntityLivingBase entity, int level) { }

	@Override
	public boolean isReady(int duration, int level) {
		return true;
	}
	
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc) {
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
