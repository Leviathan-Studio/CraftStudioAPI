package com.leviathanstudio.craftstudio.common.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.animation.CustomChannel;

import net.minecraft.util.ResourceLocation;

public class AnimationHandler
{
    public static AnimTickHandler   animTickHandler;
    /** Owner of this handler. */
    protected final IAnimated       animatedElement;
    /** List of all the activated animations of this element. */
    public List<String>             animCurrentChannels = new ArrayList<>();
    /** Previous time of every active animation. */
    public Map<String, Long>        animPrevTime        = new HashMap<>();
    /** Current frame of every active animation. */
    public Map<String, Float>       animCurrentFrame    = new HashMap<>();
    /** Map with all the animations. */
    public HashMap<String, Channel> animChannels        = new HashMap<>();

    public AnimationHandler(IAnimated animated) {
        if (animTickHandler == null)
            animTickHandler = new AnimTickHandler();
        animTickHandler.addAnimated(animated);
        this.animatedElement = animated;
    }

    public void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        this.animChannels.put(anim.toString(), new Channel(anim.toString(), 60.0F, looped));
    }

    public void addAnim(String modid, String animNameIn, String modelNameIn, CustomChannel customChannelIn) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        this.animChannels.put(anim.toString(), new Channel(anim.toString(), 60.0F, false));
    }

    public void addAnim(String modid, String animNameIn, String invertedChannelName) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        boolean looped = this.animChannels.get(invertedChannelName).looped;
        this.animChannels.put(anim.toString(), new Channel(anim.toString(), 60.0F, looped));
    }

    protected IAnimated getAnimated() {
        return this.animatedElement;
    }

    public void startAnimation(String modid, String name, float startingFrame) {
        String selectedChannel = modid + ":" + name;
        if (this.animChannels.get(selectedChannel) != null) {
            int indexToRemove = this.animCurrentChannels.indexOf(selectedChannel);
            if (indexToRemove != -1)
                this.animCurrentChannels.remove(indexToRemove);

            this.animCurrentChannels.add(selectedChannel);
            this.animPrevTime.put(selectedChannel, System.nanoTime());
            this.animCurrentFrame.put(selectedChannel, startingFrame);
        }
        else
            CraftStudioApi.getLogger().warn("The animation called " + name + "from " + modid + " doesn't exist!");
    }

    public void startAnimation(String modid, String name) {
        this.startAnimation(modid, name, 0.0F);
    }

    public void stopAnimation(String modid, String name) {
        String selectedChannel = modid + ":" + name;
        if (this.animChannels.get(selectedChannel) != null) {
            int indexToRemove = this.animCurrentChannels.indexOf(selectedChannel);
            if (indexToRemove != -1) {
                this.animCurrentChannels.remove(indexToRemove);
                this.animPrevTime.remove(selectedChannel);
                this.animCurrentFrame.remove(selectedChannel);
            }
        }
        else
            CraftStudioApi.getLogger().warn("The animation stopped " + name + "from " + modid + " doesn't exist!");
    }

    public void animationsUpdate() {
        for (final Iterator<String> it = this.animCurrentChannels.iterator(); it.hasNext();) {
            final String anim = it.next();
            final float prevFrame = this.animCurrentFrame.get(anim);
            final boolean animStatus = updateAnimation(this.animChannels.get(anim));
            if (this.animCurrentFrame.get(anim) != null)
                this.fireAnimationEvent(this.animChannels.get(anim), prevFrame, this.animCurrentFrame.get(anim));
            if (!animStatus) {
                it.remove();
                this.animPrevTime.remove(anim);
                this.animCurrentFrame.remove(anim);
            }
        }
    }

    public boolean isAnimationActive(String modid, String name) {
        return this.isAnimationActive(modid + ":" + name);
    }

    public boolean isAnimationActive(String name) {
        boolean animAlreadyUsed = false;
        for (String anim : this.animCurrentChannels)
            if (anim.equals(name)) {
                animAlreadyUsed = true;
                break;
            }
        return animAlreadyUsed;
    }

    /** Update animation values. Return false if the animation should stop. */
    public boolean updateAnimation(Channel channel) {
        long currentTime = System.nanoTime();
        long prevTime = this.animPrevTime.get(channel.name);
        float prevFrame = this.animCurrentFrame.get(channel.name);

        double deltaTime = (currentTime - prevTime) / 1000000000.0;
        float numberOfSkippedFrames = (float) (deltaTime * channel.fps);

        float currentFrame = prevFrame + numberOfSkippedFrames;

        /*
         * -1 as the first frame mustn't be "executed" as it is the starting
         * situation
         */
        if (currentFrame < channel.totalFrames - 1) {
            this.animPrevTime.put(channel.name, currentTime);
            this.animCurrentFrame.put(channel.name, currentFrame);
            return true;
        }
        else {
            if (channel.looped) {
                this.animPrevTime.put(channel.name, currentTime);
                this.animCurrentFrame.put(channel.name, 0F);
                return true;
            }
            return false;
        }
    }

    public void fireAnimationEvent(Channel anim, float prevFrame, float frame) {}
}
