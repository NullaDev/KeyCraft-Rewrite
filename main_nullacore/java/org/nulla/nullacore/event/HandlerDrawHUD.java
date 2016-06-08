package org.nulla.nullacore.event;

import org.lwjgl.opengl.GL11;
import org.nulla.nullacore.ClientProxy;
import org.nulla.nullacore.NullaUtils;
import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.SkillPassive;
import org.nulla.nullacore.api.skill.SkillUtils;
import org.nulla.nullacore.client.Resources;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class HandlerDrawHUD {
	
	@SubscribeEvent
	public void drawAuroraPoint(RenderGameOverlayEvent.Pre event) {
		
		int width = event.resolution.getScaledWidth();
		int height = event.resolution.getScaledHeight();
		
		drawAuroraStrip(width, height);
		drawSkillSlot(width, height);
	}
	
	/** 
	 * 绘制HUD上的欧若拉条。<BR/>
	 * width，height代表屏幕的宽高。
	 */
	public static void drawAuroraStrip(int width, int height) {
		
		NullaUtils.initDrawerState();

		width *= 0.95;
		height *= 0.05;
		
		int currentAuroraPoint = SkillUtils.getAuroraPoint(NullaUtils.getPlayerCl());
		int maximumAuroraPoint = SkillUtils.MAX_AURORA_POINT;
	        
		int length = Math.min(105 *  currentAuroraPoint / maximumAuroraPoint, 105);
		
		GL11.glEnable(GL11.GL_BLEND);
		NullaUtils.getMC().getTextureManager().bindTexture(Resources.aurora_strip_inside);
		NullaUtils.drawScaledCustomSizeModalRect(width - length - 15, height + 1, 630 - 6 * length, 0, 6 * length + 90, 120, length + 15, 18, 720, 120);
		
		NullaUtils.getMC().getTextureManager().bindTexture(Resources.aurora_strip_outside);
		NullaUtils.drawScaledCustomSizeModalRect(width - 120, height, 0, 0, 720, 120, 120, 20, 720, 120);
		GL11.glDisable(GL11.GL_BLEND);
	        
		String info = "Aurora: " + SkillUtils.getAuroraPoint(NullaUtils.getPlayerCl()) + " / " + SkillUtils.MAX_AURORA_POINT;
		FontRenderer fontRenderer = NullaUtils.getMC().fontRenderer;
		int color = 0x7FFFBF;
		//if (currentAuroraPoint <= MaximumAuroraPoint * 0.25) {color = 0xFF0000;}
		//else if (currentAuroraPoint <= MaximumAuroraPoint * 0.5) {color = 0xFFFF00;}
		//else if (currentAuroraPoint > MaximumAuroraPoint) {color = 0x00FF00;}
		fontRenderer.drawStringWithShadow(info, width - 105, height - 3, color);
		NullaUtils.initDrawerState();
		NullaUtils.getMC().renderEngine.bindTexture(Gui.icons);
	}
	
	/** 
	 * 绘制HUD上的技能槽。<BR/>
	 * width，height代表屏幕的宽高。
	 */
	public static void drawSkillSlot(int width, int height) {
		
		NullaUtils.initDrawerState();
		
		int widthToDraw = (int) (0.9 * width);
		int heightToDraw[] = new int[4];
		heightToDraw[0] = (int) (height * 0.2);
		heightToDraw[1] = (int) (height * 0.35);
		heightToDraw[2] = (int) (height * 0.5);
		heightToDraw[3] = (int) (height * 0.65);
		
		String[] SkillButton = new String[4];
		SkillButton[0] = GameSettings.getKeyDisplayString(ClientProxy.kbSkill1.getKeyCode());
		SkillButton[1] = GameSettings.getKeyDisplayString(ClientProxy.kbSkill2.getKeyCode());
		SkillButton[2] = "Shift+" + GameSettings.getKeyDisplayString(ClientProxy.kbSkill1.getKeyCode());
		SkillButton[3] = "Shift+" + GameSettings.getKeyDisplayString(ClientProxy.kbSkill2.getKeyCode());
					
		GL11.glEnable(GL11.GL_BLEND);
		Skill skillinslot[] = new Skill[SkillUtils.SKILL_SLOT_SIZE];
		
		EntityPlayer player = NullaUtils.getPlayerCl();
		for (int i = 0; i < skillinslot.length; i++) {
			skillinslot[i] = SkillUtils.getSkillInSlot(player, i);
			if (skillinslot[i] != null) {
				//绘制技能图标
				NullaUtils.getMC().getTextureManager().bindTexture(skillinslot[i].mIcon);
				NullaUtils.drawScaledCustomSizeModalRect(widthToDraw, heightToDraw[i], 0, 0, 64, 64, 32, 32, 64, 64);
				//绘制熟练度条
				int exp = 32 * skillinslot[i].getExperience(player) / skillinslot[i].MAX_EXPERIENCE;
				NullaUtils.drawRect(widthToDraw + 32, heightToDraw[i] + 32 - exp, 4, exp, 0xFF7FFF7F);
				//绘制Aurora消耗
				NullaUtils.getfontRenderer().drawStringWithShadow(skillinslot[i].mAuroraCost + "", widthToDraw + 2, heightToDraw[i] + 24, 0x000000);
				NullaUtils.initDrawerState();
				//绘制CD状态
				if (!skillinslot[i].checkCD(player)) {
					int time = (int) (player.worldObj.getTotalWorldTime() - skillinslot[i].getLastUseTime(player));
					int length =  32 - 32 * time / skillinslot[i].mCD;
					NullaUtils.drawRect(widthToDraw, heightToDraw[i], 32, length, 0x80000000);
					NullaUtils.initDrawerState();
				}
			}
			
			if (skillinslot[i] != null && skillinslot[i] instanceof SkillPassive) {
				SkillPassive toDraw = (SkillPassive) skillinslot[i];
				if (toDraw.getIsOn(player))
					NullaUtils.getMC().getTextureManager().bindTexture(Resources.skill_passive_on);
				else
					NullaUtils.getMC().getTextureManager().bindTexture(Resources.skill_passive_off);
				NullaUtils.drawScaledCustomSizeModalRect(widthToDraw, heightToDraw[i], 0, 0, 64, 64, 32, 32, 64, 64);
			} else {
				NullaUtils.getMC().getTextureManager().bindTexture(Resources.skill_empty_slot);
				NullaUtils.drawScaledCustomSizeModalRect(widthToDraw, heightToDraw[i], 0, 0, 64, 64, 32, 32, 64, 64);
				NullaUtils.getfontRenderer().drawStringWithShadow(SkillButton[i], widthToDraw + 2, heightToDraw[i], 0xFF0000);
				NullaUtils.initDrawerState();
			}
			
			NullaUtils.getMC().getTextureManager().bindTexture(Resources.skill_empty_exp);
			NullaUtils.drawScaledCustomSizeModalRect(widthToDraw + 32, heightToDraw[i], 0, 0, 8, 64, 4, 32, 8, 64);
						
		}	

		NullaUtils.initDrawerState();
		GL11.glDisable(GL11.GL_BLEND);
		NullaUtils.getMC().renderEngine.bindTexture(Gui.icons);
	}

}
