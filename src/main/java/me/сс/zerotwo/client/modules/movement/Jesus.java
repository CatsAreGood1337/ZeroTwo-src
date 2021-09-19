package me.сс.zerotwo.client.modules.movement;

import me.сс.zerotwo.api.util.moduleUtil.Wrapper;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Jesus extends Module {

    private final Setting <Jesus.Mode> mode = register ( new Setting <Jesus.Mode> ( "Mode" , Jesus.Mode.SOLID ) );

    public Jesus()
    {
        super ( "NewJesus" , "NewJesus" , Category.MOVEMENT , true , false , false );
    }


    public void onUpdate() {
        if(mode.getValue().equals(Mode.SOLID)) {
            if (Wrapper.mc.world != null) {
                if (isInWater(Wrapper.mc.player) && !Wrapper.mc.player.isSneaking()) {
                    Wrapper.mc.player.motionY = 0.1;
                    if (Wrapper.mc.player.getRidingEntity() != null) {
                        Wrapper.mc.player.getRidingEntity().motionY = 0.2;
                    }
                }
                if (isAboveWater(Wrapper.getPlayer()) && !isInWater(Wrapper.getPlayer()) && !isAboveLand(Wrapper.getPlayer()) && !Wrapper.mc.player.isSneaking()) {
                    Wrapper.mc.player.motionY = 0;
                    Wrapper.mc.player.onGround = true;
                }
            }
        }

        if(mode.getValue().equals(Mode.DOLPHIN)) {
            if (Wrapper.mc.world != null) {
                if (isInWater(Wrapper.mc.player) && !Wrapper.mc.player.isSneaking()) {
                    Wrapper.mc.player.motionY = 0.1;
                    if (Wrapper.mc.player.getRidingEntity() != null) {
                        Wrapper.mc.player.getRidingEntity().motionY = 0.2;
                    }
                }
            }
        }
    }

    public static boolean isInWater(Entity entity) {
        if(entity == null) return false;

        double y = entity.posY + 0.01;

        for(int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
                BlockPos pos = new BlockPos(x, (int) y, z);

                if (Wrapper.getWorld().getBlockState(pos).getBlock() instanceof BlockLiquid) return true;
            }

        return false;
    }

    private static boolean isAboveWater(Entity entity){
        double y = entity.posY - 0.03;

        for(int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
                BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);

                if (Wrapper.getWorld().getBlockState(pos).getBlock() instanceof BlockLiquid) return true;
            }

        return false;
    }

    private static boolean isAboveLand(Entity entity){
        if(entity == null) return false;

        double y = entity.posY - 0.01;

        for(int x = MathHelper.floor(entity.posX); x < MathHelper.ceil(entity.posX); x++)
            for (int z = MathHelper.floor(entity.posZ); z < MathHelper.ceil(entity.posZ); z++) {
                BlockPos pos = new BlockPos(x, MathHelper.floor(y), z);

                if (Wrapper.mc.world.getBlockState(pos).getBlock().isFullBlock(Wrapper.mc.world.getBlockState(pos))) return true;
            }
        return false;
    }

    public enum Mode{
        SOLID, DOLPHIN
    }
}

