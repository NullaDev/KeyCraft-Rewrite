package org.nulla.kcrw.gui.part;

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
    public int xPosition;
    public int yPosition;
    public int id;
    public boolean enabled;
    public boolean visible;
    protected boolean isPointAt;
    public int packedFGColour;

    public GuiButtonImage(int btnID, int btnPosX, int btnPosY, int btnWidth, int btnHeight, String name) {
        this.enabled = true;
        this.visible = true;
        this.id = btnID;
        this.xPosition = btnPosX;
        this.yPosition = btnPosY;
        this.width = btnWidth;
        this.height = btnHeight;
        this.buttonTextures = KCResources.getLocationFromName(name);
    }

    /**
     * ���Disable����0, û��Hover����1, Hover����2��
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
     * ����Ļ�ϻ���Button��
     */
    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
        	mc.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.isPointAt = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            KCUtils.drawScaledCustomSizeModalRect(this.xPosition, this.yPosition, 0, 0, 64, 64, this.width, this.height, 64, 64);
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
        return this.enabled && this.visible && p_146116_2_ >= this.xPosition && p_146116_3_ >= this.yPosition && p_146116_2_ < this.xPosition + this.width && p_146116_3_ < this.yPosition + this.height;
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