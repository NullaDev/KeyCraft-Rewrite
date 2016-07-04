package org.nulla.kcrw.item;

import org.nulla.kcrw.KCMaterials;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemAuroraIronHoe extends KCItemBase {

	protected final Item.ToolMaterial mToolMaterial = KCMaterials.AURORA_IRON;

    public ItemAuroraIronHoe() {
        this.maxStackSize = 1;
        this.setMaxDamage(this.mToolMaterial.getMaxUses());
    }

    /**
     * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
     * True if something happen and false if it don't. This is for ITEMS, not BLOCKS
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int posX, int posY, int posZ, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (!player.canPlayerEdit(posX, posY, posZ, p_77648_7_, stack)) {
            return false;
        } else {
            UseHoeEvent event = new UseHoeEvent(player, stack, world, posX, posY, posZ);
            if (MinecraftForge.EVENT_BUS.post(event)) {
                return false;
            }

            if (event.getResult() == Result.ALLOW) {
            	stack.damageItem(1, player);
                return true;
            }

            Block block = world.getBlock(posX, posY, posZ);

            if (p_77648_7_ != 0 && world.getBlock(posX, posY + 1, posZ).isAir(world, posX, posY + 1, posZ) && (block == Blocks.grass || block == Blocks.dirt)) {
                world.playSoundEffect((double)((float)posX + 0.5F), (double)((float)posY + 0.5F), (double)((float)posZ + 0.5F), Blocks.farmland.stepSound.getStepResourcePath(), (Blocks.farmland.stepSound.getVolume() + 1.0F) / 2.0F, Blocks.farmland.stepSound.getPitch() * 0.8F);

                if (world.isRemote) {
                    return true;
                } else {
                	world.setBlock(posX, posY, posZ, Blocks.farmland);
                    stack.damageItem(1, player);
                    return true;
                }
            } else {
                return false;
            }
        }
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }
    
    public String getToolMaterialName() {
        return this.mToolMaterial.toString();
    }
    
    /** Return 这个物品能不能续一秒 in an 铁砧。 */
    @Override
    public boolean getIsRepairable(ItemStack p_82789_1_, ItemStack p_82789_2_) {
        ItemStack mat = this.mToolMaterial.getRepairItemStack();
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, p_82789_2_, false)) return true;
        return super.getIsRepairable(p_82789_1_, p_82789_2_);
    }
	
}
