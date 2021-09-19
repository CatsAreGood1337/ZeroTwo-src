package me.сс.zerotwo.api.event.events;

import me.сс.zerotwo.api.event.EventStage;

public
class KeyEvent extends EventStage {

    public boolean info;
    public boolean pressed;

    public
    KeyEvent ( int stage , boolean info , boolean pressed ) {
        super ( stage );
        this.info = info;
        this.pressed = pressed;
    }
}
