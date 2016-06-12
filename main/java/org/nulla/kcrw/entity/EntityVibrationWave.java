package org.nulla.kcrw.entity;

import java.util.Iterator;
import java.util.List;

import org.nulla.kcrw.skill.SkillsRw;
import org.nulla.nullacore.api.damage.NullaDamageSource;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class EntityVibrationWave extends EntityHasOwner {

	public EntityVibrationWave(World world) {
		super(world);
	}
	
	public EntityVibrationWave(EntityPlayer owner) {
		super(owner.worldObj);
		
		this.setCurrentRadius(0.0f);
		int exp = SkillsRw.VibrationWave.getExperience(owner);
		this.setMaxRadius(4.0f * 2048.0f / (2048.0f - exp));
		
		this.setOwner(owner);
		this.posX = owner.posX;
		this.posY = owner.posY;
		this.posZ = owner.posZ;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
        this.dataWatcher.addObject(17, Float.valueOf(0.0f));
        this.dataWatcher.addObject(18, Float.valueOf(4.0f));
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		this.setCurrentRadius(nbt.getFloat("CurrentRadius"));
		if (nbt.hasKey("MaxRadius"))
			this.setMaxRadius(nbt.getFloat("MaxRadius"));
		else
			this.setMaxRadius(4.0f);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setFloat("CurrentRadius", this.getCurrentRadius());
		nbt.setFloat("MaxRadius", this.getMaxRadius());
	}
	
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		
		float curRadius = this.getCurrentRadius();
		this.setCurrentRadius(curRadius + 0.1f);
		if (this.worldObj.isRemote)
			return;
		if (curRadius >= this.getMaxRadius()) {
			this.setDead();
		}
		
		// 检测波面附近的实体
		if (this.ticksExisted % 3 == 0) {
			float r = getCurrentRadius();
			List list = this.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(
					posX - r, posY - 0.5, posZ - r, posX + r, posY + 1.5, posZ + r));
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				EntityLivingBase entity = (EntityLivingBase)iterator.next();
				
				if (entity == this.getOwner())
					continue;
				
	            if (entity.boundingBox.minX > posX - r + 0.5
	            	&& entity.boundingBox.maxX < posX + r - 0.5
	            	&& entity.boundingBox.minZ > posZ - r + 0.5
	            	&& entity.boundingBox.maxZ < posZ + r - 0.5)
	            {
	            	float damageBasic = 10F * 2048 / (2048 - SkillsRw.VibrationWave.getExperience(this.getOwner()));
	            	float p = this.getCurrentRadius() / this.getMaxRadius();
	            	entity.attackEntityFrom(NullaDamageSource.CauseAuroraDamage(this.getOwner()), damageBasic / (1 + 4 * p * p));
	            }
	        }
		}
	}

	
	public void setCurrentRadius(float radius) {
		this.dataWatcher.updateObject(17, Float.valueOf(radius));
	}
	
	public float getCurrentRadius() {
		return this.dataWatcher.getWatchableObjectFloat(17);
	}
	
	public void setMaxRadius(float maxRadius) {
		this.dataWatcher.updateObject(18, Float.valueOf(maxRadius));
	}
	
	public float getMaxRadius() {
		return this.dataWatcher.getWatchableObjectFloat(18);
	}

}
