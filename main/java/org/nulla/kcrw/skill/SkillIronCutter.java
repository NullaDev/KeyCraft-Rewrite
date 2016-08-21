package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.entity.effect.EntityParticleFX;
import org.nulla.nullacore.api.damage.NullaDamageSource;
import org.nulla.nullacore.api.skill.Skill;

public class SkillIronCutter extends Skill {

	public SkillIronCutter(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/iron_cutter.png");
	}

	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.VibrationWave.getExperience(player) >= 256;
	}
	
	@Override
	public boolean onUse(final EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		Entity targetCl = Minecraft.getMinecraft().pointedEntity;
		Entity targetSr = null;
		
		if (targetCl != null) {
			targetSr = player.worldObj.getEntityByID(targetCl.getEntityId());
		}
		
		float base = (float) player.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
		float extra = new Random().nextFloat() * 1024 / (2048 - this.getExperience(player));
		
		if (targetSr instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) targetSr;
			if (entity instanceof EntitySlime) {
				base = Math.min(base / 5, entity.getHealth() - 0.1F);
				extra = 0F;
			}
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), base);
			entity.attackEntityFrom(NullaDamageSource.CauseAuroraDamage(player), base * extra);
			Minecraft.getMinecraft().thePlayer.setVelocity(entity.posX - player.posX, entity.posY - player.posY, entity.posZ - player.posZ);

		} else {
			return false;
		}
				
		if (player.getHeldItem() != null) {
			player.getHeldItem().damageItem(16, player);
		}
		
		// 随机事件只在服务器发生
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(5) + 1;
			modifyExperience(player, exp);
		}	
		
		return true;
	}

}