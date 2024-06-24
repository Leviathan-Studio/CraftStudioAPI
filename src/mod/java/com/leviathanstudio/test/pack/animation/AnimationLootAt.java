package com.leviathanstudio.test.pack.animation;

import javax.vecmath.Quat4f;

import com.leviathanstudio.craftstudio.client.model.CSModelRenderer;
import com.leviathanstudio.craftstudio.client.util.MathHelper;
import com.leviathanstudio.craftstudio.common.animation.CustomChannel;
import com.leviathanstudio.craftstudio.common.animation.IAnimated;

import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AnimationLootAt extends CustomChannel {
	private String headPart;

	public AnimationLootAt(String headPartIn) {
		super("lookat");
		this.headPart = headPartIn;
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void update(CSModelRenderer parts, IAnimated animated) {
		if (animated instanceof LivingEntity)
			if (parts.boxName.equals(this.headPart)) {
				LivingEntity entityL = (LivingEntity) animated;
				float diff = entityL.getRotationYawHead() - entityL.renderYawOffset;
				Quat4f quat = MathHelper.quatFromEuler(entityL.rotationPitch, 0.0F, diff);
				Quat4f quat2 = new Quat4f(parts.getDefaultRotationAsQuaternion());
				quat.mul(quat2);
				parts.getRotationMatrix().set(quat);
				parts.getRotationMatrix().transpose();
			}
	}

}