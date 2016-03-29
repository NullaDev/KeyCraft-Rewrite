package org.nulla.kcrw;

import java.util.ArrayList;
import java.util.List;

import org.nulla.kcrw.item.*;
import org.nulla.kcrw.item.crafting.KCRecipe;
import org.nulla.kcrw.potion.KCPotions;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.potion.*;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class KCItems {
	
	public static final ArrayList<Item> items = new ArrayList<Item>();
	
	public static Item aurora_iron_ingot;
	
	public static Item music_player;
	
	public static Item peach_juice;
	
	public static Item aurora_blade;
	
    public static void InitItems() {
    	
    	aurora_iron_ingot = new ItemAuroraIronIngot()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.iron_ingot, 1)}, 1, 16))
			.setUnlocalizedName("auroraIronIngot")
			.setTextureName("kcrw:aurora_iron_ingot");
    	GameRegistry.registerItem(aurora_iron_ingot, "aurora_iron_ingot");
    	
    	music_player = new ItemMusicPlayer()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Blocks.jukebox, 1)}, 1, 128))
			.setUnlocalizedName("musicPlayer")
			.setTextureName("kcrw:music_player");
    	GameRegistry.registerItem(music_player, "music_player");
		
    	peach_juice = new ItemFoodKC(3)
    		.setCallback(new ItemFoodKC.ICallback() {
				@Override
				public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
    				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 30 * 20));
				}
				@Override
				public void addInformation(ItemStack stack, EntityPlayer player, List information, boolean p_77624_4_) {
					information.add(StatCollector.translateToLocal("kcrw.item.intro.peachjuice"));
				}
    		})
    		.setAlwaysEdible()
    		.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.slime_ball, 4), new ItemStack(Items.apple, 1)} , 4, 10))
    		.setUnlocalizedName("peachJuice")
    		.setTextureName("kcrw:peach_juice");
    	GameRegistry.registerItem(peach_juice, "peach_juice");
    	
    	//KCUtils.addEnchantedNamedRecipe(new ItemStack(Items.stick, 1), Enchantment.knockback, 99, "���ǹ��", new Object[] { " A ", "ABA", " A " , 'A', Blocks.piston, 'B', Items.stick });
    	
    	aurora_blade = new ItemAuroraBlade()
			.setUnlocalizedName("auroraBlade")
			.setTextureName("kcrw:aurora_blade");
    	GameRegistry.registerItem(aurora_blade, "aurora_blade");
    }
}
