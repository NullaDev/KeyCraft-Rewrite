package org.nulla.kcrw.clinet;

import java.lang.reflect.Field;

import org.nulla.kcrw.KCUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.util.ResourceLocation;

public class KCMusicHelper {
	
	private static ISound currentSound = null; 
	
	public static boolean isBgmPlaying() {
		return currentSound != null;
	}

	/** 停止当前BGM、MC原版BGM并播放新的BGM */
	public static void playBgm(ResourceLocation location) {
		SoundHandler soundHandler = KCUtils.getMC().getSoundHandler();
		if (isBgmPlaying()) {
			soundHandler.stopSound(currentSound);
		}
		stopMcBgm();
		currentSound = PositionedSoundRecord.func_147673_a(location);
		soundHandler.playSound(currentSound);
	}
	
	/** 当前BGM播放完后恢复原版BGM */
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event)
	{
		Minecraft mc = KCUtils.getMC();
		if (event.phase == TickEvent.Phase.END
			&& !mc.isGamePaused()
			&& isBgmPlaying()
			&& !mc.getSoundHandler().isSoundPlaying(currentSound))
		{
			currentSound = null;
			restartMcBgm();
		}
	}
	
	
	private static boolean mcBgmPlaying = true;
	
	private static boolean isMcBgmPlaying() {
		return mcBgmPlaying;
	}
	
	private static void stopMcBgm() {
		if (!mcBgmPlaying)
			return;
		
		Minecraft mc = Minecraft.getMinecraft();
		try {
			Field musicTickerField = Minecraft.class.getDeclaredField("mcMusicTicker");
			musicTickerField.setAccessible(true);
			MusicTicker mcMusicTicker = (MusicTicker)musicTickerField.get(mc);

			Field bgmPlayingField = MusicTicker.class.getDeclaredField("field_147678_c");
			Field bgmCdField = MusicTicker.class.getDeclaredField("field_147676_d");
			bgmPlayingField.setAccessible(true);
			bgmCdField.setAccessible(true);
			
			bgmCdField.setInt(mcMusicTicker, Integer.MAX_VALUE);
			ISound bgmPlaying = (ISound)bgmPlayingField.get(mcMusicTicker);
			if (bgmPlaying != null)
				mc.getSoundHandler().stopSound(bgmPlaying);
			bgmPlayingField.set(mcMusicTicker, null);
			
			mcBgmPlaying = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void restartMcBgm() {
		if (mcBgmPlaying)
			return;
		if (isBgmPlaying())
		{
			KCUtils.getMC().getSoundHandler().stopSound(currentSound);
			currentSound = null;
		}
		
		Minecraft mc = Minecraft.getMinecraft();
		try {
			Field musicTickerField = Minecraft.class.getDeclaredField("mcMusicTicker");
			musicTickerField.setAccessible(true);
			MusicTicker mcMusicTicker = (MusicTicker)musicTickerField.get(mc);

			Field bgmCdField = MusicTicker.class.getDeclaredField("field_147676_d");
			bgmCdField.setAccessible(true);
			
			bgmCdField.setInt(mcMusicTicker, 0);
			
			mcBgmPlaying = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
