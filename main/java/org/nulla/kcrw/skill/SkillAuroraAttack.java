package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.kcrw.damage.KCDamageSource;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class SkillAuroraAttack extends SkillPassive {

	public SkillAuroraAttack(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_attack.png");
	}

}
