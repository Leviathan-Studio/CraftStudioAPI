package com.leviathanstudio.craftstudio.server.animation;

import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.Channel;
import com.leviathanstudio.craftstudio.common.animation.CustomChannel;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

/**
 * An object that hold the informations about its animated objects and all their
 * animations. It also start/stop/update the animations. This is the server side
 * AnimationHandler.
 *
 * @param <T> The class of the animated object.
 * @author Timmypote
 * @since 0.3.0
 */
@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerAnimationHandler<T extends IAnimated> extends AnimationHandler<T> {
    /**
     * Map with all the animations.
     */
    private Map<String, Channel> animChannels = new HashMap<>();

    /**
     * Map with the informations about animations.
     */
    private Map<T, Map<String, AnimInfo>> currentAnimInfo = new WeakHashMap<>();

    /**
     * Map with the initialized animations and their starting frames.
     */
    private Map<T, Map<String, Float>> startingAnimations = new WeakHashMap<>();

    @Override
    public void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped) {
        super.addAnim(modid, animNameIn, modelNameIn, looped);
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        this.animChannels.put(anim.toString(), new Channel(anim.toString(), 60.0F, looped));
    }

    @Override
    public void addAnim(String modid, String animNameIn, CustomChannel customChannelIn) {
        super.addAnim(modid, animNameIn, customChannelIn);
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        this.animChannels.put(anim.toString(), new Channel(anim.toString(), 60.0F, false));
    }

    @Override
    public void addAnim(String modid, String invertedAnimationName, String animationToInvert) {
        super.addAnim(modid, invertedAnimationName, animationToInvert);
        ResourceLocation anim = new ResourceLocation(modid, invertedAnimationName);
        ResourceLocation inverted = new ResourceLocation(modid, animationToInvert);
        boolean looped = this.animChannels.get(inverted.toString()).looped;
        this.animChannels.put(anim.toString(), new Channel(anim.toString(), 60.0F, looped));
    }

    @Override
    public boolean clientStartAnimation(String res, float startingFrame, T animatedElement) {
        return false;
    }

    @Override
    protected boolean serverInitAnimation(String res, float startingFrame, T animatedElement) {
        if (!this.animChannels.containsKey(res))
            return false;
        Map<String, Float> startingAnimMap = this.startingAnimations.get(animatedElement);
        if (startingAnimMap == null)
            this.startingAnimations.put(animatedElement, startingAnimMap = new HashMap<>());
        startingAnimMap.put(res, startingFrame);
        return true;
    }

    @Override
    protected boolean serverStartAnimation(String res, float endingFrame, T animatedElement) {
        if (!this.animChannels.containsKey(res))
            return false;
        Map<String, Float> startingAnimMap = this.startingAnimations.get(animatedElement);
        if (startingAnimMap == null)
            return false;
        if (!startingAnimMap.containsKey(res))
            return false;

        Map<String, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            this.currentAnimInfo.put(animatedElement, animInfoMap = new HashMap<>());

        Channel anim = this.animChannels.get(res);
        anim.totalFrames = (int) endingFrame;
        animInfoMap.remove(res);

        animInfoMap.put(res, new AnimInfo(System.nanoTime(), startingAnimMap.get(res)));
        startingAnimMap.remove(res);
        return true;
    }

    @Override
    public boolean clientStopAnimation(String res, T animatedElement) {
        return false;
    }

    @Override
    protected boolean serverStopAnimation(String res, T animatedElement) {
        Map<String, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return false;
        animInfoMap.remove(res);
        return true;
    }

    @Override
    public void animationsUpdate(T animatedElement) {
        Map<String, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return;

        for (Iterator<Entry<String, AnimInfo>> it = animInfoMap.entrySet().iterator(); it.hasNext(); ) {
            Entry<String, AnimInfo> animInfo = it.next();
            boolean animStatus = this.canUpdateAnimation(this.animChannels.get(animInfo.getKey()), animatedElement);
            if (!animStatus)
                it.remove();
        }
    }

    @Override
    public boolean isAnimationActive(String name, T animatedElement) {
        Map<String, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return false;

        for (Entry<String, AnimInfo> animInfo : animInfoMap.entrySet())
            if (animInfo.getKey().equals(name))
                return true;
        return false;
    }

    @Override
    public boolean isHoldAnimationActive(String name, T animatedElement) {
        return this.isAnimationActive(name, animatedElement);
    }

    @Override
    public boolean canUpdateAnimation(Channel channel, T animatedElement) {
        Map<String, AnimInfo> animInfoMap = this.currentAnimInfo.get(animatedElement);
        if (animInfoMap == null)
            return false;

        AnimInfo animInfo = animInfoMap.get(channel.name);
        if (animInfo == null)
            return false;

        long currentTime = System.nanoTime();

        double deltaTime = (currentTime - animInfo.prevTime) / 1000000000.0;
        float numberOfSkippedFrames = (float) (deltaTime * channel.fps);

        float currentFrame = animInfo.currentFrame + numberOfSkippedFrames;

        if (currentFrame < channel.totalFrames - 1) {
            animInfo.prevTime = currentTime;
            animInfo.currentFrame = currentFrame;
            return true;
        }
        if (channel.looped) {
            animInfo.prevTime = currentTime;
            animInfo.currentFrame = 0F;
            return true;
        }
        return false;
    }
}
