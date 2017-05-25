package com.leviathanstudio.craftstudio.common.exceptions;

public class CSResourceNotRegisteredException extends RuntimeException
{
    private static final long serialVersionUID = -3495512420502365486L;

    public CSResourceNotRegisteredException(String resourceNameIn) {
        super("You are trying to acces \"" + resourceNameIn + "\", but it's not registered");
    }
}