package com.leviathanstudio.craftstudio.client.animation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.Channel;
import com.leviathanstudio.craftstudio.common.animation.CustomChannel;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.common.animation.InfoChannel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ClientAnimationHandler<T extends IAnimated> extends AnimationHandler<T>
{
    /** Map with all the animations. */
    private Map<String, InfoChannel>           animChannels    = new HashMap<>();

    private Map<T, Map<InfoChannel, AnimInfo>> currentAnimInfo = new HashMap<>();

    public ClientAnimationHandler() {
        super();
    }

    @Override
    public void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn), model = new ResourceLocation(modid, modelNameIn);
        this.animChannels.put(anim.toString(), new CSAnimChannel(anim, model, looped));
        this.channelIds.add(anim.toString());
    }

    @Override
    public void addAnim(String modid, String animNameIn, String modelNameIn, CustomChannel customChannelIn) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn), model = new ResourceLocation(modid, modelNameIn);
        this.animChannels.put(anim.toString(), customChannelIn);
        this.channelIds.add(anim.toString());
    }

    @Override
    public void addAnim(String modid, String invertedAnimationName, String animationToInvert) {
        ResourceLocation anim = new ResourceLocation(modid, invertedAnimationName);
        ResourceLocation inverted = new ResourceLocation(modid, animationToInvert);
        if (this.animChannels.get(inverted.toString()) instanceof ClientChannel) {
            ClientChannel channel = ((ClientChannel) this.animChannels.get(inverted.toString())).getInvertedChannel(invertedAnimationName);
            channel.name = anim.toString();
            this.animChannels.put(anim.toString(), channel);
            this.channelIds.add(anim.toString());
        }
    }

    @Override
    public void startAnimation(String animationNameIn, float startingFrame, T animatedElement) {
        if (Minecraft.getMinecraft().isSingleplayer())
            this.clientStartAnimation(animationNameIn, startingFrame, animatedElement);
    }

    @Override
    public void clientStartAnimation(String res, float startingFrame, T animatedElement) {
        if (this.animChannels.get(res) == null) {
            CraftStudioApi.getLogger().warn("The animation called " + res + " doesn't exist!");
            return;
        }
        Map<InfoChannel, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            this.currentAnimInfo.put(animatedElement, animInfoMap = new HashMap<>());

        InfoChannel selectedChannel = this.animChannels.get(res);
        animInfoMap.remove(selectedChannel);

        animInfoMap.put(selectedChannel, new AnimInfo(System.nanoTime(), startingFrame));
    }

    @Override
    public void stopAnimation(String res, T animatedElement) {
        if (Minecraft.getMinecraft().isSingleplayer())
            this.clientStopAnimation(res, animatedElement);
    }

    public void clientStopAnimation(String res, T animatedElement) {
        if (!this.animChannels.containsKey(res)) {
            CraftStudioApi.getLogger().warn("The animation stopped " + res + " doesn't exist!");
            return;
        }

        Map<InfoChannel, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return;

        InfoChannel selectedChannel = this.animChannels.get(res);
        animInfoMap.remove(selectedChannel);
        if (animInfoMap.isEmpty())
            this.currentAnimInfo.remove(animatedElement);
    }

    @Override
    public void stopStartAnimation(String animToStop, String animToStart, float startingFrame, T animatedElement) {
        this.stopAnimation(animToStop, animatedElement);
        this.startAnimation(animToStart, startingFrame, animatedElement);
    }

    /**
     * Update the animation
     */
    @Override
    public void animationsUpdate(T animatedElement) {
        Map<InfoChannel, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return;

        for (Iterator<Entry<InfoChannel, AnimInfo>> it = animInfoMap.entrySet().iterator(); it.hasNext();) {
            Entry<InfoChannel, AnimInfo> animInfo = it.next();
            float prevFrame = animInfo.getValue().currentFrame;
            boolean canUpdate = this.canUpdateAnimation(animInfo.getKey(), animatedElement);
            if (!canUpdate)
                it.remove();
        }
    }

    /**
     * Check if animation is active
     */
    @Override
    public boolean isAnimationActive(String name, T animatedElement) {
        InfoChannel anim = this.animChannels.get(name);
        if (anim == null)
            return false;
        Map<InfoChannel, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return false;
        if (animInfoMap.containsKey(anim)) {
            AnimInfo info = animInfoMap.get(anim);
            if (anim instanceof CustomChannel || info.currentFrame < anim.totalFrames - 1)
                return true;
        }
        return false;
    }

    /**
     * Check if an hold animation is active
     */
    public boolean isHoldAnimationActive(String name, T animatedElement) {
        InfoChannel anim = this.animChannels.get(name);
        if (anim == null)
            return false;
        Map<InfoChannel, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return false;
        if (animInfoMap.containsKey(anim))
            return true;
        return false;
    }

    /**
     * Check if animation can be updated
     */
    @Override
    public boolean canUpdateAnimation(Channel channel, T animatedElement) {
        Map<InfoChannel, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return false;

        long currentTime = System.nanoTime();
        if (!(channel instanceof InfoChannel))
            return false;
        InfoChannel infoChannel = (InfoChannel) channel;
        AnimInfo animInfo = animInfoMap.get(channel);
        if (!ClientAnimationHandler.isGamePaused()) {
            if (infoChannel instanceof ClientChannel) {
                ClientChannel clientChannel = (ClientChannel) infoChannel;

                double deltaTime = (currentTime - animInfo.prevTime) / 1000000000.0;
                float numberOfSkippedFrames = (float) (deltaTime * channel.fps);

                float currentFrame = animInfo.currentFrame + numberOfSkippedFrames;

                if (currentFrame < clientChannel.totalFrames - 1) {
                    animInfo.prevTime = currentTime;
                    animInfo.currentFrame = currentFrame;
                    return true;
                }
                if (clientChannel.getAnimationMode() == EnumAnimationMode.LOOP) {
                    animInfo.prevTime = currentTime;
                    animInfo.currentFrame = 0F;
                    return true;
                }
                if (clientChannel.getAnimationMode() == EnumAnimationMode.HOLD) {
                    animInfo.prevTime = currentTime;
                    animInfo.currentFrame = (float) channel.totalFrames - 1;
                    return true;
                }
                return false;
            }
            else
                return true;
        }
        else {
            animInfo.prevTime = currentTime;
            return true;
        }

    }

    /**
     * Check if game is paused (Exit screen)
     */
    public static boolean isGamePaused() {
        Minecraft MC = Minecraft.getMinecraft();
        return MC.isSingleplayer() && MC.currentScreen != null && MC.currentScreen.doesGuiPauseGame() && !MC.getIntegratedServer().getPublic();
    }

    /**
     * Apply animations if running or apply initial values. Must be called only
     * by the model class.
     */
    public static void performAnimationInModel(List<CSModelRenderer> parts, IAnimated entity) {
        for (CSModelRenderer entry : parts)
            performAnimationForBlock(entry, entity);
    }

    /**
     * Apply animations for model block
     */
    public static void performAnimationForBlock(CSModelRenderer block, IAnimated entity) {
        String boxName = block.boxName;

        if (entity.getAnimationHandler() instanceof ClientAnimationHandler) {
            ClientAnimationHandler animHandler = (ClientAnimationHandler) entity.getAnimationHandler();

            if (block.childModels != null)
                for (ModelRenderer child : block.childModels)
                    if (child instanceof CSModelRenderer) {
                        CSModelRenderer childModel = (CSModelRenderer) child;
                        performAnimationForBlock(childModel, entity);
                    }

            block.resetRotationPoint();
            block.resetRotationMatrix();
            block.resetOffset();
            block.resetStretch();

            Map<InfoChannel, AnimInfo> animInfoMap = (Map<InfoChannel, AnimInfo>) animHandler.currentAnimInfo.get(entity);
            if (animInfoMap == null)
                return;

            for (Entry<InfoChannel, AnimInfo> animInfo : animInfoMap.entrySet())
                if (animInfo.getKey() instanceof ClientChannel) {
                    ClientChannel clientChannel = (ClientChannel) animInfo.getKey();
                    float currentFrame = animInfo.getValue().currentFrame;

                    // Rotations
                    KeyFrame prevRotationKeyFrame = clientChannel.getPreviousRotationKeyFrameForBox(boxName, currentFrame);
                    int prevRotationKeyFramePosition = prevRotationKeyFrame != null ? clientChannel.getKeyFramePosition(prevRotationKeyFrame) : 0;

                    KeyFrame nextRotationKeyFrame = clientChannel.getNextRotationKeyFrameForBox(boxName, currentFrame);
                    int nextRotationKeyFramePosition = nextRotationKeyFrame != null ? clientChannel.getKeyFramePosition(nextRotationKeyFrame) : 0;

                    float SLERPProgress = (currentFrame - prevRotationKeyFramePosition)
                            / (nextRotationKeyFramePosition - prevRotationKeyFramePosition);
                    if (SLERPProgress > 1F || SLERPProgress < 0F)
                        SLERPProgress = 1F;

                    if (prevRotationKeyFramePosition == 0 && prevRotationKeyFrame == null && !(nextRotationKeyFramePosition == 0)) {
                        Quat4f currentQuat = new Quat4f();
                        currentQuat.interpolate(block.getDefaultRotationAsQuaternion(), nextRotationKeyFrame.modelRenderersRotations.get(boxName),
                                SLERPProgress);
                        Matrix4f mat = block.getRotationMatrix();
                        mat.set(currentQuat);
                        mat.transpose();
                    }
                    else if (nextRotationKeyFramePosition != 0) {
                        Quat4f currentQuat = new Quat4f();
                        currentQuat.interpolate(prevRotationKeyFrame.modelRenderersRotations.get(boxName),
                                nextRotationKeyFrame.modelRenderersRotations.get(boxName), SLERPProgress);
                        Matrix4f mat = block.getRotationMatrix();
                        mat.set(currentQuat);
                        mat.transpose();
                    }

                    // Translations
                    KeyFrame prevTranslationKeyFrame = clientChannel.getPreviousTranslationKeyFrameForBox(boxName, currentFrame);
                    int prevTranslationsKeyFramePosition = prevTranslationKeyFrame != null
                            ? clientChannel.getKeyFramePosition(prevTranslationKeyFrame) : 0;

                    KeyFrame nextTranslationKeyFrame = clientChannel.getNextTranslationKeyFrameForBox(boxName, currentFrame);
                    int nextTranslationsKeyFramePosition = nextTranslationKeyFrame != null
                            ? clientChannel.getKeyFramePosition(nextTranslationKeyFrame) : 0;

                    float LERPProgress = (currentFrame - prevTranslationsKeyFramePosition)
                            / (nextTranslationsKeyFramePosition - prevTranslationsKeyFramePosition);
                    if (LERPProgress > 1F)
                        LERPProgress = 1F;

                    if (prevTranslationsKeyFramePosition == 0 && prevTranslationKeyFrame == null && !(nextTranslationsKeyFramePosition == 0)) {
                        Vector3f startPosition = block.getPositionAsVector();
                        Vector3f endPosition = nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, LERPProgress);
                        block.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);
                    }
                    else if (nextTranslationsKeyFramePosition != 0) {
                        Vector3f startPosition = prevTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        Vector3f endPosition = nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, LERPProgress);
                        block.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);
                    }

                    // Offsets
                    KeyFrame prevOffsetKeyFrame = clientChannel.getPreviousOffsetKeyFrameForBox(boxName, currentFrame);
                    int prevOffsetKeyFramePosition = prevOffsetKeyFrame != null ? clientChannel.getKeyFramePosition(prevOffsetKeyFrame) : 0;

                    KeyFrame nextOffsetKeyFrame = clientChannel.getNextOffsetKeyFrameForBox(boxName, currentFrame);
                    int nextOffsetKeyFramePosition = nextOffsetKeyFrame != null ? clientChannel.getKeyFramePosition(nextOffsetKeyFrame) : 0;

                    float OffProgress = (currentFrame - prevOffsetKeyFramePosition) / (nextOffsetKeyFramePosition - prevOffsetKeyFramePosition);
                    if (OffProgress > 1F)
                        OffProgress = 1F;

                    if (prevOffsetKeyFramePosition == 0 && prevOffsetKeyFrame == null && !(nextOffsetKeyFramePosition == 0)) {
                        Vector3f startPosition = block.getOffsetAsVector();
                        Vector3f endPosition = nextOffsetKeyFrame.modelRenderersOffsets.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, OffProgress);
                        block.setOffset(currentPosition.x, currentPosition.y, currentPosition.z);
                    }
                    else if (nextOffsetKeyFramePosition != 0) {
                        Vector3f startPosition = prevOffsetKeyFrame.modelRenderersOffsets.get(boxName);
                        Vector3f endPosition = nextOffsetKeyFrame.modelRenderersOffsets.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, OffProgress);
                        block.setOffset(currentPosition.x, currentPosition.y, currentPosition.z);
                    }

                    // Stretch
                    KeyFrame prevStretchKeyFrame = clientChannel.getPreviousStretchKeyFrameForBox(boxName, currentFrame);
                    int prevStretchKeyFramePosition = prevStretchKeyFrame != null ? clientChannel.getKeyFramePosition(prevStretchKeyFrame) : 0;

                    KeyFrame nextStretchKeyFrame = clientChannel.getNextStretchKeyFrameForBox(boxName, currentFrame);
                    int nextStretchKeyFramePosition = nextStretchKeyFrame != null ? clientChannel.getKeyFramePosition(nextStretchKeyFrame) : 0;

                    float strProgress = (currentFrame - prevStretchKeyFramePosition) / (nextStretchKeyFramePosition - prevStretchKeyFramePosition);
                    if (strProgress > 1F)
                        strProgress = 1F;

                    if (prevStretchKeyFramePosition == 0 && prevStretchKeyFrame == null && !(nextStretchKeyFramePosition == 0)) {
                        Vector3f startPosition = block.getStretchAsVector();
                        Vector3f endPosition = nextStretchKeyFrame.modelRenderersStretchs.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, strProgress);
                        block.setStretch(currentPosition.x, currentPosition.y, currentPosition.z);
                    }
                    else if (nextStretchKeyFramePosition != 0) {
                        Vector3f startPosition = prevStretchKeyFrame.modelRenderersStretchs.get(boxName);
                        Vector3f endPosition = nextStretchKeyFrame.modelRenderersStretchs.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, strProgress);
                        block.setStretch(currentPosition.x, currentPosition.y, currentPosition.z);
                    }

                }
                else if (animInfo.getKey() instanceof CustomChannel)
                    ((CustomChannel) animInfo.getKey()).update(block, entity);
        }

    }

    /** Getters */
    public Map<String, InfoChannel> getAnimChannels() {
        return this.animChannels;
    }

    @Override
    public void removeAnimated(T animated) {
        super.removeAnimated(animated);
        this.currentAnimInfo.remove(animated);
    }
}