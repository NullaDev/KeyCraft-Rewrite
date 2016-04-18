package org.nulla.kcrw.common;

import java.util.ArrayList;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class KCEffectHandler {
	
	public static ArrayList<KCSpecialEffect> AllEffects = new ArrayList<KCSpecialEffect>();
	
	@SubscribeEvent
	public void Effect_Triggerer(PlayerTickEvent event) {
		if (event.phase == Phase.END) {
			return;
		}
		
		ArrayList<KCSpecialEffect> NewAllEffects = new ArrayList<KCSpecialEffect>();
		
		for (KCSpecialEffect i : AllEffects) {
			if (i instanceof KCDelayedEffect) {
				KCDelayedEffect effect = (KCDelayedEffect) i;
				if (effect.rest_ticks != 0) {
					KCDelayedEffect newEffect = effect.clone();
					newEffect.rest_ticks--;
					NewAllEffects.add(newEffect);
				} else {
					effect.onTrigger();
				}
			}
		}
		
		AllEffects = NewAllEffects;
		
	}
}
