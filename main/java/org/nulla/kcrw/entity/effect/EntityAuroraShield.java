package org.nulla.kcrw.entity.effect;

import java.util.HashMap;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityAuroraShield extends Entity {
	
	public EntityLivingBase mOwner;
	
	public static HashMap<EntityLivingBase, EntityAuroraShield> map = new HashMap<EntityLivingBase, EntityAuroraShield>();
	
	public static EntityAuroraShield create(EntityLivingBase entity) {
		if (map.get(entity) != null)
			return map.get(entity);
		EntityAuroraShield shield = new EntityAuroraShield(entity.worldObj, entity);
		map.put(entity, shield);
		return shield;
	}
	
	private EntityAuroraShield(World world, EntityLivingBase owner) {
		super(world);
		this.mOwner = owner;
	}

	@Override
	protected void entityInit() {

	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		this.posX = this.mOwner.posX;
		this.posY = this.mOwner.posY;
		this.posZ = this.mOwner.posZ;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound p_70037_1_) {
		// TODO Auto-generated method stub	
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound p_70014_1_) {
		// TODO Auto-generated method stub
	}

}
