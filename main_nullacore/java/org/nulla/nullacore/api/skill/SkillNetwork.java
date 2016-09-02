package org.nulla.nullacore.api.skill;

import java.io.IOException;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;

/** 处理和技能有关的网络事件 */
public class SkillNetwork
{
	public static final String CHANNEL_STRING = "RW_SKILL_CHANNEL";
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
	
	/** 服务器同步欧若拉点、技能的辅助函数 */
	private void syncSkills(EntityPlayerMP player)
	{
		if (player instanceof EntityPlayerMP)
		{
			Channel.sendTo(createSyncAuroraPointPacket(player), player);
			Channel.sendTo(createSyncSkillPacket(player), player);
			Channel.sendTo(createSyncSkillSlotPacket(player), player);
		}
	}
	
	/** 服务器玩家登陆处理，初始化欧若拉点、同步 */
	@SubscribeEvent
	public void onPlayerLoggedIn(PlayerLoggedInEvent event)
	{
		SkillUtils.initializeAuroraPoint(event.player);
		syncSkills((EntityPlayerMP)event.player);
	}
	
	/** 服务器玩家复活处理，同步 */
	@SubscribeEvent
	public void onPlayerRespawn(PlayerRespawnEvent event)
	{
		syncSkills((EntityPlayerMP)event.player);
	}
		
	/** 服务器玩家切换世界处理，同步  */
	@SubscribeEvent
	public void onPlayerChangeDimension(PlayerChangedDimensionEvent event)
	{
		syncSkills((EntityPlayerMP)event.player);
	}
	
	/**
	 * 在玩家死亡时将旧玩家的所有Skill以及欧若拉点数克隆给Respawn以后的新玩家
	 * 在从末地通关回到主世界时也会调用PlayerEvent.Clone和PlayerRespawnEvent，而不会调用PlayerChangedDimensionEvent
	 */
	@SubscribeEvent
	public void onClone(PlayerEvent.Clone event) {
		final EntityPlayer _old = event.original;
		final EntityPlayer _new = event.entityPlayer;

		// 设置新玩家欧若拉点数，切换世界点数不变，死亡减512
		final int newAuroraPoint = event.wasDeath ? Math.max(SkillUtils.getAuroraPoint(_old) - 512, 0) : SkillUtils.getAuroraPoint(_old);
		_new.getEntityData().setInteger("AuroraPoint", newAuroraPoint);
		
		// 克隆技能、CD、经验、开启
		for (Skill i : Skills.AllSkills)
		{
			i.setSkill(_new, i.hasSkill(_old));
			i.setLastUseTime(_new, i.getLastUseTime(_old));
			i.setExperience(_new, i.getExperience(_old));
			if (i instanceof SkillPassive)
				((SkillPassive)i).setIsOn(_new, ((SkillPassive)i).getIsOn(_old));
		}
		
		// 克隆技能槽
		for (int i = 0; i < SkillUtils.SKILL_SLOT_SIZE; i++)
			SkillUtils.setSkillInSlot(_new, i, SkillUtils.getSkillInSlot(_old, i), false);
		
		// 复活或切换世界后同步，@onPlayerRespawn
	}
	
	/*------------------- 事件处理结束 -------------------*/
	/*------------------- 自定义封包处理开始 -------------------*/
	
	public static final int SYNC_AURORA_POINT_CODE = 0;
	public static final int SYNC_SKILL_CODE = 1;
	public static final int LEARN_SKILL_CODE = 2;
	public static final int USE_SKILL_CODE = 3;
	public static final int SYNC_SKILL_SLOT_CODE = 4;
	
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
				
			case SYNC_SKILL_SLOT_CODE:
				for (int i = 0; i < SkillUtils.SKILL_SLOT_SIZE; i++)
					SkillUtils.setSkillInSlot(player, i, stream.readInt(), false);
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
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		
		ByteBufInputStream stream = new ByteBufInputStream(event.packet.payload());
		try
		{
			switch (stream.readInt())
			{
			case SYNC_AURORA_POINT_CODE:
				SkillUtils.setAuroraPoint(player, stream.readInt());
				break;
				
			case SYNC_SKILL_CODE:
				for (Skill i : Skills.AllSkills)
				{
					i.setSkill(player, stream.readBoolean());
					i.setLastUseTime(player, stream.readLong());
					i.setExperience(player, stream.readInt());
					if (i instanceof SkillPassive)
						((SkillPassive)i).setIsOn(player, stream.readBoolean());
				}
				break;
				
			case SYNC_SKILL_SLOT_CODE:
				for (int i = 0; i < SkillUtils.SKILL_SLOT_SIZE; i++)
					SkillUtils.setSkillInSlot(player, i, stream.readInt(), false);
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
			stream.writeInt(SkillUtils.getAuroraPoint(player));
			
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
				stream.writeBoolean(i.hasSkill(player));
				stream.writeLong(i.getLastUseTime(player));
				stream.writeInt(i.getExperience(player));
				if (i instanceof SkillPassive)
					stream.writeBoolean(((SkillPassive)i).getIsOn(player));
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
			stream.writeInt(SYNC_SKILL_SLOT_CODE);
			for (int i = 0; i < SkillUtils.SKILL_SLOT_SIZE; i++)
			{
				Skill skill = SkillUtils.getSkillInSlot(player, i);
				if (skill != null)
					stream.writeInt(skill.mID);
				else
					stream.writeInt(-1);
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
	
	/*------------------- 创建封包用的函数结束 -------------------*/

}
