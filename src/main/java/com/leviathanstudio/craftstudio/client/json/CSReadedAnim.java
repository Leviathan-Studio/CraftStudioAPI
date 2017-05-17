package com.leviathanstudio.craftstudio.client.json;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import com.leviathanstudio.craftstudio.client.json.CSReadedAnimBlock.ReadedKeyFrame;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Class that store the informations relative to an animation.
 * @author Timmypote
 */
@SideOnly(Side.CLIENT)
public class CSReadedAnim
{
	public String modid, name;
	public int duration;
	public boolean holdLastK;
	public List<CSReadedAnimBlock> blocks = new ArrayList<CSReadedAnimBlock>();
	private Integer[] keyFrames;
	
	/**
	 * Get the keys of keyframes used in the animation.
	 * @return Array of the keys.
	 */
	public Integer[] getKeyFrames(){
		if (this.keyFrames != null)
			return this.keyFrames;
		
		Set set = new HashSet<Integer>();
		for (CSReadedAnimBlock block : this.blocks)
			for (Entry<Integer, ReadedKeyFrame> entry : block.keyFrames.entrySet())
				set.add(entry.getKey());
		
		Integer[] tab = new Integer[1];
		tab = (Integer[]) set.toArray(tab);
		return tab;
	}
}
