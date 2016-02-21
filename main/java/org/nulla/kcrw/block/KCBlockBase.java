package org.nulla.kcrw.block;

import org.nulla.kcrw.KeyCraft_Rewrite;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class KCBlockBase extends Block {

	public KCBlockBase(Material material) {
		super(material);
		this.setCreativeTab(KeyCraft_Rewrite.KCCreativeTab);
	}
	
	@Override
	public boolean onBlockActivated(World world, int posX, int posY, int posZ, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        return false;
    }

}
