package com.overlast.mobeffect;

import java.awt.Color;


import com.overlast.OverLast;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MobEffectMod extends Potion
{
    private final ResourceLocation iconTexture;

    public MobEffectMod(String name, boolean isBadEffect, int red, int green, int blue)
    {
        super(isBadEffect, new Color(red, green, blue).getRGB());
        this.setRegistryName(OverLast.MOD_ID + ":" + name);
        this.setPotionName("mob_effect." + this.getRegistryName());
        this.iconTexture = new ResourceLocation(OverLast.MOD_ID + ":textures/potions/" + name + ".png");
    }

    @Override
    public boolean hasStatusIcon()
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(int x, int y, PotionEffect effect, Minecraft mc)
    {
        if(mc.currentScreen != null)
        {
            mc.getTextureManager().bindTexture(this.iconTexture);
            Gui.drawModalRectWithCustomSizedTexture(x + 6, y + 7, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void renderHUDEffect(int x, int y, PotionEffect effect, Minecraft mc, float alpha)
    {
        mc.getTextureManager().bindTexture(this.iconTexture);
        Gui.drawModalRectWithCustomSizedTexture(x + 3, y + 3, 0, 0, 18, 18, 18, 18);
    }
}