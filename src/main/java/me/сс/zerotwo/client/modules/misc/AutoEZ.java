package me.сс.zerotwo.client.modules.misc;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.api.event.events.DeathEvent;
import me.сс.zerotwo.api.event.events.PacketEvent;
import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.modules.combat.AutoCrystal;
import me.сс.zerotwo.client.setting.Setting;
import me.сс.zerotwo.api.manager.FileManager;
import me.сс.zerotwo.api.util.moduleUtil.MathUtil;
import me.сс.zerotwo.api.util.moduleUtil.Timer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public
class AutoEZ extends Module {
    private static final String path = "ZeroTwo/autoez.txt";
    private final Setting < Boolean > onOwnDeath = this.register ( new Setting < Boolean > ( "OwnDeath" , false ) );
    private final Setting < Boolean > greentext = this.register ( new Setting < Boolean > ( "Greentext" , false ) );
    private final Setting < Boolean > loadFiles = this.register ( new Setting < Boolean > ( "LoadFiles" , false ) );
    private final Setting < Integer > targetResetTimer = this.register ( new Setting < Integer > ( "Reset" , 30 , 0 , 90 ) );
    private final Setting < Integer > delay = this.register ( new Setting < Integer > ( "Delay" , 10 , 0 , 30 ) );
    private final Setting < Boolean > test = this.register ( new Setting < Boolean > ( "Test" , false ) );
    private final Timer timer = new Timer ( );
    private final Timer cooldownTimer = new Timer ( );
    public Map < EntityPlayer, Integer > targets = new ConcurrentHashMap < EntityPlayer, Integer > ( );
    public List < String > messages = new ArrayList < String > ( );
    public EntityPlayer cauraTarget;
    private boolean cooldown;

    public
    AutoEZ ( ) {
        super ( "AutoEZ" , "Automatically EZs" , Module.Category.MISC , true , false , false );
        File file = new File ( path );
        if ( ! file.exists ( ) ) {
            try {
                file.createNewFile ( );
            } catch ( Exception e ) {
                e.printStackTrace ( );
            }
        }
    }

    @Override
    public
    void onEnable ( ) {
        this.loadMessages ( );
        this.timer.reset ( );
        this.cooldownTimer.reset ( );
    }

    @Override
    public
    void onTick ( ) {
        if ( this.loadFiles.getValue ( ).booleanValue ( ) ) {
            this.loadMessages ( );
            Command.sendMessage ( "<AutoEZ> Loaded messages." );
            this.loadFiles.setValue ( false );
        }
        if ( AutoCrystal.target != null && this.cauraTarget != AutoCrystal.target ) {
            this.cauraTarget = AutoCrystal.target;
        }
        if ( this.test.getValue ( ).booleanValue ( ) ) {
            this.announceDeath ( AutoEZ.mc.player );
            this.test.setValue ( false );
        }
        if ( ! this.cooldown ) {
            this.cooldownTimer.reset ( );
        }
        if ( this.cooldownTimer.passedS ( this.delay.getValue ( ).intValue ( ) ) && this.cooldown ) {
            this.cooldown = false;
            this.cooldownTimer.reset ( );
        }
        if ( AutoCrystal.target != null ) {
            this.targets.put ( AutoCrystal.target , (int) ( this.timer.getPassedTimeMs ( ) / 1000L ) );
        }
        this.targets.replaceAll ( ( p , v ) -> (int) ( this.timer.getPassedTimeMs ( ) / 1000L ) );
        for (EntityPlayer player : this.targets.keySet ( )) {
            if ( this.targets.get ( player ) <= this.targetResetTimer.getValue ( ) ) continue;
            this.targets.remove ( player );
            this.timer.reset ( );
        }
    }

    @SubscribeEvent
    public
    void onEntityDeath ( DeathEvent event ) {
        if ( this.targets.containsKey ( event.player ) && ! this.cooldown ) {
            this.announceDeath ( event.player );
            this.cooldown = true;
            this.targets.remove ( event.player );
        }
        if ( event.player == this.cauraTarget && ! this.cooldown ) {
            this.announceDeath ( event.player );
            this.cooldown = true;
        }
        if ( event.player == AutoEZ.mc.player && this.onOwnDeath.getValue ( ).booleanValue ( ) ) {
            this.announceDeath ( event.player );
            this.cooldown = true;
        }
    }

    @SubscribeEvent
    public
    void onAttackEntity ( AttackEntityEvent event ) {
        if ( event.getTarget ( ) instanceof EntityPlayer && ! ZeroTwo.friendManager.isFriend ( event.getEntityPlayer ( ) ) ) {
            this.targets.put ( (EntityPlayer) event.getTarget ( ) , 0 );
        }
    }

    @SubscribeEvent
    public
    void onSendAttackPacket ( PacketEvent.Send event ) {
        CPacketUseEntity packet;
        if ( event.getPacket ( ) instanceof CPacketUseEntity && ( packet = event.getPacket ( ) ).getAction ( ) == CPacketUseEntity.Action.ATTACK && packet.getEntityFromWorld ( AutoEZ.mc.world ) instanceof EntityPlayer && ! ZeroTwo.friendManager.isFriend ( (EntityPlayer) packet.getEntityFromWorld ( AutoEZ.mc.world ) ) ) {
            this.targets.put ( (EntityPlayer) packet.getEntityFromWorld ( AutoEZ.mc.world ) , 0 );
        }
    }

    public
    void loadMessages ( ) {
        this.messages = FileManager.readTextFileAllLines ( path );
    }

    public
    String getRandomMessage ( ) {
        this.loadMessages ( );
        Random rand = new Random ( );
        if ( this.messages.size ( ) == 0 ) {
            return "<player> you ez, ZeroTwo owns u and all";
        }
        if ( this.messages.size ( ) == 1 ) {
            return this.messages.get ( 0 );
        }
        return this.messages.get ( MathUtil.clamp ( rand.nextInt ( this.messages.size ( ) ) , 0 , this.messages.size ( ) - 1 ) );
    }

    public
    void announceDeath ( EntityPlayer target ) {
        AutoEZ.mc.player.connection.sendPacket ( new CPacketChatMessage ( ( this.greentext.getValue ( ) != false ? ">" : "" ) + this.getRandomMessage ( ).replaceAll ( "<player>" , target.getDisplayNameString ( ) ) ) );
    }
}

