package me.сс.zerotwo;

import me.сс.zerotwo.api.manager.*;
import me.сс.zerotwo.api.util.moduleUtil.IconUtil;
import me.сс.zerotwo.client.modules.exploit.DonkeyFinder;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.io.InputStream;
import java.nio.ByteBuffer;

@Mod(modid = ZeroTwo.MODID, name = ZeroTwo.MODNAME, version = ZeroTwo.MODVER)
public class ZeroTwo {

    public static final String MODID = "zerotwo";
    public static final String MODNAME = "ZeroTwo";
    public static final String MODVER = "1.1";
    public static final Logger LOGGER = LogManager.getLogger ( "zerotwo" );
    public static ModuleManager moduleManager;
    public static SpeedManager speedManager;
    public static PositionManager positionManager;
    public static RotationManager rotationManager;
    public static CommandManager commandManager;
    public static EventManager eventManager;
    public static ConfigManager configManager;
    public static FileManager fileManager;
    public static FriendManager friendManager;
    public static TextManager textManager;
    public static ColorManager colorManager;
    public static ServerManager serverManager;
    public static PotionManager potionManager;
    public static InventoryManager inventoryManager;
    public static TimerManager timerManager;
    public static PacketManager packetManager;
    public static ReloadManager reloadManager;
    public static TotemPopManager totemPopManager;
    public static HoleManager holeManager;
    public static NotificationManager notificationManager;
    public static SafetyManager safetyManager;
    @Mod.Instance
    public static ZeroTwo INSTANCE;
    private static String name = "zerotwo";
    private static boolean unloaded = false;

    public static
    String getName ( ) {
        return name;
    }

    public static
    void setName ( String newName ) {
        name = newName;
    }

    public static
    void load ( ) {
        LOGGER.info ( "\n\nLoading zerotwo " + MODVER );
        unloaded = false;
        if ( reloadManager != null ) {
            reloadManager.unload ( );
            reloadManager = null;
        }

        totemPopManager = new TotemPopManager ( );
        timerManager = new TimerManager ( );
        packetManager = new PacketManager ( );
        serverManager = new ServerManager ( );
        colorManager = new ColorManager ( );
        textManager = new TextManager ( );
        moduleManager = new ModuleManager ( );
        speedManager = new SpeedManager ( );
        rotationManager = new RotationManager ( );
        positionManager = new PositionManager ( );
        commandManager = new CommandManager ( );
        eventManager = new EventManager ( );
        configManager = new ConfigManager ( );
        fileManager = new FileManager ( );
        friendManager = new FriendManager ( );
        potionManager = new PotionManager ( );
        inventoryManager = new InventoryManager ( );
        holeManager = new HoleManager ( );
        notificationManager = new NotificationManager ( );
        safetyManager = new SafetyManager ( );
        LOGGER.info ( "Initialized Managers" );

        moduleManager.init ( );
        LOGGER.info ( "Modules loaded." );
        configManager.init ( );
        eventManager.init ( );
        LOGGER.info ( "EventManager loaded." );
        textManager.init ( true );
        moduleManager.onLoad ( );
        totemPopManager.init ( );
        timerManager.init ( );
        LOGGER.info ( "zerotwo initialized!\n" );

    }

    public static
    void unload ( boolean unload ) {
        LOGGER.info ( "\n\nUnloading zerotwo " + MODVER );
        if ( unload ) {
            reloadManager = new ReloadManager ( );
            reloadManager.init ( commandManager != null ? commandManager.getPrefix ( ) : "." );
        }
        onUnload ( );
        eventManager = null;
        holeManager = null;
        timerManager = null;
        moduleManager = null;
        totemPopManager = null;
        serverManager = null;
        colorManager = null;
        textManager = null;
        speedManager = null;
        rotationManager = null;
        positionManager = null;
        commandManager = null;
        configManager = null;
        fileManager = null;
        friendManager = null;
        potionManager = null;
        inventoryManager = null;
        notificationManager = null;
        safetyManager = null;
        LOGGER.info ( "zerotwo unloaded!\n" );
    }

    public static
    void reload ( ) {
        unload ( false );
        load ( );
    }

    public static
    void onUnload ( ) {
        if ( ! unloaded ) {
            eventManager.onUnload ( );
            moduleManager.onUnload ( );
            configManager.saveConfig ( configManager.config.replaceFirst ( "zerotwo/" , "" ) );
            moduleManager.onUnloadPost ( );
            timerManager.unload ( );
            unloaded = true;
        }
    }

    @Mod.EventHandler
    public
    void preInit ( FMLPreInitializationEvent event ) {
    }

    public static void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (InputStream inputStream16x = Minecraft.class.getResourceAsStream("/zerotwo/icons/icon-16x.png");
                 InputStream inputStream32x = Minecraft.class.getResourceAsStream("/zerotwo/icons/icon-32x.png")) {
                ByteBuffer[] icons = new ByteBuffer[]{IconUtil.INSTANCE.readImageToBuffer(inputStream16x), IconUtil.INSTANCE.readImageToBuffer(inputStream32x)};
                Display.setIcon(icons);
            } catch (Exception e) {
                ZeroTwo.LOGGER.error("Couldn't set Windows Icon", e);
            }
        }
    }

    private void setWindowsIcon() {
        ZeroTwo.setWindowIcon();
    }

    @Mod.EventHandler
    public
    void init ( FMLInitializationEvent event ) {
        Display.setTitle ( "zerotwo - v." + MODVER );
        load ( );
        setWindowsIcon();
    }
}

