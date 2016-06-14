package org.nulla.kcrw.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.nullacore.api.skill.SkillPassive;

public class SkillPoisonImmunity extends SkillPassive {

	public SkillPoisonImmunity(String name, int auroraRequired, int auroraCost,
			int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/poison_immunity.png");
	}
	
	@Override
	public boolean canLearnSkill(EntityPlayer player) {
		return SkillsRw.PoisonProtection.getExperience(player) >= 1024;
	}

}
