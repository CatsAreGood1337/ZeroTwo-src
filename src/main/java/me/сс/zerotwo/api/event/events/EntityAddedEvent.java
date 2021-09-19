package me.сс.zerotwo.api.event.events;

import me.сс.zerotwo.api.event.EventStage;
import net.minecraft.entity.Entity;

public
class EntityAddedEvent extends EventStage {
    private final Entity entity;

    public
    EntityAddedEvent ( Entity entity ) {
        super ( );
        this.entity = entity;
    }

    public
    Entity getEntity ( ) {
        return entity;
    }
}
