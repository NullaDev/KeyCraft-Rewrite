package org.nulla.kcrw.client.renderer;

import org.lwjgl.opengl.GL11;
import org.nulla.kcrw.entity.effect.EntityAuroraShield;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RendererAuroraShield extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityAuroraShield)entity, x, y, z, p_76986_8_, p_76986_9_);
    }
	
	public void doRender(EntityAuroraShield entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        
        Tessellator tessellator = Tessellator.instance;
        
        GL11.glEnable(GL11.GL_ALPHA);
        for (int i = 0; i < 4; ++i) {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            tessellator.startDrawingQuads();
            tessellator.setColorRGBA(0, 255, 127, 127);
            tessellator.addVertex(-3.25D, -0.5D, 0.0D); // 左下
            tessellator.addVertex( 3.25D, -0.5D, 0.0D); // 左上
            tessellator.addVertex( 3.25D,  0.5D, 0.0D); // 右上
            tessellator.addVertex(-3.25D,  0.5D, 0.0D); // 右下
            tessellator.draw();
        }
        GL11.glDisable(GL11.GL_ALPHA);

        GL11.glPopMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}

}
