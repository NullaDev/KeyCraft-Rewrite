package org.nulla.kcrw.item;

import java.util.List;

import org.nulla.kcrw.KeyCraft_Rewrite;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemFoodKC extends ItemFood {
	
	public interface ICallback {
		void onFoodEaten(ItemStack stack, World world, EntityPlayer player);
		void addInformation(ItemStack stack, EntityPlayer player, List information, boolean p_77624_4_);
	}
	
	protected ICallback Callback = null;

	public ItemFoodKC(int amount) {
		super(amount, 0.6F, false);	//后两个是干嘛的
		this.setCreativeTab(KeyCraft_Rewrite.KCCreativeTab);
	}
	
	public ItemFoodKC setCallback(ICallback callback) {
		Callback = callback;
		return this;
	}
	
	@Override
	protected void onFoodEaten(ItemStack stack, World world, EntityPlayer player) {
		super.onFoodEaten(stack, world, player);
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

}
