package me.сс.zerotwo.client.modules.movement;

import me.сс.zerotwo.client.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public
class NoVoid extends Module {
    public NoVoid() {
        super("NoVoid", "", Category.MOVEMENT, true, false, false);
    }

    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        if (this.mc.player.onGround) {
            return;
        }
        if (!this.mc.player.onGround && this.mc.player.fallDistance >= 4.0f) {
            this.mc.player.motionY = 0.1;
        }
    }
}


