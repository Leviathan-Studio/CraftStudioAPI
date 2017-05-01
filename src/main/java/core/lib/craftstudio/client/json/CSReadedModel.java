package lib.craftstudio.client.json;

import java.util.ArrayList;
import java.util.List;

public class CSReadedModel
{
    public String                   name;
    public int                      textureWidth, textureHeight;
    public List<CSReadedModelBlock> parents = new ArrayList<>();
}
