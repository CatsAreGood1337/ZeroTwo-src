package me.сс.zerotwo.client.modules.render;

import me.сс.zerotwo.client.modules.Module;
import me.сс.zerotwo.client.setting.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Fov extends Module {

    Minecraft mc = Minecraft.getMinecraft();

    public float defaultFov;
    private final Setting<Mode> mode = register ( new Setting <Fov.Mode> ( "Mode" , Fov.Mode.FOV ) );
    private final Setting<Integer> fov = register(new Setting<Integer>("FOV", 130, 0, 170));

    public Fov(){super("FOV", "CustomFOV", Category.RENDER, true, false, false);

    }


    @SubscribeEvent
    public void fovOn(final EntityViewRenderEvent.FOVModifier e) {
        if (mode.getValue().equals(Fov.Mode.VMC)) {
            e.setFOV((float)this.fov.getValue());
        }
    }

    public void onUpdate() {
        if(mode.getValue().equals(Fov.Mode.FOV)) {
            mc.gameSettings.fovSetting = fov.getValue().floatValue();
        }
    }

    public void onEnable() {
        this.defaultFov = mc.gameSettings.fovSetting;
    }

    public void onDisable() {
        mc.gameSettings.fovSetting = this.defaultFov;
    }

    public enum Mode{
        FOV, VMC
    }

}
