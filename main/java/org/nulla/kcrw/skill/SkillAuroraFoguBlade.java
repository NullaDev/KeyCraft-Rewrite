package org.nulla.kcrw.skill;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.nulla.kcrw.KCItems;
import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.nullacore.api.damage.NullaDamageSource;
import org.nulla.nullacore.api.skill.Skill;

/**
 * 出自《Rewrite Harvest festa!》中灰太狼在战胜御堂后获得的技能“オーロラ＋フォゴ剣”。
 * 在原作中伤害很高，所以在这里给了物伤、魔伤和火伤的混合伤害。可惜对下界生物（毕竟抗火）效果很差啊。
 * 然而毕竟是Aurora剑发动的技能，所以会崩掉手里的剑。。【大概
 */
public class SkillAuroraFoguBlade extends Skill {
	
	public SkillAuroraFoguBlade(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_fogu_blade.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.AuroraBlade.getExperience(player) >= 256;
	}
	
	@Override
	public boolean onUse(EntityPlayer player) {
		ItemStack held = player.getHeldItem();
		
		if (held == null) {
			return false;
    	}
		
		if (held.getItem() != KCItems.aurora_blade) {
			return false;
    	}
		
		Entity targetCl = Minecraft.getMinecraft().pointedEntity;
		Entity targetSr = null;
		
		if (targetCl != null) {
			targetSr = player.worldObj.getEntityByID(targetCl.getEntityId());
		}
		
		if (targetSr instanceof EntityLivingBase) {
			EntityLivingBase entity = (EntityLivingBase) targetSr;
			float damage = 2F * 4096 / (2048 - this.getExperience(player));
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), 8F);
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player).setFireDamage(), damage);
			entity.attackEntityFrom(NullaDamageSource.CauseAuroraDamage(player), damage);
		} else {
			return false;
		}
		
		//移除手中的剑
		player.setCurrentItemOrArmor(0, null);
		
		//蹦剑
		SkillsRw.AuroraBlade.onBladeDead(player, 1);
		
		// 随机事件只在服务器发生
		if (!player.worldObj.isRemote) {
			Random rand = new Random();
			int exp = rand.nextInt(10) + 1;
			modifyExperience(player, exp);
		}
		
		return true;
	}
	
}