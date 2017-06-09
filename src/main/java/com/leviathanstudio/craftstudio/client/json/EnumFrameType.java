package com.leviathanstudio.craftstudio.client.json;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public enum EnumFrameType {
    POSITION, ROTATION, OFFSET, SIZE, STRETCH;
}
