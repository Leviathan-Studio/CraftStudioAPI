package com.leviathanstudio.craftstudio.client.animation;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.common.animation.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3f;
import java.util.*;
import java.util.Map.Entry;

/**
 * An object that hold the informations about its animated objects and all their
 * animations. It also start/stop/update the animations and render the models.
 * This is the client side AnimationHandler.
 *
 * @param <T> The class of the animated object.
 * @author Timmypote
 * @author ZeAmateis
 * @since 0.3.0
 */
@OnlyIn(Dist.CLIENT)
public class ClientAnimationHandler<T extends IAnimated> extends AnimationHandler<T> {
    /**
     * Map with all the animations.
     */
    private Map<String, InfoChannel> animChannels = new HashMap<>();

    /**
     * Map with the info about the animations.
     **/
    private Map<T, Map<InfoChannel, AnimInfo>> currentAnimInfo = new WeakHashMap<>();

    /**
     * Check if game is paused, on the exit screen.
     *
     * @return true, if the game is paused.
     */
    public static boolean isGamePaused() {
        Minecraft MC = Minecraft.getInstance();
        return MC.isSingleplayer() && MC.currentScreen != null && MC.currentScreen.isPauseScreen() && !MC.getIntegratedServer().getPublic();
    }

    /**
     * Apply animations if running or apply initial values. Should be only
     * called by the model class.
     *
     * @param parts    The list of block to update.
     * @param animated The object that is animated.
     */
    public static void performAnimationInModel(List<CSModelRenderer> parts, IAnimated animated) {
        for (CSModelRenderer entry : parts)
            performAnimationForBlock(entry, animated);
    }

