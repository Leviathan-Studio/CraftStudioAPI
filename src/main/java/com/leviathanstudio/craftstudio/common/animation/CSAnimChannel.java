package com.leviathanstudio.craftstudio.common.animation;

import java.util.Map.Entry;

import com.leviathanstudio.craftstudio.client.CSAnimMesher;
import com.leviathanstudio.craftstudio.client.CSModelMesher;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnim;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock;
import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock.ReadedKeyFrame;
import com.leviathanstudio.craftstudio.client.json.CSReadedModel;
import com.leviathanstudio.craftstudio.client.json.CSReadedModelBlock;
import com.leviathanstudio.craftstudio.common.math.Quaternion;

public class CSAnimChannel extends Channel {
	private CSReadedAnim rAnim;
	private CSReadedModel rModel;
	
	public CSAnimChannel(String animNameIn, String modelNameIn, float fps, boolean looped){
		this(animNameIn, animNameIn, modelNameIn, fps, looped);
	}
	
	public CSAnimChannel(String animNameIn, String name, String modelNameIn, float fps, boolean looped){
		super(name, false);
		this.rAnim = CSAnimMesher.animations.get(animNameIn);
		this.rModel = CSModelMesher.models.get(modelNameIn);
		this.fps = fps;
		this.totalFrames = this.rAnim.duration;
		if (looped)
			this.animationMode = EnumAnimationMode.LOOP;
		this.initializeAllFrames();
	}
	
	@Override
	protected void initializeAllFrames(){
		KeyFrame keyFrame;
		ReadedKeyFrame rKeyFrame;
		for (int i: this.rAnim.getKeyFrames()){
			this.keyFrames.put(i, new KeyFrame());
		}
		for (CSReadedAnimBlock block : this.rAnim.blocks){
			CSReadedModelBlock mBlock = this.rModel.getBlockFromName(block.name);
			if (mBlock != null)
				for (Entry<Integer, ReadedKeyFrame> entry : block.keyFrames.entrySet()){
					keyFrame = this.keyFrames.get(entry.getKey());
					rKeyFrame = entry.getValue();
					if (rKeyFrame.position != null)
						keyFrame.modelRenderersTranslations.put(block.name, rKeyFrame.position.add(mBlock.rotationPoint));
					if (rKeyFrame.rotation != null)
						keyFrame.modelRenderersRotations.put(block.name, new Quaternion(rKeyFrame.rotation.add(mBlock.rotation)));
				}
			else
				System.out.println("The block " + block.name + " doesn't exist in model " + this.rModel.name + " !");
			
		}
		//Not Accurate for holdLastK = true
		if(!this.rAnim.holdLastK)
			if (!this.keyFrames.containsKey(this.totalFrames))
				this.keyFrames.put(this.totalFrames, this.keyFrames.get(0).clone());
		
	}
}
