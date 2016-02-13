package org.nulla.kcrw;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class KCUtils {
	
	public static Minecraft getMC() {
		return Minecraft.getMinecraft();
	}
	
	/** 只能在Client端用，以获取Client端的玩家信息（例如位置），不会改变Server端信息。 */
	public static EntityPlayer getPlayerCl() {
		return getMC().thePlayer;
	}

}
