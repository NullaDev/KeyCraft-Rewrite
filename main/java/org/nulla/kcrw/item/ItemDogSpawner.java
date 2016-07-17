package org.nulla.kcrw.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.nulla.kcrw.entity.entityliving.EntityBlackDog;
import org.nulla.kcrw.entity.entityliving.EntityRedDog;

public class ItemDogSpawner extends KCItemBase {
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote) {
			return stack;
		}
		world.spawnEntityInWorld(new EntityBlackDog(world, player.posX, player.posY, player.posZ));
		world.spawnEntityInWorld(new EntityRedDog(world, player.posX, player.posY, player.posZ));
		return stack;
	}

}
