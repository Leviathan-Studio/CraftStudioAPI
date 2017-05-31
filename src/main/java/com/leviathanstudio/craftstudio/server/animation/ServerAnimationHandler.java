package com.leviathanstudio.craftstudio.server.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.animation.CustomChannel;
import com.leviathanstudio.craftstudio.common.animation.AnimationHandler;
import com.leviathanstudio.craftstudio.common.animation.Channel;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.common.network.EndAnimationMessage;
import com.leviathanstudio.craftstudio.common.network.FireAnimationMessage;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.SERVER)
public class ServerAnimationHandler extends AnimationHandler
{
    /** List of all the activated animations of this element. */
    public List<String>         animCurrentChannels = new ArrayList<>();
    /** Previous time of every active animation. */
    public Map<String, Long>    animPrevTime        = new HashMap<>();
    /** Current frame of every active animation. */
    public Map<String, Float>   animCurrentFrame    = new HashMap<>();
    /** Map with all the animations. */
    public Map<String, Channel> animChannels        = new HashMap<>();

    private Map<String, Float>  startingFrames      = new HashMap<>();

    public ServerAnimationHandler(IAnimated animated)
    {
        super(animated);
    }

    @Override
    public void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped)
    {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        this.animChannels.put(anim.toString(), new Channel(anim.toString(), 60.0F, looped));
    }

    @Override
    public void addAnim(String modid, String animNameIn, String modelNameIn, CustomChannel customChannelIn)
    {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        this.animChannels.put(anim.toString(), new Channel(anim.toString(), 60.0F, false));
    }

    @Override
    public void addAnim(String modid, String invertedAnimationName, String animationToInvert)
    {
        ResourceLocation anim = new ResourceLocation(modid, invertedAnimationName);
        ResourceLocation inverted = new ResourceLocation(modid, animationToInvert);
        boolean looped = this.animChannels.get(inverted.toString()).looped;
        this.animChannels.put(anim.toString(), new Channel(anim.toString(), 60.0F, looped));
    }

    @Override
    public void startAnimation(String ress, float startingFrame)
    {
        this.startingFrames.put(ress, startingFrame);
        if (!(this.animatedElement instanceof Entity))
            return;
        Entity e = (Entity) this.animatedElement;
        CraftStudioApi.NETWORK.sendToAllAround(new FireAnimationMessage(ress, this.animatedElement, startingFrame),
                new TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 100));
    }

    public void serverStartAnimation(String ress, float endingFrame)
    {
        if (this.animChannels.get(ress) != null && this.startingFrames.get(ress) != null)
        {
            Channel anim = this.animChannels.get(ress);
            anim.totalFrames = (int) endingFrame;
            int indexToRemove = this.animCurrentChannels.indexOf(ress);
            if (indexToRemove != -1)
                this.animCurrentChannels.remove(indexToRemove);

            this.animCurrentChannels.add(ress);
            this.animPrevTime.put(ress, System.nanoTime());
            this.animCurrentFrame.put(ress, this.startingFrames.get(ress));
        }
        else
            CraftStudioApi.getLogger().warn("The animation called " + ress + " doesn't exist!");
    }

    @Override
    public void stopAnimation(String res)
    {
        if (!(this.animatedElement instanceof Entity))
            return;
        Entity e = (Entity) this.animatedElement;
        CraftStudioApi.NETWORK.sendToAllAround(new EndAnimationMessage(res, this.animatedElement),
                new TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 100));
        if (this.animChannels.get(res) != null)
        {
            int indexToRemove = this.animCurrentChannels.indexOf(res);
            if (indexToRemove != -1)
            {
                this.animCurrentChannels.remove(indexToRemove);
                this.animPrevTime.remove(res);
                this.animCurrentFrame.remove(res);
            }
        }
        else
            CraftStudioApi.getLogger().warn("The animation stopped " + res + " doesn't exist!");
    }

    @Override
    public void animationsUpdate()
    {
        for (final Iterator<String> it = this.animCurrentChannels.iterator(); it.hasNext();)
        {
            final String anim = it.next();
            final float prevFrame = this.animCurrentFrame.get(anim);
            final boolean animStatus = this.canUpdateAnimation(this.animChannels.get(anim));
            if (this.animCurrentFrame.get(anim) != null)
                this.fireAnimationEvent(this.animChannels.get(anim), prevFrame, this.animCurrentFrame.get(anim));
            if (!animStatus)
            {
                it.remove();
                this.animPrevTime.remove(anim);
                this.animCurrentFrame.remove(anim);
            }
        }
    }

    @Override
    public boolean isAnimationActive(String name)
    {
        boolean animAlreadyUsed = false;
        for (String anim : this.animCurrentChannels)
            if (anim.equals(name))
            {
                animAlreadyUsed = true;
                break;
            }
        return animAlreadyUsed;
    }

    /** Update animation values. Return false if the animation should stop. */
    @Override
    public boolean canUpdateAnimation(Channel channel)
    {
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
        if (currentFrame < channel.totalFrames - 1)
        {
            this.animPrevTime.put(channel.name, currentTime);
            this.animCurrentFrame.put(channel.name, currentFrame);
            return true;
        }
        else
        {
            if (channel.looped)
            {
                this.animPrevTime.put(channel.name, currentTime);
                this.animCurrentFrame.put(channel.name, 0F);
                return true;
            }
            return false;
        }
    }

    @Override
    public void fireAnimationEvent(Channel anim, float prevFrame, float frame)
    {
    }
}
