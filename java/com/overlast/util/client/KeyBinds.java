package com.overlast.util.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyBinds
{
    public static final KeyBinding KEY_SWITCH = new KeyBinding("key.switchhud", Keyboard.KEY_COMMA, "key.overlast");

    public static void register()
    {
        ClientRegistry.registerKeyBinding(KEY_SWITCH);
    }
}
