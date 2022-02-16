package com.overlast.item.potion;


import com.overlast.creativetab.TabOverLast;
import com.overlast.lib.ModMobEffects;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import com.overlast.OverLast;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDrinkedPotion extends Item  {


    public ItemDrinkedPotion() {

        this.setRegistryName(new ResourceLocation(OverLast.MOD_ID, "drinking_potion"));

        this.setMaxStackSize(1);
        this.setCreativeTab(TabOverLast.TAB_overlast);
        this.setNoRepair();
        this.setHasSubtypes(true);

    }

    // Number of sips. 
    private final int canteenSips = 6;

    // Durability 
    private final int canteenDurability = 30;

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entityLiving) {

        // Server-side. 
        if (entityLiving instanceof EntityPlayerMP && !world.isRemote) {

            // Basic variables.
            EntityPlayerMP player = (EntityPlayerMP) entityLiving;
            
            int canteenType = stack.getMetadata();
            // Purify water
            if (canteenType == 1) {
                player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESPURIFY, 1800, 0, false, true));
            }

            // Infect water
            else if (canteenType == 2) {
                player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESINFECT, 1800, 0, false, true));
            }

            // Infect 2 water
            else if (canteenType == 3) {
                player.addPotionEffect(new PotionEffect(ModMobEffects.PARASITESINFECT, 3600, 1, false, true));
            }

            // Decrease durability and set sips.
            NBTTagCompound nbt = stack.getTagCompound();

            if (nbt != null) {

                if (nbt.getInteger("sips") > 0) {

                    nbt.setInteger("sips", nbt.getInteger("sips") - 1);
                }

                else {

                    nbt.setInteger("sips", nbt.getInteger("sips") - 1);
                    stack.setItemDamage(0);
                }

                nbt.setInteger("durability", nbt.getInteger("durability") - 1);
                stack.setTagCompound(nbt);
            }
        }

        return stack;
    }



    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        // Server-side.
        if (!world.isRemote) {
            // Held item. 
            ItemStack heldItem = player.getHeldItem(hand);

            // NBT Tag of canteen (which shouldn't be null by now).
            NBTTagCompound nbt = heldItem.getTagCompound();

            if (heldItem.getMetadata() == 0) {

                return new ActionResult<ItemStack>(EnumActionResult.PASS, heldItem);
            }
            else {

                player.setActiveHand(hand);
                return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, heldItem);
            }
        }
        else {

            player.setActiveHand(hand);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {

        if (stack.getMetadata() == 0) { return "item." + OverLast.MOD_ID + "." + "drinking_potion"; }
        else if (stack.getMetadata() == 1) { return "item." + OverLast.MOD_ID + "." + "drinking_potion_1"; }
        else if (stack.getMetadata() == 2) { return "item." + OverLast.MOD_ID + "." + "drinking_potion_2"; }
        else if (stack.getMetadata() == 3) { return "item." + OverLast.MOD_ID + "." + "drinking_potion_3"; }
        else { stack.setItemDamage(0); return "item." + OverLast.MOD_ID + "." + "drinking_potion";  }
    }

    // When crafted, give this item an NBT tag to store its number of sips available, along with durability.
    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {

        if (!stack.hasTagCompound()) {

            // Making a new NBT tag compound.
            NBTTagCompound nbt = new NBTTagCompound();


            if (stack.getMetadata() == 0) {
                nbt.setInteger("sips", 0);
            }
            else {
                nbt.setInteger("sips", canteenSips);
            }

            // Add durability tag.
            nbt.setInteger("durability", canteenDurability);
            stack.setTagCompound(nbt);
        }
    }

    // This is to ensure the canteen always has NBT.
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {

        if (!stack.hasTagCompound()) {

            // Making a new NBT tag compound.
            NBTTagCompound nbt = new NBTTagCompound();

            if (stack.getMetadata() == 0) {

                nbt.setInteger("sips", 0);
            }

            else {

                nbt.setInteger("sips", canteenSips);
            }

            // Add durability tag.
            nbt.setInteger("durability", canteenDurability);
            stack.setTagCompound(nbt);
        }

        else {

            // If there are zero sips left, make it an empty canteen.
            NBTTagCompound nbt = stack.getTagCompound();

            if (nbt.getInteger("sips") <= 0) {

                stack.setItemDamage(0);
            }

            // If durability is 0, destroy the canteen.
            if (nbt.getInteger("durability") <= 0) {

                stack.shrink(1);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

        // NBT Tag and percentLeft float to determine color.
        NBTTagCompound nbt = stack.getTagCompound();
        float percentLeft;

        if (nbt != null) {

            percentLeft = (int) Math.round((nbt.getInteger("sips") * 100) / canteenSips);

            if (percentLeft == 100 || percentLeft >= 80) {

                tooltip.add(TextFormatting.GREEN + Integer.toString(nbt.getInteger("sips")) + " sips");
            }

            else if ((percentLeft <= 80 || percentLeft >= 20) && nbt.getInteger ("sips") > 1) {

                tooltip.add(TextFormatting.YELLOW + Integer.toString(nbt.getInteger("sips")) + " sips");
            }

            else if (nbt.getInteger("sips") == 1) {

                tooltip.add(TextFormatting.RED + Integer.toString(nbt.getInteger("sips")) + " sips");
            }

            else if (percentLeft <= 20 || percentLeft >= 0) {

                tooltip.add(TextFormatting.RED + Integer.toString(nbt.getInteger("sips")) + " sips");
            }

            else {

                tooltip.add(TextFormatting.WHITE + Integer.toString(nbt.getInteger("sips")) + " sips");
            }

            // Durability info. In an if-statement just in case.
            if (nbt.hasKey("durability")) {

                tooltip.add(TextFormatting.GOLD + Integer.toString(nbt.getInteger("durability")) + " durability");
            }
        }

        else {

            tooltip.add(TextFormatting.BLUE + "Potion");
        }
    }

    // Create sub items
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {

        if (this.isInCreativeTab(tab)) {

            for (int i = 0; i < 4; i++) {

                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    // Makes it a drink.
    @Override
    public EnumAction getItemUseAction(ItemStack stack) {

        return EnumAction.DRINK;
    }

    // How long it takes to drink it (how long to show the animation).
    @Override
    public int getMaxItemUseDuration(ItemStack stack) {

        return 32;
    }

    // Figure out when to show durability.
    @Override
    public boolean showDurabilityBar(ItemStack stack) {

        // NBT
        NBTTagCompound nbt = stack.getTagCompound();

        if (nbt != null) {

            // What is configured?
                // Show sips
                if (nbt.getInteger("sips") > 0) {
                    return true;
                }
                else {

                    return false;
                }
        }

        else {

            return false;
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {

        // NBT Tag and necessary variables.
        NBTTagCompound nbt = stack.getTagCompound();
        int percentLeft = 0;
        double durabilityToShow = 0;

        if (nbt != null) {


                percentLeft = Math.round((nbt.getInteger("sips") * 100) / canteenSips);
                durabilityToShow = 1.0 - ((double) percentLeft / 100);


            return durabilityToShow;
        }

        else {

            return 1;
        }
    }
}