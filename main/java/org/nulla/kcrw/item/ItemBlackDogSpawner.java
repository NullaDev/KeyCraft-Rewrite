package org.nulla.kcrw.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.nulla.kcrw.entity.entityliving.EntityBlackDog;

public class ItemBlackDogSpawner extends KCItemBase {
	
	@Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (world.isRemote) {
			return stack;
		}
		world.spawnEntityInWorld(new EntityBlackDog(world, player.posX, player.posY, player.posZ));
		return stack;
	}

}
