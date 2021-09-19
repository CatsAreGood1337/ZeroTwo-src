package me.сс.zerotwo.api.event.events;

import me.сс.zerotwo.api.event.EventStage;

public
class UpdateEvent extends EventStage {
    private final int stage;

    public
    UpdateEvent ( int stage ) {
        this.stage = stage;
    }

    public final
    int getStage ( ) {
        return this.stage;
    }
}
