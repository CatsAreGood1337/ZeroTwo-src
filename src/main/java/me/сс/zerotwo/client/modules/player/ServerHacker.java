package me.сс.zerotwo.client.modules.player;

import me.сс.zerotwo.client.modules.Module;
import net.minecraft.client.Minecraft;

public class ServerHacker extends Module {

    public ServerHacker() {
        super("ServerHacker", "", Module.Category.EXPLOIT, true, false, false);
    }

    private Minecraft mc = Minecraft.getMinecraft();

    @Override
    public void onEnable() {
        if(mc.player != null)
            mc.player.sendChatMessage("my coords (X) " + ( mc.player.getPosition().getX() ) + " (Y) " + (mc.player.getPosition().getY()) + " (Z) " + (mc.player.getPosition().getZ()) );
        toggle();
    }
}