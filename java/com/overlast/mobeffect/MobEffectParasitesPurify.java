package com.overlast.mobeffect;

public class MobEffectParasitesPurify extends MobEffectMod {

    //寄生净化，抵抗负面效果 柠檬薄纱	#FFFACD	255,250,205
	public MobEffectParasitesPurify() {
		super("parasites_purify", true, 255, 250, 205);
	}
	
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }

}
