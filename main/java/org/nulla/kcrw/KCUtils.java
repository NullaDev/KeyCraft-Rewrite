package org.nulla.kcrw;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.nulla.nullacore.api.skill.Skill;
import org.nulla.nullacore.api.skill.SkillPassive;
import org.nulla.nullacore.api.skill.SkillUtils;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;

public class KCUtils {
	
	/** 零向量。 */
	public static final Vec3 zeroVec3 = Vec3.createVectorHelper(0, 0, 0);
	
	public static Minecraft getMC() {
		return Minecraft.getMinecraft();
	}
	
	/** 
	 * 只能在Client端用，以获取Client端的玩家信息（例如位置），不会改变Server端信息。
	 */
	public static EntityPlayer getPlayerCl() {
		return getMC().thePlayer;
	}
	
	public static FontRenderer getfontRenderer() {
		return getMC().fontRenderer;
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
	 * 注册带附魔的合成表。
	 */
	public static void addEnchantedRecipe(ItemStack item, Enchantment enchantment, int enchantmentLevel, Object[] ingredientArray) {
		if (enchantment != null) {
			item.addEnchantment(enchantment, enchantmentLevel);
	    }
	    GameRegistry.addRecipe(item, ingredientArray);
	}
	
	/** 
	 * 注册带附魔的合成表。
	 */
	public static void addEnchantedRecipe(Item item, Enchantment enchantment, int enchantmentLevel, Object[] ingredientArray) {
		addEnchantedRecipe(new ItemStack(item), enchantment, enchantmentLevel, ingredientArray);
	}
	
	/** 
	 * 注册带附魔和名字的合成表。
	 */
	public static void addEnchantedNamedRecipe(ItemStack item, Enchantment enchantment, int enchantmentLevel, String name, Object[] ingredientArray) {
		if (enchantment != null) {
			item.addEnchantment(enchantment, enchantmentLevel);
	    }
		if (name != null) {
			item.setStackDisplayName(name);
		}
	    GameRegistry.addRecipe(item, ingredientArray);
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
	public static void minusNumberOfItemInPlayer(EntityPlayer player, Item item, int number) {
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			if (player.inventory.mainInventory[i] != null
				&& player.inventory.mainInventory[i].getItem().equals(item)) {
				int size = player.inventory.mainInventory[i].stackSize;
				if (size >= number) {
					player.inventory.decrStackSize(i, number);
					number = 0;
				} else {
					player.inventory.decrStackSize(i, size);
					number -= size;
				}
			}
			if (number <= 0)
				break;
		}
	}
	
	public static void drawLine(int x0, int y0, int x1, int y1, float R, float G, float B, float width) {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(R, G, B);
		GL11.glLineWidth(width);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2f(x0, y0);
		GL11.glVertex2f(x1, y1);
		GL11.glEnd();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

	
    public static void drawRect(int x1, int y1, int width, int height, int color) {
    	int x2 = x1 + width;
    	int y2 = y1 + height;
    	
        int j1;

        if (x1 < x2) {
            j1 = x1;
            x1 = x2;
            x2 = j1;
        }

        if (y1 < y2) {
            j1 = y1;
            y1 = y2;
            y2 = j1;
        }

        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f, f1, f2, f3);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double)x1, (double)y2, 0.0D);
        tessellator.addVertex((double)x2, (double)y2, 0.0D);
        tessellator.addVertex((double)x2, (double)y1, 0.0D);
        tessellator.addVertex((double)x1, (double)y1, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
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
	 * 初始化（绘制器的）撞钛鸡。
	 */
	public static void initDrawerState() {
		FontRenderer fontRenderer = getMC().fontRenderer;
		fontRenderer.drawString("", 0, 0, 0xFFFFFF);
	}
	
	/** 
	 * 获取玩家身上某一个ItemStack的位置。
	 */
	public static int getPosOfStack(EntityPlayer player, ItemStack stack) {
		for (int i = 0; i < player.inventory.mainInventory.length; i++) {
			if (ItemStack.areItemStacksEqual(player.inventory.mainInventory[i], stack)) {
				return i;
			}
		}
		return -1;
	}
	
}
