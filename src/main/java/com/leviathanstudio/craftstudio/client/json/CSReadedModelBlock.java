package com.leviathanstudio.craftstudio.client.json;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.common.math.Vector3f;

public class CSReadedModelBlock
{
    public String                   name;
    public Vector3f                 boxSetup, rotationPoint, rotation, size;
    public float                    vertex[][];
    public int[]                    texOffset = new int[2];
    public List<CSReadedModelBlock> childs    = new ArrayList<>();
}
