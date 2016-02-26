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
	/** ���������õ�ʱ�䡣 */
    public final int itemUseDuration;
    /** ��������ӵļ��ȡ� */
    private final int healAmount;
    private final float saturationModifier;
    /** ��ϲ��ϲ���������⡣ */
    private final boolean isWolfsFavoriteMeat;
    /** �����������true����ʹ����Ҳ�ܳ���ȥ�� */
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
	
	/** ��ȡʹ�ô���Ʒ��ʱ�䡣 */
    public int getMaxItemUseDuration(ItemStack stack) {
        return this.itemUseDuration != 0 ? this.itemUseDuration : 32;
    }

    /** return���ʹ�ô���Ʒ��Action�� */
    public EnumAction getItemUseAction(ItemStack p_77661_1_) {
        return EnumAction.eat;
    }

    /** �Ҽ���Itemʱ���õķ������˴����ԡ� */
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

    /** return��ϲ��ϲ���������⡣ */
    public boolean isWolfsFavoriteMeat() {
        return this.isWolfsFavoriteMeat;
    }
    
    /** ������ô˷�����������ʳ���ڼ�ʹ��ʳ������ʱҲ���Գԡ� */
    public ItemFoodKC setAlwaysEdible() {
        this.alwaysEdible = true;
        return this;
    }

}
