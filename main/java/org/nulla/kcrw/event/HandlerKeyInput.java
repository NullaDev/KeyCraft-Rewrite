package org.nulla.kcrw.event;

import org.nulla.kcrw.*;
import org.nulla.kcrw.gui.*;
import org.nulla.kcrw.skill.*;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class HandlerKeyInput {
	
	@SubscribeEvent
	public void keyListener(KeyInputEvent event) {
		EntityPlayer player = KCUtils.getPlayerCl();
		Skill skill = null;
		if (KCClientProxy.kbSkill1.isPressed()) {
			if (KCUtils.isShiftKeyDown()) {
				skill = SkillUtils.getSkillInSlot(player, 2);
				//System.out.println("使用技能2");
			} else {
				skill = SkillUtils.getSkillInSlot(player, 0);
				//System.out.println("使用技能0");
			}
		} else if (KCClientProxy.kbSkill2.isPressed()) {
		    if (KCUtils.isShiftKeyDown()) {
				skill = SkillUtils.getSkillInSlot(player, 3);
				//System.out.println("使用技能3");
		    } else {
				skill = SkillUtils.getSkillInSlot(player, 1);
				//System.out.println("使用技能1");
		    }
		}
		if (skill != null)
			skill.useSkill(player);
		
		if (KCClientProxy.kbSwitchSkill.isPressed()) {
			KCUtils.getMC().displayGuiScreen(new GuiSwitchSkill(KCUtils.getMC().currentScreen, player));
		}
	}

}
