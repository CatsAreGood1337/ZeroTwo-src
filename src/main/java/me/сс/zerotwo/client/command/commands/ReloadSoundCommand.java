package me.сс.zerotwo.client.command.commands;

import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.api.util.moduleUtil.TextUtil;
import net.minecraft.client.audio.SoundManager;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public
class ReloadSoundCommand extends Command {

    public
    ReloadSoundCommand ( ) {
        super ( "sound" , new String[]{} );
    }

    @Override
    public
    void execute ( String[] commands ) {
        try {
            SoundManager sndManager = ObfuscationReflectionHelper.getPrivateValue ( net.minecraft.client.audio.SoundHandler.class , mc.getSoundHandler ( ) , new String[]{"sndManager" , "field_147694_f"} );
            sndManager.reloadSoundSystem ( );
            sendMessage ( TextUtil.GREEN + "Reloaded Sound System." );
        } catch ( Exception e ) {
            System.out.println ( "Could not restart sound manager: " + e );
            e.printStackTrace ( );
            sendMessage ( TextUtil.RED + "Couldnt Reload Sound System!" );
        }
    }
}
