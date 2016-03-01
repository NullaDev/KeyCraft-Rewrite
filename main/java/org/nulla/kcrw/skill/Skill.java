package org.nulla.kcrw.skill;

import org.nulla.kcrw.*;

import net.minecraft.entity.player.*;
import net.minecraft.nbt.NBTTagCompound;

/** 所有技能的基类，实现和技能紧密相关的部分（使用、CD、熟练度） */
public abstract class Skill {
	
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
	
	/*------------------- 技能实现部分开始 -------------------*/
	
	/** 重载实现使用技能 */
	protected abstract boolean onUse(EntityPlayer player);
	
	/*------------------- 技能实现部分结束 -------------------*/
	/*------------------- 学习技能开始 -------------------*/

	/** 判断有没有技能 */
	public boolean hasSkill(EntityPlayer player) {
		return player.getEntityData().getBoolean("Skill" + mName);
	}

	/** 设置有没有技能 */
	public void setSkill(EntityPlayer player, boolean hasSkill) {
		player.getEntityData().setBoolean("Skill" + mName, hasSkill);
	}
	
	/** 学习技能，会发同步包，如果技能槽还有空位就塞进去 */
	public void learnSkill(EntityPlayer player) {
		if (hasSkill(player))
			return;
		
		if (SkillUtils.getAuroraPoint(player) > mAuroraRequired) { // 不让欧若拉变为0
			SkillUtils.modifyAuroraPoint(player, -mAuroraRequired);

			// 客户端发学习技能包
			if (player.worldObj.isRemote)
				SkillNetwork.Channel.sendToServer(SkillNetwork.createLearnSkillPacket(mID));
			setSkill(player, true);
		}
		// 服务端发同步技能包
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
		
		// 放进技能槽
		for (int i = 0; i < SkillUtils.SKILL_SLOT_SIZE; i++) {
			if (SkillUtils.getSkillInSlot(player, i) == null) {
				SkillUtils.setSkillInSlot(player, i, this, true);
				break;
			}
		}
	}
	
	/** 学习技能，会发同步包，如果技能槽还有空位就塞进去 */
	public static void learnSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.AllSkills.size())
			Skills.AllSkills.get(skill).learnSkill(player);
	}

	/*------------------- 学习技能结束 -------------------*/
	/*------------------- 使用技能开始 -------------------*/
	
	/** 使用技能，会发同步包 */
	public boolean useSkill(EntityPlayer player) {
		// 检查拥有技能
		if (!hasSkill(player))
			return false;
		// 检查CD
		if (!checkCD(player))
			return false;
		// 检查欧若拉点
		if (SkillUtils.getAuroraPoint(player) <= mAuroraCost) // 不让欧若拉变为0
			return false;
		
		// 客户端发使用技能包
		if (player.worldObj.isRemote)
			SkillNetwork.Channel.sendToServer(SkillNetwork.createUseSkillPacket(mID));
		boolean res = onUse(player);
		
		if (res) {
			// 设置上次使用时间
			setLastUseTime(player, player.worldObj.getTotalWorldTime());
			// 减欧若拉点
			SkillUtils.modifyAuroraPoint(player, -mAuroraCost);
		}
		
		// 服务端发同步技能包
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);		
		return res;
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static boolean useSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.AllSkills.size())
			return Skills.AllSkills.get(skill).useSkill(player);
		return false;
	}
	
	/*------------------- 使用技能结束 -------------------*/
	/*------------------- CD开始 -------------------*/
	
	/** 取上次使用时间，以World.getTotalWorldTime()计算时间 */
	public long getLastUseTime(EntityPlayer player) {
		final String name = "LastTime" + mName;
		final NBTTagCompound nbt = player.getEntityData();
		if (!nbt.hasKey(name))
			return -mCD;
		return nbt.getLong(name);
	}
	
	/** 设置上次使用时间，以World.getTotalWorldTime()计算时间 */
	public void setLastUseTime(EntityPlayer player, long time) {
		player.getEntityData().setLong("LastTime" + mName, time);
	}
	
	/** 技能已冷却则返回true */
	public boolean checkCD(EntityPlayer player) {
		return player.worldObj.getTotalWorldTime() - getLastUseTime(player) >= mCD;
	}
	
	/*------------------- CD结束 -------------------*/
	/*------------------- 熟练度开始 -------------------*/
	
	public static final int MAX_EXPERIENCE = 1024;
	
	/** 取熟练度 */
	public int getExperience(EntityPlayer player) {
		return player.getEntityData().getInteger("Experience" + mName);
	}
	
	/** 设置熟练度，如果在服务端会发同步包 */
	public void setExperience(EntityPlayer player, int experience) {
		if (experience < 0)
			experience = 0;
		else if (experience > MAX_EXPERIENCE)
			experience = MAX_EXPERIENCE;
		player.getEntityData().setInteger("Experience" + mName, experience);
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
	}
	
	/** 调整熟练度，如果在服务端会发同步包 */
	public void modifyExperience(EntityPlayer player, int experience) {
		setExperience(player, getExperience(player) + experience);
	}
	
	/*------------------- 熟练度结束 -------------------*/
	
}
