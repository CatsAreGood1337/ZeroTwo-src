package me.сс.zerotwo.client.modules.misc;

import me.сс.zerotwo.client.command.Command;
import me.сс.zerotwo.client.modules.Module;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

import java.util.Random;

public class ByteDupe extends Module {
    public ByteDupe() {
        super("ByteDupe", "", Category.MISC, true, false, false);
    }

    private final Random random = new Random();

    public void onEnable() {
        EntityPlayerSP player = mc.player;
        WorldClient world = mc.world;
        ;

        if (player == null || mc.world == null) return;

        ItemStack itemStack = player.getHeldItemMainhand();

        if (itemStack.isEmpty()) {
            Command.sendMessage("You need to hold an item in hand to dupe!");
            disable();
            return;
        }

        int count = random.nextInt(31) + 1;

        for (int i = 0; i <= count; i++) {
            EntityItem entityItem = player.dropItem(itemStack.copy(), false, true);
            if (entityItem != null) world.addEntityToWorld(entityItem.entityId, entityItem);
        }

        int total = count * itemStack.getCount();
        disable();
    }
}

