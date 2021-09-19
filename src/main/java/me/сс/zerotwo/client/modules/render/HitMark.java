package me.сс.zerotwo.client.modules.render;

import me.сс.zerotwo.api.event.events.Render2DEvent;
import me.сс.zerotwo.api.util.moduleUtil.RenderUtil;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;

public final class HitMark extends Module
{

    public final ResourceLocation image;
    public final ResourceLocation sounds;
    private int renderTicks;

    public HitMark() {
        super("HitMark", "", Module.Category.RENDER, false, false, false);
        this.image = new ResourceLocation("hitmarker.png");
        this.sounds = new ResourceLocation("hit.ogg");
        renderTicks = 100;
    }

    public Setting<Integer> red = this.register(new Setting<Integer>("Red", 255, 0, 255));
    public Setting<Integer> green = this.register(new Setting<Integer>("Green", 255, 0, 255));
    public Setting<Integer> blue = this.register(new Setting<Integer>("Blue", 255, 0, 255));
    public Setting<Integer> alpha = this.register(new Setting<Integer>("Alpha", 255, 0, 255));
    public Setting<Integer> thickness = this.register(new Setting<Integer>("Thickness", 2, 1, 6));
    public Setting<Double> time = this.register(new Setting<Double>("Time", 20D, 1D, 50D));
    public Setting<Boolean> sound = this.register(new Setting<Boolean>("sound", true));

    @Override
    public void onRender2D(Render2DEvent event) {
        if (this.renderTicks < time.getValue()) {
            final ScaledResolution resolution = new ScaledResolution(HitMark.mc);
            this.drawHitMarkers();
        }
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    @SubscribeEvent
    public void onAttackEntity(final AttackEntityEvent event) {
        if (!event.getEntity().equals(HitMark.mc.player)) {
            return;
        }
        this.renderTicks = 0;
    }

    @SubscribeEvent
    public void onTickClientTick(final TickEvent event) {
        ++renderTicks;
    }

    public void drawHitMarkers() {
        final ScaledResolution resolution = new ScaledResolution(HitMark.mc);
        RenderUtil.drawLine(resolution.getScaledWidth() / 2.0f - 4.0f, resolution.getScaledHeight() / 2.0f - 4.0f, resolution.getScaledWidth() / 2.0f - 8.0f, resolution.getScaledHeight() / 2.0f - 8.0f, this.thickness.getValue(), new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB());
        RenderUtil.drawLine(resolution.getScaledWidth() / 2.0f + 4.0f, resolution.getScaledHeight() / 2.0f - 4.0f, resolution.getScaledWidth() / 2.0f + 8.0f, resolution.getScaledHeight() / 2.0f - 8.0f, this.thickness.getValue(), new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB());
        RenderUtil.drawLine(resolution.getScaledWidth() / 2.0f - 4.0f, resolution.getScaledHeight() / 2.0f + 4.0f, resolution.getScaledWidth() / 2.0f - 8.0f, resolution.getScaledHeight() / 2.0f + 8.0f, this.thickness.getValue(), new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB());
        RenderUtil.drawLine(resolution.getScaledWidth() / 2.0f + 4.0f, resolution.getScaledHeight() / 2.0f + 4.0f, resolution.getScaledWidth() / 2.0f + 8.0f, resolution.getScaledHeight() / 2.0f + 8.0f, this.thickness.getValue(), new Color(red.getValue(), green.getValue(), blue.getValue()).getRGB());
    }
}
