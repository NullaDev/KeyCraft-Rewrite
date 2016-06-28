package org.nulla.kcrw.entity.effect;

import org.nulla.kcrw.entity.EntityHasOwner;
import org.nulla.kcrw.skill.SkillsRw;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityAuroraShield extends EntityHasOwner {
	
	public float mShieldValue = 20;
	public int mRestTick = 60 * 20;
	public boolean isActive = false;
	
	public EntityAuroraShield(World world) {
		super(world);
	}
	
	public EntityAuroraShield(World world, EntityPlayer owner) {
		this(world, owner, 16, 60 * 20);
	}
	
	public EntityAuroraShield(World world, EntityPlayer owner, int value, int tick) {
		super(world);
		this.setOwner(owner);
		this.posX = owner.posX;
		this.posY = owner.posY;
		this.posZ = owner.posZ;
		this.rotationYaw = owner.rotationYaw;
		this.isActive = true;
		this.mShieldValue = value;
		this.mRestTick = tick;
	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		
		EntityPlayer owner = this.getOwner();
		if (owner == null) {
			this.setDead();
			this.isActive = false;
			return;
		}
		
		if (this.mRestTick <= 0 || this.mShieldValue <= 0) {
			this.setDead();
			this.isActive = false;
			return;
		} else {
			this.mRestTick--;
		}
		
		this.posX = owner.posX;
		this.posY = owner.posY;
		this.posZ = owner.posZ;
		this.rotationYaw = owner.rotationYaw;
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.mShieldValue = nbt.getFloat("ShieldValue");
		this.mRestTick = nbt.getInteger("RestTick");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setFloat("ShieldValue", this.mShieldValue);
		nbt.setInteger("RestTick", this.mRestTick);
	}

}
