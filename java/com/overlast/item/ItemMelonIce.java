package com.overlast.item;

import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.item.ItemFood;

public class ItemMelonIce extends ItemFood {
    private final String name = "melon_ice";
    public ItemMelonIce(int hungerHeal, float saturation, boolean isWolfFood) {
        super(4, 0.4F, false);
        this.setMaxStackSize(32);
        this.setUnlocalizedName(OverLast.MOD_ID + "." + name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);
        setAlwaysEdible();
    }
}