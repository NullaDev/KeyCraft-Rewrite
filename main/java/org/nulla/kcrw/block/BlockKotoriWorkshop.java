package org.nulla.kcrw.block;

import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.client.gui.GuiKotoriWorkshop;
import org.nulla.kcrw.skill.*;

import cpw.mods.fml.relauncher.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockKotoriWorkshop extends KCBlockBase {

	private IIcon blockIconTop;
	private IIcon blockIconFront;

	public BlockKotoriWorkshop() {
		super(Material.iron);
	}

	@Override
	public boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
		if (!world.isRemote) {
			return true;
		}
		KCUtils.getMC().displayGuiScreen(new GuiKotoriWorkshop(KCUtils.getMC().currentScreen, player));
		return true;
    }
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int p_149691_2_)
    {
        return side == 1 ? this.blockIconTop : (side == 0 ? Blocks.planks.getBlockTextureFromSide(side) : (side != 2 && side != 4 ? this.blockIcon : this.blockIconFront));
    }

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(this.getTextureName() + "_side");
        this.blockIconTop = p_149651_1_.registerIcon(this.getTextureName() + "_top");
        this.blockIconFront = p_149651_1_.registerIcon(this.getTextureName() + "_front");
    }

	
}
