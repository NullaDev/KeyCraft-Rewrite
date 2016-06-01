package org.nulla.kcrw.entity.effect;

import java.util.HashMap;

import org.nulla.kcrw.entity.EntityHasOwner;
import org.nulla.kcrw.skill.SkillsRw;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityAuroraShield extends EntityHasOwner {
	
	public EntityAuroraShield(World world) {
		super(world);
	}
	
	public EntityAuroraShield(World world, Entity owner) {
		super(world);
		this.setOwnerUUID(owner.getUniqueID().toString());
		this.posX = owner.posX;
		this.posY = owner.posY;
		this.posZ = owner.posZ;
		this.rotationYaw = owner.rotationYaw;
	}
	
	@Override
    public void onUpdate() {
		super.onUpdate();
		
		Entity owner = this.getOwner();
		if (owner == null) { // 每次玩家UUID会变？
			this.setDead();
			return;
		}
		
		if (owner instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) owner;
			boolean flag = false;
			if (flag) { //以后再说
				this.setDead();
			}
		}
		
		this.posX = owner.posX;
		this.posY = owner.posY;
		this.posZ = owner.posZ;
		this.rotationYaw = owner.rotationYaw;
	}

}
