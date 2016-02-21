package org.nulla.kcrw.skill;

import java.io.IOException;

import org.nulla.kcrw.KCUtils;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

/** 处理和技能有关的网络事件 */
public class SkillNetwork
{
	public static final String CHANNEL_STRING = "REWRITE_SKILL_CHANNEL";
	public static FMLEventChannel Channel;
	
	// 单例模式
	private static SkillNetwork instance = new SkillNetwork();
	private SkillNetwork(){ }
	public static SkillNetwork getInstance(){ return instance; }
	
	/** 初始化，注册事件、频道 */
	public void init()
	{
		FMLCommonHandler.instance().bus().register(this);
		MinecraftForge.EVENT_BUS.register(this);
		Channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(SkillNetwork.CHANNEL_STRING);
		Channel.register(this);
	}
	
	/*------------------- 事件处理开始 -------------------*/
	
	/** 同步欧若拉点、技能的辅助函数 */
	private void syncSkills(EntityPlayer player)
	{
		if (player instanceof EntityPlayerMP)
		{
			Channel.sendTo(createSyncAuroraPointPacket(player), (EntityPlayerMP)player);
			Channel.sendTo(createSyncSkillPacket(player), (EntityPlayerMP)player);
			Channel.sendTo(createSyncSkillSlotPacket(player), (EntityPlayerMP)player);
		}
	}
	
	/** 服务器玩家登陆处理，初始化欧若拉点、同步 */
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		Skill.initializeAuroraPoint(event.player);
		syncSkills(event.player);
	}
	
	/** 玩家复活处理，同步 */
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		syncSkills(event.player);
	}
		
	/** 玩家切换世界处理，同步  */
	@SubscribeEvent
	public void onPlayerChangeDimension(PlayerChangedDimensionEvent event)
	{
		syncSkills(event.player);
	}
	
	/**
	 * 在玩家死亡时将旧玩家的所有Skill以及欧若拉点数克隆给Respawn以后的新玩家
	 * 在从末地通关回到主世界时也会调用PlayerEvent.Clone和PlayerRespawnEvent，而不会调用PlayerChangedDimensionEvent
	 */
	@SubscribeEvent
	public void onClone(PlayerEvent.Clone event)
	{
		final EntityPlayer _old = event.original;
		final EntityPlayer _new = event.entityPlayer;

		// 设置新玩家欧若拉点数，切换世界点数不变，死亡减10
		final int newAuroraPoint = event.wasDeath ? Math.max(Skill.getAuroraPoint(_old) - 10, 0) : Skill.getAuroraPoint(_old);
		_new.getEntityData().setInteger("AuroraPoint", newAuroraPoint);
		
		// 克隆技能、CD
		for (Skill i : Skills.AllSkills)
		{
			Skill.setSkill(_new, i, Skill.hasSkill(_old, i));
			Skill.setLastUseTime(_new, i, Skill.getLastUseTime(_old, i));
		}
		
		// 复活或切换世界后同步，@SendSyncPacket.onPlayerRespawn
	}
	
	/*------------------- 事件处理结束 -------------------*/
	/*------------------- 自定义封包处理开始 -------------------*/
	
	public static final int SYNC_AURORA_POINT_CODE = 0;
	public static final int SYNC_SKILL_CODE = 1;
	public static final int LEARN_SKILL_CODE = 2;
	public static final int USE_SKILL_CODE = 3;
	public static final int SYNC_SLOT_CODE = 4;
	
	/** 服务器封包处理，学习技能、使用技能 */
	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event)
	{
		EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
		
		ByteBufInputStream stream = new ByteBufInputStream(event.packet.payload());
		try
		{
			switch (stream.readInt())
			{
			case LEARN_SKILL_CODE:
	    		Skill.learnSkill(player, stream.readInt());
				break;
				
			case USE_SKILL_CODE:
				Skill.useSkill(player, stream.readInt());
				break;
			}
			
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/** 客户端封包处理，同步欧若拉点、技能 */
	@SubscribeEvent
	public void onClientPacket(ClientCustomPacketEvent event)
	{
		EntityPlayer player = KCUtils.getPlayerCl();
		
		ByteBufInputStream stream = new ByteBufInputStream(event.packet.payload());
		try
		{
			switch (stream.readInt())
			{
			case SYNC_AURORA_POINT_CODE:
				Skill.setAuroraPoint(player, stream.readInt());
				break;
				
			case SYNC_SKILL_CODE:
				for (int i = 0; i < Skills.AllSkills.size(); i++)
				{
					Skill.setSkill(player, Skills.AllSkills.get(i), stream.readBoolean());
					Skill.setLastUseTime(player, Skills.AllSkills.get(i), stream.readLong());
				}
				break;
			}
			
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/*------------------- 自定义封包处理结束 -------------------*/
	/*------------------- 创建封包用的函数开始 -------------------*/
	
	public static FMLProxyPacket createSyncAuroraPointPacket(EntityPlayer player)
	{
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try
		{
			stream.writeInt(SYNC_AURORA_POINT_CODE);
			stream.writeInt(Skill.getAuroraPoint(player));
			
			packet = new FMLProxyPacket(stream.buffer(), CHANNEL_STRING);
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return packet;
	}
	
	public static FMLProxyPacket createSyncSkillPacket(EntityPlayer player)
	{
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try
		{
			stream.writeInt(SYNC_SKILL_CODE);
			for (Skill i : Skills.AllSkills)
			{
				stream.writeBoolean(Skill.hasSkill(player, i));
				stream.writeLong(Skill.getLastUseTime(player, i));
			}

			packet = new FMLProxyPacket(stream.buffer(), CHANNEL_STRING);
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return packet;
	}
	
	public static FMLProxyPacket createLearnSkillPacket(int skillId)
	{
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try
		{
			stream.writeInt(LEARN_SKILL_CODE);
			stream.writeInt(skillId);
			
			packet = new FMLProxyPacket(stream.buffer(), CHANNEL_STRING);
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return packet;
	}
	
	public static FMLProxyPacket createUseSkillPacket(int skillId)
	{
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try
		{
			stream.writeInt(USE_SKILL_CODE);
			stream.writeInt(skillId);
			
			packet = new FMLProxyPacket(stream.buffer(), CHANNEL_STRING);
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return packet;
	}
	
	public static FMLProxyPacket createSyncSkillSlotPacket(EntityPlayer player)
	{
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try
		{
			stream.writeInt(SYNC_SLOT_CODE);
			for (int i = 0; i < 4; i++)
				if (Skill.getSkillInSlot(player, i) != null)
					stream.writeInt(Skill.getSkillInSlot(player, i).mID);
				else
					stream.writeInt(-1);
			
			packet = new FMLProxyPacket(stream.buffer(), CHANNEL_STRING);
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return packet;
	}
	
	/*------------------- 创建封包用的函数结束 -------------------*/

}
