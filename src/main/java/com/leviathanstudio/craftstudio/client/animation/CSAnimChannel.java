package com.leviathanstudio.craftstudio.client.animation;

import java.util.Map.Entry;

import javax.vecmath.Vector3f;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.exception.CSResourceNotRegisteredException;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock.ReadedKeyFrame;
import com.leviathanstudio.craftstudio.client.registry.RegistryHandler;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.json.CSReadedModelBlock;
import com.leviathanstudio.craftstudio.client.util.MathHelper;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Animation Channel for CraftStudio imported animation.
 *
 * @since 0.3.0
 *
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class CSAnimChannel extends ClientChannel
{
    /** The registered animation it represent */
    private CSReadedAnim  rAnim;
    /** The registered model it animate */
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
        this.rAnim = RegistryHandler.ANIMATION_REGISTRY.getObject(animIn);
        if (this.rAnim == null)
            throw new CSResourceNotRegisteredException(animIn.toString());
        this.rModel = RegistryHandler.MODEL_REGISTRY.getObject(modelIn);
        if (this.rModel == null)
            throw new CSResourceNotRegisteredException(modelIn.toString());
        if (!this.rModel.isAnimable()) {
            CraftStudioApi.getLogger().warn("You are trying to animate the model \"" + modelIn.toString() + "\"");
            CraftStudioApi.getLogger().warn("But it contains at least two blocks with the name \"" + this.rModel.whyUnAnimable() + "\"");
            CraftStudioApi.getLogger().warn("There could be weird result with your animation");
        }
        this.fps = fps;
        this.totalFrames = this.rAnim.getDuration();
        if (looped)
            this.setAnimationMode(EnumAnimationMode.LOOP);
        else if (this.rAnim.isHoldLastK())
            this.setAnimationMode(EnumAnimationMode.HOLD);
        this.initializeAllFrames();
    }

    @Override
    protected void initializeAllFrames() {
        KeyFrame keyFrame;
        ReadedKeyFrame rKeyFrame;
        int lastRK, lastTK, lastOK, lastSK;
        for (int i : this.rAnim.getKeyFrames())
            this.getKeyFrames().put(i, new KeyFrame());
        if (this.rAnim.isHoldLastK())
            if (!this.getKeyFrames().containsKey(this.totalFrames))
                this.getKeyFrames().put(this.totalFrames, new KeyFrame());
        for (CSReadedAnimBlock block : this.rAnim.getBlocks()) {
            CSReadedModelBlock mBlock = this.rModel.getBlockFromName(block.getName());
            lastRK = 0;
            lastTK = 0;
            lastOK = 0;
            lastSK = 0;
            if (mBlock != null)
                for (Entry<Integer, ReadedKeyFrame> entry : block.getKeyFrames().entrySet()) {
                    Vector3f vector;
                    keyFrame = this.getKeyFrames().get(entry.getKey());
                    rKeyFrame = entry.getValue();
                    if (rKeyFrame.position != null) {
                        vector = new Vector3f(rKeyFrame.position);
                        vector.add(mBlock.getRotationPoint());
                        keyFrame.modelRenderersTranslations.put(block.getName(), vector);
                        if (lastTK < entry.getKey())
                            lastTK = entry.getKey();
                    }
                    if (rKeyFrame.rotation != null) {
                        vector = new Vector3f(rKeyFrame.rotation);
                        vector.add(mBlock.getRotation());
                        keyFrame.modelRenderersRotations.put(block.getName(), MathHelper.quatFromEuler(vector));
                        if (lastRK < entry.getKey())
                            lastRK = entry.getKey();
                    }
                    if (rKeyFrame.offset != null) {
                        vector = new Vector3f(rKeyFrame.offset);
                        vector.add(mBlock.getOffset());
                        keyFrame.modelRenderersOffsets.put(block.getName(), vector);
                        if (lastOK < entry.getKey())
                            lastOK = entry.getKey();
                    }
                    if (rKeyFrame.stretching != null) {
                        vector = new Vector3f(rKeyFrame.stretching);
                        vector.add(mBlock.getStretch());
                        keyFrame.modelRenderersStretchs.put(block.getName(), vector);
                        if (lastSK < entry.getKey())
                            lastSK = entry.getKey();
                    }
                }
            else
                System.out.println("The block " + block.getName() + " doesn't exist in model " + this.rModel.getName() + " !");
            if (this.rAnim.isHoldLastK()) {
                if (lastTK != 0)
                    this.getKeyFrames().get(this.totalFrames).modelRenderersTranslations.put(block.getName(),
                            this.getKeyFrames().get(lastTK).modelRenderersTranslations.get(block.getName()));
                if (lastRK != 0)
                    this.getKeyFrames().get(this.totalFrames).modelRenderersRotations.put(block.getName(),
                            this.getKeyFrames().get(lastRK).modelRenderersRotations.get(block.getName()));
                if (lastOK != 0)
                    this.getKeyFrames().get(this.totalFrames).modelRenderersOffsets.put(block.getName(),
                            this.getKeyFrames().get(lastOK).modelRenderersOffsets.get(block.getName()));
                if (lastSK != 0)
                    this.getKeyFrames().get(this.totalFrames).modelRenderersStretchs.put(block.getName(),
                            this.getKeyFrames().get(lastSK).modelRenderersStretchs.get(block.getName()));
            }
        }
    }
}
