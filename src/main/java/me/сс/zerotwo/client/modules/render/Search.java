package me.сс.zerotwo.client.modules.render;

import me.сс.zerotwo.api.event.events.Render3DEvent;
import me.сс.zerotwo.api.util.moduleUtil.RenderUtil;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPortal;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;

public class Search extends Module {
    private final ArrayList<BlockPos> blockPosArrayList = new ArrayList();
    private final Setting<Integer> distance = this.register(new Setting<Integer>("Distance", 60, 10, 100));
    private final Setting<Boolean> box = this.register(new Setting<Boolean>("Box", false));
    private final Setting<Integer> boxAlpha = this.register(new Setting<Object>("BoxAlpha", Integer.valueOf(125), Integer.valueOf(0), Integer.valueOf(255), v -> this.box.getValue()));
    private final Setting<Boolean> outline = this.register(new Setting<Boolean>("Outline", true));
    private final Setting<Float> lineWidth = this.register(new Setting<Object>("LineWidth", Float.valueOf(1.0f), Float.valueOf(0.1f), Float.valueOf(5.0f), v -> this.outline.getValue()));
    private int cooldownTicks;

    public Search() {
        super("Search", "Draws portals", Module.Category.RENDER, true, false, false);
    }

    @SubscribeEvent
    public void onTickEvent(TickEvent.ClientTickEvent event) {
        if (Search.mc.world == null) {
            return;
        }
        if (this.cooldownTicks < 1) {
            this.blockPosArrayList.clear();
            this.compileDL();
            this.cooldownTicks = 80;
        }
        --this.cooldownTicks;
    }

    @Override
    public void onRender3D(Render3DEvent event) {
        if (Search.mc.world == null) {
            return;
        }
        for (BlockPos pos : this.blockPosArrayList) {
            RenderUtil.drawBoxESP(pos, new Color(204, 0, 153, 255), false, new Color(204, 0, 153, 255), this.lineWidth.getValue().floatValue(), this.outline.getValue(), this.box.getValue(), this.boxAlpha.getValue(), false);
        }
    }

    private void compileDL() {
        if (Search.mc.world == null || Search.mc.player == null) {
            return;
        }
        for (int x = (int) Search.mc.player.posX - this.distance.getValue(); x <= (int) Search.mc.player.posX + this.distance.getValue(); ++x) {
            for (int y = (int) Search.mc.player.posY - this.distance.getValue(); y <= (int) Search.mc.player.posY + this.distance.getValue(); ++y) {
                int z = (int) Math.max(Search.mc.player.posZ - (double) this.distance.getValue().intValue(), 0.0);
                while ((double) z <= Math.min(Search.mc.player.posZ + (double) this.distance.getValue().intValue(), 255.0)) {
                    BlockPos pos = new BlockPos(x, y, z);
                    Block block = Search.mc.world.getBlockState(pos).getBlock();
                    if (block instanceof BlockPortal) {
                        this.blockPosArrayList.add(pos);
                    }
                    ++z;
                }
            }
        }
    }
}

