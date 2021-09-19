package me.сс.zerotwo.client.modules.movement;

import io.netty.util.internal.ConcurrentSet;
import me.сс.zerotwo.api.event.events.MoveEvent;
import me.сс.zerotwo.api.event.events.PacketEvent;
import me.сс.zerotwo.api.event.events.PushEvent;
import me.сс.zerotwo.api.event.events.UpdateWalkingPlayerEvent;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Set;

public class PacketFly
        extends Module {
    public Setting<Mode> mode = this.register(new Setting<Mode>("Mode", Mode.PACKETFLY));
    public Setting<PacketFlyMode> type = this.register(new Setting<Object>("Type", (Object)PacketFlyMode.SETBACK, v -> this.mode.getValue() == Mode.PACKETFLY));
    public Setting<Integer> xMove = this.register(new Setting<Object>("HMove", 625, 1, 1000, v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK, "XMovement speed."));
    public Setting<Integer> yMove = this.register(new Setting<Object>("YMove", 625, 1, 1000, v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK, "YMovement speed."));
    public Setting<Boolean> extra = this.register(new Setting<Object>("ExtraPacket", Boolean.valueOf(true), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Integer> offset = this.register(new Setting<Object>("Offset", 1337, -1337, 1337, v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK && this.extra.getValue() != false, "Up speed."));
    public Setting<Boolean> fallPacket = this.register(new Setting<Object>("FallPacket", Boolean.valueOf(true), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Boolean> teleporter = this.register(new Setting<Object>("Teleport", Boolean.valueOf(true), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Boolean> boundingBox = this.register(new Setting<Object>("BoundingBox", Boolean.valueOf(true), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Integer> teleportConfirm = this.register(new Setting<Object>("Confirm", Integer.valueOf(2), Integer.valueOf(0), Integer.valueOf(4), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Boolean> ultraPacket = this.register(new Setting<Object>("DoublePacket", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Boolean> updates = this.register(new Setting<Object>("Update", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Boolean> setOnMove = this.register(new Setting<Object>("SetMove", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Boolean> cliperino = this.register(new Setting<Object>("NoClip", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK && this.setOnMove.getValue() != false));
    public Setting<Boolean> scanPackets = this.register(new Setting<Object>("ScanPackets", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Boolean> resetConfirm = this.register(new Setting<Object>("Reset", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Boolean> posLook = this.register(new Setting<Object>("PosLook", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK));
    public Setting<Boolean> cancel = this.register(new Setting<Object>("Cancel", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK && this.posLook.getValue() != false));
    public Setting<Boolean> cancelType = this.register(new Setting<Object>("SetYaw", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK && this.posLook.getValue() != false && this.cancel.getValue() != false));
    public Setting<Boolean> onlyY = this.register(new Setting<Object>("OnlyY", Boolean.valueOf(false), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK && this.posLook.getValue() != false));
    public Setting<Integer> cancelPacket = this.register(new Setting<Object>("Packets", Integer.valueOf(20), Integer.valueOf(0), Integer.valueOf(20), v -> this.mode.getValue() == Mode.PACKETFLY && this.type.getValue() == PacketFlyMode.SETBACK && this.posLook.getValue() != false));
    private static PacketFly INSTANCE = new PacketFly();
    private Set<CPacketPlayer> packets = new ConcurrentSet();
    private boolean teleport = true;
    private int teleportIds = 0;
    private int posLookPackets;

    public PacketFly() {
        super("PacketFly", "Makes you able to phase through blocks.", Module.Category.MOVEMENT, true, false, false);
        this.setInstance();
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public static PacketFly getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PacketFly();
        }
        return INSTANCE;
    }

    @Override
    public void onDisable() {
        this.packets.clear();
        this.posLookPackets = 0;
        if (PacketFly.mc.player != null) {
            if (this.resetConfirm.getValue().booleanValue()) {
                this.teleportIds = 0;
            }
            PacketFly.mc.player.noClip = false;
        }
    }

    @Override
    public String getDisplayInfo() {
        return this.mode.currentEnumName();
    }

    @SubscribeEvent
    public void onMove(MoveEvent event) {
        if (this.setOnMove.getValue().booleanValue() && this.type.getValue() == PacketFlyMode.SETBACK && event.getStage() == 0 && !mc.isSingleplayer() && this.mode.getValue() == Mode.PACKETFLY) {
            event.setX(PacketFly.mc.player.motionX);
            event.setY(PacketFly.mc.player.motionY);
            event.setZ(PacketFly.mc.player.motionZ);
            if (this.cliperino.getValue().booleanValue()) {
                PacketFly.mc.player.noClip = true;
            }
        }
        if (this.type.getValue() == PacketFlyMode.NONE || event.getStage() != 0 || mc.isSingleplayer() || this.mode.getValue() != Mode.PACKETFLY) {
            return;
        }
        if (!this.boundingBox.getValue().booleanValue() && !this.updates.getValue().booleanValue()) {
            this.doPhase(event);
        }
    }

    @SubscribeEvent
    public void onPush(PushEvent event) {
        if (event.getStage() == 1 && this.type.getValue() != PacketFlyMode.NONE) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onMove(UpdateWalkingPlayerEvent event) {
        if (PacketFly.fullNullCheck() || event.getStage() != 0 || this.type.getValue() != PacketFlyMode.SETBACK || this.mode.getValue() != Mode.PACKETFLY) {
            return;
        }
        if (this.boundingBox.getValue().booleanValue()) {
            this.doBoundingBox();
        } else if (this.updates.getValue().booleanValue()) {
            this.doPhase(null);
        }
    }

    private void doPhase(MoveEvent event) {
        if (this.type.getValue() == PacketFlyMode.SETBACK && !this.boundingBox.getValue().booleanValue()) {
            double[] dirSpeed = this.getMotion(this.teleport ? (double)this.yMove.getValue().intValue() / 10000.0 : (double)(this.yMove.getValue() - 1) / 10000.0);
            double posX = PacketFly.mc.player.posX + dirSpeed[0];
            double posY = PacketFly.mc.player.posY + (PacketFly.mc.gameSettings.keyBindJump.isKeyDown() ? (this.teleport ? (double)this.yMove.getValue().intValue() / 10000.0 : (double)(this.yMove.getValue() - 1) / 10000.0) : 1.0E-8) - (PacketFly.mc.gameSettings.keyBindSneak.isKeyDown() ? (this.teleport ? (double)this.yMove.getValue().intValue() / 10000.0 : (double)(this.yMove.getValue() - 1) / 10000.0) : 2.0E-8);
            double posZ = PacketFly.mc.player.posZ + dirSpeed[1];
            CPacketPlayer.PositionRotation packetPlayer = new CPacketPlayer.PositionRotation(posX, posY, posZ, PacketFly.mc.player.rotationYaw, PacketFly.mc.player.rotationPitch, false);
            this.packets.add((CPacketPlayer)packetPlayer);
            PacketFly.mc.player.connection.sendPacket((Packet)packetPlayer);
            if (this.teleportConfirm.getValue() != 3) {
                PacketFly.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportIds - 1));
                ++this.teleportIds;
            }
            if (this.extra.getValue().booleanValue()) {
                CPacketPlayer.PositionRotation packet = new CPacketPlayer.PositionRotation(PacketFly.mc.player.posX, (double)this.offset.getValue().intValue() + PacketFly.mc.player.posY, PacketFly.mc.player.posZ, PacketFly.mc.player.rotationYaw, PacketFly.mc.player.rotationPitch, true);
                this.packets.add((CPacketPlayer)packet);
                PacketFly.mc.player.connection.sendPacket((Packet)packet);
            }
            if (this.teleportConfirm.getValue() != 1) {
                PacketFly.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportIds + 1));
                ++this.teleportIds;
            }
            if (this.ultraPacket.getValue().booleanValue()) {
                CPacketPlayer.PositionRotation packet2 = new CPacketPlayer.PositionRotation(posX, posY, posZ, PacketFly.mc.player.rotationYaw, PacketFly.mc.player.rotationPitch, false);
                this.packets.add((CPacketPlayer)packet2);
                PacketFly.mc.player.connection.sendPacket((Packet)packet2);
            }
            if (this.teleportConfirm.getValue() == 4) {
                PacketFly.mc.player.connection.sendPacket((Packet)new CPacketConfirmTeleport(this.teleportIds));
                ++this.teleportIds;
            }
            if (this.fallPacket.getValue().booleanValue()) {
                PacketFly.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) PacketFly.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
            }
            PacketFly.mc.player.setPosition(posX, posY, posZ);
            boolean bl = this.teleport = this.teleporter.getValue() == false || !this.teleport;
            if (event != null) {
                event.setX(0.0);
                event.setY(0.0);
                event.setX(0.0);
            } else {
                PacketFly.mc.player.motionX = 0.0;
                PacketFly.mc.player.motionY = 0.0;
                PacketFly.mc.player.motionZ = 0.0;
            }
        }
    }

    private void doBoundingBox() {
        double[] dirSpeed = this.getMotion(this.teleport ? (double)0.0225f : (double)0.0224f);
        PacketFly.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PacketFly.mc.player.posX + dirSpeed[0], PacketFly.mc.player.posY + (PacketFly.mc.gameSettings.keyBindJump.isKeyDown() ? (this.teleport ? 0.0625 : 0.0624) : 1.0E-8) - (PacketFly.mc.gameSettings.keyBindSneak.isKeyDown() ? (this.teleport ? 0.0625 : 0.0624) : 2.0E-8), PacketFly.mc.player.posZ + dirSpeed[1], PacketFly.mc.player.rotationYaw, PacketFly.mc.player.rotationPitch, false));
        PacketFly.mc.player.connection.sendPacket((Packet)new CPacketPlayer.PositionRotation(PacketFly.mc.player.posX, -1337.0, PacketFly.mc.player.posZ, PacketFly.mc.player.rotationYaw, PacketFly.mc.player.rotationPitch, true));
        PacketFly.mc.player.connection.sendPacket((Packet)new CPacketEntityAction((Entity) PacketFly.mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        PacketFly.mc.player.setPosition(PacketFly.mc.player.posX + dirSpeed[0], PacketFly.mc.player.posY + (PacketFly.mc.gameSettings.keyBindJump.isKeyDown() ? (this.teleport ? 0.0625 : 0.0624) : 1.0E-8) - (PacketFly.mc.gameSettings.keyBindSneak.isKeyDown() ? (this.teleport ? 0.0625 : 0.0624) : 2.0E-8), PacketFly.mc.player.posZ + dirSpeed[1]);
        this.teleport = !this.teleport;
        PacketFly.mc.player.motionZ = 0.0;
        PacketFly.mc.player.motionY = 0.0;
        PacketFly.mc.player.motionX = 0.0;
        PacketFly.mc.player.noClip = this.teleport;
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (this.posLook.getValue().booleanValue() && event.getPacket() instanceof SPacketPlayerPosLook) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook)event.getPacket();
            if (PacketFly.mc.player.isEntityAlive() && PacketFly.mc.world.isBlockLoaded(new BlockPos(PacketFly.mc.player.posX, PacketFly.mc.player.posY, PacketFly.mc.player.posZ)) && !(PacketFly.mc.currentScreen instanceof GuiDownloadTerrain)) {
                if (this.teleportIds <= 0) {
                    this.teleportIds = packet.getTeleportId();
                }
                if (this.cancel.getValue().booleanValue() && this.cancelType.getValue().booleanValue()) {
                    packet.yaw = PacketFly.mc.player.rotationYaw;
                    packet.pitch = PacketFly.mc.player.rotationPitch;
                    return;
                }
                if (!(!this.cancel.getValue().booleanValue() || this.posLookPackets < this.cancelPacket.getValue() || this.onlyY.getValue().booleanValue() && (PacketFly.mc.gameSettings.keyBindForward.isKeyDown() || PacketFly.mc.gameSettings.keyBindRight.isKeyDown() || PacketFly.mc.gameSettings.keyBindLeft.isKeyDown() || PacketFly.mc.gameSettings.keyBindBack.isKeyDown()))) {
                    this.posLookPackets = 0;
                    event.setCanceled(true);
                }
                ++this.posLookPackets;
            }
        }
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Send event) {
        if (this.scanPackets.getValue().booleanValue() && event.getPacket() instanceof CPacketPlayer) {
            CPacketPlayer packetPlayer = (CPacketPlayer)event.getPacket();
            if (this.packets.contains(packetPlayer)) {
                this.packets.remove(packetPlayer);
            } else {
                event.setCanceled(true);
            }
        }
    }

    private double[] getMotion(double speed) {
        float moveForward = PacketFly.mc.player.movementInput.moveForward;
        float moveStrafe = PacketFly.mc.player.movementInput.moveStrafe;
        float rotationYaw = PacketFly.mc.player.prevRotationYaw + (PacketFly.mc.player.rotationYaw - PacketFly.mc.player.prevRotationYaw) * mc.getRenderPartialTicks();
        if (moveForward != 0.0f) {
            if (moveStrafe > 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? -45 : 45);
            } else if (moveStrafe < 0.0f) {
                rotationYaw += (float)(moveForward > 0.0f ? 45 : -45);
            }
            moveStrafe = 0.0f;
            if (moveForward > 0.0f) {
                moveForward = 1.0f;
            } else if (moveForward < 0.0f) {
                moveForward = -1.0f;
            }
        }
        double posX = (double)moveForward * speed * -Math.sin(Math.toRadians(rotationYaw)) + (double)moveStrafe * speed * Math.cos(Math.toRadians(rotationYaw));
        double posZ = (double)moveForward * speed * Math.cos(Math.toRadians(rotationYaw)) - (double)moveStrafe * speed * -Math.sin(Math.toRadians(rotationYaw));
        return new double[]{posX, posZ};
    }

    public static enum PacketFlyMode {
        NONE,
        SETBACK;

    }

    public static enum Mode {
        PACKETFLY;

    }
}
