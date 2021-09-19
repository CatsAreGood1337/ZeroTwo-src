package me.сс.zerotwo.api.util.moduleUtil;

import net.minecraft.network.play.client.CPacketUseEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = {CPacketUseEntity.class})
public
interface ICPacketUseEntity {
    @Accessor(value = "entityId")
    void setEntityId ( int var1 );

    @Accessor(value = "action")
    void setAction ( CPacketUseEntity.Action var1 );
}
