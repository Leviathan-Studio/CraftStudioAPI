package com.leviathanstudio.craftstudio.client.json;

import java.util.ArrayList;
import java.util.List;

public class CSReadedModel
{
    public String                   name, modid;
    public int                      textureWidth, textureHeight;
    public List<CSReadedModelBlock> parents = new ArrayList<>();
    
    public CSReadedModelBlock getBlockFromName(String name){
    	CSReadedModelBlock b;
    	for (CSReadedModelBlock block : this.parents){
    		b = block.getBlockFromName(name);
    		if (b != null)
    			return b;
    	}
    	return null;
    }
    
    public boolean isAnimable(){
    	List<String> names = new ArrayList<String>();
    	for (CSReadedModelBlock block: parents)
    		if (block.getAnimability(names) == false)
    			return false;
    	return true;
    }

    String whyUnAnimable(){
    	boolean flag = true;
    	List<String> names = new ArrayList<String>();
    	for (CSReadedModelBlock block: parents)
    		if (block.getAnimability(names) == false){
    			flag = false;
    			break;
    		}
    	if (flag)
    		return null;
    	else
    		return names.get(0);
    }
}
