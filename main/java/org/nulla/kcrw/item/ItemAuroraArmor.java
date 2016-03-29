package org.nulla.kcrw.item;

import org.nulla.kcrw.KCMaterials;
import org.nulla.kcrw.KeyCraft_Rewrite;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ItemAuroraArmor extends ItemArmor {
	
	int pos;

	/** position代表护甲位置，0为头盔，1为胸甲，2为护腿，3为靴子。 */
	public ItemAuroraArmor(int position) {
		super(KCMaterials.AuroraIronArmor, 0, position);
		this.pos = position;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		if (pos == 2)
			return KeyCraft_Rewrite.MODID + ":textures/models/armor/auroraIron_layer_2.png";	
		return KeyCraft_Rewrite.MODID + ":textures/models/armor/auroraIron_layer_1.png";	
	}
	
}
