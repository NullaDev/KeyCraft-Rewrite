package org.nulla.nullacore.api.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class SkillPassive extends Skill {

	public SkillPassive(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}
	
	public boolean getIsOn(EntityPlayer player) {
		return player.getEntityData().getBoolean("SkillIsOn" + mName);
	}
	
	/** 无同步 */
	protected void setIsOn(EntityPlayer player, boolean isOn) {
		player.getEntityData().setBoolean("SkillIsOn" + mName, isOn);
	}
	
	/** 切换开关 */
	@Override
	protected boolean onUse(EntityPlayer player) {
		boolean isOn = !getIsOn(player);
		setIsOn(player, isOn);
		return isOn;
	}
	
	/** 使用技能（切换开关），会发同步包 */
	@Override
	public boolean useSkill(EntityPlayer player) {
		// 检查拥有技能
		if (!hasSkill(player))
			return false;
		
		// 客户端发使用技能包
		if (player.worldObj.isRemote)
			SkillNetwork.Channel.sendToServer(SkillNetwork.createUseSkillPacket(mID));
		boolean res = onUse(player);
		
		// 服务端发同步技能包
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
		return res;
	}
	
	/** 触发被动技能效果 */
	public boolean trigSkill(EntityPlayer player) {
		// 检查拥有技能
		if (!hasSkill(player))
			return false;
		// 检查技能槽
		if (!isInSlot(player))
			return false;
		// 检查开启
		if (!getIsOn(player))
			return false;
		// 检查CD
		if (!checkCD(player))
			return false;
		// 检查欧若拉点
		if (SkillUtils.getAuroraPoint(player) <= mAuroraCost) // 不让欧若拉变为0
			return false;
		
		boolean res = onTrig(player);
		
		if (res) {
			// 设置上次使用时间
			setLastUseTime(player, player.worldObj.getTotalWorldTime());
			// 减欧若拉点
			SkillUtils.modifyAuroraPoint(player, -mAuroraCost);
		}
		
		return res;
	}
	
	/** 触发效果时 */
	protected boolean onTrig(EntityPlayer player) {
		return true;
	}
	
	protected boolean isInSlot(EntityPlayer player) {
		boolean flag = false;
		for (int i = 0; i < SkillUtils.SKILL_SLOT_SIZE; i++) {
			if (SkillUtils.getSkillInSlot(player, i) == this) {
				flag = true;
			}
		}
		return flag;
	}

}
