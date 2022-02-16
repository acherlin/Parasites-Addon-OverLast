package com.overlast.item;

import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemFood;
import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ItemPolluteBowlHerbal extends ItemFood {
    private final String name = "pollute_bowl_herbal";
    public ItemPolluteBowlHerbal(int hungerHeal, float saturation, boolean isWolfFood) {
        super(2, 0.3F, false);
        this.setMaxStackSize(8);
        this.setUnlocalizedName(OverLast.MOD_ID + "." + name);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setRegistryName(name);
    }
    public void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player)
    {
        if (!worldIn.isRemote)
        {
            player.removePotionEffect(SRPPotions.COTH_E);
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 100, 0, false, false));
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 0, false, false));
        }
        super.onFoodEaten(stack, worldIn, player);
    }
}