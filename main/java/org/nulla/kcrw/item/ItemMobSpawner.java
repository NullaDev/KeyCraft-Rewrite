package org.nulla.kcrw.item;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.nulla.kcrw.entity.entityliving.EntityBlackDog;
import org.nulla.kcrw.entity.entityliving.EntityRedDog;

public class ItemMobSpawner extends KCItemBase {
	
	private final Class<? extends EntityLivingBase> toSpawn;
	
	public ItemMobSpawner(Class<? extends EntityLivingBase> entityClass) {
		super();
		this.toSpawn = entityClass;
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote) {
			return stack;
		}
		EntityLivingBase entity = null;
		try {
			entity = toSpawn.getConstructor(World.class).newInstance(player.worldObj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (entity != null) {
			entity.setPosition(player.posX, player.posY, player.posZ);
			world.spawnEntityInWorld(entity);
		}
		//world.spawnEntityInWorld(new EntityBlackDog(world, player.posX, player.posY, player.posZ));
		//world.spawnEntityInWorld(new EntityRedDog(world, player.posX, player.posY, player.posZ));
		return stack;
	}

}
