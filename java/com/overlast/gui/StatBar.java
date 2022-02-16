package com.overlast.gui;

import com.overlast.config.OverConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class StatBar {
	
	/*
	 * Defines a statistic bar to be displayed on the GUI, the HUD, whatever you wanna call it.
	 */
	
	// Type
	public enum StatType { SANITY, EVOLUTION }
	private StatType type;


	// Texture
	private ResourceLocation texture;
	
	// Full width and height  
	private int fullWidth;
	private int fullHeight;
	
	// Full width of the bar that actually MOVES.
	private int defaultBarWidth;
	
	// Starting position of the moving bar in the texture file. 
	private int movingTextureX;
	private int movingTextureY;

	// Values it holds (actual and max)  
    private float value = 0f;
    private float maxValue = 100f;
	
	public StatBar(StatType type, int fullWidth, int fullHeight, int defaultBarWidth, int movingTextureX, int movingTextureY, ResourceLocation texture) {
		
		this.type = type;
		this.fullWidth = fullWidth;
		this.fullHeight = fullHeight;
		this.defaultBarWidth = defaultBarWidth;
		this.movingTextureX = movingTextureX;
		this.movingTextureY = movingTextureY;
		this.texture = texture;
	}

	public void setValue(float value) {

	    this.value = value;
    }

    public void setMaxValue(float value) {

	    this.maxValue = value;
    }

	
	public int getFullWidth() {
		
		return this.fullWidth;
	}
	
	public int getFullHeight() {
		
		return this.fullHeight;
	}
	
	public int getMovingTextureX() {
		
		return this.movingTextureX;
	}
	
	public int getMovingTextureY() {
		
		return this.movingTextureY;
	}
	
	public int getFullBarWidth() {
		
		return this.defaultBarWidth;
	}
	
	public ResourceLocation getTexture() {
		
		return this.texture;
	}
	public void setTexture(ResourceLocation texture) {
		this.texture = texture;
	}
	
	// Should this bar even be displayed?
	public boolean shouldBeDisplayed() {
		
		// Minecraft instance. Figure out if f3 debug mode is on.
		Minecraft mc = Minecraft.getMinecraft();
		
		boolean isDebugEnabled = mc.gameSettings.showDebugInfo;
		
		// Don't display most crap if debug mode is enabled.
		if (!isDebugEnabled) {
				return true;
			}
			else if (type == StatType.EVOLUTION) {
				return true;
			}
			else {
				return false;
			}
	}
	
	// Determine the width of the bar. 确定属性条的实际宽度。
	public int getMovingWidth() {

		// One "unit". The width of the bar PER one thirst, one temperature, one sanity, etc. 一个单位即为一个条的宽度
		//这里默认是800/100，即单个单元宽度为8，数字更小渲染更密。
		double singleUnit = (double) defaultBarWidth / maxValue;
		
		// Multiplication of singleUnit to get the width
		int width = (int) (singleUnit * value);
		
		return width;
	}
	
	// Determine text to be displayed.
	public String getTextToDisplay() {
		
		// Round the actual value
		double roundedValue = (double) (Math.round(value * 10)) / 10;

		double evoValue = (double) (Math.round(value)) / 1.0;
		String text;
		
		// String
		    if (type == StatType.EVOLUTION) {

		    	if(roundedValue>=1000000) {
		    		text =String.format("%.2f",evoValue/10000)+ "wP";
				}else {
					text = roundedValue + "P";
				}
		}

		else {
			
			text = Double.toString(roundedValue);
		}
		
		return text;
	}
}