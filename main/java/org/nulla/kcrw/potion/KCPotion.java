package org.nulla.kcrw.potion;

import org.nulla.kcrw.KCResources;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;

public abstract class KCPotion extends Potion {
	
	private static int nextID = 100;
	
	/*public final int mID;
	public final String mName;*/
	public final ResourceLocation mLocation;
		
	public KCPotion(String name, ResourceLocation location) {
		super(nextID, false, 0);
		
		/*this.mID = nextID++;
		this.mName = name;*/
		this.mLocation = location;
	}
	
	/** 重载实现效果 */
	@Override
	public abstract void performEffect(EntityLivingBase entity, int level);
	
	@Override
	public boolean isReady(int duration, int level)
	{
		return true;
	}
	
	// 以后实现
	@Override
	public void renderInventoryEffect(int x, int y, PotionEffect effect, net.minecraft.client.Minecraft mc)
	{
		
	}

}
