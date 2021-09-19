package me.сс.zerotwo.api.event.events;

import me.сс.zerotwo.api.event.EventStage;
import net.minecraft.entity.player.EntityPlayer;

public
class DeathEvent extends EventStage {

    public EntityPlayer player;

    public
    DeathEvent ( EntityPlayer player ) {
        super ( );
        this.player = player;
    }

}
