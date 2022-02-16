package com.overlast;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import com.overlast.cap.CapEvents;
import com.overlast.packet.OverPackets;
import com.overlast.util.Registererer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		
		// Register all new items and blocks.
		MinecraftForge.EVENT_BUS.register(new Registererer());

	}
	
	public void init(FMLInitializationEvent event) {
		
		// Register event handlers.
		MinecraftForge.EVENT_BUS.register(new CapEvents());

		// Register network packets.
		OverPackets.initPackets();

	}
	
	public void postInit(FMLPostInitializationEvent event) {
		
	}
	
	public void serverStarted(FMLServerStartedEvent event) {

	}

	public IThreadListener getThreadListener(MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player.mcServer;
		} else
			return null;
	}

	public EntityPlayer getPlayer(MessageContext context) {
		if (context.side.isServer()) {
			return context.getServerHandler().player;
		} else
			return null;
	}
}