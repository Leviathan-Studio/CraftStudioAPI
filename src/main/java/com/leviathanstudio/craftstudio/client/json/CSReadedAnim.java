package com.leviathanstudio.craftstudio.client.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock.ReadedKeyFrame;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry.Impl;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class that store the informations relative to an animation.
 *
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class CSReadedAnim extends Impl<CSReadedAnim> implements IForgeRegistryEntry<CSReadedAnim>
{
    private String                  modid;
    private String                  name;
    private int                     duration;
    private boolean                 holdLastK;
    private List<CSReadedAnimBlock> blocks = new ArrayList<>();
    private Integer[]               keyFrames;

    /**
     * Get the keys of keyframes used in the animation.
     *
     * @return Array of the keys.
     */
    public Integer[] getKeyFrames() {
        if (this.keyFrames != null)
            return this.keyFrames;

        Set set = new HashSet<Integer>();
        for (CSReadedAnimBlock block : this.blocks)
            for (Entry<Integer, ReadedKeyFrame> entry : block.getKeyFrames().entrySet())
                set.add(entry.getKey());

        Integer[] tab = new Integer[1];
        tab = (Integer[]) set.toArray(tab);
        return tab;
    }

    public String getModid() {
        return this.modid;
    }

    public void setModid(String modid) {
        this.modid = modid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isHoldLastK() {
        return this.holdLastK;
    }

    public void setHoldLastK(boolean holdLastK) {
        this.holdLastK = holdLastK;
    }

    public List<CSReadedAnimBlock> getBlocks() {
        return this.blocks;
    }

    public void setBlocks(List<CSReadedAnimBlock> blocks) {
        this.blocks = blocks;
    }

}
