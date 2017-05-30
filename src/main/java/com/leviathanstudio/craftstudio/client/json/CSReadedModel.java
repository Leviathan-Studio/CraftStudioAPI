package com.leviathanstudio.craftstudio.client.json;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry.Impl;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class that store information relative to a model.
 *
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class CSReadedModel extends Impl<CSReadedModel> implements IForgeRegistryEntry<CSReadedModel>
{
    private String                   name, modid;
    private int                      textureWidth, textureHeight;
    private List<CSReadedModelBlock> parents = new ArrayList<>();

    /**
     * Get a block from the model with this name.
     *
     * @param name
     *            The name of the block.
     * @return A block with this name. <i>null</i>, if no block with this
     *         name.</br>
     *         If multiple block with the same name, return one of them.
     */
    public CSReadedModelBlock getBlockFromName(String name)
    {
        CSReadedModelBlock b;
        for (CSReadedModelBlock block : this.parents)
        {
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
    public boolean isAnimable()
    {
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
    String whyUnAnimable()
    {
        boolean flag = true;
        List<String> names = new ArrayList<>();
        for (CSReadedModelBlock block : this.parents)
            if (block.getAnimability(names) == false)
            {
                flag = false;
                break;
            }
        if (flag)
            return null;
        else
            return names.get(0);
    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getModid()
    {
        return this.modid;
    }

    public void setModid(String modid)
    {
        this.modid = modid;
    }

    public int getTextureWidth()
    {
        return this.textureWidth;
    }

    public void setTextureWidth(int textureWidth)
    {
        this.textureWidth = textureWidth;
    }

    public int getTextureHeight()
    {
        return this.textureHeight;
    }

    public void setTextureHeight(int textureHeight)
    {
        this.textureHeight = textureHeight;
    }

    public List<CSReadedModelBlock> getParents()
    {
        return this.parents;
    }

    public void setParents(List<CSReadedModelBlock> parents)
    {
        this.parents = parents;
    }
}
