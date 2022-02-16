package com.overlast.config;

import net.minecraftforge.common.config.Config;

@Config.LangKey("config.overlast:mechanics")
public class Mechanics {

    @Config.LangKey("config.overlast:mechanics.naturalEvolutionScale")
    @Config.RequiresWorldRestart
    @Config.RangeDouble(min = 0, max = 10.0)
    public double naturalEvolutionScale = 1.0;

}
