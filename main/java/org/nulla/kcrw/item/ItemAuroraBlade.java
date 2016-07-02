package org.nulla.kcrw.item;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KCMaterials;
import org.nulla.kcrw.skill.SkillAuroraBlade;

import com.google.common.collect.Sets;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;

public class ItemAuroraBlade extends ItemTool {
	
	public static final HashSet<String> HARVESTABLE = Sets.newHashSet("axe", "pickaxe", "shovel");
	
	public ItemAuroraBlade() {
		super(4.0f, KCMaterials.AuroraBlade, new HashSet());
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta) {
		 if (HARVESTABLE.contains(block.getHarvestTool(meta))) {
			 return efficiencyOnProperMaterial;
		 } else {
			 return super.getDigSpeed(stack, block, meta);
		 }
		 
	 }
	 
	 @Override
	 public boolean canHarvestBlock(Block block, ItemStack itemStack) {
		 if (HARVESTABLE.contains(block.getHarvestTool(0))) {
			 return true;
		 } else {
			 return super.canHarvestBlock(block, itemStack);
		 }
	 }
		
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
	    player.setItemInUse(stack, 72000);
	    return stack;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
	    return EnumAction.block;
	}
	
	 /** 砍人。 */
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
		stack.damageItem(1, attacker);
		return true;
	}

	 /** 砍东西。 */
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, int posX, int posY ,int posZ, EntityLivingBase entity) {
		stack.damageItem(2, entity);
		return true;
	}
	
	/** 自动掉耐久。 */
	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int index, boolean isCurrentItem) {
		if (player instanceof EntityPlayer && !isCurrentItem) {
			((EntityPlayer)player).inventory.mainInventory[index] = null;
			onBladeDead(stack, (EntityPlayer)player);
			return;
		}
		
		if (stack.getItemDamage() >= this.getMaxDamage()) {
			onBladeDead(stack, (EntityPlayer)player);
		} else {
			if (new Random().nextBoolean())
				stack.setItemDamage(stack.getItemDamage() + 1);
		}
	}
	
	/** 扔东西。 */
	@SubscribeEvent
	public void onDropped(ItemTossEvent event) {
		if (event.entityItem.getEntityItem().getItem() instanceof ItemAuroraBlade) {
			event.setCanceled(true);
			onBladeDead(event.entityItem.getEntityItem(), event.player);
		}
    }
	
	public void onBladeDead(ItemStack stack, EntityPlayer player) {
		double p = 1D * stack.getItemDamage() / stack.getMaxDamage();
		SkillAuroraBlade.onBladeDead(player, p);
		stack.damageItem(1024, player);
	}

}