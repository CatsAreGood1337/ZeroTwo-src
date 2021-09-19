package me.сс.zerotwo.client.command.commands;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.command.Command;

public
class HelpCommand extends Command {

    public
    HelpCommand ( ) {
        super ( "help" );
    }

    @Override
    public
    void execute ( String[] commands ) {
        sendMessage ( "You can use following commands: " );
        for (Command command : ZeroTwo.commandManager.getCommands ( )) {
            sendMessage ( ZeroTwo.commandManager.getPrefix ( ) + command.getName ( ) );
        }
    }
}
