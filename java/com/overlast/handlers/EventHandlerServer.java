package com.overlast.handlers;

import com.dhanantry.scapeandrunparasites.entity.ai.EntityPInfected;
import com.dhanantry.scapeandrunparasites.entity.monster.infected.*;
import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.overlast.OverLast;
import com.overlast.gui.RenderHUD;
import com.overlast.lib.ModMobEffects;
import com.overlast.util.client.KeyBinds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockStone;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


@EventBusSubscriber(modid = OverLast.MOD_ID)
public class EventHandlerServer {

    private static int updateTimer = 0;

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyBinds.KEY_SWITCH.isPressed()) {
            if (RenderHUD.switchhud) {
                RenderHUD.switchhud = false;
            } else {
                RenderHUD.switchhud = true;
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerMining(BlockEvent.HarvestDropsEvent e) {
        if (e.getHarvester() != null && !(e.getHarvester().getActivePotionEffect(ModMobEffects.FORTUNATE) == null)) {
            if (e.isSilkTouching())
                return;
            Block origBlock = e.getState().getBlock();
            if (!e.getHarvester().getHeldItemMainhand().canHarvestBlock(e.getState()))
                return;
            if (origBlock instanceof BlockStone)
                return;
            if (!(origBlock instanceof BlockOre))
                return;
            if(origBlock == Blocks.DIAMOND_ORE || origBlock == Blocks.COAL_ORE || origBlock == Blocks.LAPIS_ORE || origBlock == Blocks.EMERALD_ORE || origBlock == Blocks.QUARTZ_ORE || origBlock == Blocks.QUARTZ_ORE)
                e.getDrops().get(0).grow(1);
        }
    }


    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onEntityUpdate(LivingEvent.LivingUpdateEvent event) {
        if (!event.getEntity().world.isRemote) {
            EntityUpdate(event.getEntity());

            if (event.getEntity() instanceof EntityAnimal) {
                EntityAnimal annimals = (EntityAnimal) event.getEntity();
                if (!(annimals.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) == null)) {
                    annimals.removePotionEffect(SRPPotions.COTH_E);
                    annimals.removePotionEffect(SRPPotions.FEAR_E);
                    annimals.removePotionEffect(SRPPotions.BLEED_E);
                    annimals.removePotionEffect(SRPPotions.CORRO_E);
                    annimals.removePotionEffect(SRPPotions.VIRA_E);
                    annimals.removePotionEffect(SRPPotions.EPEL_E);
                }
            }
            if (event.getEntity() instanceof EntityVillager) {
                EntityVillager villager = (EntityVillager) event.getEntity();
                if (!(villager.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) == null)) {
                    villager.removePotionEffect(SRPPotions.COTH_E);
                    villager.removePotionEffect(SRPPotions.FEAR_E);
                    villager.removePotionEffect(SRPPotions.BLEED_E);
                    villager.removePotionEffect(SRPPotions.CORRO_E);
                    villager.removePotionEffect(SRPPotions.EPEL_E);
                }
            }
        }
    }

    public static void EntityUpdate(Entity entity) {
        if (updateTimer < 20) {
            updateTimer++;
        } else {
            updateTimer = 0;
            if (entity instanceof EntityPInfected) {
                double dx = entity.posX;
                double dy = entity.posY;
                double dz = entity.posZ;
                if (!(((EntityPInfected) entity).getActivePotionEffect(ModMobEffects.PARASITESPURIFY) == null)
                        && ((EntityPInfected) entity).getActivePotionEffect(ModMobEffects.PARASITESPURIFY).getDuration() <= 40) {
                    EntityMob infAnnimals = (EntityMob) entity;
                    if (infAnnimals instanceof EntityDorpa) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntitySpider entityageable = new EntitySpider(infAnnimals.world);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfBear) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityPolarBear(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfCow) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityCow(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfEnderman) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityEnderman entityageable = new EntityEnderman(infAnnimals.world);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfHorse) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityHorse(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfHuman) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityZombie entityageable = new EntityZombie(infAnnimals.world);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfPig) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityPig(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfSheep) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntitySheep(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfVillager) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityVillager entityageable = new EntityVillager(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                    if (infAnnimals instanceof EntityInfWolf) {
                        infAnnimals.setDead();
                        infAnnimals.world.createExplosion(infAnnimals, dx, dy + 0.2F, dz, 0, true);
                        EntityAnimal entityageable = new EntityWolf(infAnnimals.world);
                        entityageable.setGrowingAge(-24000);
                        entityageable.setLocationAndAngles(dx, dy + 0.2F, dz, 0.0F, 0.0F);
                        infAnnimals.world.spawnEntity(entityageable);
                    }
                }
            }
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (!(player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY) == null)) {
                    player.removePotionEffect(SRPPotions.COTH_E);
                    player.removePotionEffect(SRPPotions.FEAR_E);
                    player.removePotionEffect(SRPPotions.BLEED_E);
                    player.removePotionEffect(SRPPotions.CORRO_E);
                    player.removePotionEffect(SRPPotions.VIRA_E);
                    player.removePotionEffect(SRPPotions.EPEL_E);
                }
            }
        }
    }
}