package com.leviathanstudio.test.common.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemTest extends Item
{

    public ItemTest() {
        this.setTranslationKey("itemTest");
        this.setCreativeTab(CreativeTabs.MISC);
    }

}