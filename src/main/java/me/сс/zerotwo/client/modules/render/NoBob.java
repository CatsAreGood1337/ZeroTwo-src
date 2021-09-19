package me.сс.zerotwo.client.modules.render;


        import me.сс.zerotwo.client.modules.Module;
        import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
        import net.minecraftforge.fml.common.gameevent.TickEvent;

        public class NoBob extends Module {

                public NoBob() {
                        super("NoBob", "", Module.Category.RENDER, true, false, false);
                }

        @SubscribeEvent
        public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.player == null)
        return;

        mc.gameSettings.viewBobbing = false;

        }

                        }
