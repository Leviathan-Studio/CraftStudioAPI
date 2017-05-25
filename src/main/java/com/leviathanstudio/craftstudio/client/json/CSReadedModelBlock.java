package com.leviathanstudio.craftstudio.client.json;

import java.util.ArrayList;
import java.util.List;

import com.leviathanstudio.craftstudio.util.math.Vector3f;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class that store information relative to a block in a model.
 *
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class CSReadedModelBlock
{
    public String                   name;
    public Vector3f                 boxSetup, rotationPoint, rotation, size;
    public float                    vertex[][];
    public int[]                    texOffset = new int[2];
    public List<CSReadedModelBlock> childs    = new ArrayList<>();

    /**
     * Create a new block with the specified name.
     *
     * @param name
     *            The name of the block.
     * @return The new block.
     */
    CSReadedModelBlock getBlockFromName(String name) {
        CSReadedModelBlock b;
        if (this.name.equals(name))
            return this;
        for (CSReadedModelBlock block : this.childs) {
            b = block.getBlockFromName(name);
            if (b != null)
                return b;
        }
        return null;
    }

    /**
     * Get if the block as a name already in the list or not. If not it add his
     * name to the list.</br>
     * If it is, the list is wiped and the block's name is add.
     *
     * @param names
     *            A list of name.
     * @return True, if the name isn't in the list. False, otherwise.
     */
    boolean getAnimability(List<String> names) {
        if (names.contains(this.name)) {
            names = new ArrayList<>();
            names.add(this.name);
            return false;
        }
        names.add(this.name);
        for (CSReadedModelBlock block : this.childs)
            if (block.getAnimability(names) == false)
                return false;
        return true;
    }
}
