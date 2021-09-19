package me.сс.zerotwo.mixin.mixins;

import me.сс.zerotwo.api.event.events.BlockEvent;
import me.сс.zerotwo.api.event.events.ProcessRightClickBlockEvent;
import me.сс.zerotwo.client.modules.exploit.FastMine;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerControllerMP.class)
public
class MixinPlayerControllerMP {

    @Redirect(method = "onPlayerDamageBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/state/IBlockState;getPlayerRelativeBlockHardness(Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;)F"))
    public
    float getPlayerRelativeBlockHardnessHook ( IBlockState state , EntityPlayer player , World worldIn , BlockPos pos ) {
        return state.getPlayerRelativeBlockHardness ( player , worldIn , pos ) * (/*TpsSync.getInstance().isOn() && TpsSync.getInstance().mining.getValue() ? 1 / nekoplus.serverManager.getTpsFactor() :*/ 1 );
    }

    @Inject(method = "resetBlockRemoving", at = @At("HEAD"), cancellable = true)
    public
    void resetBlockRemovingHook ( CallbackInfo info ) {
        if ( FastMine.getInstance ( ).isOn ( ) && FastMine.getInstance ( ).reset.getValue ( ) ) {
            info.cancel ( );
        }
    }

    @Inject(method = "clickBlock", at = @At("HEAD"), cancellable = true)
    private
    void clickBlockHook ( BlockPos pos , EnumFacing face , CallbackInfoReturnable < Boolean > info ) {
        BlockEvent event = new BlockEvent ( 3 , pos , face );
        MinecraftForge.EVENT_BUS.post ( event );
    }

    @Inject(method = "onPlayerDamageBlock", at = @At("HEAD"), cancellable = true)
    private
    void onPlayerDamageBlockHook ( BlockPos pos , EnumFacing face , CallbackInfoReturnable < Boolean > info ) {
        BlockEvent event = new BlockEvent ( 4 , pos , face );
        MinecraftForge.EVENT_BUS.post ( event );
    }

    @Inject(method = "getBlockReachDistance", at = @At("RETURN"), cancellable = true)
    private
    void getReachDistanceHook ( CallbackInfoReturnable < Float > distance ) {
        /*if(Reach.getInstance().isOn()) {
            float range = distance.getReturnValue();
            distance.setReturnValue(Reach.getInstance().override.getValue() ? Reach.getInstance().reach.getValue() : range + Reach.getInstance().add.getValue());
        }*/
    }

    @Redirect(method = "processRightClickBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemBlock;canPlaceBlockOnSide(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/EnumFacing;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/item/ItemStack;)Z"))
    public
    boolean canPlaceBlockOnSideHook ( ItemBlock itemBlock , World worldIn , BlockPos pos , EnumFacing side , EntityPlayer player , ItemStack stack ) {
        Block block = worldIn.getBlockState ( pos ).getBlock ( );

        if ( block == Blocks.SNOW_LAYER && block.isReplaceable ( worldIn , pos ) ) {
            side = EnumFacing.UP;
        } else if ( ! block.isReplaceable ( worldIn , pos ) ) {
            pos = pos.offset ( side );
        }

        IBlockState iblockstate1 = worldIn.getBlockState ( pos );
        AxisAlignedBB axisalignedbb = itemBlock.block.getDefaultState ( ).getCollisionBoundingBox ( worldIn , pos );
        if ( axisalignedbb != Block.NULL_AABB && ! worldIn.checkNoEntityCollision ( axisalignedbb.offset ( pos ) , null ) ) {
            return false;
        } else if ( iblockstate1.getMaterial ( ) == Material.CIRCUITS && itemBlock.block == Blocks.ANVIL ) {
            return true;
        }

        return iblockstate1.getBlock ( ).isReplaceable ( worldIn , pos ) && itemBlock.block.canPlaceBlockOnSide ( worldIn , pos , side );
    }

    @Inject(method = "processRightClickBlock", at = @At("HEAD"), cancellable = true)
    public
    void processRightClickBlock ( EntityPlayerSP player , WorldClient worldIn , BlockPos pos , EnumFacing direction , Vec3d vec , EnumHand hand , CallbackInfoReturnable < EnumActionResult > cir ) {
        ProcessRightClickBlockEvent event = new ProcessRightClickBlockEvent ( pos , hand , Minecraft.getMinecraft ( ).player.getHeldItem ( hand ) );
        MinecraftForge.EVENT_BUS.post ( event );
        if ( event.isCanceled ( ) ) {
            cir.cancel ( );
        }
    }

}
