package org.nulla.kcrw;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;

public class KCUtils {
	
	public static Minecraft getMC() {
		return Minecraft.getMinecraft();
	}
	
	/** 
	 * ֻ����Client���ã��Ի�ȡClient�˵������Ϣ������λ�ã�������ı�Server����Ϣ��
	 */
	public static EntityPlayer getPlayerCl() {
		return getMC().thePlayer;
	}
	
	/** 
	 * ���ƿ����ŵ�����<BR/>
	 * x��y�����������Ļ�ϵ�λ�á�<BR/>
	 * u��v����Ҫ���ƵĲ�����Դ�ز�ͼ�ϵ�λ�á�<BR/>
	 * uW��vH����Ҫ���ƵĲ�����Դ�ز�ͼ�ϵĴ�С��<BR/>
	 * w��h����Ҫ���ƵĲ��ֵĴ�С��<BR/>
	 * tW��tH����Դ�ز�ͼ�Ĵ�С�������ã���
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

}
