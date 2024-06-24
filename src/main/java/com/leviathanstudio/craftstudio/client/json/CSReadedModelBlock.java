package com.leviathanstudio.craftstudio.client.json;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that store information relative to a block in a model.
 *
 * @author Timmypote
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class CSReadedModelBlock {
    private String name;
    private Vector3f rotationPoint, rotation, size, stretch, offset;
    private float vertex[][];
    private int[] texOffset = new int[2];
    private List<CSReadedModelBlock> childs = new ArrayList<>();

    /**
     * Create a new block with the specified name.
     *
     * @param name The name of the block.
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
     * Check if the block or one of it's child as a name already in the list or
     * not and complete the list.
     *
     * @param names A list of name.
     * @return null, if no block has duplicate name. One name that is
     * duplicated, otherwise.
     */
    String whyUnAnimable(List<String> names) {
        String str;
        if (names.contains(this.name))
            return this.name;
        names.add(this.name);
        for (CSReadedModelBlock block : this.childs) {
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

    public Vector3f getRotationPoint() {
        return this.rotationPoint;
    }

    public void setRotationPoint(Vector3f rotationPoint) {
        this.rotationPoint = rotationPoint;
    }

    public Vector3f getRotation() {
        return this.rotation;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public Vector3f getSize() {
        return this.size;
    }

    public void setSize(Vector3f size) {
        this.size = size;
    }

    public Vector3f getStretch() {
        return this.stretch;
    }

    public void setStretch(Vector3f stretch) {
        this.stretch = stretch;
    }

    public Vector3f getOffset() {
        return this.offset;
    }

    public void setOffset(Vector3f offset) {
        this.offset = offset;
    }

    public float[][] getVertex() {
        return this.vertex;
    }

    public void setVertex(float[][] vertex) {
        this.vertex = vertex;
    }

    public int[] getTexOffset() {
        return this.texOffset;
    }

    public void setTexOffset(int[] texOffset) {
        this.texOffset = texOffset;
    }

    public List<CSReadedModelBlock> getChilds() {
        return this.childs;
    }

    public void setChilds(List<CSReadedModelBlock> childs) {
        this.childs = childs;
    }
}
