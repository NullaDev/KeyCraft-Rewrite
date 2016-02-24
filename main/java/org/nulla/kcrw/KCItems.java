package org.nulla.kcrw;

import java.util.List;

import org.nulla.kcrw.item.ItemFoodKC;
import org.nulla.kcrw.item.crafting.KCRecipe;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class KCItems {
	
	public static Item PeachJuice;
	
    public static void InitItems() {

    	PeachJuice = new ItemFoodKC(3)
    		.setCallback(new ItemFoodKC.ICallback() {
				@Override
				public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
    				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30 * 20));					
				}
				@Override
				public void addInformation(ItemStack stack, EntityPlayer player, List information, boolean p_77624_4_) {
					information.add(StatCollector.translateToLocal("kcrw.item.intro.peachJuice"));
				}
    		})
    		.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.slime_ball, 4), new ItemStack(Items.apple, 1)} , 4, 10))
    		.setUnlocalizedName("peachJuice")
    		.setTextureName("kcrw:peach_juice");
    	GameRegistry.registerItem(PeachJuice, "peachJuice");
    	
    }
}
