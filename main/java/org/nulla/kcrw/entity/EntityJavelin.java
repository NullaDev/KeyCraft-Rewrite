package org.nulla.kcrw.entity;

import java.util.Random;

import org.nulla.kcrw.entity.effect.EntityParticleFX;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityJavelin extends KCEntityThrowable {

	protected static final float SPEED_BASIC = 0.5F;
	protected static final float SPEED_HAS_SKILL = 2.0F;
	protected static final float DAMAGE_BASIC = 10.0F;
	protected static final float DAMAGE_HAS_SKILL = 20.0F;
	
	private boolean mHasSkill = false;
	
	public EntityJavelin(World world) {
        super(world, 0.5f, 0.5F);
    }
	
	public EntityJavelin(World world, EntityPlayer thrower) {
		this(world, thrower, 0.5F, 0.5F ,0.01F);
	}
	
	public EntityJavelin(World world, EntityPlayer thrower, boolean hasSkill) {
		this(world, thrower, 0.5F, 0.5F, hasSkill ? 0F : 0.01F);
		this.mHasSkill = hasSkill;
		if (hasSkill) {
			this.velocityDecreaseRate = 1.01F;
		}
	}
	
	public EntityJavelin(World world, EntityPlayer thrower, float width, float height, float gravity) {
        super(world, thrower, width, height, gravity);
        this.resetLocationAndSpeed();
        this.age = 200;
    }
	
	protected void onImpact(MovingObjectPosition target) {
        if (target.entityHit != null) {
        	EntityLivingBase thrower = this.getOwner();
        	if (mHasSkill) {
    			target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), DAMAGE_HAS_SKILL);
        	} else {
    			target.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, thrower), DAMAGE_BASIC);
        	}
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
        if (!mHasSkill) {
        	this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, SPEED_BASIC, 0.0F);
        } else {
        	this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, SPEED_HAS_SKILL, 0.0F);
        }
	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		
		//无技能无粒子效果
		if (!this.mHasSkill) {
			return;
		}
		
		//4tick释放一次
		if (this.ticksExisted % 4 != 0) {
			return;
		}
		
		//释放粒子
		for(int i = 0; i < 4; i++) {
			Random ran = new Random();
			Vec3 vec = Vec3.createVectorHelper(-this.motionX, -this.motionY, -this.motionZ).normalize();
			vec.rotateAroundX(0.1F - 0.2F * ran.nextFloat());
			vec.rotateAroundY(0.1F - 0.2F * ran.nextFloat());
			vec.rotateAroundZ(0.1F - 0.2F * ran.nextFloat());

			EntityParticleFX par = new EntityParticleFX(this.worldObj, this.posX, this.posY, this.posZ, vec, 0.5F);
			par.setRBGColorF(1F, 0F, 0F);
			Minecraft.getMinecraft().effectRenderer.addEffect(par);
		}
		
		if (this.mHasSkill) {
			double motionXZ = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationPitch = (float) Math.atan(this.motionY / motionXZ);
		}
		
	}

}

