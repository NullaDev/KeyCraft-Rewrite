package org.nulla.kcrw.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import org.nulla.kcrw.entity.EntityJavelin;

public class ItemJavelin extends KCItemBase {
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
        	world.spawnEntityInWorld(new EntityJavelin(world, player, false));
        }

        return stack;
    }

}
