package org.nulla.kcrw.entity;

import java.util.Random;

import org.nulla.kcrw.entity.effect.EntityParticleFX;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRibbon extends KCEntityThrowable {

	protected static final float SPEED = 0.5F;
	protected static final float DAMAGE = 5.0F;
	
		
	public EntityRibbon(World world) {
        super(world, 0.2f, 0.2F);
        this.setOrigPos(this.posX, this.posY, this.posZ);
        this.ignoreFrustumCheck = true;
    }
	
	public EntityRibbon(World world, EntityPlayer thrower) {
		this(world, thrower, 1F, 1F ,0F);
	}
	
	
	public EntityRibbon(World world, EntityPlayer thrower, float width, float height, float gravity) {
        super(world, thrower, width, height, gravity);
        this.setOrigPos(thrower.posX, thrower.posY + thrower.getEyeHeight(), thrower.posZ);
        this.resetLocationAndSpeed();
        this.ignoreFrustumCheck = true;

    }
	
	@Override
	protected void entityInit() {
		super.entityInit();
        this.dataWatcher.addObject(17, Float.valueOf(0.0F));
        this.dataWatcher.addObject(18, Float.valueOf(0.0F));
        this.dataWatcher.addObject(19, Float.valueOf(0.0F));
	}
	
	public void setOrigPos(double x, double y, double z)
	{
		this.dataWatcher.updateObject(17, Float.valueOf((float)x));
		this.dataWatcher.updateObject(18, Float.valueOf((float)y));
		this.dataWatcher.updateObject(19, Float.valueOf((float)z));
	}
	
	public Vec3 getOrigPos()
	{
		return Vec3.createVectorHelper(this.dataWatcher.getWatchableObjectFloat(17), 
				this.dataWatcher.getWatchableObjectFloat(18), 
				this.dataWatcher.getWatchableObjectFloat(19));
	}
	
	protected void onImpact(MovingObjectPosition target) {
        if (target.entityHit != null) {
        	EntityPlayer thrower = this.getOwner();
    		target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), DAMAGE);
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
	
    // 重新设置位置和速度
	protected void resetLocationAndSpeed() {
		EntityPlayer thrower = this.getOwner();
        this.setLocationAndAngles(thrower.posX, thrower.posY + (double)thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, SPEED, 0.0F);
	}

}
