package com.leviathanstudio.craftstudio.pack.animation;

import com.leviathanstudio.craftstudio.client.animation.CustomChannel;
import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;
import com.leviathanstudio.craftstudio.util.CraftStudioHelper;
import com.leviathanstudio.craftstudio.util.math.Quaternion;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnimationLootAt extends CustomChannel
{
    private String       headPart;
    private EntityLiving entityToAnimate;

    public AnimationLootAt(EntityLiving entityToAnimateIn, String headPartIn)
    {
        super("lookAt");
        this.headPart = headPartIn;
        this.entityToAnimate = entityToAnimateIn;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void update(CSModelRenderer parts, IAnimated animated)
    {
        if (this.entityToAnimate instanceof IAnimated)
            if (parts.boxName.equals(this.headPart))
            {
                float diff = this.entityToAnimate.getRotationYawHead() - this.entityToAnimate.renderYawOffset;
                Quaternion quat = CraftStudioHelper.getQuaternionFromEulers(-this.entityToAnimate.rotationPitch, -diff,
                        0.0F);
                Quaternion quat2 = new Quaternion(parts.getDefaultRotationAsQuaternion());
                quat.mul(quat2);
                parts.getRotationMatrix().set(quat);
            }
    }

}