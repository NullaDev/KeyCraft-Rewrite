package org.nulla.kcrw;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;

public class KCMusicHelper {
	
	private static ISound currentSound = null; 
	
	public static boolean isPlayingMusic() {
		if (currentSound == null)
			return false;
		return KCUtils.getMC().getSoundHandler().isSoundPlaying(currentSound);
	}

	public static void playSound(ResourceLocation location) {
		if (!isPlayingMusic()) {
			stopMcBgm();
			currentSound = PositionedSoundRecord.func_147673_a(location);
			Minecraft.getMinecraft().getSoundHandler().playSound(KCMusicHelper.currentSound);
		}
	}
	
	
	private static boolean mcBgmPlaying = true;
	
	public static boolean isMcBgmPlaying() {
		return mcBgmPlaying;
	}
	
	public static void stopMcBgm() {
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
	
	public static void restartMcBgm() {
		if (mcBgmPlaying)
			return;
		
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
