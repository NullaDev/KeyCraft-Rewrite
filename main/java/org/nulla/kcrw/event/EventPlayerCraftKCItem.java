package org.nulla.kcrw.event;

import org.nulla.kcrw.item.KCItemBase;

import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.player.EntityPlayer;

public class EventPlayerCraftKCItem extends Event {
	
	public final EntityPlayer crafter;
	public final KCItemBase kcitem;
	
	public EventPlayerCraftKCItem(EntityPlayer player, KCItemBase item) {
		this.crafter = player;
		this.kcitem = item;
	}

}
