package com.leviathanstudio.craftstudio.common.animation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.leviathanstudio.craftstudio.CraftStudioApi;
import com.leviathanstudio.craftstudio.client.CSModelRenderer;
import com.leviathanstudio.craftstudio.common.IAnimated;
import com.leviathanstudio.craftstudio.util.math.Quaternion;
import com.leviathanstudio.craftstudio.util.math.Vector3f;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AnimationHandler
{
    public static AnimTickHandler                    animTickHandler;
    /** Owner of this handler. */
    private final IAnimated                          animatedElement;
    /** List of all the activated animations of this element. */
    public List<Channel>                             animCurrentChannels = new ArrayList<>();
    /** Previous time of every active animation. */
    public Map<String, Long>                         animPrevTime        = new HashMap<>();
    /** Current frame of every active animation. */
    public Map<String, Float>                        animCurrentFrame    = new HashMap<>();
    /**
     * Contains the unique names of the events that have been already fired
     * during each animation. It becomes empty at the end of every animation.
     * The key is the animation name and the value is the list of already-called
     * events.
     */
    private final HashMap<String, ArrayList<String>> animationEvents     = new HashMap<>();

    /** Map with all the animations. */
    public static HashMap<String, Channel>           animChannels        = new HashMap<>();

    public AnimationHandler(IAnimated animated) {
        if (AnimationHandler.animTickHandler == null)
            AnimationHandler.animTickHandler = new AnimTickHandler();
        AnimationHandler.animTickHandler.addAnimated(animated);

        this.animatedElement = animated;
    }

    public static void addAnim(String modid, String animNameIn, String modelNameIn, boolean looped) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn), model = new ResourceLocation(modid, modelNameIn);
        AnimationHandler.animChannels.put(anim.toString(), new CSAnimChannel(anim, model, false));
    }

    public static void addAnim(String modid, String animNameIn, String modelNameIn, CustomChannel customChannelIn) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn), model = new ResourceLocation(modid, modelNameIn);
        AnimationHandler.animChannels.put(anim.toString(), customChannelIn);
    }

    public static void addAnim(String modid, String animNameIn, String invertedChannelName) {
        ResourceLocation anim = new ResourceLocation(modid, animNameIn);
        Channel channel = AnimationHandler.animChannels.get(invertedChannelName).getInvertedChannel(animNameIn);
        channel.name = anim.toString();
        AnimationHandler.animChannels.put(anim.toString(), channel);
    }

    private IAnimated getAnimated() {
        return this.animatedElement;
    }

    public boolean isAnimationActive(String modid, String name) {
        return this.isAnimationActive(modid + ":" + name);
    }

    public void startAnimation(HashMap<String, Channel> animChannels, String modid, String name, float startingFrame) {
        if (animChannels.get(modid + ":" + name) != null) {
            final Channel selectedChannel = animChannels.get(modid + ":" + name);
            final int indexToRemove = this.animCurrentChannels.indexOf(selectedChannel);
            if (indexToRemove != -1)
                this.animCurrentChannels.remove(indexToRemove);

            this.animCurrentChannels.add(selectedChannel);
            this.animPrevTime.put(selectedChannel.name, System.nanoTime());
            this.animCurrentFrame.put(selectedChannel.name, startingFrame);
            if (this.animationEvents.get(selectedChannel.name) == null)
                this.animationEvents.put(selectedChannel.name, new ArrayList<String>());
        }
        else
            CraftStudioApi.getLogger().warn("The animation called " + name + "from " + modid + " doesn't exist!");
    }

    public abstract void startAnimationAt(String modid, String name, float startingFrame);

    public abstract void startAnimation(String modid, String name);

    public void stopAnimation(HashMap<String, Channel> animChannels, String modid, String name) {
        final Channel selectedChannel = animChannels.get(modid + ":" + name);
        if (selectedChannel != null) {
            final int indexToRemove = this.animCurrentChannels.indexOf(selectedChannel);
            if (indexToRemove != -1) {
                this.animCurrentChannels.remove(indexToRemove);
                this.animPrevTime.remove(modid + ":" + name);
                this.animCurrentFrame.remove(modid + ":" + name);
                this.animationEvents.get(modid + ":" + name).clear();
            }
        }
        else
            CraftStudioApi.getLogger().warn("The animation called " + name + "from " + modid + " doesn't exist!");
    }

    public abstract void stopAnimation(String modid, String name);

    public void animationsUpdate() {
        for (final Iterator<Channel> it = this.animCurrentChannels.iterator(); it.hasNext();) {
            final Channel anim = it.next();
            final float prevFrame = this.animCurrentFrame.get(anim.name);
            final boolean animStatus = AnimationHandler.updateAnimation(this.animatedElement, anim, this.animPrevTime, this.animCurrentFrame);
            if (this.animCurrentFrame.get(anim.name) != null)
                this.fireAnimationEvent(anim, prevFrame, this.animCurrentFrame.get(anim.name));
            if (!animStatus) {
                it.remove();
                this.animPrevTime.remove(anim.name);
                this.animCurrentFrame.remove(anim.name);
                this.animationEvents.get(anim.name).clear();
            }
        }
    }

    public boolean isAnimationActive(String name) {
        boolean animAlreadyUsed = false;
        for (final Channel anim : this.animatedElement.getAnimationHandler().animCurrentChannels)
            if (anim.name != null) {
                if (anim.name.equals(name)) {
                    animAlreadyUsed = true;
                    break;
                }
            }
            else {
                CraftStudioApi.getLogger().catching(new Exception(String.format("Animation %s doesn't exist !", name)));
                break;
            }

        return animAlreadyUsed;
    }

    private void fireAnimationEvent(Channel anim, float prevFrame, float frame) {
        if (AnimationHandler.isWorldRemote(this.animatedElement))
            this.fireAnimationEventClientSide(anim, prevFrame, frame);
        else
            this.fireAnimationEventServerSide(anim, prevFrame, frame);
    }

    @SideOnly(Side.CLIENT)
    public abstract void fireAnimationEventClientSide(Channel anim, float prevFrame, float frame);

    public abstract void fireAnimationEventServerSide(Channel anim, float prevFrame, float frame);

    /** Check if the animation event has already been called. */
    public boolean alreadyCalledEvent(String animName, String eventName) {
        if (this.animationEvents.get(animName) == null) {
            CraftStudioApi.getLogger().warn("Cannot check for event " + eventName + "!\nAnimation " + animName + "does not exist or is not active.");
            return true;
        }
        return this.animationEvents.get(animName).contains(eventName);
    }

    /** Set the animation event as "called", so it won't be fired again. */
    public void setCalledEvent(String animName, String eventName) {
        if (this.animationEvents.get(animName) != null)
            this.animationEvents.get(animName).add(eventName);
        else
            CraftStudioApi.getLogger().warn("Cannot set event " + eventName + "!\nAnimation " + animName + "does not exist or is not active.");
    }

    /** Update animation values. Return false if the animation should stop. */
    public static boolean updateAnimation(IAnimated entity, Channel channel, Map<String, Long> prevTimeAnim, Map<String, Float> prevFrameAnim) {
        if (FMLCommonHandler.instance().getEffectiveSide().isServer()
                || FMLCommonHandler.instance().getEffectiveSide().isClient() && !AnimationHandler.isGamePaused()) {
            if (!(channel.animationMode == Channel.EnumAnimationMode.CUSTOM)) {
                final long prevTime = prevTimeAnim.get(channel.name);
                final float prevFrame = prevFrameAnim.get(channel.name);

                final long currentTime = System.nanoTime();
                final double deltaTime = (currentTime - prevTime) / 1000000000.0;
                final float numberOfSkippedFrames = (float) (deltaTime * channel.fps);

                final float currentFrame = prevFrame + numberOfSkippedFrames;

                /*
                 * -1 as the first frame mustn't be "executed" as it is the
                 * starting situation
                 */
                if (currentFrame < channel.totalFrames - 1) {
                    prevTimeAnim.put(channel.name, currentTime);
                    prevFrameAnim.put(channel.name, currentFrame);
                    return true;
                }
                else {
                    if (channel.animationMode == Channel.EnumAnimationMode.LOOP) {
                        prevTimeAnim.put(channel.name, currentTime);
                        prevFrameAnim.put(channel.name, 0F);
                        return true;
                    }
                    return false;
                }
            }
            else
                return true;
        }
        else

        {
            final long currentTime = System.nanoTime();
            prevTimeAnim.put(channel.name, currentTime);
            return true;
        }
    }

    @SideOnly(Side.CLIENT)
    private static boolean isGamePaused() {
        final net.minecraft.client.Minecraft MC = net.minecraft.client.Minecraft.getMinecraft();
        return MC.isSingleplayer() && MC.currentScreen != null && MC.currentScreen.doesGuiPauseGame() && !MC.getIntegratedServer().getPublic();
    }

    /**
     * Apply animations if running or apply initial values. Must be called only
     * by the model class.
     */
    @SideOnly(Side.CLIENT)
    public static void performAnimationInModel(List<CSModelRenderer> parts, IAnimated entity) {
        for (final CSModelRenderer entry : parts)
            performAnimationForBlock(entry, entity);
    }

    @SideOnly(Side.CLIENT)
    public static void performAnimationForBlock(CSModelRenderer block, IAnimated entity) {
        final CSModelRenderer box = block;
        final String boxName = box.boxName;

        boolean anyRotationApplied = false;
        boolean anyTranslationApplied = false;
        boolean anyCustomAnimationRunning = false;

        if (block.childModels != null)
            for (ModelRenderer child : block.childModels)
                if (child instanceof CSModelRenderer) {
                    CSModelRenderer childModel = (CSModelRenderer) child;
                    performAnimationForBlock(childModel, entity);
                }

        for (final Channel channel : entity.getAnimationHandler().animCurrentChannels)
            if (channel.animationMode != Channel.EnumAnimationMode.CUSTOM) {
                final float currentFrame = entity.getAnimationHandler().animCurrentFrame.get(channel.name);

                // Rotations
                final KeyFrame prevRotationKeyFrame = channel.getPreviousRotationKeyFrameForBox(boxName,
                        entity.getAnimationHandler().animCurrentFrame.get(channel.name));
                final int prevRotationKeyFramePosition = prevRotationKeyFrame != null ? channel.getKeyFramePosition(prevRotationKeyFrame) : 0;

                final KeyFrame nextRotationKeyFrame = channel.getNextRotationKeyFrameForBox(boxName,
                        entity.getAnimationHandler().animCurrentFrame.get(channel.name));
                final int nextRotationKeyFramePosition = nextRotationKeyFrame != null ? channel.getKeyFramePosition(nextRotationKeyFrame) : 0;

                // Quaternion defaultQuat =
                // block.getDefaultRotationAsQuaternion();
                // defaultQuat.x = -defaultQuat.x;

                float SLERPProgress = (currentFrame - prevRotationKeyFramePosition) / (nextRotationKeyFramePosition - prevRotationKeyFramePosition);
                if (SLERPProgress > 1F || SLERPProgress < 0F)
                    SLERPProgress = 1F;

                if (prevRotationKeyFramePosition == 0 && prevRotationKeyFrame == null && !(nextRotationKeyFramePosition == 0)) {
                    final Quaternion currentQuat = new Quaternion();
                    currentQuat.slerp(box.getDefaultRotationAsQuaternion(), nextRotationKeyFrame.modelRenderersRotations.get(boxName), SLERPProgress);
                    // currentQuat.mul(defaultQuat);
                    box.getRotationMatrix().set(currentQuat).transpose();

                    anyRotationApplied = true;
                }
                else if (prevRotationKeyFramePosition == 0 && prevRotationKeyFrame != null && !(nextRotationKeyFramePosition == 0)) {
                    final Quaternion currentQuat = new Quaternion();
                    currentQuat.slerp(prevRotationKeyFrame.modelRenderersRotations.get(boxName),
                            nextRotationKeyFrame.modelRenderersRotations.get(boxName), SLERPProgress);
                    // currentQuat.mul(defaultQuat);

                    box.getRotationMatrix().set(currentQuat).transpose();

                    anyRotationApplied = true;
                }
                else if (!(prevRotationKeyFramePosition == 0) && !(nextRotationKeyFramePosition == 0)) {
                    final Quaternion currentQuat = new Quaternion();
                    currentQuat.slerp(prevRotationKeyFrame.modelRenderersRotations.get(boxName),
                            nextRotationKeyFrame.modelRenderersRotations.get(boxName), SLERPProgress);
                    // currentQuat.mul(defaultQuat);
                    box.getRotationMatrix().set(currentQuat).transpose();

                    anyRotationApplied = true;
                }

                // Translations
                final KeyFrame prevTranslationKeyFrame = channel.getPreviousTranslationKeyFrameForBox(boxName,
                        entity.getAnimationHandler().animCurrentFrame.get(channel.name));
                final int prevTranslationsKeyFramePosition = prevTranslationKeyFrame != null ? channel.getKeyFramePosition(prevTranslationKeyFrame)
                        : 0;

                final KeyFrame nextTranslationKeyFrame = channel.getNextTranslationKeyFrameForBox(boxName,
                        entity.getAnimationHandler().animCurrentFrame.get(channel.name));
                final int nextTranslationsKeyFramePosition = nextTranslationKeyFrame != null ? channel.getKeyFramePosition(nextTranslationKeyFrame)
                        : 0;

                final Vector3f defaultPos = new Vector3f(box.getDefaultRotationPointX(), box.getDefaultRotationPointY(),
                        box.getDefaultRotationPointZ());

                float LERPProgress = (currentFrame - prevTranslationsKeyFramePosition)
                        / (nextTranslationsKeyFramePosition - prevTranslationsKeyFramePosition);
                if (LERPProgress > 1F)
                    LERPProgress = 1F;

                if (prevTranslationsKeyFramePosition == 0 && prevTranslationKeyFrame == null && !(nextTranslationsKeyFramePosition == 0)) {
                    final Vector3f startPosition = box.getPositionAsVector();
                    final Vector3f endPosition = nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                    // .add(defaultPos);
                    final Vector3f currentPosition = new Vector3f(startPosition);
                    currentPosition.interpolate(endPosition, LERPProgress);

                    box.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);

                    anyTranslationApplied = true;
                }
                else if (prevTranslationsKeyFramePosition == 0 && prevTranslationKeyFrame != null && !(nextTranslationsKeyFramePosition == 0)) {
                    final Vector3f startPosition = prevTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                    // .add(defaultPos);
                    final Vector3f endPosition = nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                    // .add(defaultPos);
                    final Vector3f currentPosition = new Vector3f(startPosition);
                    currentPosition.interpolate(endPosition, LERPProgress);

                    box.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);
                    anyTranslationApplied = true;
                }
                else if (!(prevTranslationsKeyFramePosition == 0) && !(nextTranslationsKeyFramePosition == 0)) {
                    final Vector3f startPosition = prevTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                    // .add(defaultPos);
                    final Vector3f endPosition = nextTranslationKeyFrame.modelRenderersTranslations.get(boxName);
                    // .add(defaultPos);
                    final Vector3f currentPosition = new Vector3f(startPosition);
                    currentPosition.interpolate(endPosition, LERPProgress);

                    box.setRotationPoint(currentPosition.x, currentPosition.y, currentPosition.z);

                    anyTranslationApplied = true;
                }
            }
            else {
                anyCustomAnimationRunning = true;
                ((CustomChannel) channel).update(block, entity);
            }

        // if (!anyRotationApplied && !anyCustomAnimationRunning)
        // box.resetRotationMatrix();
        // if (!anyTranslationApplied && !anyCustomAnimationRunning)
        // box.resetRotationPoint();
    }

    /** Get world object from an IAnimated */
    public static boolean isWorldRemote(IAnimated animated) {
        if (animated instanceof Entity)
            return ((Entity) animated).getEntityWorld().isRemote;
        else if (animated instanceof TileEntity)
            return ((TileEntity) animated).getWorld().isRemote;
        else
            return false;
    }
}
