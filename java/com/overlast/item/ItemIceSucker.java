package com.overlast.item;

import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.item.ItemFood;

public class ItemIceSucker extends ItemFood {
    private final String name = "ice_sucker";
    public ItemIceSucker(int hungerHeal, float saturation, boolean isWolfFood) {
        super(2, 0.2F, false);
        this.setMaxStackSize(32);
        this.setUnlocalizedName(OverLast.MOD_ID + "." + name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);
    }
}