package com.overlast;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.overlast.lib.ModItems;

@Mod(modid = OverLast.MOD_ID, name = OverLast.MOD_NAME, version = OverLast.VERSION, acceptedMinecraftVersions = OverLast.MCVERSION, dependencies = OverLast.DEPENDENCIES)
public class OverLast {
	
	// Basic mod constants.
	public static final String MOD_ID = "overlast";
	public static final String MOD_NAME = "OverLast";
	public static final String VERSION = "0.1.9";
	public static final String MCVERSION = "1.12";
	public static final String DEPENDENCIES = "required-after:forge@[14.23.1.2611,);required-after:srparasites@[1.9.1,)";
	public static final String RESOURCE_PREFIX = MOD_ID + ":"; // overlast:
	
	// Make an instance of the mod.
	@Instance(MOD_ID)
	public static OverLast instance;
	
	// Logger
	public static final Logger logger = LogManager.getLogger(MOD_NAME);
	
	// Create proxies to load stuff correctly.
	@SidedProxy(clientSide = "com.overlast.ClientProxy", serverSide = "com.overlast.CommonProxy")
	public static CommonProxy proxy;
	
	// Basic event handlers. All of the work is done in the proxies.
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
		proxy.postInit(event);
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event) {
		
		proxy.serverStarted(event);
	}

}