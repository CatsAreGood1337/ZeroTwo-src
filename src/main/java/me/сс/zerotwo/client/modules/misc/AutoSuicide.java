package me.сс.zerotwo.client.modules.misc;

import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.network.play.client.CPacketChatMessage;


public
class AutoSuicide extends Module {

    private final Setting < Boolean > suicide = register ( new Setting < Boolean > ( "suicide" , false ) );


    public
    AutoSuicide ( ) {
        super ( "AutoSuicide" , "commits suicide" , Category.MISC , true , false , false );
    }

    @Override
    public
    void onEnable ( ) {
        if ( suicide.getValue ( ) ) {
            mc.player.connection.sendPacket ( new CPacketChatMessage ( "/suicide" ) );
            suicide.setValue ( false );
            toggle ( );
        } else
            mc.player.connection.sendPacket ( new CPacketChatMessage ( "/kill" ) );
        toggle ( );
    }
}



