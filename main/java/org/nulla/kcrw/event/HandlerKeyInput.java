package org.nulla.kcrw.event;

import org.nulla.kcrw.KCClientProxy;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.skill.Skill;
import org.nulla.kcrw.skill.SkillUtils;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.entity.player.EntityPlayer;

public class HandlerKeyInput {
	
	@SubscribeEvent
	public void keyListener(KeyInputEvent event) {
		EntityPlayer player = KCUtils.getPlayerCl();
		Skill skill = null;
		if (KCClientProxy.kbSkill1.isPressed()) {
			if (KCUtils.isShiftKeyDown()) {
				skill = SkillUtils.getSkillInSlot(player, 2);
				System.out.println("使用技能2");
			} else {
				skill = SkillUtils.getSkillInSlot(player, 0);
				System.out.println("使用技能0");
			}
		} else if (KCClientProxy.kbSkill2.isPressed()) {
		    if (KCUtils.isShiftKeyDown()) {
				skill = SkillUtils.getSkillInSlot(player, 3);
				System.out.println("使用技能3");
		    } else {
				skill = SkillUtils.getSkillInSlot(player, 1);
				System.out.println("使用技能1");
		    }
		}
		if (skill != null)
			skill.useSkill(player);
	}

}
