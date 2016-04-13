package org.nulla.kcrw.entity;

import org.nulla.kcrw.skill.SkillUtils;

import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityJumpHelper;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class MonsterBase extends EntityLivingBase
{
	/** Equipment (armor and held item) for this entity. */
    private ItemStack[] equipment = new ItemStack[5];
    /** The experience points the Entity gives. */
    protected int experienceValue;
    public final EntityAITasks tasks;
    public final EntityAITasks targetTasks;
    /** The active target the Task system uses for tracking */
    private EntityLivingBase attackTarget;
    
	public MonsterBase(World p_i1594_1_) {
		super(p_i1594_1_);
		this.tasks = new EntityAITasks(p_i1594_1_ != null && p_i1594_1_.theProfiler != null ? p_i1594_1_.theProfiler : null);
        this.targetTasks = new EntityAITasks(p_i1594_1_ != null && p_i1594_1_.theProfiler != null ? p_i1594_1_.theProfiler : null);
	}

	/**重写以返回实体死亡时掉落的欧若拉点数*/
	public int getDeathAuroraDrop() {
		return 0;
	}
	
	@Override
	public void onDeath(DamageSource par1) {
		super.onDeath(par1);
		//死亡加欧若拉
		if (par1.getEntity() instanceof EntityPlayer) {
			EntityPlayer player=(EntityPlayer) par1.getEntity();
			SkillUtils.setAuroraPoint(player, SkillUtils.getAuroraPoint(player)+getDeathAuroraDrop());
		}
		if (worldObj.isRemote) {
			worldObj.spawnParticle("magicCrit", posX, posY, posZ, 0, 2, 0);//死亡DUANG...
		}
		
	}
	/*------------------以下为EntityLiving类的各种搬运-----------------------*/
	@Override
	public ItemStack getHeldItem() {
		return this.equipment[0];
	}

	@Override
	public ItemStack getEquipmentInSlot(int pos) {
		return this.equipment[pos];
	}

	@Override
	public void setCurrentItemOrArmor(int pos, ItemStack stack) {
		this.equipment[pos] = stack;
	}

	@Override
	public ItemStack[] getLastActiveItems() {
		return this.equipment;
	}
	/**
     * Gets the active target the Task system uses for tracking
     */
    public EntityLivingBase getAttackTarget() {
        return this.attackTarget;
    }

    /**
     * Sets the active target the Task system uses for tracking
     */
    public void setAttackTarget(EntityLivingBase p_70624_1_) {
        this.attackTarget = p_70624_1_;
        ForgeHooks.onLivingSetAttackTarget(this, p_70624_1_);
    }

    /**
     * Returns true if this entity can attack entities of the specified class.
     */
    public boolean canAttackClass(Class p_70686_1_) {
        return EntityCreeper.class != p_70686_1_ && EntityGhast.class != p_70686_1_;
    }
    /**
     * Changes pitch and yaw so that the entity calling the function is facing the entity provided as an argument.
     */
    public void faceEntity(Entity entity, float p_70625_2_, float p_70625_3_) {
        double d0 = entity.posX - this.posX;
        double d2 = entity.posZ - this.posZ;
        double d1;

        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
            d1 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
        } else {
            d1 = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
        float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f3, p_70625_3_);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f2, p_70625_2_);
    }
    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotation(float p_70663_1_, float p_70663_2_, float p_70663_3_) {
        float f3 = MathHelper.wrapAngleTo180_float(p_70663_2_ - p_70663_1_);

        if (f3 > p_70663_3_) {
            f3 = p_70663_3_;
        }

        if (f3 < -p_70663_3_) {
            f3 = -p_70663_3_;
        }

        return p_70663_1_ + f3;
    }
}