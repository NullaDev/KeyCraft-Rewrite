package org.nulla.kcrw.item;

import java.util.List;

import org.nulla.kcrw.KCItems;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemHandSonic extends KCItemBase {
    private float damage;

    public ItemHandSonic() {
        this.maxStackSize = 1;
        this.setMaxDamage(2400);
        this.damage = 8.0F;
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
    	stack.damageItem(1, p_77644_3_);
        return true;
    }

    /** 这个工具是用来打人的，打方块掉双倍耐久。 */
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int posX, int posY, int posZ, EntityLivingBase entity) {
        if ((double)block.getBlockHardness(world, posX, posY, posZ) != 0.0D) {
        	stack.damageItem(2, entity);
        }
        return true;
    }

    /** 立体渲染。 */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    /**
     * returns the action that specifies what animation to play when the items is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    /** Return 附魔能力 of 这个物品, 大多数情况基于material。 */
    @Override
    public int getItemEnchantability() {
        return 25;
    }

    /** Return 这个物品能不能续一秒 in an 铁砧。 */
    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
        ItemStack mat = new ItemStack(KCItems.aurora_iron_ingot, 1);
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, p_82789_2_, false)) return true;
        return super.getIsRepairable(p_82789_1_, p_82789_2_);
    }

    /** 获得 a map of 物品 attribute modifiers, used by 剑物品 to 提高打击伤害。 */
    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.damage, 0));
        return multimap;
    }
    
    @Override
    public void onUpdate(ItemStack stack, World p_77663_2_, Entity entity, int p_77663_4_, boolean p_77663_5_) {
    	if (!(entity instanceof EntityLivingBase)) {
    		return;
    	}
    	EntityLivingBase living = (EntityLivingBase) entity;
    	if (!living.isPotionActive(Potion.moveSpeed) && living.getHeldItem() != null) {
    		if (living.getHeldItem().getItem() == this)
    			living.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 5 * 20));
    	}
    }
    
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List information, boolean p_77624_4_) {
		information.add(StatCollector.translateToLocal("kcrw.item.intro.handsonic"));
	}

}
