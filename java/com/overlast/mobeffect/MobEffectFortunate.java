package com.overlast.mobeffect;

public class MobEffectFortunate extends MobEffectMod {

    //福运 220,20,60
	public MobEffectFortunate() {
		super("fortunate", false, 220, 20, 60);
	}

    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

}
