package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class Skill
{
	
	public static final int AURORA_POINT_MAXIMUM = 65535;
	public static final int INITIAL_AURORA_POINT = 16384;
	
	
	/** 在Skills中的索引 */
	public final int mID;
	/** 用于NBT */
	public final String mName;
	/** 学习消耗的欧若拉 */
	public final int mAuroraRequired;
	/** 使用消耗的欧若拉 */
	public final int mAuroraCost;
	/** 单位：Tick */
	public final int mCD;
	
	public Skill(String name, int auroraRequired, int auroraCost, int cd)
	{
		this.mID = Skills.AllSkills.size();
		this.mName = name;
		this.mAuroraRequired = auroraRequired;
		this.mAuroraCost = auroraCost;
		this.mCD = cd;
		Skills.AllSkills.add(this);
	}
	
	/** 重载实现使用技能 */
	protected boolean onUse(EntityPlayer player)
	{
		return false;
	}
	
	
	/** 取欧若拉点 */
	public static int getAuroraPoint(EntityPlayer player)
	{
		return player.getEntityData().getInteger("AuroraPoint");
	}
	
	/** 设置欧若拉点，如果在服务端会发同步包 */
	public static void setAuroraPoint(EntityPlayer player, int point)
	{
		if (point < 0)
			point = 0;
		else if (point > AURORA_POINT_MAXIMUM)
			point = AURORA_POINT_MAXIMUM;
		player.getEntityData().setInteger("AuroraPoint", point);
		if (player instanceof EntityPlayerMP)
			SkillNetwork.channel.sendTo(SkillNetwork.createSyncAuroraPointPacket(player), (EntityPlayerMP)player);
	}
	
	/** 改变欧若拉点，如果在服务端会发同步包 */
	public static void modifyAuroraPoint(EntityPlayer player, int point)
	{
		setAuroraPoint(player, getAuroraPoint(player) + point);
	}
	
	/** 初始化欧若拉点数，用于玩家刚出生时 */
	public static void initializeAuroraPoint(EntityPlayer player)
	{
		// 测试
		Skill.learnSkill(player, Skills.Test);
		if (!hasInitialized(player))
			setAuroraPoint(player, INITIAL_AURORA_POINT);
	}
	
	/** 判断是否已经初始化欧若拉点数，用于玩家刚出生时 */
	public static boolean hasInitialized(EntityPlayer player)
	{
		return player.getEntityData().hasKey("AuroraPoint");
	}

	/** 判断有没有技能 */
	public static boolean hasSkill(EntityPlayer player, Skill skill)
	{
		return player.getEntityData().getBoolean("Skill" + skill.mName);
	}

	/** 设置有没有技能 */
	public static void setSkill(EntityPlayer player, Skill skill, boolean hasSkill)
	{
		player.getEntityData().setBoolean("Skill" + skill.mName, hasSkill);
	}
	
	/** 学习技能，会发同步包 */
	public static void learnSkill(EntityPlayer player, Skill skill)
	{
		if (hasSkill(player, skill))
			return;
		
		if (getAuroraPoint(player) > skill.mAuroraRequired) // 不让欧若拉变为0？
		{
			modifyAuroraPoint(player, -skill.mAuroraRequired);

			// 客户端发学习技能包
			if (player.worldObj.isRemote)
				SkillNetwork.channel.sendToServer(SkillNetwork.createLearnSkillPacket(skill.mID));
			setSkill(player, skill, true);
		}
		// 服务端发同步技能包
		if (player instanceof EntityPlayerMP)
			SkillNetwork.channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
	}
	
	/** 学习技能，会发同步包 */
	public static void learnSkill(EntityPlayer player, int skill)
	{
		if (0 <= skill && skill < Skills.AllSkills.size())
			learnSkill(player, Skills.AllSkills.get(skill));
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static boolean useSkill(EntityPlayer player, Skill skill)
	{
		// 检查拥有技能
		if (!hasSkill(player, skill))
			return false;
		// 检查CD
		long curTime = player.worldObj.getTotalWorldTime();
		if (curTime - getLastUseTime(player, skill) < skill.mCD)
			return false;
		// 检查欧若拉点
		if (getAuroraPoint(player) < skill.mAuroraCost)
			return false;
		modifyAuroraPoint(player, -skill.mAuroraCost);
		
		// 客户端发使用技能包
		if (player.worldObj.isRemote)
			SkillNetwork.channel.sendToServer(SkillNetwork.createUseSkillPacket(skill.mID));
		boolean res = skill.onUse(player);
		
		// 设置上次使用时间
		if (res)
			setLastUseTime(player, skill, curTime);
		return res;
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static boolean useSkill(EntityPlayer player, int skill)
	{
		if (0 <= skill && skill < Skills.AllSkills.size())
			return useSkill(player, Skills.AllSkills.get(skill));
		return false;
	}
	
	/** 取上次使用时间 */
	public static long getLastUseTime(EntityPlayer player, Skill skill)
	{
		final String name = "LastTime" + skill.mName;
		final NBTTagCompound nbt = player.getEntityData();
		if (!nbt.hasKey(name))
			return -skill.mCD;
		return nbt.getLong(name);
	}
	
	/** 设置上次使用时间 */
	public static void setLastUseTime(EntityPlayer player, Skill skill, long time)
	{
		player.getEntityData().setLong("LastTime" + skill.mName, time);
	}
	
}
