package lib.craftstudio.client.json;

import java.util.ArrayList;
import java.util.List;

import lib.craftstudio.common.math.Vector3f;

public class CSReadedModelBlock
{
    public String                   name;
    public Vector3f                 boxSetup, rotationPoint, rotation;
    public int[]                    texOffset = new int[2], size = new int[3];
    public List<CSReadedModelBlock> childs    = new ArrayList<>();
}
