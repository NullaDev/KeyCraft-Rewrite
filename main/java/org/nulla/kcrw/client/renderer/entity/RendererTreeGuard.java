package org.nulla.kcrw.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;

public class RendererTreeGuard extends RenderLiving {

	public RendererTreeGuard(ModelBase model, float p_i1262_2_) {
		super(model, p_i1262_2_);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
		return new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/models/entities/tree_guard.png");
	}

}
