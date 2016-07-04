package org.nulla.kcrw.entity;

import java.util.Random;

import org.nulla.kcrw.entity.effect.EntityParticleFX;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityRibbon extends KCEntityThrowable {

	protected static final float SPEED = 0.5F;
	protected static final float DAMAGE = 5.0F;
	
	public final double oriPosX;
	public final double oriPosY;
	public final double oriPosZ;
	
		
	public EntityRibbon(World world) {
        super(world, 0.2f, 0.2F);
        this.oriPosX = this.posX;
        this.oriPosY = this.posY;
        this.oriPosZ = this.posZ;
    }
	
	public EntityRibbon(World world, EntityLivingBase thrower) {
		this(world, thrower, 1F, 1F ,0F);
	}
	
	
	public EntityRibbon(World world, EntityLivingBase thrower, float width, float height, float gravity) {
        super(world, thrower, width, height, gravity);
        this.oriPosX = thrower.posX;
        this.oriPosY = thrower.posY + thrower.getEyeHeight();
        this.oriPosZ = thrower.posZ;
        this.resetLocationAndSpeed();

    }
	
	protected void onImpact(MovingObjectPosition target) {
        if (target.entityHit != null) {
        	EntityLivingBase thrower = this.getThrower();
    		target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), DAMAGE);
        }
        if (!this.worldObj.isRemote) {
            this.setDead();
        }
    }
	
    // 重新设置位置和速度
	protected void resetLocationAndSpeed() {
        this.setLocationAndAngles(thrower.posX, thrower.posY + (double)thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, SPEED, 0.0F);
	}

}
