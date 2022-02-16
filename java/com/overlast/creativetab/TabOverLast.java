package com.overlast.creativetab;

import com.overlast.lib.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TabOverLast extends CreativeTabs
{
    public static final TabOverLast TAB_overlast = new TabOverLast();

    public TabOverLast()
    {
        super("overlast");
    }

    @Override
    public ItemStack getTabIconItem()
    {
        return new ItemStack(ModItems.EVO_DEVICE);
    }


}
