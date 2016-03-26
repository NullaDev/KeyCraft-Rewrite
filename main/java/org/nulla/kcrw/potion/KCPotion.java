package org.nulla.kcrw.potion;

import org.nulla.kcrw.KCResources;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public abstract class KCPotion {
	
	public final int mID;
	public final String mName;
	public final ResourceLocation mLocation;
		
	public KCPotion(String name, ResourceLocation location) {
		this.mID = Potions.AllPotions.size();
		this.mName = name;
		this.mLocation = location;
		Potions.AllPotions.add(this);
	}
	
	/** 重载实现效果 */
	public abstract void onPerform(EntityPlayer player, int level);

}
