package org.nulla.kcrw.mcalib.common.animation;

import java.util.ArrayList;

import net.minecraft.entity.Entity;

import org.nulla.kcrw.mcalib.common.IMCAnimatedEntity;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.relauncher.Side;

public class AnimTickHandler {
	private ArrayList<IMCAnimatedEntity> activeEntities = new ArrayList<IMCAnimatedEntity>();
	private ArrayList<IMCAnimatedEntity> removableEntities = new ArrayList<IMCAnimatedEntity>();

	public AnimTickHandler() {
		FMLCommonHandler.instance().bus().register(this);
	}

	public void addEntity(IMCAnimatedEntity entity) {
		activeEntities.add(entity);
	}

	//Called when the client ticks. 
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if(!activeEntities.isEmpty()) {
			if(event.phase == Phase.START) {
				for(IMCAnimatedEntity entity : activeEntities) {
					entity.getAnimationHandler().animationsUpdate();

					if(((Entity)entity).isDead) {
						removableEntities.add(entity);
					}
				}

				for(IMCAnimatedEntity entity : removableEntities) {
					activeEntities.remove(entity);
				}
				removableEntities.clear();
			}
		}
	}

	//Called when the server ticks. Usually 20 ticks a second. 
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {
		if(!activeEntities.isEmpty()) {
			if(event.phase == Phase.START) {
				for(IMCAnimatedEntity entity : activeEntities) {
					entity.getAnimationHandler().animationsUpdate();

					if(((Entity)entity).isDead) {
						removableEntities.add(entity);
					}
				}

				for(IMCAnimatedEntity entity : removableEntities) {
					activeEntities.remove(entity);
				}
				removableEntities.clear();
			}
		}
	}

	//Called when a new frame is displayed (See fps) 
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {
	}

	//Called when the world ticks
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
	}
}
