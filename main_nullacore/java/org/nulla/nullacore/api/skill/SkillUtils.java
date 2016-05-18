package org.nulla.nullacore.api.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

/** 实现关于技能的其他部分（欧若拉点、技能槽） */
public class SkillUtils {
	
	/*------------------- 欧若拉点开始 -------------------*/
	
	public static final int MAX_AURORA_POINT = 65535;
	public static final int INITIAL_AURORA_POINT = 16384;

	/** 取欧若拉点 */
	public static int getAuroraPoint(EntityPlayer player) {
		return player.getEntityData().getInteger("AuroraPoint");
	}
	
	/** 设置欧若拉点，如果在服务端会发同步包 */
	public static void setAuroraPoint(EntityPlayer player, int point) {
		if (point < 0)
			point = 0;
		else if (point > MAX_AURORA_POINT)
			point = MAX_AURORA_POINT;
		player.getEntityData().setInteger("AuroraPoint", point);
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncAuroraPointPacket(player), (EntityPlayerMP)player);
	}
	
	/** 改变欧若拉点，如果在服务端会发同步包 */
	public static void modifyAuroraPoint(EntityPlayer player, int point) {
		setAuroraPoint(player, getAuroraPoint(player) + point);
	}
	
	/** 初始化欧若拉点数，用于玩家登陆服务器时，如果已初始化不会重复初始化 */
	public static void initializeAuroraPoint(EntityPlayer player) {
		if (!hasInitialized(player)) {
			setAuroraPoint(player, INITIAL_AURORA_POINT);
		}
	}
	
	/** 判断是否已经初始化欧若拉点数 */
	public static boolean hasInitialized(EntityPlayer player) {
		return player.getEntityData().hasKey("AuroraPoint");
	}

	/*------------------- 欧若拉点结束 -------------------*/
	/*------------------- 技能槽开始 -------------------*/
	
	public static final int SKILL_SLOT_SIZE = 4;
	
	/** 取出技能槽里对应的技能 */
	public static Skill getSkillInSlot(EntityPlayer player, int pos) {
		final String name = "SkillSlot" + Integer.toString(pos);
		final NBTTagCompound nbt = player.getEntityData();
		if (!nbt.hasKey(name))
			return null;
		int skill = nbt.getInteger(name);
		if (0 <= skill && skill < Skills.AllSkills.size())
			return Skills.AllSkills.get(skill);
		return null;
	}
	
	/** 把技能放进技能槽里 */
	public static void setSkillInSlot(EntityPlayer player, int pos, Skill skill, boolean shouldSync) {
		final String name = "SkillSlot" + Integer.toString(pos);
		player.getEntityData().setInteger(name, skill != null ? skill.mID : -1);
		
		if (shouldSync) {
			if (player instanceof EntityPlayerMP)
				SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillSlotPacket(player), (EntityPlayerMP)player);
			else
				SkillNetwork.Channel.sendToServer(SkillNetwork.createSyncSkillSlotPacket(player));
		}
	}
	
	/** 把技能放进技能槽里 */
	public static void setSkillInSlot(EntityPlayer player, int pos, int skill, boolean shouldSync) {
		if (0 <= skill && skill < Skills.AllSkills.size())
			setSkillInSlot(player, pos, Skills.AllSkills.get(skill), shouldSync);
		else
			setSkillInSlot(player, pos, null, shouldSync);
	}
	
	/*------------------- 技能槽结束 -------------------*/
	
}
