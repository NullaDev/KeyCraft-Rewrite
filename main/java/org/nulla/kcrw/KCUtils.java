package org.nulla.kcrw;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class KCUtils {
	
	public static Minecraft getMC() {
		return Minecraft.getMinecraft();
	}
	
	/** ֻ����Client���ã��Ի�ȡClient�˵������Ϣ������λ�ã�������ı�Server����Ϣ�� */
	public static EntityPlayer getPlayerCl() {
		return getMC().thePlayer;
	}

}
