package me.сс.zerotwo.client;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.gui.zerotwoGui;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import me.сс.zerotwo.api.manager.TextManager;
import me.сс.zerotwo.api.util.moduleUtil.Util;

import java.util.ArrayList;
import java.util.List;

public
class Client implements Util {

    public List < Setting > settings = new ArrayList <> ( );
    public TextManager renderer = ZeroTwo.textManager;
    private String name;

    public Client( ) {
    }

    public Client( String name ) {
        this.name = name;
    }

    public static
    boolean nullCheck ( ) {
        return mc.player == null;
    }

    public static
    boolean fullNullCheck ( ) {
        return mc.player == null || mc.world == null;
    }

    public
    String getName ( ) {
        return this.name;
    }

    public
    List < Setting > getSettings ( ) {
        return this.settings;
    }

    public
    boolean hasSettings ( ) {
        return ! this.settings.isEmpty ( );
    }

    public
    boolean isEnabled ( ) {
        if ( this instanceof Module ) {
            return ( (Module) this ).isOn ( );
        }
        return false;
    }

    public
    boolean isDisabled ( ) {
        return ! isEnabled ( );
    }

    public
    Setting register ( Setting setting ) {
        setting.setFeature ( this );
        this.settings.add ( setting );
        if ( this instanceof Module && mc.currentScreen instanceof zerotwoGui) {
            zerotwoGui.getInstance ( ).updateModule ( (Module) this );
        }
        return setting;
    }

    public
    void unregister ( Setting settingIn ) {
        List < Setting > removeList = new ArrayList <> ( );
        for (Setting setting : this.settings) {
            if ( setting.equals ( settingIn ) ) {
                removeList.add ( setting );
            }
        }

        if ( ! removeList.isEmpty ( ) ) {
            this.settings.removeAll ( removeList );
        }

        if ( this instanceof Module && mc.currentScreen instanceof zerotwoGui) {
            zerotwoGui.getInstance ( ).updateModule ( (Module) this );
        }
    }

    public
    Setting getSettingByName ( String name ) {
        for (Setting setting : this.settings) {
            if ( setting.getName ( ).equalsIgnoreCase ( name ) ) {
                return setting;
            }
        }
        return null;
    }

    public
    void reset ( ) {
        for (Setting setting : this.settings) {
            setting.setValue ( setting.getDefaultValue ( ) );
        }
    }

    public
    void clearSettings ( ) {
        this.settings = new ArrayList <> ( );
    }
}
