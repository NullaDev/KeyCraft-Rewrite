package org.nulla.kcrw.entity;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHasOwner extends Entity implements IEntityOwnable {
	
	public EntityHasOwner(World world)
    {
        super(world);
    }

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(17, "");
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
        setOwnerUUID(nbt.getString("OwnerUUID"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setString("OwnerUUID", this.func_152113_b());
	}
	
	public void setOwnerUUID(String uuid)
    {
        this.dataWatcher.updateObject(17, uuid);
    }

	/** 取拥有者UUID */
	@Override
	public String func_152113_b() {
		return this.dataWatcher.getWatchableObjectString(17);
	}

	@Override
	public Entity getOwner() {
		try
        {
            UUID uuid = UUID.fromString(this.func_152113_b());
            return uuid == null ? null : this.worldObj.func_152378_a(uuid);
        }
        catch (IllegalArgumentException illegalargumentexception)
        {
            return null;
        }
	}

}
