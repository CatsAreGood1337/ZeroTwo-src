package me.сс.zerotwo.api.manager;

import me.сс.zerotwo.api.event.events.PacketEvent;
import me.сс.zerotwo.client.Client;
import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.api.util.moduleUtil.TextUtil;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public
class ReloadManager extends Client {

    public String prefix;

    public
    void init ( String prefix ) {
        this.prefix = prefix;
        MinecraftForge.EVENT_BUS.register ( this );
        if ( ! fullNullCheck ( ) ) {
            Command.sendMessage ( TextUtil.RED + "ZeroTwo has been unloaded. Type " + prefix + "reload to reload." );
        }
    }

    public
    void unload ( ) {
        MinecraftForge.EVENT_BUS.unregister ( this );
    }

    @SubscribeEvent
    public
    void onPacketSend ( PacketEvent.Send event ) {
        if ( event.getPacket ( ) instanceof CPacketChatMessage ) {
            CPacketChatMessage packet = event.getPacket ( );
            if ( packet.getMessage ( ).startsWith ( this.prefix ) && packet.getMessage ( ).contains ( "reload" ) ) {
                ZeroTwo.load ( );
                event.setCanceled ( true );
            }
        }
    }
}
