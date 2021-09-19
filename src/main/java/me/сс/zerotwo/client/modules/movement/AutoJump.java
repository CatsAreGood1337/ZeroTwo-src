package me.сс.zerotwo.client.modules.movement;

import me.сс.zerotwo.client.modules.Module;
import net.minecraft.client.settings.KeyBinding;

public class AutoJump
        extends Module {
    public AutoJump() {
        super("AutoJump", "", Module.Category.MOVEMENT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (nullCheck())
            return;

        KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
    }
}
