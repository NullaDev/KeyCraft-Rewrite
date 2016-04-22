package org.nulla.kcrw.entity.effect;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAuroraBlast extends EntityEffect {
	
	private int livingTime = 0;

	public EntityAuroraBlast(World world)
	{
		super(world);
	}

	public EntityAuroraBlast(World world, double x, double y, double z, float yaw, float pitch, int livingTime)
	{
		this(world);
		this.setPositionAndRotation(x, y, z, yaw, pitch);
		this.livingTime = livingTime;
	}
	
	public EntityAuroraBlast(Entity user, int livingTime)
	{
		this(user.worldObj, user.posX, user.posY, user.posZ, user.rotationYaw, user.rotationPitch, livingTime);
	}
	
	public Vec3 getLookVec()
    {
		float f1 = MathHelper.cos(-this.rotationYaw * 0.017453292F - (float)Math.PI);
		float f2 = MathHelper.sin(-this.rotationYaw * 0.017453292F - (float)Math.PI);
		float f3 = -MathHelper.cos(-this.rotationPitch * 0.017453292F);
		float f4 = MathHelper.sin(-this.rotationPitch * 0.017453292F);
        return Vec3.createVectorHelper((double)(f2 * f3), (double)f4, (double)(f1 * f3));
    }
	
	public void onUpdate()
    {
        super.onUpdate();
        if (this.ticksExisted > this.livingTime)
            this.setDead();
        // 不用管客户端
        if (this.worldObj.isRemote)
        	return;
        
        if ((this.ticksExisted - 1) % 10 == 0) // 10tick一次
        {
        	int i = (this.ticksExisted - 1) / 10;
        	Vec3 direct = this.getLookVec();
        	Double expX = this.posX + i * direct.xCoord * 2;
			Double expY = this.posY + i * direct.yCoord * 2;
			Double expZ = this.posZ + i * direct.zCoord * 2;
			float expS = 2.0F + 0.2F * i;
        	worldObj.createExplosion(this, expX, expY, expZ, expS, true);
        }
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		this.livingTime = nbt.getInteger("livingTime");
		super.readEntityFromNBT(nbt);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setInteger("livingTime", this.livingTime);
		super.writeEntityToNBT(nbt);
	}

}
