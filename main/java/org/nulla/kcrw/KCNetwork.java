package org.nulla.kcrw;

import java.io.IOException;

import org.nulla.kcrw.gui.GuiKotoriWorkshop;
import org.nulla.kcrw.item.KCItemBase;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.FMLNetworkEvent.ClientCustomPacketEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent.ServerCustomPacketEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.network.NetHandlerPlayServer;

/** 处理其他的网络事件 */
public class KCNetwork
{
	public static final String CHANNEL_STRING = "REWRITE_CHANNEL";
	public static FMLEventChannel Channel;
	
	// 单例模式
	private static KCNetwork instance = new KCNetwork();
	private KCNetwork(){ }
	public static KCNetwork getInstance(){ return instance; }
	
	/** 初始化，注册频道 */
	public void init()
	{
		Channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(KCNetwork.CHANNEL_STRING);
		Channel.register(this);
	}
	
	/*------------------- 自定义封包处理开始 -------------------*/
	
	public static final int CRAFT_CODE = 0;
	
	/** 服务器封包处理 */
	@SubscribeEvent
	public void onServerPacket(ServerCustomPacketEvent event)
	{
		EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
		
		ByteBufInputStream stream = new ByteBufInputStream(event.packet.payload());
		try
		{
			switch (stream.readInt())
			{
			case CRAFT_CODE:
				GuiKotoriWorkshop.craft(Item.getItemById(stream.readInt()), player);
				break;
			}
			
			stream.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/** 客户端封包处理 */
	@SubscribeEvent
	public void onClientPacket(ClientCustomPacketEvent event)
	{
		EntityPlayer player = KCUtils.getPlayerCl();
		
		ByteBufInputStream stream = new ByteBufInputStream(event.packet.payload());
		try
		{
			switch (stream.readInt())
			{
				
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
	
	public static FMLProxyPacket createCraftPacket(KCItemBase output)
	{
		ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
		FMLProxyPacket packet = null;
		try
		{
			stream.writeInt(CRAFT_CODE);
			stream.writeInt(Item.getIdFromItem(output));
			
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
