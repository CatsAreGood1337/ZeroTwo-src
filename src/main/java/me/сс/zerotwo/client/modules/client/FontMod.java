package me.сс.zerotwo.client.modules.client;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.api.event.events.ClientEvent;
import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import me.сс.zerotwo.api.util.moduleUtil.TextUtil;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public
class FontMod extends Module {

    private static FontMod INSTANCE = new FontMod ( );
    public Setting < String > fontName = register ( new Setting ( "FontName" , "Arial" , "Name of the font." ) );
    public Setting < Integer > fontSize = register ( new Setting ( "FontSize" , 19 , "Size of the font." ) );
    public Setting < Integer > fontStyle = register ( new Setting ( "FontStyle" , 0 , "Style of the font." ) );
    public Setting < Boolean > antiAlias = register ( new Setting ( "AntiAlias" , true , "Smoother font." ) );
    public Setting < Boolean > fractionalMetrics = register ( new Setting ( "Metrics" , true , "Thinner font." ) );
    public Setting < Boolean > shadow = register ( new Setting ( "Shadow" , true , "Less shadow offset font." ) );
    public Setting < Boolean > showFonts = register ( new Setting ( "Fonts" , false , "Shows all fonts." ) );
    private boolean reloadFont = false;

    public FontMod() {
        super("FontMod", "", Category.CLIENT, true, false, true);
        setInstance ( );
    }

    public static
    FontMod getInstance ( ) {
        if ( INSTANCE == null ) {
            INSTANCE = new FontMod ( );
        }
        return INSTANCE;
    }

    public static
    boolean checkFont ( String font , boolean message ) {
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment ( ).getAvailableFontFamilyNames ( );
        for (String s : fonts) {
            if ( ! message && s.equals ( font ) ) {
                return true;
            } else if ( message ) {
                Command.sendMessage ( s );
            }
        }
        return false;
    }

    private
    void setInstance ( ) {
        INSTANCE = this;
    }

    @SubscribeEvent
    public
    void onSettingChange ( ClientEvent event ) {
        if ( event.getStage ( ) == 2 ) {
            Setting setting = event.getSetting ( );
            if ( setting != null && setting.getFeature ( ).equals ( this ) ) {
                if ( setting.getName ( ).equals ( "FontName" ) ) {
                    if ( ! checkFont ( setting.getPlannedValue ( ).toString ( ) , false ) ) {
                        Command.sendMessage ( TextUtil.RED + "That font doesnt exist." );
                        event.setCanceled ( true );
                        return;
                    }
                }
                reloadFont = true;
            }
        }
    }

    @Override
    public
    void onTick ( ) {
        if ( showFonts.getValue ( ) ) {
            checkFont ( "Hello" , true );
            Command.sendMessage ( "Current Font: " + fontName.getValue ( ) );
            showFonts.setValue ( false );
        }

        if ( reloadFont ) {
            ZeroTwo.textManager.init ( false );
            reloadFont = false;
        }
    }
}
