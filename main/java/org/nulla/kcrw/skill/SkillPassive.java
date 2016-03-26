package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class SkillPassive extends Skill {

	public SkillPassive(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
	}

	@Override
	protected boolean onUse(EntityPlayer player) {
		return false;
	}
	
	@Override
	public boolean isPassive() {
		return true;
	}
	
	public boolean trigger(EntityPlayer player) {
		// 检查拥有技能
		if (!hasSkill(player))
			return false;
		// 检查CD
		if (!checkCD(player))
			return false;
		// 检查被动
		if (!isPassive()) {
			return false;
		}
		// 检查欧若拉点
		if (SkillUtils.getAuroraPoint(player) <= mAuroraCost) // 不让欧若拉变为0
			return false;
				
		// 客户端发使用技能包
		if (player.worldObj.isRemote)
			SkillNetwork.Channel.sendToServer(SkillNetwork.createUseSkillPacket(mID));
		boolean res = onTrigger(player);
				
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
	
	/** 重载实现使用技能 */
	public boolean onTrigger(EntityPlayer player) {
		return false;
	}

}
