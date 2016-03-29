package org.nulla.kcrw.skill;

import net.minecraft.util.ResourceLocation;

import org.nulla.kcrw.KeyCraft_Rewrite;

public class SkillAuroraRepair extends SkillPassive {

	public SkillAuroraRepair(String name, int auroraRequired, int auroraCost, int cd) {
		super(name, auroraRequired, auroraCost, cd);
		this.mIcon = new ResourceLocation(KeyCraft_Rewrite.MODID, "textures/icons/skills/aurora_repair.png");
	}

}