    /**
     * Apply animations for model block.
     *
     * @param block    The block to update.
     * @param animated The object that is animated.
     */
    public static void performAnimationForBlock(CSModelRenderer block, IAnimated animated) {
        String boxName = block.boxName;
        RendererModel child;

        if (animated.getAnimationHandler() instanceof ClientAnimationHandler) {
            ClientAnimationHandler animHandler = (ClientAnimationHandler) animated.getAnimationHandler();

            if (block.childModels != null)
                for (int i = 0; i < block.childModels.size(); i++) {
                    child = block.childModels.get(i);
                    if (child instanceof CSModelRenderer) {
                        CSModelRenderer childModel = (CSModelRenderer) child;
                        performAnimationForBlock(childModel, animated);
                    }
                }

            block.resetRotationPoint();
            block.resetRotationMatrix();
            block.resetOffset();
            block.resetStretch();

            Map<InfoChannel, AnimInfo> animInfoMap = (Map<InfoChannel, AnimInfo>) animHandler.currentAnimInfo.get(animated);
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
                    } else if (nextRotationKeyFramePosition != 0) {
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
                    if (LERPProgress > 1F || LERPProgress < 0F)
                        LERPProgress = 1F;

                    if (prevTranslationsKeyFramePosition == 0 && prevTranslationKeyFrame == null && !(nextTranslationsKeyFramePosition == 0)) {
                        Vector3f startPosition = block.getPositionAsVector();
                        Vector3f endPosition = nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, LERPProgress);
                        block.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);
                    } else if (nextTranslationsKeyFramePosition != 0) {
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
                    if (OffProgress > 1F || OffProgress < 0F)
                        OffProgress = 1F;

                    if (prevOffsetKeyFramePosition == 0 && prevOffsetKeyFrame == null && !(nextOffsetKeyFramePosition == 0)) {
                        Vector3f startPosition = block.getOffsetAsVector();
                        Vector3f endPosition = nextOffsetKeyFrame.modelRenderersOffsets.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, OffProgress);
                        block.setOffset(currentPosition.x, currentPosition.y, currentPosition.z);
                    } else if (nextOffsetKeyFramePosition != 0) {
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
                    if (strProgress > 1F || strProgress < 0F)
                        strProgress = 1F;

                    if (prevStretchKeyFramePosition == 0 && prevStretchKeyFrame == null && !(nextStretchKeyFramePosition == 0)) {
                        Vector3f startPosition = block.getStretchAsVector();
                        Vector3f endPosition = nextStretchKeyFrame.modelRenderersStretchs.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, strProgress);
                        block.setStretch(currentPosition.x, currentPosition.y, currentPosition.z);
                    } else if (nextStretchKeyFramePosition != 0) {
                        Vector3f startPosition = prevStretchKeyFrame.modelRenderersStretchs.get(boxName);
                        Vector3f endPosition = nextStretchKeyFrame.modelRenderersStretchs.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, strProgress);
                        block.setStretch(currentPosition.x, currentPosition.y, currentPosition.z);
                    }

                } else if (animInfo.getKey() instanceof CustomChannel)
                    ((CustomChannel) animInfo.getKey()).update(block, animated);
        }

    }

    @Override
    public void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped) {
        super.addAnim(modid, animNameIn, modelNameIn, looped);
        ResourceLocation anim = new ResourceLocation(modid, animNameIn), model = new ResourceLocation(modid, modelNameIn);
        this.animChannels.put(anim.toString(), new CSAnimChannel(anim, model, looped));
    }

    @Override
    public void addAnim(String modid, String animNameIn, CustomChannel customChannelIn) {
        super.addAnim(modid, animNameIn, customChannelIn);
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        this.animChannels.put(anim.toString(), customChannelIn);
    }

    @Override
    public void addAnim(String modid, String invertedAnimationName, String animationToInvert) {
        super.addAnim(modid, invertedAnimationName, animationToInvert);
        ResourceLocation anim = new ResourceLocation(modid, invertedAnimationName);
        ResourceLocation toInvert = new ResourceLocation(modid, animationToInvert);
        if (this.animChannels.get(toInvert.toString()) instanceof ClientChannel) {
            ClientChannel channel = ((ClientChannel) this.animChannels.get(toInvert.toString())).getInvertedChannel(invertedAnimationName);
            channel.name = anim.toString();
            this.animChannels.put(anim.toString(), channel);
        }
    }

    @Override
    public boolean clientStartAnimation(String res, float startingFrame, T animatedElement) {
        if (this.animChannels.get(res) == null) {
            CraftStudioApi.getLogger().warn("The animation called " + res + " doesn't exist!");
            return false;
        }
        Map<InfoChannel, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            this.currentAnimInfo.put(animatedElement, animInfoMap = new HashMap<>());

        InfoChannel selectedChannel = this.animChannels.get(res);
        animInfoMap.remove(selectedChannel);

        animInfoMap.put(selectedChannel, new AnimInfo(System.nanoTime(), startingFrame));
        return true;
    }

    @Override
    protected boolean serverInitAnimation(String res, float startingFrame, T animatedElement) {
        if (!this.animChannels.containsKey(res))
            return false;
        return true;
    }

    @Override
    protected boolean serverStartAnimation(String res, float endingFrame, T animatedElement) {
        if (!this.animChannels.containsKey(res))
            return false;
        return true;
    }

    @Override
    public boolean clientStopAnimation(String res, T animatedElement) {
        if (!this.animChannels.containsKey(res)) {
            CraftStudioApi.getLogger().warn("The animation stopped " + res + " doesn't exist!");
            return false;
        }

        Map<InfoChannel, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return false;

        InfoChannel selectedChannel = this.animChannels.get(res);
        animInfoMap.remove(selectedChannel);
        if (animInfoMap.isEmpty())
            this.currentAnimInfo.remove(animatedElement);
        return true;
    }

    @Override
    protected boolean serverStopAnimation(String res, T animatedElement) {
        return this.currentAnimInfo.containsKey(animatedElement);
    }

    @Override
    public void animationsUpdate(T animatedElement) {
        Map<InfoChannel, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return;

        for (Iterator<Entry<InfoChannel, AnimInfo>> it = animInfoMap.entrySet().iterator(); it.hasNext(); ) {
            Entry<InfoChannel, AnimInfo> animInfo = it.next();
            animInfo.getValue();
            boolean canUpdate = this.canUpdateAnimation(animInfo.getKey(), animatedElement);
            if (!canUpdate)
                it.remove();
        }
    }

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

    @Override
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
            } else
                return true;
        } else {
            animInfo.prevTime = currentTime;
            return true;
        }

    }

    /**
     * Getter of currentAnimInfo.
     *
     * @return the currentAnimInfo.
     */
    public Map<T, Map<InfoChannel, AnimInfo>> getCurrentAnimInfo() {
        return this.currentAnimInfo;
    }

    /**
     * Setter of currentAnimInfo.
     *
     * @param currentAnimInfo the currentAnimInfo to set.
     */
    public void setCurrentAnimInfo(Map<T, Map<InfoChannel, AnimInfo>> currentAnimInfo) {
        this.currentAnimInfo = currentAnimInfo;
    }

    /**
     * Getter of animChannels.
     *
     * @return the animChannels.
     */
    public Map<String, InfoChannel> getAnimChannels() {
        return this.animChannels;
    }

    /**
     * Setter of animChannels.
     *
     * @param animChannels the animChannels to set.
     */
    public void setAnimChannels(Map<String, InfoChannel> animChannels) {
        this.animChannels = animChannels;
    }
}