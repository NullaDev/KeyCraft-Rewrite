package org.nulla.kcrw.event;

import net.minecraft.entity.player.EntityPlayer;

import org.lwjgl.input.Keyboard;
import org.nulla.kcrw.KCClientProxy;
import org.nulla.kcrw.KCUtils;
import org.nulla.kcrw.skill.Skill;
import org.nulla.kcrw.skill.Skills;

import scala.swing.event.Key;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class HandlerKeyInput {
	
	@SubscribeEvent
	public void keyListener(KeyInputEvent event) {
		EntityPlayer player = KCUtils.getPlayerCl();
		if (KCClientProxy.kbSkill1.isPressed()) {
			if (KCUtils.isShiftKeyDown()) {
		    	Skill.useSkill(player, Skill.getSkillInSlot(player, 2));
				System.out.println("使用技能2");
			} else {
		    	Skill.useSkill(player, Skill.getSkillInSlot(player, 0));
				System.out.println("使用技能0");
			}
		} else if (KCClientProxy.kbSkill2.isPressed()) {
		    if (KCUtils.isShiftKeyDown()) {
		    	Skill.useSkill(player, Skill.getSkillInSlot(player, 3));
				System.out.println("使用技能3");
		    } else {
		    	Skill.useSkill(player, Skill.getSkillInSlot(player, 1));
				System.out.println("使用技能1");
		    }
		}
	}

}
