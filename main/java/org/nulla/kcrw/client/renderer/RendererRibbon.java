package org.nulla.kcrw.client.renderer;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.lwjgl.opengl.GL11;
import org.nulla.kcrw.entity.EntityRibbon;

public class RendererRibbon extends Render {

	@Override
	public void doRender(Entity entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityRibbon)entity, x, y, z, p_76986_8_, p_76986_9_);
    }
	
	public void doRender(EntityRibbon entity, double x, double y, double z, float p_76986_8_, float p_76986_9_) {		
		GL11.glPushMatrix();
        Vec3 origPos = entity.getOrigPos();
        GL11.glTranslated(x - entity.posX + origPos.xCoord, y - entity.posY + origPos.yCoord, z - entity.posZ + origPos.zCoord);
        GL11.glRotatef(entity.prevRotationYaw - 90.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(entity.prevRotationPitch, 0.0F, 0.0F, 1.0F);
        GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
        
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        
        Tessellator tessellator = Tessellator.instance;
        
        GL11.glColor4f(0.75F, 0F, 0F, 0.5F);
		tessellator.setBrightness(1);
		
        double length = Math.sqrt((entity.posX - origPos.xCoord) * (entity.posX - origPos.xCoord) 
        		+ (entity.posY - origPos.zCoord) * (entity.posY - origPos.zCoord) 
        		+ (entity.posZ - origPos.zCoord) * (entity.posZ - origPos.zCoord));
        for (int i = 0; i < 4; ++i)
        {
            GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
            GL11.glNormal3f(0.0F, 0.0F, 1.0F);
            tessellator.startDrawingQuads();
            tessellator.addVertex(0.0D, -0.2D, 0.0D);
            tessellator.addVertex(length, -0.2D, 0.0D);
            tessellator.addVertex(length, 0.2D, 0.0D);
            tessellator.addVertex(0.0D, 0.2D, 0.0D);
            tessellator.draw();
        }

        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return null;
	}
}
