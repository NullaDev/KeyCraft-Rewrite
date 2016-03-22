package org.nulla.kcrw;

import java.util.List;

import org.nulla.kcrw.item.*;
import org.nulla.kcrw.item.crafting.KCRecipe;

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
	
	public static Item musicplayer;
	
	public static Item peachjuice;
	
	public static Item aurorablade;
	
    public static void InitItems() {
    	
    	//普通物品
    	musicplayer = new ItemMusicPlayer()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Blocks.jukebox, 1)}, 1, 128))
			.setUnlocalizedName("musicplayer")
			.setTextureName("kcrw:music_player");
    	GameRegistry.registerItem(musicplayer, "musicplayer");
		
    	peachjuice = new ItemFoodKC(3)
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
    		.setUnlocalizedName("peachjuice")
    		.setTextureName("kcrw:peach_juice");
    	GameRegistry.registerItem(peachjuice, "peachjuice");
    	
    	//KCUtils.addEnchantedNamedRecipe(new ItemStack(Items.stick, 1), Enchantment.knockback, 99, "我是光棍", new Object[] { " A ", "ABA", " A " , 'A', Blocks.piston, 'B', Items.stick });
    	
    	
    	//技能物品
    	aurorablade = new ItemAuroraBlade()
			.setUnlocalizedName("aurorablade")
			.setTextureName("kcrw:aurora_blade");
    	GameRegistry.registerItem(aurorablade, "aurorablade");
    }
}
