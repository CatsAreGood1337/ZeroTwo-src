package me.сс.zerotwo.client.modules.combat;

import me.сс.zerotwo.api.util.moduleUtil.InventoryUtil;
import me.сс.zerotwo.client.modules.Module;
import net.minecraft.item.ItemExpBottle;

public class EXPFast extends Module {
    public EXPFast() {
        super("EXPFast", "", Module.Category.COMBAT, true, false, false);
    }

    @Override
    public void onUpdate() {
        if (EXPFast.fullNullCheck()) {
            return;
        }
        if (InventoryUtil.holdingItem(ItemExpBottle.class)) {
            EXPFast.mc.rightClickDelayTimer = 0;
        }
    }
}