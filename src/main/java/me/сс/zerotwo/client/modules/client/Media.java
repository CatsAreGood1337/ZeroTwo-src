package me.сс.zerotwo.client.modules.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;

public class Media extends Module {
    public final Setting<String> NameString = register(new Setting<Object>("Name", "New Name Here..."));
    private static Media instance;

    public Media() {
        super("Media", "Changes name", Module.Category.CLIENT, false, false, false);
        instance = this;
    }

    @Override
    public void onEnable() {
        Command.sendMessage(ChatFormatting.GRAY + "Success! Name succesfully changed to " + ChatFormatting.GREEN + NameString.getValue());
    }

    public static Media getInstance() {
        if (instance == null) {
            instance = new Media();
        }
        return instance;
    }
}
