package me.сс.zerotwo.client.command.commands;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.command.Command;

public
class UnloadCommand extends Command {

    public
    UnloadCommand ( ) {
        super ( "unload" , new String[]{} );
    }

    @Override
    public
    void execute ( String[] commands ) {
        ZeroTwo.unload ( true );
    }
}
