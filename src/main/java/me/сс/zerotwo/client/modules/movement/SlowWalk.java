package me.сс.zerotwo.client.modules.movement;

import me.сс.zerotwo.api.event.events.WalkEvent;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SlowWalk
        extends Module {

    public Setting< Boolean > sneakOnly = register ( new Setting ( "Sneak" , true ) );

    public SlowWalk() {
        super("SlowWalk", "", Module.Category.MOVEMENT, true, false, false);
    }

    private Minecraft mc = Minecraft.getMinecraft();

    @SubscribeEvent
    public void onUpdateWalkingPlayer(WalkEvent event) {
        if(mc.world == null) return;
        if(sneakOnly.getValue()) {
            if(mc.player.isSneaking()) {
                mc.player.motionX = 0.001;
                mc.player.motionZ = 0.001;
            }

        } else {
            mc.player.motionX = 0.001;
            mc.player.motionZ = 0.001;
        }
    }
}

