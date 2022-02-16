package com.overlast;

import com.overlast.util.client.KeyBinds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import com.overlast.gui.RenderHUD;
import com.overlast.util.client.ModelRegisterer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		
		super.preInit(event);
		
		// Register models.
		MinecraftForge.EVENT_BUS.register(new ModelRegisterer());
		KeyBinds.register();
	}
	
	@Override
	public void init(FMLInitializationEvent event) {
		super.init(event);
	}


	@Override
	public void postInit(FMLPostInitializationEvent event) {
		
		super.postInit(event);
		
		// Render stat bars.
		MinecraftForge.EVENT_BUS.register(new RenderHUD());
	}


	public void registerItemRenderer(Item item, int meta, String id){
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
	}


	public void registerItemRenderer(Item item, int meta, String pathName, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(new ResourceLocation(pathName), id));
	}


	@Override
	public IThreadListener getThreadListener(MessageContext context){
		if (context.side.isClient())
		{
			return Minecraft.getMinecraft();
		}
		else
		{
			return context.getServerHandler().player.mcServer;
		}
	}

	@Override
	public EntityPlayer getPlayer(MessageContext context)
	{
		if (context.side.isClient())
		{
			return Minecraft.getMinecraft().player;
		}
		else
		{
			return context.getServerHandler().player;
		}
	}
}