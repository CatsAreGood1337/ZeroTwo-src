package me.сс.zerotwo.client.command.commands;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.api.manager.FriendManager;
import me.сс.zerotwo.api.util.moduleUtil.TextUtil;

public
class FriendCommand extends Command {

    public
    FriendCommand ( ) {
        super ( "friend" , new String[]{"<add/del/name/clear>" , "<name>"} );
    }

    @Override
    public
    void execute ( String[] commands ) {
        if ( commands.length == 1 ) {
            if ( ZeroTwo.friendManager.getFriends ( ).isEmpty ( ) ) {
                sendMessage ( "You currently dont have any friends added." );
            } else {
                String f = "Friends: ";
                for (FriendManager.Friend friend : ZeroTwo.friendManager.getFriends ( )) {
                    try {
                        f += friend.getUsername ( ) + ", ";
                    } catch ( Exception e ) {
                        continue;
                    }
                }
                sendMessage ( f );
            }
            return;
        }

        if ( commands.length == 2 ) {
            switch (commands[0]) {
                case "reset":
                    ZeroTwo.friendManager.onLoad ( );
                    sendMessage ( "Friends got reset." );
                    break;
                default:
                    sendMessage ( commands[0] + ( ZeroTwo.friendManager.isFriend ( commands[0] ) ? " is friended." : " isnt friended." ) );
                    break;
            }
            return;
        }

        if ( commands.length >= 2 ) {
            switch (commands[0]) {
                case "add":
                    ZeroTwo.friendManager.addFriend ( commands[1] );
                    sendMessage ( TextUtil.AQUA + commands[1] + " has been friended" );
                    break;
                case "del":
                    ZeroTwo.friendManager.removeFriend ( commands[1] );
                    sendMessage ( TextUtil.RED + commands[1] + " has been unfriended" );
                    break;
                default:
                    sendMessage ( TextUtil.RED + "Bad Command, try: friend <add/del/name> <name>." );
            }
        }
    }
}
