package me.сс.zerotwo.client.modules.misc;

import me.сс.zerotwo.api.discordUtil.DiscordUtil;
import me.сс.zerotwo.client.modules.Module;

public
class RPC extends Module {
    public static RPC INSTANCE;

    public
    RPC ( ) {
        super ( "RPC" , "DiscordUtil rich presence" , Category.CLIENT , false , false , false );
        INSTANCE = this;
    }

    @Override
    public
    void onEnable ( ) {
        super.onEnable ( );
        DiscordUtil.init();
    }

    @Override
    public
    void onDisable ( ) {
        super.onDisable ( );
        DiscordUtil.shutdown ( );
    }
}
