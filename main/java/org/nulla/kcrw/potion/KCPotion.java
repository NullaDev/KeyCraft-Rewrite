package org.nulla.kcrw.potion;

import org.nulla.kcrw.KCResources;

import net.minecraft.util.ResourceLocation;

public class KCPotion {
	
	public final int mID;
	public final String mName;
	public final ResourceLocation mLocation;
		
	public KCPotion(String name, ResourceLocation location) {
		this.mID = PotionUtils.AllPotions.size();
		this.mName = name;
		this.mLocation = location;
		PotionUtils.AllPotions.add(this);
	}
	
	public static final KCPotion poisonResistance = new KCPotion("poisonResistance", KCResources.potion_poisonResistance);

}
