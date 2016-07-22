package org.nulla.kcrw.client.renderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.EntityJavelin;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RendererJavelin extends Render {
	
	private static final ResourceLocation JavelinTexture = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/entity/javelin.png");
	
	public void doRender(EntityJavelin entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
		this.bindEntityTexture(entity);
		GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        //原点移到实体中心
        GL11.glTranslatef(0.0F, entity.height / 2.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch, 0.0F, 0.0F, 1.0F);
        
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);

        float f10 = 0.5625F;
        GL11.glScalef(f10, f10, f10);
        for (int i = 0; i < 4; ++i)
        {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, f10);
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(-3.25D, -0.5D, 0.0D,  1.0D / 16.0D, 14.0D / 16.0D); // 左下
            tessellator.addVertexWithUV( 3.25D, -0.5D, 0.0D, 14.0D / 16.0D,  1.0D / 16.0D); // 左上
            tessellator.addVertexWithUV( 3.25D,  0.5D, 0.0D, 16.0D / 16.0D,  3.0D / 16.0D); // 右上
            tessellator.addVertexWithUV(-3.25D,  0.5D, 0.0D,  3.0D / 16.0D, 16.0D / 16.0D); // 右下
            tessellator.draw();
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
	
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return JavelinTexture;
    }
	
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityJavelin)entity, x, y, z, p_76986_8_, p_76986_9_);
    }
	
}

