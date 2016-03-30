package org.nulla.kcrw.item;

import java.util.Set;

import org.nulla.kcrw.KCMaterials;

import com.google.common.collect.*;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;

public class ItemAuroraTool extends KCItemBase {
	
	private Set mEffectiveBlocks = null;
    protected float efficiencyOnProperMaterial = 4.0F;
    /** 打entities的伤害。 */
    private float damageVsEntity;
    /** 这个物品的material。 */
    protected ToolMaterial toolMaterial;

    public ItemAuroraTool(float extraAttack, Set effectiveBlocks) {
        this.toolMaterial = KCMaterials.AURORA_IRON;
        this.mEffectiveBlocks = effectiveBlocks;
        this.maxStackSize = 1;
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.efficiencyOnProperMaterial = toolMaterial.getEfficiencyOnProperMaterial();
        this.damageVsEntity = extraAttack + toolMaterial.getDamageVsEntity();
    }
    
    public ItemAuroraTool(String toolType) {
    	this.toolMaterial = KCMaterials.AURORA_IRON;
        this.toolClass = toolType;
        this.maxStackSize = 1;
        this.setMaxDamage(toolMaterial.getMaxUses());
        this.efficiencyOnProperMaterial = toolMaterial.getEfficiencyOnProperMaterial();
        this.damageVsEntity = getExtraDamage(toolType) + toolMaterial.getDamageVsEntity();
    }
    
    public float getExtraDamage(String toolType) {
    	switch (toolType) {
    		case "axe" :
    			return 3.0F;
    		case "pickaxe" :
    			return 2.0F;
    		case "shovel" :
    			return 1.0F;
    		default:
    			return 0.0F;
    	}
    }

    /** 这个工具不是用来打人的，打人掉双倍耐久。 */
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
    	stack.damageItem(2, p_77644_3_);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, Block block, int posX, int posY, int posZ, EntityLivingBase entity) {
        if ((double)block.getBlockHardness(world, posX, posY, posZ) != 0.0D) {
        	stack.damageItem(1, entity);
        }
        return true;
    }

    /** 立体渲染。 */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    /** Return 附魔能力 of 这个物品, 大多数情况基于material。 */
    @Override
    public int getItemEnchantability() {
        return this.toolMaterial.getEnchantability();
    }

    /** Return 这个物品能不能续一秒 in an 铁砧。 */
    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
        ItemStack mat = this.toolMaterial.getRepairItemStack();
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, p_82789_2_, false)) return true;
        return super.getIsRepairable(p_82789_1_, p_82789_2_);
    }

    /** 获得 a map of 物品 attribute modifiers, used by 剑物品 to 提高打击伤害。 */
    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap multimap = super.getItemAttributeModifiers();
        multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", (double)this.damageVsEntity, 0));
        return multimap;
    }

    /*===================================== FORGE START =================================*/
    private String toolClass;
    
    @Override
    public int getHarvestLevel(ItemStack stack, String toolClass) {
        int level = super.getHarvestLevel(stack, toolClass);
        if (level == -1 && toolClass != null && toolClass.equals(this.toolClass)) {
            return this.toolMaterial.getHarvestLevel();
        } else {
            return level;
        }
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return toolClass != null ? ImmutableSet.of(toolClass) : super.getToolClasses(stack);
    }

    @Override
    public float getDigSpeed(ItemStack stack, Block block, int meta) {
        if (ForgeHooks.isToolEffective(stack, block, meta)) {
            return efficiencyOnProperMaterial;
        }
        return super.getDigSpeed(stack, block, meta);
    }
    /*===================================== FORGE END =================================*/
}