package com.leviathanstudio.test.common;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemTest extends Item
{

    public ItemTest()
    {
        this.setUnlocalizedName("itemTest");
        this.setCreativeTab(CreativeTabs.MISC);
    }

}