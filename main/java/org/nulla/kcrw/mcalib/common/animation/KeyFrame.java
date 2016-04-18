package org.nulla.kcrw.mcalib.common.animation;

import java.util.ArrayList;
import java.util.HashMap;

import org.nulla.kcrw.mcalib.common.math.Quaternion;
import org.nulla.kcrw.mcalib.common.math.Vector3f;

public class KeyFrame {
	public HashMap<String, Quaternion> modelRenderersRotations = new HashMap<String, Quaternion>();
	public HashMap<String, Vector3f> modelRenderersTranslations = new HashMap<String, Vector3f>();
	
	public boolean useBoxInRotations(String boxName)
	{
		return modelRenderersRotations.get(boxName) != null;
	}
	
	public boolean useBoxInTranslations(String boxName)
	{
		return modelRenderersTranslations.get(boxName) != null;
	}
}