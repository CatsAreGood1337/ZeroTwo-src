package me.сс.zerotwo.client.notifications;

import me.сс.zerotwo.client.modules.client.HUD;
import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.api.util.moduleUtil.RenderUtil;
import me.сс.zerotwo.api.util.moduleUtil.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public
class Notifications {
    private final String text;
    private final long disableTime;
    private final float width;
    private final Timer timer = new Timer ( );

    public
    Notifications ( String text , long disableTime ) {
        this.text = text;
        this.disableTime = disableTime;
        this.width = ZeroTwo.moduleManager.getModuleByClass ( HUD.class ).renderer.getStringWidth ( text );
        timer.reset ( );
    }

    public
    void onDraw ( int y ) {
        final ScaledResolution scaledResolution = new ScaledResolution ( Minecraft.getMinecraft ( ) );
        if ( timer.passedMs ( disableTime ) ) ZeroTwo.notificationManager.getNotifications ( ).remove ( this );
        RenderUtil.drawRect ( scaledResolution.getScaledWidth ( ) - 4 - width , y , scaledResolution.getScaledWidth ( ) - 2 , y + ZeroTwo.moduleManager.getModuleByClass ( HUD.class ).renderer.getFontHeight ( ) + 3 , 0x75000000 );
        ZeroTwo.moduleManager.getModuleByClass ( HUD.class ).renderer.drawString ( text , scaledResolution.getScaledWidth ( ) - width - 3 , y + 2 , - 1 , true );
    }
}
