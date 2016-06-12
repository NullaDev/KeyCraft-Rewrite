package org.nulla.kcrw.skill;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.nullacore.api.damage.NullaDamageSource;
import org.nulla.nullacore.api.skill.Skill;

public class SkillHealthFog extends Skill {

	public SkillHealthFog(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/health_fog.png");
	}

	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return true;
	}
	
	@Override
	public boolean onUse(final EntityPlayer player) {
		if (player.worldObj.isRemote) {
			return false;
		}
		
		List list = player.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(
				player.posX - 4, player.posY - 1, player.posZ - 4, player.posX + 4, player.posY + 1, player.posZ + 4));
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			EntityLivingBase entity = (EntityLivingBase)iterator.next();
            entity.addPotionEffect(new PotionEffect(Potion.heal.id, 1, 1));
            System.out.println(entity);
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
