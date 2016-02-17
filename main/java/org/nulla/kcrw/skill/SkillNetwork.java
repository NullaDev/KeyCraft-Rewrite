package org.nulla.kcrw.skill;

import java.io.IOException;

import org.nulla.kcrw.KCUtils;

import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.*;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.*;
import net.minecraft.entity.player.*;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.util.*;

public class SkillNetwork {
	
	public static final String CHANNEL_STRING = "REWRITE_SKILL_CHANNEL";
	public static FMLEventChannel channel;

	public static final int SYNC_AURORA_POINT_CODE = 0;
	public static final int SYNC_SKILL_CODE = 1;
	public static final int LEARN_SKILL_CODE = 2;
	public static final int USE_SKILL_CODE = 3;
	
	public static class SendSyncPacket {
		/** 发同步包 */
		protected static void syncSkills(EntityPlayer player) {
			if (player instanceof EntityPlayerMP) {
				channel.sendTo(createSyncAuroraPointPacket(player), (EntityPlayerMP)player);
				channel.sendTo(createSyncSkillPacket(player), (EntityPlayerMP)player);
			}
		}
		
		/** 服务器玩家登陆处理 */
		@SubscribeEvent
		public void onPlayerLoggedIn(PlayerLoggedInEvent event) {
			Skill.initializeAuroraPoint(event.player);
			syncSkills(event.player);
		}
		
		/** 玩家复活处理 */
		@SubscribeEvent
		public void onPlayerRespawn(PlayerRespawnEvent event) {
			syncSkills(event.player);
		}
		
		/** 玩家切换世界处理 */
		@SubscribeEvent
		public void onPlayerChangeDimension(PlayerChangedDimensionEvent event) {
			syncSkills(event.player);
		}
	}
	
	/** 服务器封包处理 */
	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event) {
		EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
		
		ByteBufInputStream stream = new ByteBufInputStream(event.packet.payload());
		try {
			switch (stream.readInt()) {
			case LEARN_SKILL_CODE:
	    		Skill.learnSkill(player, stream.readInt());
				break;
				
			case USE_SKILL_CODE:
				Skill.useSkill(player, stream.readInt());
				break;
			}
			
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/** 客户端封包处理 */
	@SubscribeEvent
	public void onClientPacket(ClientCustomPacketEvent event) {
		EntityPlayer player = KCUtils.getPlayerCl();
		
		ByteBufInputStream stream = new ByteBufInputStream(event.packet.payload());
		try {
			switch (stream.readInt()) {
			case SYNC_AURORA_POINT_CODE:
				Skill.setAuroraPoint(player, stream.readInt());
				break;
				
			case SYNC_SKILL_CODE:
				for (int i = 0; i < Skill.Skills.size(); i++) {
					Skill.setSkill(player, Skill.Skills.get(i), stream.readBoolean());
				}
				break;
			}
			
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	////////////////////////////////// 创建封包用的函数 //////////////////////////////////
	
	public static FMLProxyPacket createSyncAuroraPointPacket(EntityPlayer player) {
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try {
			stream.writeInt(SYNC_AURORA_POINT_CODE);
			stream.writeInt(Skill.getAuroraPoint(player));
			
			packet = new FMLProxyPacket(stream.buffer(), CHANNEL_STRING);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return packet;
	}
	
	public static FMLProxyPacket createSyncSkillPacket(EntityPlayer player) {
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try {
			stream.writeInt(SYNC_SKILL_CODE);
			for (Skill i : Skill.Skills) {
				stream.writeBoolean(Skill.hasSkill(player, i));
			}

			packet = new FMLProxyPacket(stream.buffer(), CHANNEL_STRING);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return packet;
	}
	
	public static FMLProxyPacket createLearnSkillPacket(int skillId) {
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try {
			stream.writeInt(LEARN_SKILL_CODE);
			stream.writeInt(skillId);
			
			packet = new FMLProxyPacket(stream.buffer(), CHANNEL_STRING);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return packet;
	}
	
	public static FMLProxyPacket createUseSkillPacket(int skillId) {
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try {
			stream.writeInt(USE_SKILL_CODE);
			stream.writeInt(skillId);
			
			packet = new FMLProxyPacket(stream.buffer(), CHANNEL_STRING);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return packet;
	}

}
