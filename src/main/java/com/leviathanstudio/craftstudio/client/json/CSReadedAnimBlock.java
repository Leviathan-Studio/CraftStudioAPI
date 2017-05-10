package com.leviathanstudio.craftstudio.client.json;

import java.util.HashMap;
import java.util.Map;

import com.leviathanstudio.craftstudio.common.math.Vector3f;

public class CSReadedAnimBlock
{
	String name;
	Map<Integer, Vector3f> position = new HashMap <Integer, Vector3f>()
			, rotation = new HashMap <Integer, Vector3f>()
			, offset = new HashMap <Integer, Vector3f>()
			, size = new HashMap <Integer, Vector3f>()
			, streching = new HashMap <Integer, Vector3f>();
}
