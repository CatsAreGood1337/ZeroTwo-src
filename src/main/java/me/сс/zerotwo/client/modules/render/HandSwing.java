package me.сс.zerotwo.client.modules.render;

import me.сс.zerotwo.api.event.events.Packet;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class HandSwing extends Module {
    private Setting<Hand> hand = register(new Setting("Hand", Hand.OFFHAND));

    public HandSwing() {
        super("HandSwing", "Changes the hand you swing with", Module.Category.RENDER, false, false, false);
    }

    public void onUpdate() {
        if (mc.world == null)
            return;
        if (hand.getValue().equals(Hand.OFFHAND)) {
            mc.player.swingingHand = EnumHand.OFF_HAND;
        }
        if (hand.getValue().equals(Hand.MAINHAND)) {
            mc.player.swingingHand = EnumHand.MAIN_HAND;
        }
    }

    @SubscribeEvent
    public void onPacket(final Packet event) {
        if (nullCheck() || event.getType() == Packet.Type.INCOMING) {
            return;
        }
        if (event.getPacket() instanceof CPacketAnimation) {
            event.setCanceled(true);
        }
    }
    public enum Hand {
        OFFHAND,
        MAINHAND,
        PACKETSWING
    }
}
