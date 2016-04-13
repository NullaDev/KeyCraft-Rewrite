package org.nulla.kcrw.item;

import java.util.List;

import org.nulla.kcrw.entity.EntityBaseball;
import org.nulla.kcrw.skill.Skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBaseball extends KCItemBase {
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		
        if (!player.capabilities.isCreativeMode) {
            stack.stackSize--;
        }

        world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        if (!world.isRemote) {
        	world.spawnEntityInWorld(new EntityBaseball(world, player));
        }

        return stack;
    }
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List information, boolean p_77624_4_) {

	}

}
