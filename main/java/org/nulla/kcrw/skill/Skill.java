package org.nulla.kcrw.skill;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class Skill {
	
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
	protected void onUse(EntityPlayer player) {
		
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
		setAuroraPoint(player, getAuroraPoint(player) + point);
	}

	/** 判断有没有技能 */
	public static boolean hasSkill(EntityPlayer player, Skill skill) {
		return player.getEntityData().getBoolean("Skill" + skill.Name);
	}

	/** 设置有没有技能 */
	public static void setSkill(EntityPlayer player, Skill skill, boolean hasSkill) {
		player.getEntityData().setBoolean("Skill" + skill.Name, hasSkill);
	}
	
	/** 学习技能，如果在服务端会发同步包 */
	public static void learnSkill(EntityPlayer player, Skill skill) {
		int point = skill.AuroraRequired;
		if (getAuroraPoint(player) >= point) {
			modifyAuroraPoint(player, -point);
			
			setSkill(player, skill, true);
			if (player instanceof EntityPlayerMP) {
				SkillNetwork.channel.sendTo(SkillNetwork.createSyncSkillPacket(player), (EntityPlayerMP)player);
			}
		}
	}
	
	/** 学习技能，如果在服务端会发同步包 */
	public static void learnSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.size()) {
			learnSkill(player, Skills.get(skill));
		}
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static void useSkill(EntityPlayer player, Skill skill) {
		if (player.worldObj.isRemote) {
			SkillNetwork.channel.sendToServer(SkillNetwork.createUseSkillPacket(skill.id));
		}
		
		skill.onUse(player);
	}
	
	/** 使用技能，如果在客户端会发同步包 */
	public static void useSkill(EntityPlayer player, int skill) {
		if (0 <= skill && skill < Skills.size()) {
			useSkill(player, Skills.get(skill));
		}
	}
	
}
