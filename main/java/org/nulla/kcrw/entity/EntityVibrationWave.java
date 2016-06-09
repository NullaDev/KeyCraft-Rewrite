package org.nulla.kcrw.entity;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityVibrationWave extends EntityHasOwner {
	
	private float mExp = 0;
	private float currentRadius = 0F;
	private float maxRadius = 4F;

	public EntityVibrationWave(World world) {
		super(world);
	}
	
	public EntityVibrationWave(EntityPlayer owner, int exp) {
		super(owner.worldObj);
		this.mExp = exp;
		this.maxRadius = 4 * 2048 / (2048 - exp);
		this.setOwner(owner);
		this.posX = owner.posX;
		this.posY = owner.posY;
		this.posZ = owner.posZ;
		this.rotationYaw = owner.rotationYaw;
	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		
		this.currentRadius += 0.1F;
		
		if (this.currentRadius >= this.maxRadius) {
			this.setDead();
		}
	}
	
	public float getcurrentRadius() {
		return this.currentRadius;
	}

}
