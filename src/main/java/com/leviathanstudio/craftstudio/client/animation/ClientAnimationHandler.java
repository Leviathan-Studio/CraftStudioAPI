package com.leviathanstudio.craftstudio.client.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.client.util.math.Quaternion;
import com.leviathanstudio.craftstudio.client.util.math.Vector3f;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.Channel;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.profiler.Profiler;
import net.minecraft.util.ResourceLocation;

public class ClientAnimationHandler extends AnimationHandler
{
    /** List of all the activated animations of this element. */
    private List<ClientChannel>            animCurrentChannels = new ArrayList<>();
    /** Previous time of every active animation. */
    private Map<String, Long>              animPrevTime        = new HashMap<>();
    /** Current frame of every active animation. */
    private Map<String, Float>             animCurrentFrame    = new HashMap<>();
    /** Map with all the animations. */
    private HashMap<String, ClientChannel> animChannels        = new HashMap<>();

    public ClientAnimationHandler(IAnimated animated, Profiler profiler) {
        super(animated, profiler);
    }

    @Override
    public void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn), model = new ResourceLocation(modid, modelNameIn);
        this.profiler.startSection("putAnim");
        this.animChannels.put(anim.toString(), new CSAnimChannel(anim, model, false));
        this.profiler.endSection();
    }

    @Override
    public void addAnim(String modid, String animNameIn, String modelNameIn, CustomChannel customChannelIn) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn), model = new ResourceLocation(modid, modelNameIn);
        this.profiler.startSection("putAnim");
        this.animChannels.put(anim.toString(), customChannelIn);
        this.profiler.endSection();
    }

    @Override
    public void addAnim(String modid, String invertedAnimationName, String animationToInvert) {
        ResourceLocation anim = new ResourceLocation(modid, invertedAnimationName);
        ResourceLocation inverted = new ResourceLocation(modid, animationToInvert);
        ClientChannel channel = this.animChannels.get(inverted.toString()).getInvertedChannel(invertedAnimationName);
        channel.name = anim.toString();
        this.profiler.startSection("putAnim");
        this.animChannels.put(anim.toString(), channel);
        this.profiler.endSection();
    }

    @Override
    public void startAnimation(String animationNameIn, float startingFrame) {
        if (AnimationHandler.isWorldRemote(this.animatedElement))
            this.clientStartAnimation(animationNameIn, startingFrame);
    }

    public void clientStartAnimation(String res, float startingFrame) {
        if (this.animChannels.get(res) != null) {
            ClientChannel selectedChannel = this.animChannels.get(res);
            int indexToRemove = this.animCurrentChannels.indexOf(selectedChannel);
            if (indexToRemove != -1)
                this.animCurrentChannels.remove(indexToRemove);

            this.animCurrentChannels.add(selectedChannel);
            this.animPrevTime.put(selectedChannel.name, System.nanoTime());
            this.animCurrentFrame.put(selectedChannel.name, startingFrame);
        }
        else
            CraftStudioApi.getLogger().warn("The animation called " + res + " doesn't exist!");
    }

    @Override
    public void stopAnimation(String res) {
        if (AnimationHandler.isWorldRemote(this.animatedElement))
            this.clientStopAnimation(res);
    }

    public void clientStopAnimation(String res) {
        ClientChannel selectedChannel = this.animChannels.get(res);
        if (selectedChannel != null) {
            int indexToRemove = this.animCurrentChannels.indexOf(selectedChannel);
            if (indexToRemove != -1) {
                this.animCurrentChannels.remove(indexToRemove);
                this.animPrevTime.remove(res);
                this.animCurrentFrame.remove(res);
            }
        }
        else
            CraftStudioApi.getLogger().warn("The animation stopped " + res + " doesn't exist!");
    }

    /**
     * Update the animation
     */
    @Override
    public void animationsUpdate() {

        for (Iterator<ClientChannel> it = this.animCurrentChannels.iterator(); it.hasNext();) {
            ClientChannel anim = it.next();
            float prevFrame = this.animCurrentFrame.get(anim.name);
            boolean canUpdate = this.canUpdateAnimation(anim);
            if (this.animCurrentFrame.get(anim.name) != null)
                this.fireAnimationEvent(anim, prevFrame, this.animCurrentFrame.get(anim.name));
            if (!canUpdate) {
                it.remove();
                this.animPrevTime.remove(anim.name);
                this.animCurrentFrame.remove(anim.name);
            }
        }
    }

    /**
     * Check if animation is active
     */
    @Override
    public boolean isAnimationActive(String name) {
        boolean animAlreadyUsed = false;
        for (ClientChannel anim : this.animCurrentChannels)
            if (anim.name != null)
                if (anim.name.equals(name) && this.animCurrentFrame.get(anim.name) < anim.totalFrames - 1) {
                    animAlreadyUsed = true;
                    break;
                }
        return animAlreadyUsed;
    }

    /**
     * Check if hold animation is active
     */
