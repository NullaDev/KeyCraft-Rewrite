package org.nulla.kcrw;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.nulla.kcrw.skill.Skill;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class KCUtils {
	
	public static Minecraft getMC() {
		return Minecraft.getMinecraft();
	}
	
	/** 
	 * 只能在Client端用，以获取Client端的玩家信息（例如位置），不会改变Server端信息。
	 */
	public static EntityPlayer getPlayerCl() {
		return getMC().thePlayer;
	}
	
	/** 
	 * 判断Shift键是否按下。
	 */
	public static boolean isShiftKeyDown() {
        return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
    }
	
	/** 
	 * 判断Ctrl键是否按下。
	 */
	public static boolean isCtrlKeyDown() {
        return Minecraft.isRunningOnMac ? Keyboard.isKeyDown(219) || Keyboard.isKeyDown(220) : Keyboard.isKeyDown(29) || Keyboard.isKeyDown(157);
    }
	
	/** 
	 * 获取player身上某种item的总数量。
	 */
	public static int getNumberOfItemInPlayer(EntityPlayer player, Item item) {
		int number = 0;
		for (int i = 0; i < 36; i++) {
			if (player.inventory.mainInventory[i] != null) {
				if (player.inventory.mainInventory[i].getItem().equals(item))
					number += player.inventory.mainInventory[i].stackSize;
			}
		}
		return number;
	}
	
	/** 
	 * 从player身上扣掉一定数量的item，用于合成。
	 */
	public static void setNumberOfItemInPlayer(EntityPlayer player, Item item, int number) {
		for (int i = 0; i < 36; i++) {
			if (player.inventory.mainInventory[i] != null) {
				if (player.inventory.mainInventory[i].getItem().equals(item)) {
					if (player.inventory.mainInventory[i].stackSize >= number)
						player.inventory.mainInventory[i].stackSize -= number;
					else {
						player.inventory.mainInventory[i].stackSize = 0;
						setNumberOfItemInPlayer(player, item, number - player.inventory.mainInventory[i].stackSize);
					}
				}
			}
		}
	}
	
	/** 
	 * 绘制可缩放的纹理。<BR/>
	 * x，y代表绘制在屏幕上的位置。<BR/>
	 * u，v代表要绘制的部分在源素材图上的位置。<BR/>
	 * uW，vH代表要绘制的部分在源素材图上的大小。<BR/>
	 * w，h代表要绘制的部分的大小。<BR/>
	 * tW，tH代表源素材图的大小（缩放用）。
	 */
	public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
	    float f4 = 1.0F / tileWidth;
	    float f5 = 1.0F / tileHeight;
	    Tessellator tessellator = Tessellator.instance;
	    tessellator.startDrawingQuads();
	    tessellator.addVertexWithUV((double)x, (double)(y + height), 0.0D, (double)(u * f4), (double)((v + (float)vHeight) * f5));
	    tessellator.addVertexWithUV((double)(x + width), (double)(y + height), 0.0D, (double)((u + (float)uWidth) * f4), (double)((v + (float)vHeight) * f5));
	    tessellator.addVertexWithUV((double)(x + width), (double)y, 0.0D, (double)((u + (float)uWidth) * f4), (double)(v * f5));
	    tessellator.addVertexWithUV((double)x, (double)y, 0.0D, (double)(u * f4), (double)(v * f5));
	    tessellator.draw();
	}
	
	/** 
	 * 绘制HUD上的欧若拉条。<BR/>
	 * width，height代表屏幕的宽高。
	 */
	public static void drawAuroraStrip(int width, int height) {
		
		width *= 0.95;
		height *= 0.05;
		
		int currentAuroraPoint = Skill.getAuroraPoint(KCUtils.getPlayerCl());
		int maximumAuroraPoint = Skill.MAX_AURORA_POINT;
	        
		int length = Math.min(105 *  currentAuroraPoint / maximumAuroraPoint, 105);
		
		GL11.glEnable(GL11.GL_BLEND);
		KCUtils.getMC().getTextureManager().bindTexture(KCResources.aurora_strip_inside);
		KCUtils.drawScaledCustomSizeModalRect(width - length - 15, height + 1, 630 - 6 * length, 0, 6 * length + 90, 120, length + 15, 18, 720, 120);
		
		KCUtils.getMC().getTextureManager().bindTexture(KCResources.aurora_strip_outside);
		KCUtils.drawScaledCustomSizeModalRect(width - 120, height, 0, 0, 720, 120, 120, 20, 720, 120);
		GL11.glDisable(GL11.GL_BLEND);
	        
		String info = "Aurora: " + Skill.getAuroraPoint(KCUtils.getPlayerCl()) + " / " + Skill.MAX_AURORA_POINT;
		FontRenderer fontRenderer = KCUtils.getMC().fontRenderer;
		int color = 0x7FFFBF;
		//if (currentAuroraPoint <= MaximumAuroraPoint * 0.25) {color = 0xFF0000;}
		//else if (currentAuroraPoint <= MaximumAuroraPoint * 0.5) {color = 0xFFFF00;}
		//else if (currentAuroraPoint > MaximumAuroraPoint) {color = 0x00FF00;}
		fontRenderer.drawStringWithShadow(info, (int)width - 105, (int)height - 3, color);
		initDrawerState();
		getMC().renderEngine.bindTexture(Gui.icons);
	}
	
	/** 
	 * 初始化（绘制器的）撞钛鸡。
	 */
	public static void initDrawerState() {
		FontRenderer fontRenderer = getMC().fontRenderer;
		fontRenderer.drawString("", 0, 0, 0xFFFFFF);
	}
	
	/** 
	 * 绘制HUD上的技能槽。<BR/>
	 * width，height代表屏幕的宽高。
	 */
	public static void drawSkill(int width, int height) {
		
		int widthToDraw = (int) (0.9 * width);
		int heightToDraw[] = new int[4];
		heightToDraw[0] = (int) (height * 0.2);
		heightToDraw[1] = (int) (height * 0.35);
		heightToDraw[2] = (int) (height * 0.5);
		heightToDraw[3] = (int) (height * 0.65);
			
		GL11.glEnable(GL11.GL_BLEND);
		Skill skillinslot[] = new Skill[Skill.SKILL_SLOT_SIZE];
		
		for (int i = 0; i < skillinslot.length; i++) {
			skillinslot[i] = Skill.getSkillInSlot(getPlayerCl(), i);
			if (skillinslot[i] != null) {
				KCUtils.getMC().getTextureManager().bindTexture(KCResources.getLocationFromName(skillinslot[i].mName));
				KCUtils.drawScaledCustomSizeModalRect(widthToDraw, heightToDraw[i], 0, 0, 64, 64, 32, 32, 64, 64);
			}
			KCUtils.getMC().getTextureManager().bindTexture(KCResources.skill_empty_slot);
			KCUtils.drawScaledCustomSizeModalRect(widthToDraw, heightToDraw[i], 0, 0, 64, 64, 32, 32, 64, 64);
		}	
		FontRenderer fontRenderer = KCUtils.getMC().fontRenderer;
		fontRenderer.drawStringWithShadow("R", widthToDraw + 2, heightToDraw[0], 0xFF0000);
		fontRenderer.drawStringWithShadow("F", widthToDraw + 2, heightToDraw[1], 0xFF0000);
		fontRenderer.drawStringWithShadow("Shift+R", widthToDraw + 2, heightToDraw[2], 0xFF0000);
		fontRenderer.drawStringWithShadow("Shift+F", widthToDraw + 2, heightToDraw[3], 0xFF0000);

		GL11.glDisable(GL11.GL_BLEND);
		initDrawerState();
		getMC().renderEngine.bindTexture(Gui.icons);
	}


}
