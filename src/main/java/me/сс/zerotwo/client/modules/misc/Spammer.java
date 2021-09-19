package me.сс.zerotwo.client.modules.misc;

import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import me.сс.zerotwo.api.util.moduleUtil.FileUtil;
import me.сс.zerotwo.api.util.moduleUtil.TextUtil;
import me.сс.zerotwo.api.util.moduleUtil.Timer;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public
class Spammer extends Module {

    private static final String fileName = "client/util/Spammer.txt";
    private static final String defaultMessage = "> ZeroTwo owns u |here - discord.gg/VPF7kEWrBu ";
    private static final List < String > spamMessages = new ArrayList ( );
    private static final Random rnd = new Random ( );
    private final Timer timer = new Timer ( );
    private final List < String > sendPlayers = new ArrayList <> ( );
    public Setting < Integer > delay = register ( new Setting ( "Delay" , 10 , 1 , 20 ) );
    public Setting < Boolean > random = register ( new Setting ( "Random" , false ) );
    public Setting < Boolean > loadFile = register ( new Setting ( "LoadFile" , false ) );

    public
    Spammer ( ) {
        super ( "Spammer" , "Spams stuff." , Category.MISC , true , false , false );
    }

    @Override
    public
    void onLoad ( ) {
        readSpamFile ( );
        this.disable ( );
    }

    @Override
    public
    void onEnable ( ) {
        if ( fullNullCheck ( ) ) {
            this.disable ( );
            return;
        }
        readSpamFile ( );
    }

    @Override
    public
    void onLogin ( ) {
        this.disable ( );
    }

    @Override
    public
    void onLogout ( ) {
        this.disable ( );
    }

    @Override
    public
    void onDisable ( ) {
        spamMessages.clear ( );
        timer.reset ( );
    }

    @Override
    public void onUpdate ( ) {
        if ( fullNullCheck ( ) ) {
            this.disable ( );
            return;
        }

        if ( loadFile.getValue ( ) ) {
            readSpamFile ( );
            loadFile.setValue ( false );
        }

        if ( ! timer.passedS ( delay.getValue ( ) ) ) {
            return;
        }

        if ( spamMessages.size ( ) > 0 ) {
            String messageOut;
            if ( random.getValue ( ) ) {
                int index = rnd.nextInt ( spamMessages.size ( ) );
                messageOut = spamMessages.get ( index );
                spamMessages.remove ( index );
            } else {
                messageOut = spamMessages.get ( 0 );
                spamMessages.remove ( 0 );
            }
            spamMessages.add ( messageOut );

            mc.player.connection.sendPacket ( new CPacketChatMessage ( messageOut.replaceAll ( TextUtil.SECTIONSIGN , "" ) ) );
        }
        timer.reset ( );
    }

    private
    void readSpamFile ( ) {
        List < String > fileInput = FileUtil.readTextFileAllLines ( fileName );
        Iterator < String > i = fileInput.iterator ( );
        spamMessages.clear ( );
        while ( i.hasNext ( ) ) {
            String s = i.next ( );
            if ( ! s.replaceAll ( "\\s" , "" ).isEmpty ( ) ) {
                spamMessages.add ( s );
            }
        }
        if ( spamMessages.size ( ) == 0 ) {
            spamMessages.add ( defaultMessage );
        }
    }
}
