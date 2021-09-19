package me.сс.zerotwo.api.event.events;

import me.сс.zerotwo.api.event.EventStage;
import net.minecraft.entity.MoverType;

public
class PlayerMoveEvent extends EventStage {

    private MoverType type;
    private double x;
    private double y;
    private double z;

    public
    PlayerMoveEvent ( MoverType moverType , double x , double y , double z ) {
        super ( );
        this.type = moverType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public
    MoverType getType ( ) {
        return this.type;
    }

    public
    void setType ( MoverType type ) {
        this.type = type;
    }

    public
    double getX ( ) {
        return this.x;
    }

    public
    void setX ( double x ) {
        this.x = x;
    }

    public
    double getY ( ) {
        return this.y;
    }

    public
    void setY ( double y ) {
        this.y = y;
    }

    public
    double getZ ( ) {
        return this.z;
    }

    public
    void setZ ( double z ) {
        this.z = z;
    }
}
