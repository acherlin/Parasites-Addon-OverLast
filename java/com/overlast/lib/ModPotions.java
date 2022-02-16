package com.overlast.lib;


import com.overlast.OverLast;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;

public class ModPotions {
	public static final PotionType PARASITESPURIFY = new PotionType(OverLast.MOD_ID + ":parasites_purify", new PotionEffect(ModMobEffects.PARASITESPURIFY, 1800)).setRegistryName(OverLast.MOD_ID + ":parasites_purify");
	public static final PotionType PARASITESINFECT = new PotionType(OverLast.MOD_ID + ":parasites_infect", new PotionEffect(ModMobEffects.PARASITESINFECT, 600)).setRegistryName(OverLast.MOD_ID + ":parasites_infect");
	public static final PotionType FORTUNATE = new PotionType(OverLast.MOD_ID + ":fortunate", new PotionEffect(ModMobEffects.FORTUNATE, 600)).setRegistryName(OverLast.MOD_ID + ":fortunate");
}
