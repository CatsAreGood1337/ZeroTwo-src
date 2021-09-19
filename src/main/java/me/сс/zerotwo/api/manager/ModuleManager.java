package me.сс.zerotwo.api.manager;

import me.сс.zerotwo.api.event.events.Render2DEvent;
import me.сс.zerotwo.api.event.events.Render3DEvent;
import me.сс.zerotwo.client.Client;
import me.сс.zerotwo.client.gui.zerotwoGui;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.modules.client.*;
import me.сс.zerotwo.client.modules.combat.*;
import me.сс.zerotwo.client.modules.exploit.*;
import me.сс.zerotwo.client.modules.exploit.LiquidInteract;
import me.сс.zerotwo.client.modules.misc.*;
import me.сс.zerotwo.client.modules.movement.*;
import me.сс.zerotwo.client.modules.player.*;
import me.сс.zerotwo.api.util.moduleUtil.Util;
import me.сс.zerotwo.client.modules.player.InventoryManager;
import me.сс.zerotwo.client.modules.render.*;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public
class ModuleManager extends Client {

    public static ArrayList < Module > modules = new ArrayList <> ( );
    public List < Module > sortedModules = new ArrayList <> ( );

    public static
    void onServerUpdate ( ) {
        modules.stream ( ).filter ( Client::isEnabled ).forEach ( Module::onServerUpdate );
    }

    public
    void init ( ) {
        //COMBAT
        modules.add ( new AutoTrap ( ) );
        modules.add ( new Offhand( ) );
        modules.add ( new AutoArmor ( ) );
        modules.add ( new Surround( ) );
        modules.add ( new HoleFill( ) );
        modules.add ( new AutoCrystal( ) );
        modules.add ( new WeebAura( ) );
        modules.add ( new Quiver ( ) );
        modules.add(new KillAura());
        modules.add(new Criticals());
        modules.add(new BedAura());
        modules.add(new BowAimBot());
        modules.add(new AutoClicker());
        modules.add(new AutoGap());
        modules.add(new MinDmg());
        modules.add(new SilentEXP());

        //MISC
        modules.add ( new Spammer( ) );
        modules.add ( new NoSoundLag( ) );
        modules.add ( new AutoEZ( ) );
        modules.add ( new AutoSuicide ( ) );
        modules.add ( new WeaknessLog( ) );
        modules.add ( new BurrowCounter( ) );
        modules.add(new Timestamps());
        modules.add(new AutoLog());
        modules.add(new GreenText());
        modules.add(new ExtraTab());
        modules.add(new ByteDupe());
        modules.add(new MobOwner());
        modules.add(new AutoReconnect());
        modules.add(new AutoRespawn());
        modules.add(new ToolTips());
        modules.add(new Nuker());
        modules.add(new FakeKick());
        modules.add(new TimerMod());
        modules.add(new AntiVanish());
        modules.add(new RPC());
        modules.add(new PortalsAddon());
        modules.add(new KitDelete());
        modules.add(new AntiAFK());
        modules.add(new PingSpoof());
        modules.add(new NoRotate());
        modules.add(new NoHandShake());
        //MOVEMENT
        modules.add ( new Velocity( ) );
        modules.add ( new Step ( ) );
        modules.add ( new ReverseStep ( ) );
        modules.add ( new Sprint ( ) );
        modules.add ( new NoSlow( ) );
        modules.add ( new NoWeb( ) );
        modules.add ( new PerrySpeed( ) );
        modules.add ( new Strafe( ) );
        modules.add(new PacketFly());
        modules.add(new IceSpeed());
        modules.add(new AutoWalk());
        modules.add(new NoVoid());
        modules.add(new Jesus());
        modules.add(new SlowWalk());
        modules.add(new AutoJump());
        modules.add(new Parkour());
        modules.add(new AntiLevitate());
        modules.add(new Anchor());
        modules.add(new Rubberband());
        modules.add(new XCarry());
        modules.add(new BoatFly());
        modules.add(new ElytraFlight());
        modules.add(new LongJump());
        modules.add(new SafeWalk());
        modules.add(new FastSwim());
        modules.add(new Flight());
        modules.add(new GuiMove());
        //PLAYER
        modules.add ( new FakePlayer( ) );
        modules.add ( new FastMine( ) );
        modules.add ( new InventoryManager( ) );
        modules.add ( new MCP ( ) );
        modules.add ( new PerryBurrow( ) );
        modules.add ( new PacketMend ( ) );
        modules.add ( new Freecam( ) );
        modules.add (new EXPFast( ) );
        modules.add(new Blink());
        modules.add(new BowSpam());
        modules.add(new ServerHacker());
        modules.add(new Scaffold());
        modules.add(new TpsSync());
        modules.add(new FastUse());
        modules.add(new YawLock());
        modules.add(new MultiTask());
        modules.add(new LiquidInteract());

        //RENDER
        modules.add ( new NoRender( ) );
        modules.add ( new Fullbright( ) );
        modules.add ( new Nametags ( ) );
        modules.add ( new CameraClip( ) );
        modules.add ( new ViewModel( ) );
        modules.add ( new HoleESP( ) );
        modules.add (new Trajectories( ) );
        modules.add (new LogoutSpots( ) );
        modules.add (new SkyColor( ) );
        modules.add (new Search( ) );
        modules.add(new Fov());
        modules.add(new ESP());
        modules.add(new HitMark());
        modules.add(new HandSwing());
        modules.add(new XRay());
        modules.add(new BlockHighlight());
        modules.add(new BreakESP());
        modules.add(new NoBob());
        modules.add(new HandColor());
        modules.add(new Skeleton());
        modules.add(new Chams());

        //EXPLOITS
        modules.add ( new GhastFinder());
        modules.add(new DonkeyFinder());
        modules.add(new PortalGodMode());
        modules.add(new EchestBP());
        modules.add(new BoatPlace());
        modules.add(new AntiHunger());
        modules.add(new Phase());
        modules.add(new Reach());
        modules.add(new LiquidInteract());
        modules.add(new AirJump());
        modules.add(new AntiCrystal());
        modules.add(new FoodFinder());
        modules.add(new FakeCrash());

        //CLIENT
        modules.add ( new Colors( ) );
        modules.add ( new Notifications( ) );
        modules.add ( new FontMod ( ) );
        modules.add ( new HUD( ) );
        modules.add ( new ClickGui( ) );
        modules.add ( new Managers ( ) );
        modules.add(new Components());
        modules.add(new Capes());
        modules.add(new Media());
    }

