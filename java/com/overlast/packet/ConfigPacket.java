package com.overlast.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.overlast.OverLast;
import com.overlast.config.OverConfig;
import com.overlast.packet.ConfigPacket.ConfigMessage;

public class ConfigPacket implements IMessageHandler<ConfigMessage, IMessage> {
	
	@Override
	public IMessage onMessage(ConfigMessage message, MessageContext ctx) {
		
		if (ctx.side.isClient()) {
			// Sync server values to client.
			double naturalEvolutionScale = message.naturalEvolutionScale;
			OverConfig.MECHANICS.naturalEvolutionScale = naturalEvolutionScale;
			OverLast.logger.info("Synced the client's config values with the server's.");
		}
		
		return null;
	}
	
	public static class ConfigMessage implements IMessage {
		
		// Variables used in the packet
		private double naturalEvolutionScale;
		
		// Necessary constructor.
		public ConfigMessage() {}
		
		public ConfigMessage(double naturalEvolutionScale) {
			this.naturalEvolutionScale=naturalEvolutionScale;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			this.naturalEvolutionScale = buf.readDouble();
		}
		
		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeDouble(naturalEvolutionScale);
		}
	}
}
