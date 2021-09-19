package me.сс.zerotwo.client.modules.misc;

import me.сс.zerotwo.client.modules.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GreenText extends Module {

    public GreenText()
    {
        super ( "GreenText" , "GreenText" , Module.Category.MISC , true , false , false );
    }

    String GREATERTHAN = ">";

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (event.getMessage().startsWith("/") || event.getMessage().startsWith(".")
                || event.getMessage().startsWith(",") || event.getMessage().startsWith("-")
                || event.getMessage().startsWith("$") || event.getMessage().startsWith("*")) return;
        event.setMessage(GREATERTHAN + event.getMessage());
    }
}
