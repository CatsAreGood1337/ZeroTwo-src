package me.сс.zerotwo.client.modules.render;

import  me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.api.event.events.ClientEvent;
import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.block.Block;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class XRay
        extends Module {
    private static XRay INSTANCE = new XRay();
    public Setting<String> newBlock = this.register(new Setting<String>("NewBlock", "Add Block..."));
    public Setting<Boolean> showBlocks = this.register(new Setting<Boolean>("ShowBlocks", false));

    public XRay() {
        super("XRay", "Lets you look through walls.", Module.Category.RENDER, false, false, true);
        this.setInstance();
    }

    public static XRay getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new XRay();
        }
        return INSTANCE;
    }

    private void setInstance() {
        INSTANCE = this;
    }

    @Override
    public void onEnable() {
        XRay.mc.renderGlobal.loadRenderers();
    }

    @Override
    public void onDisable() {
        XRay.mc.renderGlobal.loadRenderers();
    }


    public boolean shouldRender(Block block) {
        return this.shouldRender(block.getLocalizedName());
    }

    public boolean shouldRender(String name) {
        for (Setting setting : this.getSettings()) {
            if (!name.equalsIgnoreCase(setting.getName())) continue;
            return true;
        }
        return false;
    }
}

