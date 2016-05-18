package org.nulla.kcrw.item;

import org.nulla.kcrw.KCResources;
import org.nulla.nullacore.api.audio.MusicHelper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMusicPlayer extends KCItemBase {
	
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if (player.worldObj.isRemote)
			MusicHelper.playBgm(KCResources.music_tori_no_uta);
		return stack;
    }

}
