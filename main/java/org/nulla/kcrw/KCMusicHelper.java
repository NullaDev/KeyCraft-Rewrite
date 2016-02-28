package org.nulla.kcrw;

import net.minecraft.client.audio.ISound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.sound.*;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class KCMusicHelper {
	
	public static ISound currentSound = null; 
	
	public static boolean isPlayingMusic() {
		if (currentSound == null)
			return false;
		return KCUtils.getMC().getSoundHandler().isSoundPlaying(currentSound);
	}
	
	@SubscribeEvent
	public void cancelBGM(PlaySoundEvent event) {
		KCUtils.getPlayerCl().addChatMessage(new ChatComponentText("nmsl,wsnd"));
		if (isPlayingMusic())
			event.setCanceled(true);
	}

}
