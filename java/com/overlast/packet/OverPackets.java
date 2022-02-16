package com.overlast.packet;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import com.overlast.OverLast;

public class OverPackets {
	
	public static SimpleNetworkWrapper net;
	
	public static void initPackets() {
		
		net = NetworkRegistry.INSTANCE.newSimpleChannel(OverLast.MOD_ID);
		registerMessage(HUDRenderPacket.class, HUDRenderPacket.HUDRenderMessage.class);
		registerMessage(ConfigPacket.class, ConfigPacket.ConfigMessage.class);
	}
	
	// Packet ID to keep the packets separate.
	private static int packetId = 0;
	
	private static void registerMessage(Class packet, Class message) {
		
		net.registerMessage(packet, message, packetId, Side.CLIENT);
		net.registerMessage(packet, message, packetId, Side.SERVER);
		packetId++;
	}
}