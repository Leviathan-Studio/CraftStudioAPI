package com.leviathanstudio.craftstudio.client.json;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry.Impl;

/**
 * Class that store information relative to a model.
 *
 * @author Timmypote
 */
public class CSReadedModel extends Impl<CSReadedModel> implements IForgeRegistryEntry<CSReadedModel>
{
    public String                   name, modid;
    public int                      textureWidth, textureHeight;
    public List<CSReadedModelBlock> parents = new ArrayList<>();

    /**
     * Get a block from the model with this name.
     *
     * @param name
     *            The name of the block.
     * @return A block with this name. <i>null</i>, if no block with this
     *         name.</br>
     *         If multiple block with the same name, return one of them.
     */
    public CSReadedModelBlock getBlockFromName(String name) {
        CSReadedModelBlock b;
        for (CSReadedModelBlock block : this.parents) {
            b = block.getBlockFromName(name);
            if (b != null)
                return b;
        }
        return null;
    }

    /**
     * Is the model able to be animated. The model can't be animated if there is
     * two or more blocks with the same name.
     *
     * @return True, if animable. False, otherwise.
     */
    public boolean isAnimable() {
        List<String> names = new ArrayList<>();
        for (CSReadedModelBlock block : this.parents)
            if (block.getAnimability(names) == false)
                return false;
        return true;
    }

    /**
     * Get the name that is duplicated and make the model unanimable.
     *
     * @return The name of the block. <i>null</i>, if the model if animable.
     */
    String whyUnAnimable() {
        boolean flag = true;
        List<String> names = new ArrayList<>();
        for (CSReadedModelBlock block : this.parents)
            if (block.getAnimability(names) == false) {
                flag = false;
                break;
            }
        if (flag)
            return null;
        else
            return names.get(0);
    }
}
