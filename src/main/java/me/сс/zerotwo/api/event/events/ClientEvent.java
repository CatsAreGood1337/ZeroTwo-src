package me.сс.zerotwo.api.event.events;

import me.сс.zerotwo.api.event.EventStage;
import me.сс.zerotwo.client.Client;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public
class ClientEvent extends EventStage {

    private Client feature;
    private Setting setting;

    public
    ClientEvent ( int stage , Client feature ) {
        super ( stage );
        this.feature = feature;
    }

    public
    ClientEvent ( Setting setting ) {
        super ( 2 );
        this.setting = setting;
    }

    public Client getFeature ( ) {
        return this.feature;
    }

    public
    Setting getSetting ( ) {
        return this.setting;
    }
}
