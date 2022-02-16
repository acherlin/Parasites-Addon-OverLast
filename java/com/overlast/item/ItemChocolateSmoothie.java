package com.overlast.item;

import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemChocolateSmoothie extends ItemFood  {
    private final String name = "chocolate_smoothie";
    public ItemChocolateSmoothie(int hungerHeal, float saturation, boolean isWolfFood) {
        super(4, 0.6F, false);
        this.setMaxStackSize(8);
        this.setUnlocalizedName(OverLast.MOD_ID + "."+ name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);
    }
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 2000, 1));
        }
        super.onFoodEaten(stack, worldIn, player);
    }

}