    public
    Module getModuleByName ( String name ) {
        for (Module module : modules) {
            if ( module.getName ( ).equalsIgnoreCase ( name ) ) {
                return module;
            }
        }
        return null;
    }

    public
    < T extends Module > T getModuleByClass ( Class < T > clazz ) {
        for (Module module : modules) {
            if ( clazz.isInstance ( module ) ) {
                return (T) module;
            }
        }
        return null;
    }

    public
    void enableModule ( Class clazz ) {
        Module module = getModuleByClass ( clazz );
        if ( module != null ) {
            module.enable ( );
        }
    }

    public
    void disableModule ( Class clazz ) {
        Module module = getModuleByClass ( clazz );
        if ( module != null ) {
            module.disable ( );
        }
    }

    public
    void enableModule ( String name ) {
        Module module = getModuleByName ( name );
        if ( module != null ) {
            module.enable ( );
        }
    }

    public
    void disableModule ( String name ) {
        Module module = getModuleByName ( name );
        if ( module != null ) {
            module.disable ( );
        }
    }

    public
    boolean isModuleEnabled ( String name ) {
        Module module = getModuleByName ( name );
        return module != null && module.isOn ( );
    }

    public
    boolean isModuleEnabled ( Class clazz ) {
        Module module = getModuleByClass ( clazz );
        return module != null && module.isOn ( );
    }

    public
    Module getModuleByDisplayName ( String displayName ) {
        for (Module module : modules) {
            if ( module.getDisplayName ( ).equalsIgnoreCase ( displayName ) ) {
                return module;
            }
        }
        return null;
    }

    public
    ArrayList < Module > getEnabledModules ( ) {
        ArrayList < Module > enabledModules = new ArrayList <> ( );
        for (Module module : modules) {
            if ( module.isEnabled ( ) ) {
                enabledModules.add ( module );
            }
        }
        return enabledModules;
    }

    public
    ArrayList < Module > getModulesByCategory ( Module.Category category ) {
        ArrayList < Module > modulesCategory = new ArrayList <> ( );
        modules.forEach ( module -> {
            if ( module.getCategory ( ) == category ) {
                modulesCategory.add ( module );
            }
        } );
        return modulesCategory;
    }

    public
    List < Module.Category > getCategories ( ) {
        return Arrays.asList ( Module.Category.values ( ) );
    }

    public
    void onLoad ( ) {
        modules.stream ( ).filter ( Module::listening ).forEach ( MinecraftForge.EVENT_BUS::register );
        modules.forEach ( Module::onLoad );
    }

    public
    void onUpdate ( ) {
        modules.stream ( ).filter ( Client::isEnabled ).forEach ( Module::onUpdate );
    }

    public
    void onTick ( ) {
        modules.stream ( ).filter ( Client::isEnabled ).forEach ( Module::onTick );
    }

    public
    void onRender2D ( Render2DEvent event ) {
        modules.stream ( ).filter ( Client::isEnabled ).forEach (module -> module.onRender2D ( event ) );
    }

    public
    void onRender3D ( Render3DEvent event ) {
        modules.stream ( ).filter ( Client::isEnabled ).forEach (module -> module.onRender3D ( event ) );
    }

    public
    void sortModules ( boolean reverse ) {
        this.sortedModules = getEnabledModules ( ).stream ( ).filter ( Module::isDrawn )
                .sorted ( Comparator.comparing ( module -> renderer.getStringWidth ( module.getFullArrayString ( ) ) * ( reverse ? - 1 : 1 ) ) )
                .collect ( Collectors.toList ( ) );
    }

    public
    void onLogout ( ) {
        modules.forEach ( Module::onLogout );
    }

    public
    void onLogin ( ) {
        modules.forEach ( Module::onLogin );
    }

    public
    void onUnload ( ) {
        modules.forEach ( MinecraftForge.EVENT_BUS::unregister );
        modules.forEach ( Module::onUnload );
    }

    public
    void onUnloadPost ( ) {
        for (Module module : modules) {
            module.enabled.setValue ( false );
        }
    }

    public
    void onKeyPressed ( int eventKey ) {
        if ( eventKey == 0 || ! Keyboard.getEventKeyState ( ) || Util.mc.currentScreen instanceof zerotwoGui) {
            return;
        }
        modules.forEach ( module -> {
            if ( module.getBind ( ).getKey ( ) == eventKey ) {
                module.toggle ( );
            }
        } );
    }
}