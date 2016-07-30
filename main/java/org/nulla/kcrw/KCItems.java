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
	
    public static Item aurora_iron_helmet;
    public static Item aurora_iron_chestplate;
    public static Item aurora_iron_leggings;
    public static Item aurora_iron_boots;
    
    public static Item aurora_iron_axe;
    public static Item aurora_iron_pickaxe;
    public static Item aurora_iron_shovel;
    
    public static Item aurora_iron_sword;
    
    public static Item aurora_iron_hoe;
    
	public static Item baseball;
	
	public static Item dog_spawner;
	
	public static Item hand_sonic;
	
	public static Item javelin;
	
	public static Item miracle_ribbon;
	
	public static Item music_player;
	
	public static Item peach_juice;
	
	public static Item pizza_jam;
	
	public static Item steel_blade;
	
	public static Item steel_blade_vibrating;
	
	public static Item sth_sticky;
	
	public static Item aurora_blade;
	
    public static void InitItems() {
    	
    	aurora_iron_ingot = new ItemAuroraIronIngot()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.iron_ingot, 1)}, 1, 16))
			.setUnlocalizedName("aurora_iron_ingot")
			.setTextureName("kcrw:aurora_iron_ingot");
    	GameRegistry.registerItem(aurora_iron_ingot, "aurora_iron_ingot");
    	
    	aurora_iron_helmet = new ItemAuroraArmor(0)
			.setUnlocalizedName("aurora_iron_helmet")
			.setTextureName("kcrw:aurora_iron_helmet");
    	GameRegistry.registerItem(aurora_iron_helmet, "aurora_iron_helmet");
    	KCUtils.addEnchantedRecipe(aurora_iron_helmet, Enchantment.protection, 2, new Object[] { "AAA", "A A", 'A', aurora_iron_ingot });

    	aurora_iron_chestplate = new ItemAuroraArmor(1)
    		.setUnlocalizedName("aurora_iron_chestplate")
    		.setTextureName("kcrw:aurora_iron_chestplate");
    	GameRegistry.registerItem(aurora_iron_chestplate, "aurora_iron_chestplate");
    	KCUtils.addEnchantedRecipe(aurora_iron_chestplate, Enchantment.protection, 2, new Object[] { "A A", "AAA", "AAA", 'A', aurora_iron_ingot });

    	aurora_iron_leggings = new ItemAuroraArmor(2)
			.setUnlocalizedName("aurora_iron_leggings")
			.setTextureName("kcrw:aurora_iron_leggings");
    	GameRegistry.registerItem(aurora_iron_leggings, "aurora_iron_leggings");
    	KCUtils.addEnchantedRecipe(aurora_iron_leggings, Enchantment.protection, 2, new Object[] { "AAA", "A A", "A A", 'A', aurora_iron_ingot });

    	aurora_iron_boots = new ItemAuroraArmor(3)
			.setUnlocalizedName("aurora_iron_boots")
			.setTextureName("kcrw:aurora_iron_boots");
    	GameRegistry.registerItem(aurora_iron_boots, "aurora_iron_boots");
    	KCUtils.addEnchantedRecipe(aurora_iron_boots, Enchantment.protection, 2, new Object[] { "A A", "A A", 'A', aurora_iron_ingot });

    	aurora_iron_axe = new ItemAuroraTool("axe")
			.setUnlocalizedName("aurora_iron_axe")
			.setTextureName("kcrw:aurora_iron_axe");
    	GameRegistry.registerItem(aurora_iron_axe, "aurora_iron_axe");
    	KCUtils.addEnchantedRecipe(aurora_iron_axe, Enchantment.unbreaking, 2, new Object[] { "AA", "AB", " B" , 'A', aurora_iron_ingot, 'B', Items.stick });

    	aurora_iron_hoe = new ItemAuroraIronHoe()
    		.setUnlocalizedName("aurora_iron_hoe")
    		.setTextureName("kcrw:aurora_iron_hoe");
    	GameRegistry.registerItem(aurora_iron_hoe, "aurora_iron_hoe");
    	KCUtils.addEnchantedRecipe(aurora_iron_hoe, Enchantment.unbreaking, 1, new Object[] { "AA", " B", " B" , 'A', aurora_iron_ingot, 'B', Items.stick });

    	aurora_iron_pickaxe = new ItemAuroraTool("pickaxe")
			.setUnlocalizedName("aurora_iron_pickaxe")
			.setTextureName("kcrw:aurora_iron_pickaxe");
    	GameRegistry.registerItem(aurora_iron_pickaxe, "aurora_iron_pickaxe");
    	KCUtils.addEnchantedRecipe(aurora_iron_pickaxe, Enchantment.unbreaking, 2, new Object[] { "AAA", " B ", " B ", 'A', aurora_iron_ingot, 'B', Items.stick });

    	aurora_iron_shovel = new ItemAuroraTool("shovel")
			.setUnlocalizedName("aurora_iron_shovel")
			.setTextureName("kcrw:aurora_iron_shovel");
    	GameRegistry.registerItem(aurora_iron_shovel, "aurora_iron_shovel");
    	KCUtils.addEnchantedRecipe(aurora_iron_shovel, Enchantment.unbreaking, 2, new Object[] { "A", "B", "B", 'A', aurora_iron_ingot, 'B', Items.stick });

    	aurora_iron_sword = new ItemAuroraSword()
			.setUnlocalizedName("aurora_iron_sword")
			.setTextureName("kcrw:aurora_iron_sword");
    	GameRegistry.registerItem(aurora_iron_sword, "aurora_iron_sword");
    	KCUtils.addEnchantedRecipe(aurora_iron_sword, Enchantment.sharpness, 2, new Object[] { "A", "A", "B", 'A', aurora_iron_ingot, 'B', Items.stick });
	
    	baseball = new ItemBaseball()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.leather, 4), new ItemStack(Blocks.wool, 1, 0)}, 16, 32))
			.setUnlocalizedName("baseball")
			.setTextureName("kcrw:baseball");
    	GameRegistry.registerItem(baseball, "baseball");
    	   	
    	dog_spawner = new ItemDogSpawner()
    		.setUnlocalizedName("dog_spawner")
    		.setTextureName("kcrw:dog_spawner");
    	GameRegistry.registerItem(dog_spawner, "dog_spawner");
    	
    	hand_sonic = new ItemHandSonic()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.iron_ingot, 4), new ItemStack(KCItems.aurora_iron_ingot, 4), new ItemStack(Blocks.lapis_block, 1)}, 1, 256))
			.setUnlocalizedName("hand_sonic")
			.setTextureName("kcrw:hand_sonic");
    	GameRegistry.registerItem(hand_sonic, "hand_sonic");
    	
    	javelin = new ItemJavelin()
    		.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.iron_ingot, 1)}, 1, 32))
			.setUnlocalizedName("javelin")
			.setTextureName("kcrw:javelin");
    	GameRegistry.registerItem(javelin, "javelin");
    	
    	miracle_ribbon = new ItemMiracleRibbon()
    		.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.string, 4)}, 1, 128))
    		.setUnlocalizedName("miracle_ribbon")
    		.setTextureName("kcrw:miracle_ribbon");
    	GameRegistry.registerItem(miracle_ribbon, "miracle_ribbon");
    	
    	music_player = new ItemMusicPlayer()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Blocks.jukebox, 1)}, 1, 128))
			.setUnlocalizedName("music_player")
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
    		.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(KCItems.sth_sticky, 2), new ItemStack(Items.apple, 1)} , 1, 10))
    		.setUnlocalizedName("peach_juice")
    		.setTextureName("kcrw:peach_juice");
    	GameRegistry.registerItem(peach_juice, "peach_juice");
    	
    	pizza_jam = new ItemFoodKC(10)
			.setCallback(new ItemFoodKC.ICallback() {
				@Override
				public void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
					player.addPotionEffect(new PotionEffect(Potion.confusion.id, 30 * 20));
					player.addPotionEffect(new PotionEffect(Potion.field_76443_y.id, 240 * 20));
				}
				@Override
				public void addInformation(ItemStack stack, EntityPlayer player, List information, boolean p_77624_4_) {
					information.add(StatCollector.translateToLocal("kcrw.item.intro.pizzajam"));
				}
			})
			.setAlwaysEdible()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.sugar, 32), new ItemStack(Items.porkchop, 1), new ItemStack(KCItems.sth_sticky, 1)} , 1, 16))
			.setUnlocalizedName("pizza_jam")
			.setTextureName("kcrw:pizza_jam");
    	GameRegistry.registerItem(pizza_jam, "pizza_jam");
    	    	
    	steel_blade = new ItemSteelBlade()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.iron_ingot, 3)}, 1, 64))
			.setUnlocalizedName("steel_blade")
			.setTextureName("kcrw:steel_blade");
    	GameRegistry.registerItem(steel_blade, "steel_blade");
    	
    	steel_blade_vibrating = new ItemSteelBladeVibrating()
    		.setUnlocalizedName("steel_blade_vibrating")
    		.setTextureName("kcrw:steel_blade_vibrating");
    	GameRegistry.registerItem(steel_blade_vibrating, "steel_blade_vibrating");
    	
    	sth_sticky = new ItemSthSticky()
			.setRecipe(new KCRecipe(new ItemStack[]{new ItemStack(Items.slime_ball, 1)}, 1, 16))
			.setUnlocalizedName("sth_sticky")
			.setTextureName("kcrw:sth_sticky");
    	GameRegistry.registerItem(sth_sticky, "sth_sticky");
    	
    	aurora_blade = new ItemAuroraBlade()
			.setUnlocalizedName("aurora_blade")
			.setTextureName("kcrw:aurora_blade")
			.setCreativeTab(null);
    	GameRegistry.registerItem(aurora_blade, "aurora_blade");
    }
    
    public static boolean canFix(Item item) {
    	if (item instanceof ItemAuroraTool)
    		return true;
    	if (item instanceof ItemAuroraBlade)
    		return true;
    	if (item instanceof ItemAuroraArmor)
    		return true;
    	if (item instanceof ItemHandSonic)
    		return true;
    	return false;
    }
}
