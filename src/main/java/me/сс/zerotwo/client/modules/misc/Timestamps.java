package me.сс.zerotwo.client.modules.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.сс.zerotwo.client.modules.Module;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Timestamps extends Module {

    public
    Timestamps ( ) {
        super ( "Timestamps" , "" , Category.MISC , true , false , false );
    }


    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onClientChatReceived(ClientChatReceivedEvent event) {
        Date date = new Date();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("HH:mm");
        String strDate = dateFormatter.format(date);
        TextComponentString time = new TextComponentString(ChatFormatting.LIGHT_PURPLE + "<" + ChatFormatting.LIGHT_PURPLE + strDate + ChatFormatting.LIGHT_PURPLE + ">" + ChatFormatting.RESET + " ");
        event.setMessage(time.appendSibling(event.getMessage()));
    }
    public void onDisable() {MinecraftForge.EVENT_BUS.unregister(this);}
}
