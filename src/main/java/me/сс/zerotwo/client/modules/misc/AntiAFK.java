package me.сс.zerotwo.client.modules.misc;

import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import java.util.Random;

public class AntiAFK
        extends Module {
    private final Setting<Boolean> swing = this.register(new Setting<Boolean>("Swing", true));
    private final Setting<Boolean> turn = this.register(new Setting<Boolean>("Turn", true));
    private final Setting<Boolean> walk = this.register(new Setting<Boolean>("Walking", true));
    private final Random random = new Random();

    public AntiAFK() {
        super("AntiAFK", "Prevents you from getting kicked for afk.", Module.Category.MISC, false, false, false);
    }

    @Override
    public void onUpdate() {
        if (AntiAFK.mc.playerController.getIsHittingBlock()) {
            return;
        }
        if (AntiAFK.mc.player.ticksExisted % 40 == 0 && this.swing.getValue().booleanValue()) {
            AntiAFK.mc.player.connection.sendPacket((Packet)new CPacketAnimation(EnumHand.MAIN_HAND));
        }
        if (AntiAFK.mc.player.ticksExisted % 15 == 0 && this.turn.getValue().booleanValue()) {
            AntiAFK.mc.player.rotationYaw = this.random.nextInt(360) - 180;
        }
        if (AntiAFK.mc.player.ticksExisted % 45 == 0 && this.walk.getValue().booleanValue()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindForward.getKeyCode(), true);;
        }
        if (!this.swing.getValue().booleanValue() && !this.turn.getValue().booleanValue() && AntiAFK.mc.player.ticksExisted % 80 == 0) {
            AntiAFK.mc.player.jump();
        }
    }
}
