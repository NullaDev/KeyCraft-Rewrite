package org.nulla.kcrw.skill;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Skill {
	
	public final int id;
	public final int AuroraRequired;
	
	public Skill(int id, int AuroraRequired) {
		this.id = id;
		this.AuroraRequired = AuroraRequired;
		Skills.add(this);
	}
	
	// 重载实现使用技能
	protected void onUse() {
		
	}
	
	
	public static ArrayList<Skill> Skills = new ArrayList<Skill>();
	
	// 测试
	/*public static final Skill AuroraCognition		= new Skill(000, 0);
	public static final Skill HuntingRhythm			= new Skill(100, 5);
	public static final Skill TrapProficient		= new Skill(111, 10);*/
	
	
	/** 取欧若拉点 */
	public static int getAuroraPoint(EntityPlayer player) {
		return player.getEntityData().getInteger("uroraPoint");
	}
	
	/** 设置欧若拉点，如果在服务端会发同步包 */
	public static void setAuroraPoint(EntityPlayer player, int point) {
		player.getEntityData().setInteger("AuroraPoint", point < 0 ? 0 : point);
		if (player instanceof EntityPlayerMP) {
			// 发同步包
			//SkillNetwork.channel.sendTo(SkillNetwork.createSyncAuroraPointPacket(player), (EntityPlayerMP)player);
		}
	}
	
	/** 改变欧若拉点，如果在服务端会发同步包 */
	public static void modifyAuroraPoint(EntityPlayer player, int point) {
		setAuroraPoint(player, getAuroraPoint(player) + point);
	}

	/** 判断有没有技能 */
	public static boolean hasSkill(EntityPlayer player, Skill skill) {
		final String name = String.format("Skill%d", skill.id);
		return player.getEntityData().getBoolean(name);
	}
	
	/** 学习技能，如果在服务端会发同步包 */
	public static void learnSkill(EntityPlayer player, Skill skill) {
		learnSkill(player, skill.id);
	}
	
	/** 学习技能，如果在服务端会发同步包 */
	public static void learnSkill(EntityPlayer player, int skill) {
		int point = getAuroraRequired(skill);
		if (getAuroraPoint(player) >= point) {
			modifyAuroraPoint(player, -point);
			
			final String name = String.format("Skill%d", skill);
			player.getEntityData().setBoolean(name, true);
			
			if (player instanceof EntityPlayerMP) {
				// 发同步包
				//SkillNetwork.channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
			}
		}
	}
	
	/** 取学习技能需要的欧若拉点 */
	public static int getAuroraRequired(int skill) {
		for (Skill i : Skills) {
			if (i.id == skill) {
				return i.AuroraRequired;
			}
		}
		return 0x7FFFFFFF;
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static void useSkill(EntityPlayer player, Skill skill) {
		if (player.worldObj.isRemote) {
			// 发同步包
			//SkillNetwork.channel.sendToServer(SkillNetwork.createUseSkillPacket(skill));
		}
		
		skill.onUse();
	}
	
}
