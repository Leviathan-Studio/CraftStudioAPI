package com.leviathanstudio.craftstudio.client.animation;

import java.util.Map.Entry;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock.ReadedKeyFrame;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.json.CSReadedModelBlock;
import com.leviathanstudio.craftstudio.common.exceptions.CSResourceNotRegisteredException;
import com.leviathanstudio.craftstudio.util.math.Quaternion;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Animation Channel for CraftStudio imported animation.
 *
 * @author Timmypote
 */
public class CSAnimChannel extends ClientChannel
{
    private CSReadedAnim  rAnim;
    private CSReadedModel rModel;

    /**
     * Create a channel with the same name as the animation. Use the 60 fps by
     * default.
     *
     * @param animNameIn
     *            The name of the animation in the registry.
     * @param modelNameIn
     *            The name of the model bind to this animation in the registry.
     * @param looped
     *            If the animation is looped or not.
     * @throws CSResourceNotRegisteredException
     *             If the animation or model if not registered
     */
    public CSAnimChannel(ResourceLocation animIn, ResourceLocation modelIn, boolean looped) throws CSResourceNotRegisteredException {
        this(animIn, modelIn, 60.0F, looped);
    }

    /**
     * Create a channel.
     *
     * @param animNameIn
     *            The name of the animation in the registry.
     * @param name
     *            The name of the channel
     * @param modelNameIn
     *            The name of the model bind to this animation in the registry.
     * @param fps
     *            Keyframes per second of the animation.
     * @param looped
     *            If the animation is looped or not.
     * @throws CSResourceNotRegisteredException
     *             If the animation or model if not registered
     */
    public CSAnimChannel(ResourceLocation animIn, ResourceLocation modelIn, float fps, boolean looped) throws CSResourceNotRegisteredException {
        super(animIn.toString(), false);
        this.rAnim = GameRegistry.findRegistry(CSReadedAnim.class).getValue(animIn);
        if (this.rAnim == null)
            throw new CSResourceNotRegisteredException(animIn.toString());
        this.rModel = GameRegistry.findRegistry(CSReadedModel.class).getValue(modelIn);
        if (this.rModel == null)
            throw new CSResourceNotRegisteredException(modelIn.toString());
        this.fps = fps;
        this.totalFrames = this.rAnim.duration;
        if (looped)
            this.animationMode = EnumAnimationMode.LOOP;
        this.initializeAllFrames();
    }

    /**
     * Initialize the keyframes.
     */
    @Override
    protected void initializeAllFrames() {
        KeyFrame keyFrame;
        ReadedKeyFrame rKeyFrame;
        int lastRK, lastTK;
        for (int i : this.rAnim.getKeyFrames())
            this.keyFrames.put(i, new KeyFrame());
        if (this.rAnim.holdLastK)
            if (!this.keyFrames.containsKey(this.totalFrames))
                this.keyFrames.put(this.totalFrames, new KeyFrame());
        for (CSReadedAnimBlock block : this.rAnim.blocks) {
            CSReadedModelBlock mBlock = this.rModel.getBlockFromName(block.name);
            lastRK = 0;
            lastTK = 0;
            if (mBlock != null)
                for (Entry<Integer, ReadedKeyFrame> entry : block.keyFrames.entrySet()) {
                    keyFrame = this.keyFrames.get(entry.getKey());
                    rKeyFrame = entry.getValue();
                    if (rKeyFrame.position != null) {
                        keyFrame.modelRenderersTranslations.put(block.name, rKeyFrame.position.add(mBlock.rotationPoint));
                        if (lastTK < entry.getKey())
                            lastTK = entry.getKey();
                    }
                    if (rKeyFrame.rotation != null) {
                        keyFrame.modelRenderersRotations.put(block.name, new Quaternion(rKeyFrame.rotation.add(mBlock.rotation)));
                        if (lastRK < entry.getKey())
                            lastRK = entry.getKey();
                    }
                }
            else
                System.out.println("The block " + block.name + " doesn't exist in model " + this.rModel.name + " !");
            if (this.rAnim.holdLastK) {
                if (lastTK != 0)
                    this.keyFrames.get(this.totalFrames).modelRenderersTranslations.put(block.name,
                            this.keyFrames.get(lastTK).modelRenderersTranslations.get(block.name));
                if (lastRK != 0)
                    this.keyFrames.get(this.totalFrames).modelRenderersRotations.put(block.name,
                            this.keyFrames.get(lastRK).modelRenderersRotations.get(block.name));
            }

        }
        if (!this.rAnim.holdLastK)
            if (!this.keyFrames.containsKey(this.totalFrames))
                this.keyFrames.put(this.totalFrames, this.keyFrames.get(0).clone());

    }
}