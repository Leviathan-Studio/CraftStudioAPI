package com.leviathanstudio.craftstudio.client.json;

import java.util.HashMap;
import java.util.Map;

import com.leviathanstudio.craftstudio.common.math.Vector3f;

public class CSReadedAnimBlock
{
	public static final byte POS = 0, ROT = 1, OFS = 2, SIZ = 3, STR = 4;
	public String name;
	public Map<Integer, ReadedKeyFrame> keyFrames = new HashMap<Integer, ReadedKeyFrame>();
	
	public void addKFElement(int keyFrame, byte type, Vector3f value){
		if (!this.keyFrames.containsKey(keyFrame)){
			this.keyFrames.put(keyFrame, new ReadedKeyFrame());
		}
		switch (type){
		case POS:
			this.keyFrames.get(keyFrame).position = value;
			break;
		case ROT:
			this.keyFrames.get(keyFrame).rotation = value;
			break;
		case OFS:
			this.keyFrames.get(keyFrame).offset = value;
			break;
		case SIZ:
			this.keyFrames.get(keyFrame).size = value;
			break;
		case STR:
			this.keyFrames.get(keyFrame).stretching = value;
			break;
		}
	}
	
	public class ReadedKeyFrame {
		public Vector3f position, rotation, offset, size, stretching;
	}
}
