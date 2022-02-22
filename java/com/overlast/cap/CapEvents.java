package com.overlast.cap;

import com.dhanantry.scapeandrunparasites.init.SRPItems;
import com.dhanantry.scapeandrunparasites.init.SRPPotions;
import com.dhanantry.scapeandrunparasites.util.SRPConfig;
import com.dhanantry.scapeandrunparasites.world.SRPWorldData;
import com.overlast.lib.ModMobEffects;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import com.overlast.config.OverConfig;
import com.overlast.packet.HUDRenderPacket;
import com.overlast.packet.OverPackets;


/*
 * This is the event handler regarding capabilities and changes to individual stats.
 * Most of the actual code is stored in the modifier classes of each stat, and fired here.
 */
@Mod.EventBusSubscriber
public class CapEvents {

	// Modifiers
	private static int evopoint;

	private int evoTimer = 0;
	
	// When a player logs on, give them their stats stored on the server.
	@SubscribeEvent
	public void onPlayerLogsIn(PlayerLoggedInEvent event) {

		EntityPlayer player = event.player;

		if (player instanceof EntityPlayerMP) {

			// Capabilities
			// Send data to client for rendering.
			IMessage msgGui = new HUDRenderPacket.HUDRenderMessage(SRPWorldData.get(player.getEntityWorld()).getEvolutionPhase(),SRPWorldData.get(player.getEntityWorld()).getTotalKills(),false);
			OverPackets.net.sendTo(msgGui, (EntityPlayerMP) player);

		}
		int phase = SRPWorldData.get(player.getEntityWorld()).getEvolutionPhase();
		switch (phase) {
			case 3:
				evopoint = (SRPConfig.phaseKillsFour-SRPConfig.phaseKillsThree)/4000;
				break;
			case 4:
				evopoint = (SRPConfig.phaseKillsFive-SRPConfig.phaseKillsFour)/4000;
				break;
			case 5:
				evopoint = (SRPConfig.phaseKillsSix-SRPConfig.phaseKillsFive)/4000;
				break;
			case 6:
				evopoint = (SRPConfig.phaseKillsSeven-SRPConfig.phaseKillsSix)/4000;
				break;
			case 7:
				evopoint = (SRPConfig.phaseKillsEight-SRPConfig.phaseKillsSeven)/4000;
				break;
			case 1:
			case 2:
			case 0:
			case -1:
			case -2:
			case 8:
			default:
				evopoint = 0;
		}
		player.sendMessage(new TextComponentTranslation("message.evopoint.login",phase,(int)(evopoint*OverConfig.MECHANICS.naturalEvolutionScale)));
		if (phase == 8) {
			player.sendMessage(new TextComponentTranslation("message.evopoint.eight"));
		}
	}
	
	// When an entity is updated. So, all the time.
	// This also deals with packets to the client.
	@SubscribeEvent
	public void onPlayerUpdate(LivingUpdateEvent event) {
		// Only continue if it's a player.
		if (event.getEntity() instanceof EntityPlayer) {
			
			// Instance of player.
			EntityPlayer player = (EntityPlayer) event.getEntity();

			// Server-side
            if (!player.world.isRemote) {
                IMessage msgGui = new HUDRenderPacket.HUDRenderMessage(SRPWorldData.get(player.getEntityWorld()).getEvolutionPhase(),SRPWorldData.get(player.getEntityWorld()).getTotalKills(),!OverConfig.MECHANICS.showRequestDirtyClock||(player.getItemStackFromSlot(EntityEquipmentSlot.MAINHAND).getItem() == SRPItems.itemEVClock || player.getItemStackFromSlot(EntityEquipmentSlot.OFFHAND).getItem() == SRPItems.itemEVClock));
                OverPackets.net.sendTo(msgGui, (EntityPlayerMP) player);

				if(evoTimer<1200) {
					evoTimer++;
				}else {
					SRPWorldData.get(player.getEntityWorld()).setTotalKills((int) (evopoint*OverConfig.MECHANICS.naturalEvolutionScale), true, player.getEntityWorld());
					evoTimer=0;
				}
				
				if(!(player.getActivePotionEffect(ModMobEffects.PARASITESINFECT)==null)&&player.getActivePotionEffect(ModMobEffects.PARASITESINFECT).getAmplifier() ==0) {
					if((player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY)==null)) {
						player.addPotionEffect(new PotionEffect(SRPPotions.COTH_E, 1200, 1, false, false));
						evoTimer+=20;
					}
					player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 2, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 2, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200, 1, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 1200, 0, false, false));
				}else if (!(player.getActivePotionEffect(ModMobEffects.PARASITESINFECT)==null)&&player.getActivePotionEffect(ModMobEffects.PARASITESINFECT).getAmplifier() ==1) {
					if((player.getActivePotionEffect(ModMobEffects.PARASITESPURIFY)==null)) {
						player.addPotionEffect(new PotionEffect(SRPPotions.COTH_E, 1200, 3, false, false));
						evoTimer+=40;
					}
					player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 3, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 2, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 1200, 2, false, false));
					player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 1200, 0, false, false));
				}
			}
		}
	}
	



}
