package com.leviathanstudio.craftstudio.client;

import java.util.HashMap;
import java.util.Map;

import com.leviathanstudio.craftstudio.client.json.CSReadedModel;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/**
 * Class that contain the Models Map.
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class CSModelMesher
{
    public static Map<String, CSReadedModel> models = new HashMap<String, CSReadedModel>();
}