<<<<<<< Updated upstream
    public boolean isHoldAnimationActive(String name)
    {
=======
    public boolean isLinearAnimationActive(String name) {
>>>>>>> Stashed changes
        boolean animAlreadyUsed = false;
        for (ClientChannel anim : this.animCurrentChannels)
            if (anim.name != null)
                if (anim.name.equals(name)) {
                    animAlreadyUsed = true;
                    break;
                }
        return animAlreadyUsed;
    }

    /**
     * Check if animation can be updated
     */
    @Override
    public boolean canUpdateAnimation(Channel channel) {
        long currentTime = System.nanoTime();
        if (!(channel instanceof ClientChannel))
            return false;
        ClientChannel clientChannel = (ClientChannel) channel;
<<<<<<< Updated upstream
        if (!ClientAnimationHandler.isGamePaused())
        {
            if (!(clientChannel.getAnimationMode() == EnumAnimationMode.CUSTOM))
            {
=======
        if (!ClientAnimationHandler.isGamePaused()) {
            if (!(clientChannel.getAnimationMode() == ClientChannel.EnumAnimationMode.CUSTOM)) {
>>>>>>> Stashed changes
                long prevTime = this.animPrevTime.get(channel.name);
                float prevFrame = this.animCurrentFrame.get(channel.name);

                double deltaTime = (currentTime - prevTime) / 1000000000.0;
                float numberOfSkippedFrames = (float) (deltaTime * channel.fps);

                float currentFrame = prevFrame + numberOfSkippedFrames;

                /*
                 * -1 as the first frame mustn't be "executed" as it is the
                 * starting situation
                 */
                if (currentFrame < channel.totalFrames - 1) {
                    this.animPrevTime.put(channel.name, currentTime);
                    this.animCurrentFrame.put(channel.name, currentFrame);
                    return true;
                }
<<<<<<< Updated upstream
                else
                {
                    if (clientChannel.getAnimationMode() == EnumAnimationMode.LOOP)
                    {
=======
                else {
                    if (clientChannel.getAnimationMode() == ClientChannel.EnumAnimationMode.LOOP) {
>>>>>>> Stashed changes
                        this.animPrevTime.put(channel.name, currentTime);
                        this.animCurrentFrame.put(channel.name, 0F);
                        return true;
                    }
<<<<<<< Updated upstream
                    else if (clientChannel.getAnimationMode() == EnumAnimationMode.HOLD)
                    {
=======
                    else if (clientChannel.getAnimationMode() == ClientChannel.EnumAnimationMode.LINEAR) {
>>>>>>> Stashed changes
                        this.animPrevTime.put(channel.name, currentTime);
                        this.animCurrentFrame.put(channel.name, (float) channel.totalFrames - 1);
                        return true;
                    }
                    return false;
                }
            }
            else
                return true;
        }
        else {
            this.animPrevTime.put(channel.name, currentTime);
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

            boolean anyRotationApplied = false;
            boolean anyTranslationApplied = false;
            boolean anyCustomAnimationRunning = false;

            Vector3f defaultPos = new Vector3f(block.getDefaultRotationPointX(), block.getDefaultRotationPointY(), block.getDefaultRotationPointZ());
            block.resetRotationPoint();
            block.resetRotationMatrix();

            for (ClientChannel channel : animHandler.animCurrentChannels)
<<<<<<< Updated upstream
                if (channel.getAnimationMode() != EnumAnimationMode.CUSTOM)
                {
=======
                if (channel.getAnimationMode() != ClientChannel.EnumAnimationMode.CUSTOM) {
>>>>>>> Stashed changes
                    float currentFrame = animHandler.animCurrentFrame.get(channel.name);

                    // Rotations
                    KeyFrame prevRotationKeyFrame = channel.getPreviousRotationKeyFrameForBox(boxName,
                            animHandler.animCurrentFrame.get(channel.name));
                    int prevRotationKeyFramePosition = prevRotationKeyFrame != null ? channel.getKeyFramePosition(prevRotationKeyFrame) : 0;

                    KeyFrame nextRotationKeyFrame = channel.getNextRotationKeyFrameForBox(boxName, animHandler.animCurrentFrame.get(channel.name));
                    int nextRotationKeyFramePosition = nextRotationKeyFrame != null ? channel.getKeyFramePosition(nextRotationKeyFrame) : 0;

                    float SLERPProgress = (currentFrame - prevRotationKeyFramePosition)
                            / (nextRotationKeyFramePosition - prevRotationKeyFramePosition);
                    if (SLERPProgress > 1F || SLERPProgress < 0F)
                        SLERPProgress = 1F;

                    if (prevRotationKeyFramePosition == 0 && prevRotationKeyFrame == null && !(nextRotationKeyFramePosition == 0)) {
                        Quaternion currentQuat = new Quaternion();
                        currentQuat.slerp(block.getDefaultRotationAsQuaternion(), nextRotationKeyFrame.modelRenderersRotations.get(boxName),
                                SLERPProgress);
                        block.getRotationMatrix().set(currentQuat).transpose();
                        anyRotationApplied = true;
                    }
                    else if (prevRotationKeyFramePosition == 0 && prevRotationKeyFrame != null && !(nextRotationKeyFramePosition == 0)) {
                        Quaternion currentQuat = new Quaternion();
                        currentQuat.slerp(prevRotationKeyFrame.modelRenderersRotations.get(boxName),
                                nextRotationKeyFrame.modelRenderersRotations.get(boxName), SLERPProgress);
                        block.getRotationMatrix().set(currentQuat).transpose();
                        anyRotationApplied = true;
                    }
                    else if (!(prevRotationKeyFramePosition == 0) && !(nextRotationKeyFramePosition == 0)) {
                        Quaternion currentQuat = new Quaternion();
                        currentQuat.slerp(prevRotationKeyFrame.modelRenderersRotations.get(boxName),
                                nextRotationKeyFrame.modelRenderersRotations.get(boxName), SLERPProgress);
                        block.getRotationMatrix().set(currentQuat).transpose();
                        anyRotationApplied = true;
                    }

                    // Translations
                    KeyFrame prevTranslationKeyFrame = channel.getPreviousTranslationKeyFrameForBox(boxName,
                            animHandler.animCurrentFrame.get(channel.name));
                    int prevTranslationsKeyFramePosition = prevTranslationKeyFrame != null ? channel.getKeyFramePosition(prevTranslationKeyFrame) : 0;

                    KeyFrame nextTranslationKeyFrame = channel.getNextTranslationKeyFrameForBox(boxName,
                            animHandler.animCurrentFrame.get(channel.name));
                    int nextTranslationsKeyFramePosition = nextTranslationKeyFrame != null ? channel.getKeyFramePosition(nextTranslationKeyFrame) : 0;

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
                        anyTranslationApplied = true;
                    }
                    else if (prevTranslationsKeyFramePosition == 0 && prevTranslationKeyFrame != null && !(nextTranslationsKeyFramePosition == 0)) {
                        Vector3f startPosition = prevTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        Vector3f endPosition = nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, LERPProgress);
                        block.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);
                        anyTranslationApplied = true;
                    }
                    else if (!(prevTranslationsKeyFramePosition == 0) && !(nextTranslationsKeyFramePosition == 0)) {
                        Vector3f startPosition = prevTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        Vector3f endPosition = nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                        Vector3f currentPosition = new Vector3f(startPosition);
                        currentPosition.interpolate(endPosition, LERPProgress);
                        block.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);
                        anyTranslationApplied = true;
                    }
                }
                else {
                    anyCustomAnimationRunning = true;
                    ((CustomChannel) channel).update(block, entity);
                }

            if (!anyRotationApplied && !anyCustomAnimationRunning)
                block.resetRotationMatrix();
            if (!anyTranslationApplied && !anyCustomAnimationRunning)
                block.resetRotationPoint();
        }

    }

    @Override
    public void fireAnimationEvent(Channel anim, float prevFrame, float frame) {}

    /** Getters */
    public List<ClientChannel> getAnimCurrentChannels() {
        return this.animCurrentChannels;
    }

    /** Getters */
    public Map<String, Long> getAnimPrevTime() {
        return this.animPrevTime;
    }

    /** Getters */
    public Map<String, Float> getAnimCurrentFrame() {
        return this.animCurrentFrame;
    }

    /** Getters */
    public HashMap<String, ClientChannel> getAnimChannels() {
        return this.animChannels;
    }

}
