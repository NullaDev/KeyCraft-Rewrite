package org.nulla.kcrw.entity;

import java.util.Random;

import org.nulla.kcrw.skill.SkillsRw;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBaseball extends KCEntityThrowable {

	protected static final float SPEED_BASIC = 1.5F;
	protected static final float SPEED_SHOOTING = 3.0F;
	
	protected static final float DAMAGE_BASIC = 5.0F;
	protected static final float DAMAGE_SHOOTING = 8.0F;
	
	protected String mSkill = "";
	
	protected Vec3 XZVec;
	
	protected boolean isExplosive = false;
	protected boolean isThundering = false;
	
	private double extraX = 0F;
	private double extraZ = 0F;
	
	public EntityBaseball(World world) {
        super(world, 0.25f, 0.25F);
    }
	
	public EntityBaseball(World world, EntityPlayer thrower) {
		this(world, thrower, 0.25F, 0.25F, 0.03F);
	}
	
	public EntityBaseball(World world, EntityPlayer thrower, String skill) {
		super(world, thrower, 0.25F, 0.25F, 0.03F);
		this.mSkill = skill;
		resetLocationAndSpeed();
		if (skill == "explosion")
			this.isExplosive = true;
		
		if (skill == "thundering")
			this.isThundering = true;
		
		if (skill == "rolling")
			this.velocityDecreaseRate = 1F;
		
		if (skill == "shooting")
			this.velocityDecreaseRate = 0.97F;
		
		//避免落地太快给一点初速度
		if (skill == "falling")
			this.motionY += 0.5F;
		
	}
	
	public EntityBaseball(World world, EntityPlayer thrower, float width, float height, float gravity) {
        super(world, thrower, width, height, gravity);
        this.resetLocationAndSpeed();
    }
	
	protected void onImpact(MovingObjectPosition target) {
    	EntityPlayer thrower = this.getOwner();
        if (target.entityHit != null) {
        	if (thrower instanceof EntityPlayer) {
        		if (this.mSkill == "falling") {
            		float extra = 2048F / (2048 - SkillsRw.BaseballFalling.getExperience(thrower));
        			target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), DAMAGE_BASIC + extra * ticksInAir / 10);
        		} else if (this.mSkill == "rolling") {
            		float extra = 2048F / (2048 - SkillsRw.BaseballRolling.getExperience(thrower));
        			target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), DAMAGE_BASIC + extra * 5 * new Random().nextFloat());
        		} else if (this.mSkill == "shooting") {
            		float extra = 4096F / (2048 - SkillsRw.BaseballShooting.getExperience(thrower));
        			target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), DAMAGE_SHOOTING + extra);
        		} else
            		target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), DAMAGE_BASIC);
        	} else {
        		target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower == null ? this : thrower), DAMAGE_BASIC);
        	}
        }
        
        if (this.isExplosive) {
    		float extra = 2048F / (2048 - SkillsRw.BaseballExplosive.getExperience(thrower));
        	this.worldObj.createExplosion(thrower, posX, posY, posZ, 3.0F * extra, false);
        }
        
        if (this.isThundering)
        	this.worldObj.addWeatherEffect(new EntityLightningBolt(this.worldObj, posX, posY, posZ));
        
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
        if (this.mSkill == "shooting") {
        	System.out.println("shooting");
        	this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, SPEED_SHOOTING, 0.0F);
        } else {
        	System.out.println("basic");
        	this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, SPEED_BASIC, 0.0F);
        }
		this.XZVec = Vec3.createVectorHelper(motionX, 0F, motionZ).normalize();

	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		if (mSkill == "rolling") {
			this.posX -= extraX;
			this.posZ -= extraZ;
			extraX = this.XZVec.zCoord * Math.sin(this.ticksInAir * Math.PI / 20) * 10F;
			extraZ = this.XZVec.xCoord * Math.sin(this.ticksInAir * Math.PI / 20) * 10F;
			this.posX += extraX;
			this.posZ += extraZ;
		}
		if (mSkill == "falling") {
			this.mGravity += 0.01F;
		}
	}

}
