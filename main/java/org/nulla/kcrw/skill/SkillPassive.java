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
		// ���ӵ�м���
		if (!hasSkill(player))
			return false;
		// ���CD
		if (!checkCD(player))
			return false;
		// ��鱻��
		if (!isPassive()) {
			return false;
		}
		// ���ŷ������
		if (SkillUtils.getAuroraPoint(player) <= mAuroraCost) // ����ŷ������Ϊ0
			return false;
				
		// �ͻ��˷�ʹ�ü��ܰ�
		if (player.worldObj.isRemote)
			SkillNetwork.Channel.sendToServer(SkillNetwork.createUseSkillPacket(mID));
		boolean res = onTrigger(player);
				
		if (res) {
			// �����ϴ�ʹ��ʱ��
			setLastUseTime(player, player.worldObj.getTotalWorldTime());
			// ��ŷ������
			SkillUtils.modifyAuroraPoint(player, -mAuroraCost);
		}
				
		// ����˷�ͬ�����ܰ�
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
		
		return res;
		
	}
	
	/** ����ʵ��ʹ�ü��� */
	public boolean onTrigger(EntityPlayer player) {
		return false;
	}

}
