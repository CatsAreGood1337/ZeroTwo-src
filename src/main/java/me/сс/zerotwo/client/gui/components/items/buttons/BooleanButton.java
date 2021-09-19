package me.сс.zerotwo.client.gui.components.items.buttons;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.gui.zerotwoGui;
import me.сс.zerotwo.client.setting.Setting;
import me.сс.zerotwo.client.modules.client.ClickGui;
import me.сс.zerotwo.api.util.moduleUtil.RenderUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;

public class BooleanButton extends Button {

    private Setting setting;

    public BooleanButton(Setting setting) {
        super(setting.getName());
        this.setting = setting;
        width = 15;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        RenderUtil.drawRect(x, y, x + width + 7.4F, y + height - 0.5f, getState() ? (!isHovering(mouseX, mouseY) ? ZeroTwo.colorManager.getColorWithAlpha((ZeroTwo.moduleManager.getModuleByClass(ClickGui.class)).hoverAlpha.getValue()) : ZeroTwo.colorManager.getColorWithAlpha((ZeroTwo.moduleManager.getModuleByClass(ClickGui.class)).alpha.getValue())) : !isHovering(mouseX, mouseY) ? 0x11555555 : 0x88555555);
        ZeroTwo.textManager.drawStringWithShadow(getName(), x + 2.3F, y - 1.7F - zerotwoGui.getClickGui().getTextOffset(), getState() ? 0xFFFFFFFF : 0xFFAAAAAA);
    }

    @Override
    public void update() {
        this.setHidden(!setting.isVisible());
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (isHovering(mouseX, mouseY)) {
            mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
    }

    @Override
    public float getHeight() {
        return 14;
    }

    public void toggle() {
        setting.setValue(!(boolean) setting.getValue());
    }

    public boolean getState() {
        return (boolean) setting.getValue();
    }
}
