package com.overlast.packet;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import com.overlast.gui.RenderHUD;
import com.overlast.packet.HUDRenderPacket.HUDRenderMessage;

public class HUDRenderPacket implements IMessageHandler<HUDRenderMessage, IMessage> {
	
	@Override
	public IMessage onMessage(HUDRenderMessage message, MessageContext ctx) {
		
		if (ctx.side.isClient()) {
			int phase =  message.phase;
			int evolution =  message.evolution;
			boolean showRequestDirtyClock = message.showRequestDirtyClock;

			RenderHUD.retrieveStats(phase, evolution,showRequestDirtyClock);
		}
		
		return null;
	}
	
	public static class HUDRenderMessage implements IMessage {
		
		// Variables sent to the client for rendering.
		//SRP
		private int phase;
		private int evolution;
		private boolean showRequestDirtyClock;

		
		// Necessary constructor.
		public HUDRenderMessage() {}
		
		public HUDRenderMessage(int phase,int evolution,boolean showRequestDirtyClock) {
			this.phase=phase;
			this.evolution = evolution;
			this.showRequestDirtyClock=showRequestDirtyClock;
		}
		
		@Override
		public void fromBytes(ByteBuf buf) {
			this.phase = buf.readInt();
			this.evolution = buf.readInt();
			this.showRequestDirtyClock=buf.readBoolean();
		}
		
		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(phase);
			buf.writeInt(evolution);
			buf.writeBoolean(showRequestDirtyClock);
		}
	}
}
