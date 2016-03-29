package org.nulla.kcrw.item;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.*;

public class ItemAuroraIronIngot extends KCItemBase {
		
	@SideOnly(Side.CLIENT)
	@Override
    public boolean hasEffect(ItemStack p_77636_1_) {
        return true;
    }

}
