package me.сс.zerotwo.client.command.commands;

import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Bind;
import me.сс.zerotwo.ZeroTwo;
import org.lwjgl.input.Keyboard;

public
class BindCommand extends Command {

    public
    BindCommand ( ) {
        super ( "bind" , new String[]{"<module>" , "<bind>"} );
    }

    @Override
    public
    void execute ( String[] commands ) {
        if ( commands.length == 1 ) {
            sendMessage ( "Please specify a module." );
            return;
        }

        String rkey = commands[1];
        String moduleName = commands[0];

        Module module = ZeroTwo.moduleManager.getModuleByName ( moduleName );

        if ( module == null ) {
            sendMessage ( "Unknown module '" + module + "'!" );
            return;
        }

        if ( rkey == null ) {
            sendMessage ( module.getName ( ) + " is bound to &b" + module.getBind ( ).toString ( ) );
            return;
        }

        int key = Keyboard.getKeyIndex ( rkey.toUpperCase ( ) );

        if ( rkey.equalsIgnoreCase ( "none" ) ) {
            key = - 1;
        }

        if ( key == 0 ) {
            sendMessage ( "Unknown key '" + rkey + "'!" );
            return;
        }

        module.bind.setValue ( new Bind ( key ) );
        sendMessage ( "Bind for &b" + module.getName ( ) + "&r set to &b" + rkey.toUpperCase ( ) );
    }
}
