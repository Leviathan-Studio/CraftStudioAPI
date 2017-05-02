package lib.craftstudio.client.json;

import java.util.ArrayList;
import java.util.List;

import lib.craftstudio.common.math.Vector3f;

public class CSReadedModelBlock
{
    public String                   name;
    public Vector3f                 boxSetup, rotationPoint, rotation, size;
    public int[]                    texOffset = new int[2], faceSize = new int[3];
    public List<CSReadedModelBlock> childs    = new ArrayList<>();
}
