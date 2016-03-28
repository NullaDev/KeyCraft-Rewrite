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
	/** 我当时写的什么注释乱码了看不出来 */
    public final int itemUseDuration;
    /** 食物回的饥饿度 */
    private final int healAmount;
    private final float saturationModifier;
    /** 是不是狼爱吃的食物（引用自原版，并没有什么狗用） */
    private final boolean isWolfsFavoriteMeat;
    /** 是否在满饥饿度时还可食用。 */
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
	
	/** How long it takes to 使用这个物品。 */
    public int getMaxItemUseDuration(ItemStack stack) {
        return this.itemUseDuration != 0 ? this.itemUseDuration : 32;
    }

    /** returns the action that 这个物品被使用时。  */
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return EnumAction.eat;
    }

    /** Called whenever 此物品被装备 and 右键按下。 */
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

    /** return 喵喵喵喵喵？ */
    public boolean isWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }
    
    /** 让 'alwaysEdible' 变成 true, 而且让食物随时可以吃 even if 你很饱。 */
    public ItemFoodKC setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }

}
