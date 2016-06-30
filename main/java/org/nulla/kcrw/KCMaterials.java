package org.nulla.kcrw;

import net.minecraftforge.common.util.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;

public class KCMaterials {
	
	public static ToolMaterial AuroraBlade = EnumHelper.addToolMaterial("AuroraBlade", 3, 255, 10.0f, 8.0f, 0);
	public static ToolMaterial AURORA_IRON = EnumHelper.addToolMaterial("AURORA_IRON", 3, 127, 8.0f, 3.0f, 26);
	
	public static int[] AuroraIronVar = {3, 8, 6, 3};
	public static ArmorMaterial AuroraIronArmor = EnumHelper.addArmorMaterial("AuroraIron", 10, AuroraIronVar, 26);

}
