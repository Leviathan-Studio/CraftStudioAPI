package com.leviathanstudio.test.proxy;

public class CommonProxy
{
    public void registerModels() {}

    public void registerAnims() {}

    public void preInit()
    {
    	this.registerModels();
    	this.registerAnims();
    }
}