package org.nulla.kcrw.entity.entityliving;

import org.nulla.kcrw.KeyCraft_Rewrite;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RendererBlackDog extends RenderLiving {

	public RendererBlackDog(ModelBase model, float p_i1262_2_) {
		super(model, p_i1262_2_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/models/entities/black_dog.png");
	}

}
