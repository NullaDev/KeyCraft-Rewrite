package org.nulla.kcrw;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class KCCreativeTab extends CreativeTabs {

	public KCCreativeTab(String lable) {
		super(lable);
	}

	@Override
	public Item getTabIconItem() {
		return Items.apple;
	}

}
