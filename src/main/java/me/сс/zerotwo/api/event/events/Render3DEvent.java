package me.сс.zerotwo.api.event.events;

import me.сс.zerotwo.api.event.EventStage;

public
class Render3DEvent extends EventStage {

    private final float partialTicks;

    public
    Render3DEvent ( float partialTicks ) {
        this.partialTicks = partialTicks;
    }

    public
    float getPartialTicks ( ) {
        return partialTicks;
    }
}
