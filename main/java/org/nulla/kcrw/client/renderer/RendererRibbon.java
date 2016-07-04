package org.nulla.kcrw.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.nulla.kcrw.entity.EntityRibbon;

public class RendererRibbon extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityRibbon)entity, x, y, z, p_76986_8_, p_76986_9_);
    }
	
	public void doRender(EntityRibbon entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {		
		GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        
        Tessellator tessellator = Tessellator.instance;
        
        GL11.glColor4f(0.75F, 0F, 0F, 0.5F);
		tessellator.setBrightness(1);
		
            GL11.glNormal3f(0.0F, 0.0F, 1.0F);
            tessellator.startDrawingQuads();
            double a = Math.atan((entity.posZ - entity.oriPosZ) / (entity.posX - entity.oriPosX));
            double dx = 1D * Math.sin(a);
            double dz = 1D * Math.cos(a);
            tessellator.addVertex(entity.oriPosX + dx, entity.oriPosY, entity.oriPosZ + dz);
            tessellator.addVertex(entity.oriPosX - dx, entity.oriPosY, entity.oriPosZ - dz);
            tessellator.addVertex(entity.posX - dx, entity.posY, entity.posZ - dz);
            tessellator.addVertex(entity.posX + dx, entity.posY, entity.posZ + dz);
            tessellator.draw();

        GL11.glPopMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
