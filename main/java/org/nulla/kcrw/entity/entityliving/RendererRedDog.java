package org.nulla.kcrw.entity.entityliving;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;

public class RendererRedDog extends RenderLiving {

	public RendererRedDog(ModelBase model, float p_i1262_2_) {
		super(model, p_i1262_2_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/models/entities/red_dog.png");
	}
	
	protected float handleRotationFloat(EntityLivingBase par1, float par2)
    {
		return (0.65F - (12.0F - par1.getHealth()) * 0.035F) * (float)Math.PI;
    }

}