package org.nulla.kcrw.entity;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class KCEntityThrowable extends EntityHasOwner implements IProjectile {
	
    private int tileX = -1;
    private int tileY = -1;
    private int tileZ = -1;
    private Block field_145785_f;
    protected boolean inGround;
    public int throwableShake;
    private int ticksInGround;
    protected int ticksInAir;
    protected float mGravity = 0.03F;
    protected float velocityDecreaseRate = 0.99F;
    public int age = 1200;

    public KCEntityThrowable(World world, float width, float height) {
        super(world);
        this.setSize(width, height);
    }

    /**
     * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
     * length * 64 * renderDistanceWeight Args: distance
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
        d1 *= 64.0D;
        return distance < d1 * d1;
    }

    public KCEntityThrowable(World world, EntityPlayer thrower, float width, float height, float gravity) {
        super(world);
        this.setOwner((EntityPlayer)thrower);
        this.setSize(width, height);
        this.mGravity = gravity;
        this.setLocationAndAngles(thrower.posX, thrower.posY + (double)thrower.getEyeHeight(), thrower.posZ, thrower.rotationYaw, thrower.rotationPitch);
        this.posX -= (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.posY -= 0.10000000149011612D;
        this.posZ -= (double)(MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * 0.16F);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        float f = 0.4F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * f);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch) / 180.0F * (float)Math.PI) * f);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 1.5F, 1.0F);
    }

    public KCEntityThrowable(World world, double posX, double posY, double posZ, float width, float height) {
        super(world);
        this.ticksInGround = 0;
        this.setSize(width, height);
        this.setPosition(posX, posY, posZ);
        this.yOffset = 0.0F;
    }

    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void setThrowableHeading(double p_70186_1_, double p_70186_3_, double p_70186_5_, float p_70186_7_, float p_70186_8_) {
        float f2 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_3_ * p_70186_3_ + p_70186_5_ * p_70186_5_);
        p_70186_1_ /= (double)f2;
        p_70186_3_ /= (double)f2;
        p_70186_5_ /= (double)f2;
        p_70186_1_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_3_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_5_ += this.rand.nextGaussian() * 0.007499999832361937D * (double)p_70186_8_;
        p_70186_1_ *= (double)p_70186_7_;
        p_70186_3_ *= (double)p_70186_7_;
        p_70186_5_ *= (double)p_70186_7_;
        this.motionX = p_70186_1_;
        this.motionY = p_70186_3_;
        this.motionZ = p_70186_5_;
        float f3 = MathHelper.sqrt_double(p_70186_1_ * p_70186_1_ + p_70186_5_ * p_70186_5_);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(p_70186_1_, p_70186_5_) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(p_70186_3_, (double)f3) * 180.0D / Math.PI);
        this.ticksInGround = 0;
    }

    /** 设置Entity速度。 */
    @SideOnly(Side.CLIENT)
    public void setVelocity(double vx, double vy, double vz) {
        this.motionX = vx;
        this.motionY = vy;
        this.motionZ = vz;

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(vx * vx + vz * vz);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(vx, vz) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(vy, (double)f) * 180.0D / Math.PI);
        }
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        this.lastTickPosX = this.posX;
        this.lastTickPosY = this.posY;
        this.lastTickPosZ = this.posZ;
        super.onUpdate();

        if (this.throwableShake > 0) {
            --this.throwableShake;
        }

        if (!this.inGround) {
            ++this.ticksInAir;
        }

        Vec3 vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.rayTraceBlocks(vec3, vec31);
        vec3 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        vec31 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (movingobjectposition != null) {
            vec31 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        if (!this.worldObj.isRemote) {
            Entity entity = null;
            List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
            double d0 = 0.0D;
            EntityPlayer entitylivingbase = this.getOwner();

            for (int j = 0; j < list.size(); ++j) {
                Entity entity1 = (Entity)list.get(j);

                if (entity1.canBeCollidedWith() && (entity1 != entitylivingbase || this.ticksInAir >= 5)) {
                    float f = 0.3F;
                    AxisAlignedBB axisalignedbb = entity1.boundingBox.expand((double)f, (double)f, (double)f);
                    MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec3, vec31);

                    if (movingobjectposition1 != null) {
                        double d1 = vec3.distanceTo(movingobjectposition1.hitVec);

                        if (d1 < d0 || d0 == 0.0D) {
                            entity = entity1;
                            d0 = d1;
                        }
                    }
                }
            }

            if (entity != null) {
                movingobjectposition = new MovingObjectPosition(entity);
            }
        }

        if (movingobjectposition != null) {
            if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK && this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ) == Blocks.portal) {
                this.setInPortal();
            } else {
                this.onImpact(movingobjectposition);
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        float f1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f1) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
            this.prevRotationYaw += 360.0F;
        }

        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

        float f2 = velocityDecreaseRate;
        if (this.isInWater()) {
            for (int i = 0; i < 4; ++i) {
                float f4 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }

            f2 = 0.8F;
        }

        //速度衰减
        this.motionX *= (double)f2;
        this.motionY *= (double)f2;
        this.motionZ *= (double)f2;
        //因为重力下降
        this.motionY -= (double)this.getGravityVelocity();
        this.setPosition(this.posX, this.posY, this.posZ);
        if (this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ <= 0.0064F)
        	this.setDead();
        if (this.ticksInAir >= this.age)
        	this.setDead();
    }

    /**
     * Gets the amount of gravity to apply to the thrown entity with each tick.
     */
    @Deprecated
    protected float getGravityVelocity() {
        return mGravity;
    }

    /**
     * Called when this EntityThrowable hits a block or entity.
     */
    protected abstract void onImpact(MovingObjectPosition p_70184_1_);

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound nbt) {
    	super.writeEntityToNBT(nbt);
    	nbt.setShort("xTile", (short)this.tileX);
    	nbt.setShort("yTile", (short)this.tileY);
    	nbt.setShort("zTile", (short)this.tileZ);
    	nbt.setByte("inTile", (byte)Block.getIdFromBlock(this.field_145785_f));
    	nbt.setByte("shake", (byte)this.throwableShake);
    	nbt.setByte("inGround", (byte)(this.inGround ? 1 : 0));
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound nbt) {
    	super.readEntityFromNBT(nbt);
        this.tileX = nbt.getShort("xTile");
        this.tileY = nbt.getShort("yTile");
        this.tileZ = nbt.getShort("zTile");
        this.field_145785_f = Block.getBlockById(nbt.getByte("inTile") & 255);
        this.throwableShake = nbt.getByte("shake") & 255;
        this.inGround = nbt.getByte("inGround") == 1;
    }

    @SideOnly(Side.CLIENT)
    public float getShadowSize() {
        return 0.0F;
    }
}