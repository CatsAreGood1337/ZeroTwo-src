package me.сс.zerotwo.client.command;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.Client;
import me.сс.zerotwo.api.util.moduleUtil.TextUtil;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract
class Command extends Client {

    protected String name;
    protected String[] commands;

    public
    Command ( String name ) {
        super ( name );
        this.name = name;
        this.commands = new String[]{""};
    }

    public
    Command ( String name , String[] commands ) {
        super ( name );
        this.name = name;
        this.commands = commands;
    }

    public static
    void sendMessage ( String message , boolean notification ) {
        sendSilentMessage ( ZeroTwo.commandManager.getClientMessage ( ) + " " + TextUtil.RESET + message );
        if ( notification )
            ZeroTwo.notificationManager.addNotification ( message , 3000 );
    }

    public static
    void sendMessage ( String message ) {
        sendSilentMessage ( ZeroTwo.commandManager.getClientMessage ( ) + " " + TextUtil.RESET + message );
    }

    public static
    void sendSilentMessage ( String message ) {
        if ( nullCheck ( ) ) return;
        mc.player.sendMessage ( new ChatMessage ( message ) );
    }

    public static
    String getCommandPrefix ( ) {
        return ZeroTwo.commandManager.getPrefix ( );
    }

    public abstract
    void execute ( String[] commands );// {}

    public
    String getName ( ) {
        return this.name;
    }

    public
    String[] getCommands ( ) {
        return this.commands;
    }

    public static
    class ChatMessage extends TextComponentBase {

        private final String text;

        public
        ChatMessage ( String text ) {
            Pattern pattern = Pattern.compile ( "&[0123456789abcdefrlosmk]" );
            Matcher matcher = pattern.matcher ( text );
            StringBuffer stringBuffer = new StringBuffer ( );
            while ( matcher.find ( ) ) {
                String replacement = "\u00A7" + matcher.group ( ).substring ( 1 );
                matcher.appendReplacement ( stringBuffer , replacement );
            }
            matcher.appendTail ( stringBuffer );
            this.text = stringBuffer.toString ( );
        }

        public
        String getUnformattedComponentText ( ) {
            return text;
        }

        @Override
        public
        ITextComponent createCopy ( ) {
            return new ChatMessage ( text );
        }
    }
}
