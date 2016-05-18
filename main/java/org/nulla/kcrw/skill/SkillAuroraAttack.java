package org.nulla.kcrw.skill;

import org.nulla.kcrw.KeyCraft_Rewrite;
import org.nulla.nullacore.api.skill.SkillPassive;

import net.minecraft.util.ResourceLocation;

public class SkillAuroraAttack extends SkillPassive {

	public SkillAuroraAttack(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_attack.png");
	}

}
