package me.сс.zerotwo.client.modules.misc;

import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.scoreboard.ScorePlayerTeam;

public class ExtraTab extends Module {

    public Setting< Integer > tabsize = register ( new Setting("TabSize", 250, 1, 600) );

    public ExtraTab() {
        super ( "ExtraTab" , "GreenText" , Module.Category.MISC , true , false , false );
    }

    public static String getPlayerName(NetworkPlayerInfo networkPlayerInfoIn) {
        String dname = networkPlayerInfoIn.getDisplayName() != null ? networkPlayerInfoIn.getDisplayName().getFormattedText() : ScorePlayerTeam.formatPlayerName(networkPlayerInfoIn.getPlayerTeam(), networkPlayerInfoIn.getGameProfile().getName());
        return dname;
    }
}
