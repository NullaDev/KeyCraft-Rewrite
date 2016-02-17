package org.nulla.kcrw.skill;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Skill {
	
	public static final int AURORA_POINT_MAXIMUM = 65535;
	public static final int INITIAL_AURORA_POINT = 16384;
	
	
	public static ArrayList<Skill> Skills = new ArrayList<Skill>();
	
	
	/** 在Skills中的索引 */
	public final int id;
	/** 用于NBT */
	public final String Name;
	public final int AuroraRequired;
	
	public Skill(String Name, int AuroraRequired) {
		this.id = Skills.size();
		this.Name = Name;
		this.AuroraRequired = AuroraRequired;
		Skills.add(this);
	}
	
	/** 重载实现使用技能 */
	protected boolean onUse(EntityPlayer player) {
		return false;
	}
	
	
	/** 取欧若拉点 */
	public static int getAuroraPoint(EntityPlayer player) {
		return player.getEntityData().getInteger("AuroraPoint");
	}
	
	/** 设置欧若拉点，如果在服务端会发同步包 */
	public static void setAuroraPoint(EntityPlayer player, int point) {
		player.getEntityData().setInteger("AuroraPoint", point < 0 ? 0 : point);
		if (player instanceof EntityPlayerMP) {
			SkillNetwork.channel.sendTo(SkillNetwork.createSyncAuroraPointPacket(player), (EntityPlayerMP)player);
		}
	}
	
	/** 改变欧若拉点，如果在服务端会发同步包 */
	public static void modifyAuroraPoint(EntityPlayer player, int point) {
		int point_new = Math.min(AURORA_POINT_MAXIMUM, getAuroraPoint(player) + point);
		setAuroraPoint(player, point_new);
	}
	
	/** 初始化欧若拉点数，用于玩家刚出生时 */
	public static void initializeAuroraPoint(EntityPlayer player) {
		if (!hasInitialized(player)) {
			setAuroraPoint(player, INITIAL_AURORA_POINT);
		}
	}
	
	/** 判断是否已经初始化欧若拉点数，用于玩家刚出生时 */
	public static boolean hasInitialized(EntityPlayer player) {
		return player.getEntityData().hasKey("AuroraPoint");
	}

	/** 判断有没有技能 */
	public static boolean hasSkill(EntityPlayer player, Skill skill) {
		return player.getEntityData().getBoolean("Skill" + skill.Name);
	}

	/** 设置有没有技能 */
	public static void setSkill(EntityPlayer player, Skill skill, boolean hasSkill) {
		player.getEntityData().setBoolean("Skill" + skill.Name, hasSkill);
	}
	
	/** 学习技能，会发同步包 */
	public static void learnSkill(EntityPlayer player, Skill skill) {
		int point = skill.AuroraRequired;
		if (getAuroraPoint(player) > point) {
			modifyAuroraPoint(player, -point);

			// 客户端发学习技能包
			if (player.worldObj.isRemote) {
				SkillNetwork.channel.sendToServer(SkillNetwork.createLearnSkillPacket(skill.id));
			}
			setSkill(player, skill, true);
		}
		// 服务端发同步技能包
		if (player instanceof EntityPlayerMP) {
			SkillNetwork.channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
		}
	}
	
	/** 学习技能，会发同步包 */
	public static void learnSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.size()) {
			learnSkill(player, Skills.get(skill));
		}
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static boolean useSkill(EntityPlayer player, Skill skill) {
		if (!hasSkill(player, skill)) {
			return false;
		}
		
		if (player.worldObj.isRemote) {
			SkillNetwork.channel.sendToServer(SkillNetwork.createUseSkillPacket(skill.id));
		}
		return skill.onUse(player);
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static boolean useSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.size()) {
			return useSkill(player, Skills.get(skill));
		}
		return false;
	}
	
}
