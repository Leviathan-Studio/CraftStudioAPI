package com.leviathanstudio.craftstudio.client;

import java.io.FileNotFoundException;

public class CraftStudioModelNotFound extends FileNotFoundException
{

    /**
     * 
     */
    private static final long serialVersionUID = -3495512420502088386L;

    public CraftStudioModelNotFound(String model)
    {
        super("Model not found: " + model);
    }
}
