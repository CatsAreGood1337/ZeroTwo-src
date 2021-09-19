package me.сс.zerotwo.mixin.mixins;
import me.сс.zerotwo.client.modules.render.ToolTips;
import me.сс.zerotwo.ZeroTwo;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemShulkerBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreen.class)
public
class MixinGuiScreen extends Gui {


        @Inject(method={"renderToolTip"}, at={@At(value="HEAD")}, cancellable=true)
        public void renderToolTipHook(ItemStack stack, int x, int y, CallbackInfo info) {
            if (ToolTips.getInstance().isOn() && ToolTips.getInstance().shulkers.getValue().booleanValue() && stack.getItem() instanceof ItemShulkerBox) {
                ToolTips.getInstance().renderShulkerToolTip(stack, x, y, null);
                info.cancel();
            }
        }
    }



