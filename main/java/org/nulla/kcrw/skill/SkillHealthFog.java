package org.nulla.kcrw.skill;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.effect.EntityParticleFX;
import org.nulla.nullacore.api.damage.NullaDamageSource;
import org.nulla.nullacore.api.skill.Skill;

/**
 * 技能出自《Rewrite》中津静流的能力。
 * 其实按照《Rewrite Harvest festa！》的设定来看，静流的治疗能力是对单人的，but who cares。
 * 不过既然静流的能力是制药工厂，那么就当是把储存的药一口气全都放出来吧233333
 */
public class SkillHealthFog extends Skill {

	public SkillHealthFog(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/health_fog.png");
	}

	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return false;
	}
	
	@Override
	public boolean onUse(final EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		Entity targetCl = Minecraft.getMinecraft().pointedEntity;
		Entity targetSr = player.worldObj.getEntityByID(targetCl.getEntityId());
		EntityLivingBase flag = player;
		
		if (targetSr != null && targetSr instanceof EntityLivingBase) {
			System.out.println(targetSr.worldObj.isRemote);
			EntityLivingBase entity = (EntityLivingBase) targetSr;
			entity.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 1));
			flag = entity;
		} else {
			if (player.getHealth() < player.getMaxHealth()) {
				player.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 1));
			} else {
				return false;
			}
		}
		
		// 随机事件只在服务器发生
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(5) + 1;
			modifyExperience(player, exp);
		}
		
		for(int i = 0; i < 32; i++) {
			Random ran = new Random();
			Vec3 vec = Vec3.createVectorHelper(2 * ran.nextFloat() - 1, 1, 2 * ran.nextFloat() - 1).normalize();
			EntityParticleFX par = new EntityParticleFX(flag.worldObj, flag.posX, flag.posY, flag.posZ, vec);
			par.setRBGColorF(1F, 0.7F, 0.7F);
			Minecraft.getMinecraft().effectRenderer.addEffect(par);
		}
		
		return true;
	}

}
