package org.nulla.kcrw.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBaseball extends KCEntityThrowable {

	protected static final float SPEED_BASIC = 2.0F;
	protected static final float SPEED_HAS_SKILL = 10.0F;
	protected static final float DAMAGE_NO_SKILL = 5.0F;
	protected static final float DAMAGE_HAS_SKILL = 10.0F;
	
	protected String mSkill = "";
	
	protected Vec3 XZVec;
	
	protected boolean isExplosive = false;
	protected boolean isThundering = false;
	
	public EntityBaseball(World world) {
        super(world, 0.25f, 0.25F);
    }
	
	public EntityBaseball(World world, EntityLivingBase thrower) {
		this(world, thrower, 0.25F, 0.25F ,0.03F);
	}
	
	public EntityBaseball(World world, EntityLivingBase thrower, String skill) {
		this(world, thrower, 0.25F, 0.25F ,0.03F);
		this.mSkill = skill;
		if (skill == "explosion") {
			this.isExplosive = true;
		}
		if (skill == "thundering") {
			this.isThundering = true;
		}
		if (skill == "rolling") {
			this.velocityDecreaseRate = 1F;
		}
	}
	
	public EntityBaseball(World world, EntityLivingBase thrower, float width, float height, float gravity) {
        super(world, thrower, width, height, gravity);
        this.resetLocationAndSpeed();
    }
	
	protected void onImpact(MovingObjectPosition target) {
        if (target.entityHit != null) {
        	EntityLivingBase thrower = this.getThrower();
        	if (thrower instanceof EntityPlayer) {
        		target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), DAMAGE_NO_SKILL);
        	} else {
        		target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower == null ? this : thrower), DAMAGE_NO_SKILL);
        	}
        }
        
        if (this.isExplosive)
        	this.worldObj.createExplosion(thrower, posX, posY, posZ, 5.0F, false);
        
        if (this.isThundering)
        	this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, posX, posY, posZ));
        
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
        if (thrower instanceof EntityPlayer) {
        	this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, SPEED_BASIC, 0.0F);
        } else {
        	this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, SPEED_BASIC, 0.0F);
        }
		this.XZVec = Vec3.createVectorHelper(motionX, 0F, motionZ).normalize();

	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		if (mSkill == "rolling") {
			this.motionX += this.XZVec.zCoord * Math.cos(this.ticksInAir * Math.PI / 10) * 0.5F;
			this.motionZ += this.XZVec.xCoord * Math.cos(this.ticksInAir * Math.PI / 10) * 0.5F;
		}
	}

}
