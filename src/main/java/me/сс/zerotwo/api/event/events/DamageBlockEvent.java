package me.сс.zerotwo.api.event.events;

import me.сс.zerotwo.api.event.EventStage;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class DamageBlockEvent extends EventStage {

    private BlockPos BlockPos;
    private EnumFacing Direction;

    public DamageBlockEvent(net.minecraft.util.math.BlockPos posBlock, EnumFacing directionFacing)
    {
        BlockPos = posBlock;
        setDirection(directionFacing);
    }

    public BlockPos getPos()
    {
        return BlockPos;
    }

    public EnumFacing getDirection()
    {
        return Direction;
    }

    public void setDirection(EnumFacing direction)
    {
        Direction = direction;
    }

}
