package me.сс.zerotwo.client.command.commands;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.client.modules.client.ClickGui;
import me.сс.zerotwo.api.util.moduleUtil.TextUtil;

public
class PrefixCommand extends Command {

    public
    PrefixCommand ( ) {
        super ( "prefix" , new String[]{"<char>"} );
    }

    @Override
    public
    void execute ( String[] commands ) {
        if ( commands.length == 1 ) {
            Command.sendMessage ( TextUtil.RED + "Specify a new prefix." );
            return;
        }

        ( ZeroTwo.moduleManager.getModuleByClass ( ClickGui.class ) ).prefix.setValue ( commands[0] );
        Command.sendMessage ( "Prefix set to " + TextUtil.GREEN + ZeroTwo.commandManager.getPrefix ( ) );
    }
}
