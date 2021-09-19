package me.сс.zerotwo.client.modules.client;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;

public class FontColor extends Module {

    public Setting<Integer> red = this.register (new Setting("Red", 255, 0, 255));
    public Setting<Integer> green = this.register (new Setting("Green", 0, 0, 255));
    public Setting<Integer> blue = this.register (new Setting("Blue", 0, 0, 255));
    public Setting<Integer> hoverAlpha = this.register (new Setting("Alpha", 180, 0, 255));

    private static FontColor INSTANCE = new FontColor();

    public FontColor() {
        super("FontColor", "", Category.CLIENT, true, false, false);
        setInstance();
    }

    private void setInstance() {
        INSTANCE = this;
    }

    public static FontColor getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new FontColor();
        }
        return INSTANCE;
    }
    @Override
    public void onLoad() {
        {
            ZeroTwo.colorManager.setColor(this.red.getValue(), this.green.getValue(), this.blue.getValue(), this.hoverAlpha.getValue());
        }
    }
}