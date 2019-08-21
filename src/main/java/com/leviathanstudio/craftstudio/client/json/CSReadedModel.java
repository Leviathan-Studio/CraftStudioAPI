package com.leviathanstudio.craftstudio.client.json;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that store information relative to a model.
 *
 * @author Timmypote
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class CSReadedModel {

    private String name;
    private int textureWidth, textureHeight;
    private List<CSReadedModelBlock> parents = new ArrayList<>();

    /**
     * Get a block from the model with this name.
     *
     * @param name The name of the block.
     * @return A block with this name. <i>null</i>, if no block with this
     * name.</br>
     * If multiple block with the same name, return one of them.
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
        if (this.whyUnAnimable() == null)
            return true;
        return false;
    }

    /**
     * Get the name of the block that is duplicated, if there is any.
     *
     * @return The name of the block. <i>null</i>, if the model if animable.
     */
    public String whyUnAnimable() {
        String str;
        List<String> names = new ArrayList<>();
        for (CSReadedModelBlock block : this.parents) {
            str = block.whyUnAnimable(names);
            if (str != null)
                return str;
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTextureWidth() {
        return this.textureWidth;
    }

    public void setTextureWidth(int textureWidth) {
        this.textureWidth = textureWidth;
    }

    public int getTextureHeight() {
        return this.textureHeight;
    }

    public void setTextureHeight(int textureHeight) {
        this.textureHeight = textureHeight;
    }

    public List<CSReadedModelBlock> getParents() {
        return this.parents;
    }

    public void setParents(List<CSReadedModelBlock> parents) {
        this.parents = parents;
    }

}
