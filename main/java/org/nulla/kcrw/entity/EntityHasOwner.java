package org.nulla.kcrw.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityHasOwner extends Entity implements IEntityOwnable {
	
	public EntityHasOwner(World world)
    {
        super(world);
    }

	@Override
	protected void entityInit() {
        this.dataWatcher.addObject(16, "");
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		setOwnerName(nbt.getString("OwnerName"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setString("OwnerName", this.func_152113_b());
	}
	
	public void setOwnerName(String uuid)
    {
        this.dataWatcher.updateObject(16, uuid);
    }
	
	public void setOwner(EntityPlayer player)
    {
		setOwnerName(player.getCommandSenderName());
    }

	/** 取拥有者名字 */
	@Override
	public String func_152113_b() {
		return this.dataWatcher.getWatchableObjectString(16);
	}

	@Override
	public EntityPlayer getOwner() {
		return this.worldObj.getPlayerEntityByName(this.func_152113_b());
	}

}
