package org.nulla.kcrw.item;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.item.ItemTossEvent;

import org.nulla.kcrw.KCMaterials;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.skill.SkillAuroraSolidification;
import org.nulla.kcrw.skill.SkillsRw;

import com.google.common.collect.Multimap;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAuroraClaw extends KCItemBase {
    private float damage;
    private final ToolMaterial material;

    public ItemAuroraClaw() {
        this.material = KCMaterials.AuroraBlade;
        this.maxStackSize = 1;
        this.setMaxDamage(material.getMaxUses() / 2);
        this.damage = 2.0F + material.getDamageVsEntity();
    }

    @Override
    public float func_150893_a(ItemStack p_150893_1_, Block block) {
        if (block == Blocks.web) {
            return 15.0F;
        } else {
            Material material = block.getMaterial();
            return material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd ? 1.0F : 1.5F;
        }
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

    @Override
    public boolean func_150897_b(Block block) {
        return block == Blocks.web;
    }

    /** 获得 a map of 物品 attribute modifiers, used by 剑物品 to 提高打击伤害。 */
    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", (double)this.damage, 0));
        return multimap;
    }
    
    /** 自动掉耐久。 */
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int index, boolean isCurrentItem) {
		if (!(entity instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer) entity;
		
		if (!isCurrentItem) {
			player.inventory.mainInventory[index] = null;
			onBladeDead(stack, player);
			return;
		}
		
		if (stack.getItemDamage() >= this.getMaxDamage()) {
			onBladeDead(stack, (EntityPlayer)player);
		} else {
			float p = 2048 / (2048 - SkillsRw.AuroraSolidification.getExperience(player));
			if (new Random().nextFloat() < p)
				stack.setItemDamage(stack.getItemDamage() + 1);
		}
	}
	
	/** 扔东西。 */
	@SubscribeEvent
	public void onDropped(ItemTossEvent event) {
		if (event.entityItem.getEntityItem().getItem() instanceof ItemAuroraClaw) {
			event.setCanceled(true);
			onBladeDead(event.entityItem.getEntityItem(), event.player);
		}
    }
	
	public void onBladeDead(ItemStack stack, EntityPlayer player) {
		double p = 1D * stack.getItemDamage() / stack.getMaxDamage();
		SkillAuroraSolidification.onBladeDead(player, p);
		int pos = KCUtils.getPosOfStack(player, stack);
		if (pos >= 0 && pos < player.inventory.mainInventory.length)
			player.inventory.mainInventory[pos] = null;
	}
	
}