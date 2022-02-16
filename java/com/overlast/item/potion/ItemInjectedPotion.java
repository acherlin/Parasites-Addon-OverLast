package com.overlast.item.potion;

import com.dhanantry.scapeandrunparasites.entity.ai.EntityPInfected;
import com.overlast.OverLast;
import com.overlast.creativetab.TabOverLast;
import com.overlast.lib.ModMobEffects;
import net.minecraft.block.BlockCauldron;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;


public class ItemInjectedPotion extends Item  {
	

	private final String name = "injected_potion";
	public ItemInjectedPotion() {
		
		// Set registry and unlocalized name.
		this.setUnlocalizedName(OverLast.MOD_ID + "." + name);
		this.setCreativeTab(TabOverLast.TAB_overlast);
		this.setRegistryName(name);
		// Basic properties.
		setMaxStackSize(6);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		// Server side.
		if (!world.isRemote) {
		}
		player.setActiveHand(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {

		// Server side.
		if (!world.isRemote && entityLiving instanceof EntityPlayerMP) {

			// Basic variables.
			EntityPlayerMP player = (EntityPlayerMP) entityLiving;

			AxisAlignedBB boundingBox = player.getEntityBoundingBox().grow(2, 2, 2);
			List nearbyMobs = player.world.getEntitiesWithinAABB(EntityPInfected.class, boundingBox);
			List nearbyAnimals = player.world.getEntitiesWithinAABB(EntityAnimal.class, boundingBox);
			List nearbyVillager = player.world.getEntitiesWithinAABB(EntityVillager.class, boundingBox);
			// Do the same for animals.

			if (nearbyMobs.size() > 0) {
				// Chosen mob
				EntityMob mob = (EntityMob) nearbyMobs.get(0);
				if (mob instanceof EntityPInfected) {
					mob.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 600, 0, false, true));
				}
			}
			if (nearbyAnimals.size() > 0) {
				// Chosen animal
				EntityAnimal animals = (EntityAnimal) nearbyAnimals.get(0);
				if (animals instanceof EntityAnimal) {
					animals.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 18000, 0, false, true));
				}
			}
			if (nearbyVillager.size() > 0) {
				// Chosen villager
				EntityVillager villager = (EntityVillager) nearbyVillager.get(0);
				if (villager instanceof EntityVillager) {
					villager.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 6000, 0, false, true));
				}
			}

			stack.setCount(stack.getCount()-1);
		}

		return stack;
	}

	// How long it takes to use it (how long to show the animation).
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {

		return 20;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {

		return EnumAction.BOW;
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {

		return slotChanged;
	}
}