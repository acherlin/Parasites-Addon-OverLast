
package com.overlast.enchantment;

import java.lang.reflect.Method;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class UtilityAccessor {

	/** Accessible reference to {@code EntityLivingBase#damageEntity} */
	private static Method damageEntity;
	/** Accessible reference to {@code EntityLivingBase#applyPotionDamageCalculations */
	private static Method applyPotionDamageCalculations;

	private static Method applyArmorCalculations;
	
	/** Accessible reference to {@code EnchantmentDurability#negateDamage */
	private static Method negateDamage;
	/** Accessible reference to {@code Enchantment#canApplyTogether */
	private static Method canApplyTogether;
	

	
	/** Damages the target for the amount of damage using the vanilla method; posts LivingHurtEvent */
	public static void damageEntity(EntityLivingBase target, DamageSource source, float amount) {
		if (damageEntity == null) {
			//public static Method findMethod(Class<?> clazz, String srgName, Class<?> returnType, Class<?>... parameterTypes)
			damageEntity = ObfuscationReflectionHelper.findMethod(EntityLivingBase.class, "func_70665_d", void.class, DamageSource.class, float.class);
		}
		try {
			damageEntity.invoke(target, source, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/** Applies armor calculation to the target.*/
	public static float applyArmorCalculations(EntityLivingBase target, DamageSource source, float amount) {
		if (applyArmorCalculations == null) {
			//public static Method findMethod(Class<?> clazz, String srgName, Class<?> returnType, Class<?>... parameterTypes)
			applyArmorCalculations = ObfuscationReflectionHelper.findMethod(EntityLivingBase.class, "func_70655_b", float.class, DamageSource.class, float.class);
		}
		try {
			applyArmorCalculations.invoke(target, source, amount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return amount;
	}
	
	/** Damages target without posting an event!*/
    public static void damageTarget(EntityLivingBase target, DamageSource damageSrc, float damageAmount)
    {
        if (!target.isEntityInvulnerable(damageSrc))
        {
            if (damageAmount <= 0) return;
            damageAmount = applyArmorCalculations(target, damageSrc, damageAmount);
            damageAmount = applyPotionDamageCalculations(target, damageSrc, damageAmount);
            float f = damageAmount;
            damageAmount = Math.max(damageAmount - target.getAbsorptionAmount(), 0.0F);
            target.setAbsorptionAmount(target.getAbsorptionAmount() - (f - damageAmount));

            if (damageAmount > 0.0F)
            {
                float f1 = target.getHealth();
                target.getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
               // System.out.println(target.getHealth());
                target.setHealth(f1 - damageAmount); // Forge: moved to fix MC-121048
               // System.out.println(target.getHealth() + "POST");
                target.setAbsorptionAmount(target.getAbsorptionAmount() - damageAmount);
            }
        }
    }
    
    /** Damages target but LivingDamageEvent is fired!*/
    public static void damageTargetEvent(EntityLivingBase target, DamageSource damageSrc, float damageAmount)
    {
        if (!target.isEntityInvulnerable(damageSrc))
        {
            if (damageAmount <= 0) return;
            damageAmount = applyArmorCalculations(target, damageSrc, damageAmount);
            damageAmount = applyPotionDamageCalculations(target, damageSrc, damageAmount);
            float f = damageAmount;
            damageAmount = Math.max(damageAmount - target.getAbsorptionAmount(), 0.0F);
            target.setAbsorptionAmount(target.getAbsorptionAmount() - (f - damageAmount));
            damageAmount = net.minecraftforge.common.ForgeHooks.onLivingDamage(target, damageSrc, damageAmount);
            
            if (damageAmount > 0.0F)
            {
                float f1 = target.getHealth();
                target.getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
               // System.out.println(target.getHealth());
                target.setHealth(f1 - damageAmount); // Forge: moved to fix MC-121048
               // System.out.println(target.getHealth() + "POST");
                target.setAbsorptionAmount(target.getAbsorptionAmount() - damageAmount);
            }
        }
    }
    
	    
	/**
	 * Returns the amount of damage the entity will receive after armor and potions are taken into account
	 */
	public static float applyPotionDamageCalculations(EntityLivingBase target, DamageSource source, float amount) {
		if (applyPotionDamageCalculations == null) {
			applyPotionDamageCalculations = ObfuscationReflectionHelper.findMethod(EntityLivingBase.class, "func_70672_c", float.class, DamageSource.class, float.class);
		}
		try {
			applyPotionDamageCalculations.invoke(target, source, amount);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}
	
	public static boolean canApplyTogether(Enchantment en1, Enchantment en2) {

		boolean flag = true;
		
		if (canApplyTogether == null) {
			//public static Method findMethod(Class<?> clazz, String srgName, Class<?> returnType, Class<?>... parameterTypes)
			canApplyTogether = ObfuscationReflectionHelper.findMethod(Enchantment.class, "func_77326_a", boolean.class, Enchantment.class);
		}
		try {
			canApplyTogether.invoke(en1, en2, flag);
			//canApplyTogether.
			//System.out.println(flag);
			return flag;
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return flag;
	}

}
	