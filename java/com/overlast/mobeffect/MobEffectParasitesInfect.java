package com.overlast.mobeffect;

public class MobEffectParasitesInfect extends MobEffectMod {

    //寄生感染，适中的紫罗兰红色	#C71585	199,21,133
	public MobEffectParasitesInfect() {
		super("parasites_infect", false, 199, 21, 133);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

}
