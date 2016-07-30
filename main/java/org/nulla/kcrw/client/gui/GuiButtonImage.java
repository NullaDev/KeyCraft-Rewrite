package org.nulla.kcrw.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.*;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.nulla.kcrw.KCResources;
import org.nulla.kcrw.KCUtils;

public class GuiButtonImage extends Gui {
    protected ResourceLocation buttonTextures;
    public int width;
    public int height;
    public int posX;
    public int posY;
    public int id;
    public boolean hasFrame;
    public boolean enabled;
    public boolean visible;
    protected boolean isPointAt;
    public int packedFGColour;
    
    public GuiButtonImage(int btnID, int btnPosX, int btnPosY, int btnWidth, int btnHeight, ResourceLocation location, boolean frame) {
        this.enabled = true;
        this.visible = true;
        this.id = btnID;
        this.posX = btnPosX;
        this.posY = btnPosY;
        this.width = btnWidth;
        this.height = btnHeight;
        this.buttonTextures = location;
        this.hasFrame = frame;
    }

    /**
     * 如果Disable返回0, 没有Hover返回1, Hover返回2。
     */
    public int getHoverState(boolean isHovering) {
        byte b0 = 1;

        if (!this.enabled) {
            b0 = 0;
        } else if (isHovering) {
            b0 = 2;
        }

        return b0;
    }

    /**
     * 在屏幕上绘制Button。
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
    	if (this.visible) {
    		mc.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.isPointAt = mouseX >= posX && mouseY >= posY && mouseX < posX + width && mouseY < posY + height;
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            
            if (this.hasFrame) {
            	//按钮背景
            	drawRect(posX, posY, posX + width, posY + height, 0xFF7F3F00);
            
            	//按钮边框
            	drawRect(posX, posY, posX + width, posY + 1, 0xFF000000);
            	drawRect(posX, posY, posX + 1, posY + height, 0xFF000000);
            	drawRect(posX + width - 1, posY, posX + width, posY + height, 0xFF000000);
            	drawRect(posX, posY + height -1, posX + width, posY + height, 0xFF000000);
            }

            KCUtils.initDrawerState();
            
            KCUtils.drawScaledCustomSizeModalRect(posX, posY, 0, 0, 64, 64, width, height, 64, 64);
            this.mouseDragged(mc, mouseX, mouseY);
        }
        
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft p_146119_1_, int p_146119_2_, int p_146119_3_) {}

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int p_146118_1_, int p_146118_2_) {}

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int p_146116_2_, int p_146116_3_) {
        return this.enabled && this.visible && p_146116_2_ >= posX && p_146116_3_ >= posY && p_146116_2_ < posX + width && p_146116_3_ < posY + height;
    }

    public boolean isPointAt() {
        return this.isPointAt;
    }

    public void func_146113_a(SoundHandler p_146113_1_) {
        p_146113_1_.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}