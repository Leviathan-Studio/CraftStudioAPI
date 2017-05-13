package com.leviathanstudio.craftstudio.common.animation;

import java.util.Map.Entry;

import com.leviathanstudio.craftstudio.client.CSAnimMesher;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock.ReadedKeyFrame;
import com.leviathanstudio.craftstudio.common.math.Quaternion;

public class CSAnimChannel extends Channel {
	private String animName;
	
	public CSAnimChannel(String animNameIn, float fps){
		this(animNameIn, animNameIn, fps);
		this.animName = animNameIn;
	}
	
	public CSAnimChannel(String animNameIn, String name, float fps){
		super(name, fps, CSAnimMesher.animations.get(animNameIn).duration, Channel.EnumAnimationMode.LINEAR);
	}
	
	@Override
	protected void initializeAllFrames(){
		CSReadedAnim readedAnim = CSAnimMesher.animations.get(this.animName);
		KeyFrame keyFrame;
		ReadedKeyFrame rKeyFrame;
		for (int i: readedAnim.getKeyFrames()){
			this.keyFrames.put(i, new KeyFrame());
		}
		for (CSReadedAnimBlock block : readedAnim.blocks){
			for (Entry<Integer, ReadedKeyFrame> entry : block.keyFrames.entrySet()){
				keyFrame = this.keyFrames.get(entry.getKey());
				rKeyFrame = entry.getValue();
				if (rKeyFrame.position != null)
					keyFrame.modelRenderersTranslations.put(block.name, rKeyFrame.position);
				if (rKeyFrame.rotation != null)
					keyFrame.modelRenderersRotations.put(block.name, new Quaternion(rKeyFrame.rotation));
			}
		}
	}
}
