package com.overlast.item;

import com.overlast.creativetab.TabOverLast;
import com.overlast.lib.ModMobEffects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemSpringFestivalFood extends ItemFood {
    public final int itemUseDuration;

    public ItemSpringFestivalFood(int amount, float saturation) {
        this(amount, saturation, 10);
    }

    ItemSpringFestivalFood(int amount, float saturation, int itemUseDuration) {
        super(amount, saturation, false);
        setCreativeTab(TabOverLast.TAB_overlast);
        this.itemUseDuration = itemUseDuration;
        setAlwaysEdible();
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return itemUseDuration;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {
        super.onFoodEaten(stack, worldIn, player);
        player.addPotionEffect(new PotionEffect(ModMobEffects.FORTUNATE, 600));
    }
}
