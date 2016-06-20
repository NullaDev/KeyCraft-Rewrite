package org.nulla.kcrw.item;

import org.nulla.kcrw.KCItems;
import org.nulla.nullacore.api.damage.NullaDamageSource;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemSteelBladeVibrating extends KCItemBase {
	private float damage;

    public ItemSteelBladeVibrating() {
        this.maxStackSize = 1;
        this.setCreativeTab(null);
        this.setMaxDamage(600);
        this.damage = 17.0F;
    }

    @Override
    public float func_150893_a(ItemStack p_150893_1_, Block block) {
        if (block == Blocks.web) {
            return 15.0F;
        } else {
            return 1.0F;
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    	stack.damageItem(20, attacker);        	
        return true;
    }

    /** 这个工具是用来打人的，打方块掉双倍耐久。 */
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int posX, int posY, int posZ, EntityLivingBase entity) {
        if ((double)block.getBlockHardness(world, posX, posY, posZ) != 0.0D) {
        	stack.damageItem(20, entity);
        }
        return true;
    }

    /** 立体渲染。 */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean func_150897_b(Block block) {
        return block == Blocks.web;
    }

    /** Return 这个物品能不能续一秒 in an 铁砧。 */
    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
        ItemStack mat = new ItemStack(Items.iron_ingot ,1);
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
    public void onUpdate(ItemStack stack, World p_77663_2_, Entity p_77663_3_, int p_77663_4_, boolean p_77663_5_) {
    	if (stack.getItemDamage() >= this.getMaxDamage())
    		p_77663_3_.setCurrentItemOrArmor(0, new ItemStack(KCItems.steel_blade, 1, stack.getItemDamage()));
    	else
    		stack.setItemDamage(stack.getItemDamage() + 1);
    }
    
    @Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		player.setCurrentItemOrArmor(0, new ItemStack(KCItems.steel_blade, 1, stack.getItemDamage()));
	    return stack;
	}
    
    @SideOnly(Side.CLIENT)
	@Override
    public boolean hasEffect(ItemStack p_77636_1_) {
        return true;
    }

}
