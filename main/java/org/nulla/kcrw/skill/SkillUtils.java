package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

public class SkillUtils {
	
	public static final int MAX_AURORA_POINT = 65535;
	public static final int INITIAL_AURORA_POINT = 16384;
	public static final int SKILL_SLOT_SIZE = 4;
	
	/*------------------- ���ܿ�ʼ -------------------*/

	/** ȡŷ������ */
	public static int getAuroraPoint(EntityPlayer player) {
		return player.getEntityData().getInteger("AuroraPoint");
	}
	
	/** ����ŷ�����㣬����ڷ���˻ᷢͬ���� */
	public static void setAuroraPoint(EntityPlayer player, int point) {
		if (point < 0)
			point = 0;
		else if (point > MAX_AURORA_POINT)
			point = MAX_AURORA_POINT;
		player.getEntityData().setInteger("AuroraPoint", point);
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncAuroraPointPacket(player), (EntityPlayerMP)player);
	}
	
	/** �ı�ŷ�����㣬����ڷ���˻ᷢͬ���� */
	public static void modifyAuroraPoint(EntityPlayer player, int point) {
		setAuroraPoint(player, getAuroraPoint(player) + point);
	}
	
	/** ��ʼ��ŷ����������������ҵ�½������ʱ������ѳ�ʼ�������ظ���ʼ�� */
	public static void initializeAuroraPoint(EntityPlayer player) {
		if (!hasInitialized(player)) {
			setAuroraPoint(player, INITIAL_AURORA_POINT);
		}
	}
	
	/** �ж��Ƿ��Ѿ���ʼ��ŷ�������� */
	public static boolean hasInitialized(EntityPlayer player) {
		return player.getEntityData().hasKey("AuroraPoint");
	}
	
	/** �ж���û�м��� */
	public static boolean hasSkill(EntityPlayer player, Skill skill) {
		return player.getEntityData().getBoolean("Skill" + skill.mName);
	}
	
	/** ������û�м��� */
	public static void setSkill(EntityPlayer player, Skill skill, boolean hasSkill) {
		player.getEntityData().setBoolean("Skill" + skill.mName, hasSkill);
	}
	
	/** ѧϰ���ܣ��ᷢͬ������������ܲۻ��п�λ������ȥ */
	public static void learnSkill(EntityPlayer player, Skill skill) {
		if (hasSkill(player, skill))
			return;
		
		if (SkillUtils.getAuroraPoint(player) > skill.mAuroraRequired) { // ����ŷ������Ϊ0
			SkillUtils.modifyAuroraPoint(player, -skill.mAuroraRequired);

			// �ͻ��˷�ѧϰ���ܰ�
			if (player.worldObj.isRemote)
				SkillNetwork.Channel.sendToServer(SkillNetwork.createLearnSkillPacket(skill.mID));
			setSkill(player, skill, true);
		}
		// ����˷�ͬ�����ܰ�
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
		
		// �Ž����ܲ�
		for (int i = 0; i < SKILL_SLOT_SIZE; i++) {
			if (getSkillInSlot(player, i) == null) {
				setSkillInSlot(player, i, skill, true);
				break;
			}
		}
	}
	
	/** ѧϰ���ܣ��ᷢͬ������������ܲۻ��п�λ������ȥ */
	public static void learnSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.AllSkills.size()) {
			learnSkill(player, Skills.AllSkills.get(skill));
		}
	}
	
	/** ʹ�ü��ܣ��ᷢͬ���� */
	public static boolean useSkill(EntityPlayer player, Skill skill) {
		// ��鼼���ǲ���null
		if (skill == null)
			return false;
		// ���ӵ�м���
		if (!hasSkill(player, skill))
			return false;
		// ���CD
		long curTime = player.worldObj.getTotalWorldTime();
		if (curTime - getLastUseTime(player, skill) < skill.mCD)
			return false;
		// ���ŷ������
		if (SkillUtils.getAuroraPoint(player) < skill.mAuroraCost) // ����ŷ������Ϊ0��
			return false;
		
		// �ͻ��˷�ʹ�ü��ܰ�
		if (player.worldObj.isRemote)
			SkillNetwork.Channel.sendToServer(SkillNetwork.createUseSkillPacket(skill.mID));
		boolean res = skill.onUse(player);
		
		if (res) {
			// �����ϴ�ʹ��ʱ��
			setLastUseTime(player, skill, curTime);
			// ��ŷ������
			SkillUtils.modifyAuroraPoint(player, -skill.mAuroraCost);
		}
		
		// ����˷�ͬ�����ܰ�
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
		return res;
	}
	
	/** ʹ�ü��ܣ�����ڿͻ��˻ᷢͬ���� */
	public static boolean useSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.AllSkills.size())
			return useSkill(player, Skills.AllSkills.get(skill));
		return false;
	}
	
	/*------------------- ���ܽ��� -------------------*/
	/*------------------- ���ܲۿ�ʼ -------------------*/
	
	/** ȡ�����ܲ����Ӧ�ļ��� */
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
	
	/** �Ѽ��ܷŽ����ܲ��� */
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
	
	/** �Ѽ��ܷŽ����ܲ��� */
	public static void setSkillInSlot(EntityPlayer player, int pos, int skill, boolean shouldSync) {
		if (0 <= skill && skill < Skills.AllSkills.size())
			setSkillInSlot(player, pos, Skills.AllSkills.get(skill), shouldSync);
		else
			setSkillInSlot(player, pos, null, shouldSync);
	}
	
	/*------------------- ���ܲ۽��� -------------------*/
	/*------------------- CD��ʼ -------------------*/
	
	/** ȡ�ϴ�ʹ��ʱ�䣬��World.getTotalWorldTime()����ʱ�� */
	public static long getLastUseTime(EntityPlayer player, Skill skill) {
		final String name = "LastTime" + skill.mName;
		final NBTTagCompound nbt = player.getEntityData();
		if (!nbt.hasKey(name))
			return -skill.mCD;
		return nbt.getLong(name);
	}
	
	/** �����ϴ�ʹ��ʱ�䣬��World.getTotalWorldTime()����ʱ�� */
	public static void setLastUseTime(EntityPlayer player, Skill skill, long time) {
		player.getEntityData().setLong("LastTime" + skill.mName, time);
	}
	
	public static boolean isCD(EntityPlayer player, Skill skill) {
		long curTime = player.worldObj.getTotalWorldTime();
		if (curTime - getLastUseTime(player, skill) < skill.mCD)
			return false;
		return true;
	}
	
	/*------------------- CD���� -------------------*/
	/*------------------- �����ȿ�ʼ -------------------*/
	
	public static final int MAX_EXPERIENCE = 1024;
	
	/** ȡ�����ȣ������Ƿ���һ���������� */
	public static int getExperience(EntityPlayer player, Skill skill) {
		return player.getEntityData().getInteger("Experience" + skill.mName);
	}
	
	/** ���������ȣ�����ڷ���˻ᷢͬ���� */
	public static void setExperience(EntityPlayer player, Skill skill, int experience) {
		player.getEntityData().setInteger("Experience" + skill.mName, experience);
		// ����˷�ͬ�����ܰ�
		if (player instanceof EntityPlayerMP)
			SkillNetwork.Channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
	}
	
	/** ���������ȣ�����ڷ���˻ᷢͬ���� */
	public static void modifyExperience(EntityPlayer player, Skill skill, int experience) {
		int exp = Math.min(getExperience(player, skill) + experience, MAX_EXPERIENCE);
		setExperience(player, skill, exp);
	}
	
	/*------------------- �����Ƚ��� -------------------*/

}
