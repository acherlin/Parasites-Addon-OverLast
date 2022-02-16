package com.overlast.gui;

import com.dhanantry.scapeandrunparasites.util.SRPConfig;
import com.overlast.OverLast;
import com.overlast.config.OverConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class RenderHUD extends Gui {


	private static int EvoIndex=1;
	public static boolean switchhud = true;
	public static int SanAdd = 0;
	// The stat bars themselves.
	private static final StatBar EVOLUTION_BAR = new StatBar(StatBar.StatType.EVOLUTION, 113, 29, 80, 23, 32, new ResourceLocation(OverLast.MOD_ID, "textures/gui/evolutionbar1.png"));

	// List of the main bars for easy iteration
	private static final StatBar[] MAIN_BARS = {EVOLUTION_BAR};


	// This method gets the correct stats of the player.  
	public static void retrieveStats(int phase, int evolution) {
		EvoIndex=phase;
		EVOLUTION_BAR.setValue(evolution);
		switch(phase) {
			case -2:
			case -1:EVOLUTION_BAR.setMaxValue(0);break;
			case 0:EVOLUTION_BAR.setMaxValue(SRPConfig.phaseKillsOne);break;
			case 1:EVOLUTION_BAR.setMaxValue(SRPConfig.phaseKillsTwo);break;
			case 2:EVOLUTION_BAR.setMaxValue(SRPConfig.phaseKillsThree);break;
			case 3:EVOLUTION_BAR.setMaxValue(SRPConfig.phaseKillsFour);break;
			case 4:EVOLUTION_BAR.setMaxValue(SRPConfig.phaseKillsFive);break;
			case 5:EVOLUTION_BAR.setMaxValue(SRPConfig.phaseKillsSix);break;
			case 6:EVOLUTION_BAR.setMaxValue(SRPConfig.phaseKillsSeven);break;
			case 7:
			case 8:
				EVOLUTION_BAR.setMaxValue(SRPConfig.phaseKillsEight);break;
		}
		EVOLUTION_BAR.setTexture(new ResourceLocation(OverLast.MOD_ID, "textures/gui/evolutionbar"+EvoIndex+".png"));
	}

	//最终渲染层
	@SubscribeEvent
	public void renderOverlay(RenderGameOverlayEvent event) {
		if(!switchhud){
			return;
		}
		if (event.getType() == RenderGameOverlayEvent.ElementType.TEXT) {
			
			// Instance of Minecraft. All of this crap is client-side (well of course)
			Minecraft mc = Minecraft.getMinecraft();
			
			// Get current screen resolution. 
			ScaledResolution scaled = event.getResolution();
			int screenWidth = scaled.getScaledWidth();
			int screenHeight = scaled.getScaledHeight();

			// Variables used to render the bars.
            int x;
            int y;
            int i = 0;
            ResourceLocation texture;
            int fullWidth;
            int fullHeight;
            int movingTextureX;
            int movingTextureY;
            int fullBarWidth;
            int movingWidth;
            String text;

            // The loop that renders the main stat bars.
			for (StatBar bar : MAIN_BARS) {

                // RIGHTMOST position of this bar. This is so we can account for the bar widths correctly.
                x = getX(screenWidth, i);
                y = getY(screenHeight, i);
                i++;

			    // Should this bar be displayed? 
                if (bar.shouldBeDisplayed()) {

                    // Get the stuff
                    texture = bar.getTexture();
                    fullWidth = bar.getFullWidth();
                    fullHeight = bar.getFullHeight();
                    movingTextureX = bar.getMovingTextureX();
                    movingTextureY = bar.getMovingTextureY();
                    fullBarWidth = bar.getFullBarWidth();
                    movingWidth = bar.getMovingWidth();
                    text = bar.getTextToDisplay();

                    // Actual rendering. First one is the moving bar. Second one is the whole bar.
                    mc.renderEngine.bindTexture(texture);
                    drawTexturedModalRect(x - fullBarWidth - 10, y + 3, movingTextureX, movingTextureY, movingWidth, fullHeight);
                    drawTexturedModalRect(x - fullWidth, y, 0, 0, fullWidth, fullHeight);
					drawCenteredString(mc.fontRenderer, text, x - fullWidth - 15, y + 13, Integer.parseInt("FFFFFF", 16));
				}
            }

		}
	}

	
	// Help determine where to place a stat bar.
	// It's more of a base position, and will be modified for whatever texture it's for.

	// This'll either be right by 0 or right by the rightmost edge of the screen.
	// So pos doesn't actually matter.
	private int getX(int screenWidth, int pos) {
		
		// Figure out where the user specified to put the bars (in config)
		// From there, figure out where exactly to put the single bar, according to the config value.
		// On the left of the screen
		if (OverConfig.CLIENT.barPositions.equals("top left") || OverConfig.CLIENT.barPositions.equals("middle left") || OverConfig.CLIENT.barPositions.equals("bottom left")) {
			
			return 150;
		}
		
		// Right of the screen
		else if (OverConfig.CLIENT.barPositions.equals("top right") || OverConfig.CLIENT.barPositions.equals("middle right") || OverConfig.CLIENT.barPositions.equals("bottom right")) {
			
			return screenWidth - 2;
		}
		
		// "middle right" by default.
		else {
			
			return screenWidth - 2;
		}
	}
	
	// The stat bars are 20 pixels apart, vertically. 
	private int getY(int screenHeight, int pos) {
		
		// Top of the screen
		if (OverConfig.CLIENT.barPositions.equals("top left") || OverConfig.CLIENT.barPositions.equals("top right")) {
			
			// Is this the 1st bar? 2nd bar? etc.
			return 10 + (20 * pos);
		}
		
		// Middle of the screen
		else if (OverConfig.CLIENT.barPositions.equals("middle left") || OverConfig.CLIENT.barPositions.equals("middle right")) {
			
			return (screenHeight / 2) - 30 + (20 * pos);
		}
		
		// Bottom of the screen
		else if (OverConfig.CLIENT.barPositions.equals("bottom left") || OverConfig.CLIENT.barPositions.equals("bottom right")) {
			
			return screenHeight - 80 + (20 * pos);
		}
		
		// Middle by default
		else {
			
			return (screenHeight / 2) - 30 + (20 * pos);
		}
	}
}