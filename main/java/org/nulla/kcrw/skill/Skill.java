package org.nulla.kcrw.skill;

import org.nulla.kcrw.KCUtils;

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
	/*------------------- 技能开始 -------------------*/

	/** 判断有没有技能 */
	public boolean hasSkill() {
		return SkillUtils.hasSkill(KCUtils.getPlayerCl(), this);
	}
	
	/** 设置有没有技能 */
	public void setSkill(boolean hasSkill) {
		SkillUtils.setSkill(KCUtils.getPlayerCl(), this, hasSkill);
	}
	
	/** 使用技能，会发同步包 */
	public boolean useSkill() {
		return SkillUtils.useSkill(KCUtils.getPlayerCl(), this);
	}
	
	/*------------------- 技能结束 -------------------*/
	/*------------------- 技能槽开始 -------------------*/
	
	/** 把技能放进技能槽里 */
	public void setSkillInSlot(int pos, boolean shouldSync) {
		SkillUtils.setSkillInSlot(KCUtils.getPlayerCl(), pos, this, shouldSync);
	}
	
	/*------------------- 技能槽结束 -------------------*/
	/*------------------- CD开始 -------------------*/
	
	/** 取上次使用时间，以World.getTotalWorldTime()计算时间 */
	public long getLastUseTime() {
		return SkillUtils.getLastUseTime(KCUtils.getPlayerCl(), this);
	}
	
	/** 设置上次使用时间，以World.getTotalWorldTime()计算时间 */
	public void setLastUseTime(long time) {
		SkillUtils.setLastUseTime(KCUtils.getPlayerCl(), this, time);
	}
	
	public boolean isCD() {
		return SkillUtils.isCD(KCUtils.getPlayerCl(), this);
	}
	
	/*------------------- CD结束 -------------------*/
	/*------------------- 熟练度开始 -------------------*/
	
	public static final int MAX_EXPERIENCE = 1024;
	
	/** 取熟练度（给你们分享一点人生经验 */
	public int getExperience() {
		return SkillUtils.getExperience(KCUtils.getPlayerCl(), this);
	}
	
	/** 设置熟练度，如果在服务端会发同步包 */
	public void setExperience(int experience) {
		SkillUtils.setExperience(KCUtils.getPlayerCl(), this, experience);
	}
	
	/** 调整熟练度，如果在服务端会发同步包 */
	public void modifyExperience(int experience) {
		SkillUtils.modifyExperience(KCUtils.getPlayerCl(), this, experience);
	}	
	
	/*------------------- 熟练度结束 -------------------*/
	
}
