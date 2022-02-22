package com.overlast.config;

import net.minecraftforge.common.config.Config;

@Config.LangKey("config.overlast:mechanics")
public class Mechanics {

    @Config.LangKey("config.overlast:mechanics.naturalEvolutionScale")
    @Config.RequiresWorldRestart
    @Config.RangeDouble(min = 0, max = 10.0)
    public double naturalEvolutionScale = 1.0;

    @Config.LangKey("config.overlast:mechanics.showRequestDirtyClock")
    @Config.RequiresWorldRestart
    public boolean showRequestDirtyClock = false;
    
}
