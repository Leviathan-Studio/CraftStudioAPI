package com.leviathanstudio.craftstudio.client;

import java.util.HashMap;
import java.util.Map;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class that contain the Animations Map.
 *
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class CSAnimMesher {
	public static Map<String, CSReadedAnim> animations = new HashMap<>();
}
