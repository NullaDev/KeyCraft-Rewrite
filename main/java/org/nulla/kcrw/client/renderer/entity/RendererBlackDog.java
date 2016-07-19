package org.nulla.kcrw.client.renderer.entity;

import org.nulla.kcrw.KeyCraft_Rewrite;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RendererBlackDog extends RenderLiving {

	public RendererBlackDog(ModelBase model, float p_i1262_2_) {
		super(model, p_i1262_2_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/models/entities/black_dog.png");
	}
	
	protected float handleRotationFloat(EntityLivingBase par1, float par2)
    {
        return (0.65F - (6.0F - par1.getHealth()) * 0.07F) * (float)Math.PI;
    }

}
