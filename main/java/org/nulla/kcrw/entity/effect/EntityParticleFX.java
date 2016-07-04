package org.nulla.kcrw.entity.effect;

import org.nulla.kcrw.KeyCraft_Rewrite;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityParticleFX extends EntityFX {
	private static TextureManager textureManager = Minecraft.getMinecraft().renderEngine;
	private static ResourceLocation texture = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/particles/particle.png");

	public EntityParticleFX(World world, double posX, double posY, double posZ, Vec3 direction) {
		this(world, posX, posY, posZ, direction, 1F);
	}
	
	public EntityParticleFX(World world, double posX, double posY, double posZ, Vec3 direction, float scale) {
		super(world, posX, posY, posZ);
		double speed = 0.1D + rand.nextDouble() * 0.1D;
		setVelocity(direction.xCoord * speed, direction.yCoord * speed, direction.zCoord * speed);
		this.particleGravity = 0.0f;
		this.particleAlpha = 1.0f;
		this.particleScale = scale;
		this.particleMaxAge = (int)(20f / (this.rand.nextFloat() * 0.5f + 0.5f));
		this.noClip = true;
	}

	@Override
	public int getFXLayer() {
		return 2;
	}

	@Override
	public void renderParticle(Tessellator tessellator, float delta, float rotationX, float rotationXZ,
			float rotationZ, float rotationYZ, float rotationXY) {
		tessellator.draw();
		tessellator.startDrawingQuads();
		textureManager.bindTexture(texture);
		float f6 = 0.0f;
		float f7 = 1.0f;
		float f8 = 0.0f;
		float f9 = 1.0f;
		float scale = 0.1F * this.particleScale;

		float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)delta - interpPosX);
		float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)delta - interpPosY);
		float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)delta - interpPosZ);
		tessellator.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
		tessellator.setBrightness(1);
		tessellator.addVertexWithUV((double)(f11 - rotationX * scale - rotationYZ * scale), (double)(f12 - rotationXZ * scale), (double)(f13 - rotationZ * scale - rotationXY * scale), (double)f7, (double)f9);
		tessellator.addVertexWithUV((double)(f11 - rotationX * scale + rotationYZ * scale), (double)(f12 + rotationXZ * scale), (double)(f13 - rotationZ * scale + rotationXY * scale), (double)f7, (double)f8);
		tessellator.addVertexWithUV((double)(f11 + rotationX * scale + rotationYZ * scale), (double)(f12 + rotationXZ * scale), (double)(f13 + rotationZ * scale + rotationXY * scale), (double)f6, (double)f8);
		tessellator.addVertexWithUV((double)(f11 + rotationX * scale - rotationYZ * scale), (double)(f12 - rotationXZ * scale), (double)(f13 + rotationZ * scale - rotationXY * scale), (double)f6, (double)f9);
		tessellator.draw();
		tessellator.startDrawingQuads();
		textureManager.bindTexture(TextureMap.locationItemsTexture);
	}
	
}
