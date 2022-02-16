package com.overlast.lib;

import com.oblivioussp.spartanshields.util.ConfigHandler;
import com.oblivioussp.spartanshields.util.ModHelper;
import com.overlast.OverLast;
import com.overlast.item.*;
import com.overlast.item.potion.ItemDrinkedPotion;
import com.overlast.item.potion.ItemInjectedPotion;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumParticleTypes;

public class ModItems {


	public static final ItemChocolateSmoothie CHOCOLATE_SMOOTHIE = new ItemChocolateSmoothie(4, 0.6F, false);

	public static final ItemPolluteBowlHerbal Pollute_Bowl_Herbal = new ItemPolluteBowlHerbal(1, 0.1F, false);
	public static final ItemBowlHerbal Bowl_Herbal = new ItemBowlHerbal(1, 0.1F, false);
	public static final ItemMelonIce Melon_Ice = new ItemMelonIce(4,0.2F,false);
	public static final ItemIceSucker Ice_Sucker = new ItemIceSucker(1,0.2F,false);

	public static final ItemDrinkedPotion Drinked_Potion = new ItemDrinkedPotion();

	public static final ItemInjectedPotion Injected_Potion = new ItemInjectedPotion();

	public static final Item DUMPLING = new ItemSpringFestivalFood(4, 0.2F)
			.setPotionEffect(new PotionEffect(MobEffects.SATURATION, 20), 0.06F)
			.setAlwaysEdible()
			.setUnlocalizedName(OverLast.MOD_ID + ".dumpling")
			.setRegistryName("dumpling");

	public static final ItemEvoDevice EVO_DEVICE = new ItemEvoDevice();


	// This list is used to actually register the items.
	public static final Item[] ITEMS = {
			CHOCOLATE_SMOOTHIE,
			Pollute_Bowl_Herbal,
			Bowl_Herbal,
			Melon_Ice,
			Drinked_Potion,
			Injected_Potion,
			Ice_Sucker,
			DUMPLING,
			EVO_DEVICE
	};

}