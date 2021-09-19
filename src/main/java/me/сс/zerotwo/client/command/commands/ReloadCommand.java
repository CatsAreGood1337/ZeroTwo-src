package me.сс.zerotwo.client.command.commands;

import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.ZeroTwo;

public
class ReloadCommand extends Command {

    public
    ReloadCommand ( ) {
        super ( "reload" , new String[]{} );
    }

    @Override
    public
    void execute ( String[] commands ) {
        ZeroTwo.reload ( );
    }
}
