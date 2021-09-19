package me.сс.zerotwo.client.modules.misc;

import me.сс.zerotwo.api.util.moduleUtil.TextUtil;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Bind;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Slot;
import org.lwjgl.input.Keyboard;

public class KitDelete
        extends Module {
    private Setting<Bind> deleteKey = this.register(new Setting<Bind>("Key", new Bind(-1)));
    private boolean keyDown;

    public KitDelete() {
        super("KitDelete", "Automates /deleteukit", Module.Category.MISC, false, false, false);
    }

    @Override
    public void onTick() {
        if (this.deleteKey.getValue().getKey() != -1) {
            if (KitDelete.mc.currentScreen instanceof GuiContainer && Keyboard.isKeyDown((int)this.deleteKey.getValue().getKey())) {
                Slot slot = ((GuiContainer)KitDelete.mc.currentScreen).getSlotUnderMouse();
                if (slot != null && !this.keyDown) {
                    KitDelete.mc.player.sendChatMessage("/deleteukit " + TextUtil.stripColor(slot.getStack().getDisplayName()));
                    this.keyDown = true;
                }
            } else if (this.keyDown) {
                this.keyDown = false;
            }
        }
    }
}
