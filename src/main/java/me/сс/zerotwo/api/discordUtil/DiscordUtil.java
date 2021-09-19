package me.сс.zerotwo.api.discordUtil;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.сс.zerotwo.ZeroTwo;
import net.minecraft.client.Minecraft;

public class DiscordUtil
{
    private static final String ClientId = "869554466280579082";
    private static final Minecraft mc;
    private static final DiscordRPC rpc;
    public static DiscordRichPresence presence;
    private static String details;
    private static String state;
    
    public static void init() {
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("DiscordUtil RPC disconnected, var1: " + var1 + ", var2: " + var2));
        DiscordUtil.rpc.Discord_Initialize("869554466280579082", handlers, true, "");
        DiscordUtil.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordUtil.presence.details = "playing with " + mc.player.getName();
        DiscordUtil.presence.state = "Main Menu";
        DiscordUtil.presence.largeImageKey = "zero";
        DiscordUtil.presence.largeImageText = ZeroTwo.MODVER;
        DiscordUtil.rpc.Discord_UpdatePresence(DiscordUtil.presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DiscordUtil.rpc.Discord_RunCallbacks();
                    DiscordUtil.details = "playing with " + mc.player.getName();
                    DiscordUtil.state = "";
                    if (DiscordUtil.mc.isIntegratedServerRunning()) {
                        DiscordUtil.state = "Playing on Singleplayer";
                    } else
                    if (DiscordUtil.mc.getCurrentServerData() != null) {
                        if (!DiscordUtil.mc.getCurrentServerData().serverIP.equals("")) {
                            DiscordUtil.state = "chilling " + DiscordUtil.mc.getCurrentServerData().serverIP;
                        }
                    }
                    else {
                        DiscordUtil.state = "owning ";
                    }
                    if (!DiscordUtil.details.equals(DiscordUtil.presence.details) || !DiscordUtil.state.equals(DiscordUtil.presence.state)) {
                        DiscordUtil.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    DiscordUtil.presence.details = DiscordUtil.details;
                    DiscordUtil.presence.state = DiscordUtil.state;
                    DiscordUtil.rpc.Discord_UpdatePresence(DiscordUtil.presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }
    
    static {
        mc = Minecraft.getMinecraft();
        rpc = DiscordRPC.INSTANCE;
        DiscordUtil.presence = new DiscordRichPresence();
    }

    public static void shutdown() {
        rpc.Discord_Shutdown();
    }
}

