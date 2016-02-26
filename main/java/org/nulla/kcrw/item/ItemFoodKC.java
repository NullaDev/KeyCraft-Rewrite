package org.nulla.kcrw.item;

import java.util.List;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.item.crafting.KCRecipe;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFoodKC extends KCItemBase {
	
	public interface ICallback {
		void onFoodEaten(ItemStack stack, World world, EntityPlayer player);
		void addInformation(ItemStack stack, EntityPlayer player, List information, boolean p_77624_4_);
	}
	
	protected ICallback Callback = null;
	protected KCRecipe craftRecipe = null;
	/** 吃这玩意用的时间。 */
    public final int itemUseDuration;
    /** 吃这玩意加的鸡腿。 */
    private final int healAmount;
    private final float saturationModifier;
    /** 狼喜不喜欢吃这玩意。 */
    private final boolean isWolfsFavoriteMeat;
    /** 如果这玩意是true，则即使撑着也能吃下去。 */
    private boolean alwaysEdible;

	public ItemFoodKC(int eatTime, int amount, float p_i45339_2_, boolean isWolfLike) {
		this.itemUseDuration = eatTime;
		this.healAmount = amount;
		this.isWolfsFavoriteMeat = isWolfLike;
		this.saturationModifier = p_i45339_2_;
		this.setCreativeTab(KeyCraft_Rewrite.KCCreativeTab);
	}
	
	public ItemFoodKC(int amount) {
		this(32, amount, 0.6F, false);
	}
	
	public ItemFoodKC setCallback(ICallback callback) {
		Callback = callback;
		return this;
	}
	
	public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        player.getFoodStats().addStats(this.getAmonut(stack), this.getSaturation(stack));
        world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(stack, world, player);
        return stack;
    }
	
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		if (Callback != null) {
			Callback.onFoodEaten(stack, world, player);
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List information, boolean p_77624_4_) {
		super.addInformation(stack, player, information, p_77624_4_);
		if (Callback != null) {
			Callback.addInformation(stack, player, information, p_77624_4_);
		}
	}
	
	/** 获取使用此物品的时间。 */
    public int getMaxItemUseDuration(ItemStack stack) {
        return this.itemUseDuration != 0 ? this.itemUseDuration : 32;
    }

    /** return玩家使用此物品的Action。 */
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return EnumAction.eat;
    }

    /** 右键此Item时调用的方法，此处即吃。 */
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (player.canEat(this.alwaysEdible)) {
        	player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }

        return stack;
    }

    public int getAmonut(ItemStack stack) {
        return this.healAmount;
    }

    public float getSaturation(ItemStack stack) {
        return this.saturationModifier;
    }

    /** return狼喜不喜欢吃这玩意。 */
    public boolean isWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }
    
    /** 如果调用此方法，则这种食物在即使饱食度已满时也可以吃。 */
    public ItemFoodKC setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }

}
