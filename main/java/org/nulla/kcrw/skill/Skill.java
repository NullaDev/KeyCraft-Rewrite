package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

/** 所有技能的基类，提供欧若拉点、技能、CD的接口 */
public abstract class Skill {
	/*------------------- 技能动态部分开始 -------------------*/
	
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
	
	public Skill(String name, int auroraRequired, int auroraCost, int cd) {
		this.mID = Skills.AllSkills.size();
		this.mName = name;
		this.mAuroraRequired = auroraRequired;
		this.mAuroraCost = auroraCost;
		this.mCD = cd;
		Skills.AllSkills.add(this);
	}
	
	/** 重载实现使用技能 */
	protected abstract boolean onUse(EntityPlayer player);
	
	/*------------------- 技能动态部分结束 -------------------*/
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
	/*------------------- 技能开始 -------------------*/

	/** 判断有没有技能 */
	public static boolean hasSkill(EntityPlayer player, Skill skill) {
		return player.getEntityData().getBoolean("Skill" + skill.mName);
	}

	/** 设置有没有技能 */
	public static void setSkill(EntityPlayer player, Skill skill, boolean hasSkill) {
		player.getEntityData().setBoolean("Skill" + skill.mName, hasSkill);
	}
	
	/** 学习技能，会发同步包，如果技能槽还有空位就塞进去 */
	public static void learnSkill(EntityPlayer player, Skill skill) {
		if (hasSkill(player, skill))
			return;
		
		if (getAuroraPoint(player) > skill.mAuroraRequired) { // 不让欧若拉变为0
			modifyAuroraPoint(player, -skill.mAuroraRequired);

			// 客户端发学习技能包
			if (player.worldObj.isRemote)
				SkillNetwork.Channel.sendToServer(SkillNetwork.createLearnSkillPacket(skill.mID));
			setSkill(player, skill, true);
		}
		// 服务端发同步技能包
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
		
		// 放进技能槽
		for (int i = 0; i < SKILL_SLOT_SIZE; i++) {
			if (getSkillInSlot(player, i) == null) {
				setSkillInSlot(player, i, skill, true);
				break;
			}
		}
	}
	
	/** 学习技能，会发同步包，如果技能槽还有空位就塞进去 */
	public static void learnSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.AllSkills.size()) {
			learnSkill(player, Skills.AllSkills.get(skill));
		}
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static boolean useSkill(EntityPlayer player, Skill skill) {
		// 检查技能是不是null
		if (skill == null)
			return false;
		// 检查拥有技能
		if (!hasSkill(player, skill))
			return false;
		// 检查CD
		long curTime = player.worldObj.getTotalWorldTime();
		if (curTime - getLastUseTime(player, skill) < skill.mCD)
			return false;
		// 检查欧若拉点
		if (getAuroraPoint(player) < skill.mAuroraCost) // 不让欧若拉变为0？
			return false;
		
		// 客户端发使用技能包
		if (player.worldObj.isRemote)
			SkillNetwork.Channel.sendToServer(SkillNetwork.createUseSkillPacket(skill.mID));
		boolean res = skill.onUse(player);
		
		if (res) {
			// 设置上次使用时间
			setLastUseTime(player, skill, curTime);
			// 减欧若拉点
			modifyAuroraPoint(player, -skill.mAuroraCost);
		}
		return res;
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static boolean useSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.AllSkills.size())
			return useSkill(player, Skills.AllSkills.get(skill));
		return false;
	}
	
	/*------------------- 技能结束 -------------------*/
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
	/*------------------- CD开始 -------------------*/
	
	/** 取上次使用时间，以World.getTotalWorldTime()计算时间 */
	public static long getLastUseTime(EntityPlayer player, Skill skill) {
		final String name = "LastTime" + skill.mName;
		final NBTTagCompound nbt = player.getEntityData();
		if (!nbt.hasKey(name))
			return -skill.mCD;
		return nbt.getLong(name);
	}
	
	/** 设置上次使用时间，以World.getTotalWorldTime()计算时间 */
	public static void setLastUseTime(EntityPlayer player, Skill skill, long time) {
		player.getEntityData().setLong("LastTime" + skill.mName, time);
	}
	
	/*------------------- CD结束 -------------------*/
	
}
