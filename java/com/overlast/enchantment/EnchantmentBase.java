package com.overlast.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public abstract class EnchantmentBase extends Enchantment
{
	private boolean registered;
	
	//Current Registry issues
	
	//EnchantmentData constructing
	
	public EnchantmentBase(Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots)
	{
		super(rarityIn, typeIn, slots);
		this.registered = false;
	}
	
	/** Returns the ModConfig.enabled for the enchantment
	 * 
	 */
	public abstract boolean isConfigEnabled();
	
	/** Whether the enchantment is both enabled in the config and registered
	 * 
	 */
	public boolean isEnabled()
	{
		return isRegistered() && isConfigEnabled();
	}
	
	public void setRegistered()
	{
		this.registered = true;
	}
	

	public boolean isRegistered()
	{
		return this.registered;
	}

	@Override
	public int getMaxLevel()
	{
		return 1;
	}
	

	@Override
	public boolean isAllowedOnBooks()
	{
		return isEnabled();
	}
	

	@Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return isEnabled() && stack.getItem().canApplyAtEnchantingTable(stack, this);
    }
	
	@Override
	public boolean canApply(ItemStack stack)
	{
		return isEnabled() && super.canApply(stack);
	}
	
	//Current Overrides List
	//Pandora's Curse
	@Override
	@SuppressWarnings("deprecation")
	public String getTranslatedName(int level)
    {
		String s = I18n.translateToLocal(this.getName());
		
		//Formatting
		
		if(!this.isEnabled())
		{
			s = TextFormatting.DARK_RED + "" + TextFormatting.STRIKETHROUGH + s;
		}
		else if (this.isCurse())
        {
            s = TextFormatting.RED + s;
        }
		else
		{
			s = getPrefix() + s;
		}

		//Content
        return level == 1 && this.getMaxLevel() == 1 ? s : s + " " + I18n.translateToLocal("enchantment.level." + level);
    }
	
	public String getPrefix()
	{
		return "";
	}
	
	public void onEntityDamagedAlt(EntityLivingBase user, Entity target, ItemStack weapon, int level)
	{
		
	}
	
	@Override
	@Deprecated
	public final void onEntityDamaged(EntityLivingBase user, Entity target, int level)
	{
		super.onEntityDamaged(user, target, level);
	}

	public static boolean isOffensivePetDisallowed(Entity immediateSource, Entity trueSource)
	{
		if(immediateSource == null || trueSource == null)
			return false;


			if(immediateSource != trueSource && immediateSource instanceof EntityLivingBase && trueSource instanceof EntityPlayer && !(immediateSource instanceof EntityPlayer))
			{
				return true;
			}
		
		//Passed all checks
		return false;
	}
}
