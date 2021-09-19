
package me.сс.zerotwo.client.gui.components.items.buttons;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.gui.zerotwoGui;
import me.сс.zerotwo.client.modules.client.ClickGui;
import me.сс.zerotwo.client.setting.Setting;
import me.сс.zerotwo.api.util.moduleUtil.RenderUtil;
import me.сс.zerotwo.api.util.moduleUtil.TextUtil;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class EnumButton extends Button
{
    public Setting setting;

    public EnumButton(final Setting setting) {
        super(setting.getName());
        this.setting = setting;
        this.width = 15;
    }

    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
            RenderUtil.drawRect(this.x, this.y, this.x + this.width + 7.4f, this.y + this.height - 0.5f, this.getState() ? (this.isHovering(mouseX, mouseY) ? ZeroTwo.colorManager.getColorWithAlpha(ZeroTwo.moduleManager.getModuleByClass(ClickGui.class).alpha.getValue()) : ZeroTwo.colorManager.getColorWithAlpha(ZeroTwo.moduleManager.getModuleByClass(ClickGui.class).hoverAlpha.getValue())) : (this.isHovering(mouseX, mouseY) ? -2007673515 : 290805077));
        ZeroTwo.textManager.drawStringWithShadow(setting.getName() + " " + TextUtil.GRAY + setting.currentEnumName(), x + 2.3F, y - 1.7F - zerotwoGui.getClickGui().getTextOffset(), getState() ? 0xFFFFFFFF : 0xFFAAAAAA);
    }

    @Override
    public void update() {
        this.setHidden(!this.setting.isVisible());
    }

    @Override
    public void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (this.isHovering(mouseX, mouseY)) {
            EnumButton.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0f));
        }
    }

    @Override
    public float getHeight() {
        return 14;
    }

    @Override
    public void toggle() {
        this.setting.increaseEnum();
    }

    @Override
    public boolean getState() {
        return true;
    }
}
