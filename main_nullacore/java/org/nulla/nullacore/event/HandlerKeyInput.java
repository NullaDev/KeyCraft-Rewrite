package org.nulla.nullacore.event;

import org.nulla.nullacore.ClientProxy;
import org.nulla.nullacore.NullaUtils;
import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.SkillUtils;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class HandlerKeyInput {
	
	@SubscribeEvent
	public void keyListener(KeyInputEvent event) {
		EntityPlayer player = NullaUtils.getPlayerCl();
		Skill skill = null;
		if (ClientProxy.kbSkill1.isPressed()) {
			if (NullaUtils.isShiftKeyDown()) {
				skill = SkillUtils.getSkillInSlot(player, 2);
				//System.out.println("使用技能2");
			} else {
				skill = SkillUtils.getSkillInSlot(player, 0);
				//System.out.println("使用技能0");
			}
		} else if (ClientProxy.kbSkill2.isPressed()) {
		    if (NullaUtils.isShiftKeyDown()) {
				skill = SkillUtils.getSkillInSlot(player, 3);
				//System.out.println("使用技能3");
		    } else {
				skill = SkillUtils.getSkillInSlot(player, 1);
				//System.out.println("使用技能1");
		    }
		}
		if (skill != null)
			skill.useSkill(player);

	}

}
