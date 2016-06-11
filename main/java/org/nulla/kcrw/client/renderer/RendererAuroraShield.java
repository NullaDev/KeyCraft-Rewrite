package org.nulla.kcrw.client.renderer;

import org.lwjgl.opengl.GL11;
import org.nulla.kcrw.entity.effect.EntityAuroraShield;

import net.minecraft.client.renderer.OpenGlHelper;
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
        GL11.glRotatef(-entity.rotationYaw, 0.0F, 1.0F, 0.0F);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        
        Tessellator tessellator = Tessellator.instance;
        
        float delta = 0.5F * (float) Math.sin(Math.PI * entity.mRestTick / 60);
        GL11.glColor4f(0.5F, 1F, 0.5F + delta, 0.125F);
        
        tessellator.startDrawingQuads();
        tessellator.addVertex( 0.5D, -1.6D,  0.5D);
        tessellator.addVertex( 0.5D, -1.6D, -0.5D);
        tessellator.addVertex(-0.5D, -1.6D, -0.5D);
        tessellator.addVertex(-0.5D, -1.6D,  0.5D);
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        tessellator.addVertex( 0.5D, 0.4D,  0.5D);
        tessellator.addVertex( 0.5D, 0.4D, -0.5D);
        tessellator.addVertex(-0.5D, 0.4D, -0.5D);
        tessellator.addVertex(-0.5D, 0.4D,  0.5D);
        tessellator.draw();
        
        for (int i = 0; i < 4; ++i) {
            GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, 1.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertex( 0.5D, -1.6D, 0.5D); // 右下
            tessellator.addVertex( 0.5D,  0.4D, 0.5D); // 右上
            tessellator.addVertex(-0.5D,  0.4D, 0.5D); // 左上
            tessellator.addVertex(-0.5D, -1.6D, 0.5D); // 左下
            tessellator.draw();
        }

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}

}
