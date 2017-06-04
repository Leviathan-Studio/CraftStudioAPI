package com.leviathanstudio.craftstudio.pack.animation;

import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.client.util.CraftStudioHelper;
import com.leviathanstudio.craftstudio.client.util.math.Quaternion;
import com.leviathanstudio.craftstudio.common.animation.CustomChannel;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AnimationLootAt extends CustomChannel
{
    private String       headPart;

    public AnimationLootAt(String headPartIn)
    {
        super("lookAt");
        this.headPart = headPartIn;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void update(CSModelRenderer parts, IAnimated animated)
    {
        if (animated instanceof EntityLiving)
            if (parts.boxName.equals(this.headPart))
            {
            	EntityLiving entityL = (EntityLiving) animated;
                float diff = entityL.getRotationYawHead() - entityL.renderYawOffset;
                Quaternion quat = CraftStudioHelper.getQuaternionFromEulers(-entityL.rotationPitch, -diff,
                        0.0F);
                Quaternion quat2 = new Quaternion(parts.getDefaultRotationAsQuaternion());
                quat.mul(quat2);
                parts.getRotationMatrix().set(quat);
            }
    }

}