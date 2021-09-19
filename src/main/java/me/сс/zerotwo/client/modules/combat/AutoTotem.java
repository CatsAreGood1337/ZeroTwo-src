package me.сс.zerotwo.client.modules.combat;


import me.сс.zerotwo.client.modules.Module;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;


import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.Item;

        public class AutoTotem extends Module {


            public AutoTotem( ) {
                super ( "AutoTotem" , "AutoTotem" , Module.Category.COMBAT , true , false , false );
            }
        private boolean switching = false;
        private int lastSlot;

        @Override
        public void onUpdate() {

        if (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory) {

        if (switching) {
        swapTotem(lastSlot, 2);
        return;
        }


        }

        }

        private int getTotem() {
        if (Items.TOTEM_OF_UNDYING == mc.player.getHeldItemOffhand().getItem()) return -1;
        for(int i = 45; i >= 0; i--) {
        final Item item = mc.player.inventory.getStackInSlot(i).getItem();
        if(item == Items.TOTEM_OF_UNDYING) {
        if (i < 9) {
        return -1;
        }
        return i;
        }
        }
        return -1;
        }

        public void swapTotem(int slot, int step) {
        if (slot == -1) return;
        if (step == 0) {
        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        }
        if (step == 1) {
        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        switching = true;
        lastSlot = slot;
        }
        if (step == 2) {
        mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
        mc.playerController.windowClick(0, slot, 0, ClickType.PICKUP, mc.player);
        switching = false;
        }

        mc.playerController.updateController();
        }
        }
