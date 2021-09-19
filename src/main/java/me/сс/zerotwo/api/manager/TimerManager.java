package me.сс.zerotwo.api.manager;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.Client;
import me.сс.zerotwo.client.modules.misc.TimerMod;

public
class TimerManager extends Client {

    private float timer = 1.0f;
    private TimerMod module;

    public
    void init ( ) {
        module = ZeroTwo.moduleManager.getModuleByClass ( TimerMod.class );
    }

    public
    void unload ( ) {
        timer = 1.0f;
        mc.timer.tickLength = 50.0f;
    }

    public
    void update ( ) {
        if ( module != null && module.isEnabled ( ) ) {
            this.timer = module.speed;
        }
        mc.timer.tickLength = 50.0f / ( timer <= 0.0f ? 0.1f : timer );
    }

    public
    float getTimer ( ) {
        return this.timer;
    }

    public
    void setTimer ( float timer ) {
        if ( timer > 0.0f ) {
            this.timer = timer;
        }
    }

    public
    void reset ( ) {
        this.timer = 1.0f;
    }
}
