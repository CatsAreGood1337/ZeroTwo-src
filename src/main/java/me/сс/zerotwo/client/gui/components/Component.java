package me.сс.zerotwo.client.gui.components;

import me.сс.zerotwo.ZeroTwo;
import me.сс.zerotwo.client.Client;
import me.сс.zerotwo.client.gui.components.items.Item;
import me.сс.zerotwo.client.gui.components.items.buttons.Button;
import me.сс.zerotwo.client.gui.zerotwoGui;
import me.сс.zerotwo.client.modules.client.ClickGui;
import me.сс.zerotwo.client.modules.client.Colors;
import me.сс.zerotwo.api.util.moduleUtil.ColorUtil;
import me.сс.zerotwo.api.util.moduleUtil.GuiRenderUtil;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Component
        extends Client {
    private ResourceLocation gear = new ResourceLocation("textures/geaer.png");
    private ResourceLocation misc = new ResourceLocation("textures/misc.png");
    private ResourceLocation world = new ResourceLocation("textures/world.png");
    private ResourceLocation combat = new ResourceLocation("textures/combat.png");
    private ResourceLocation chat = new ResourceLocation("textures/chat.png");
    private ResourceLocation movement = new ResourceLocation("textures/movement.png");
    private ResourceLocation render = new ResourceLocation("textures/render.png");
    private ResourceLocation zerotwo = new ResourceLocation("textures/zerotwo.png");
    private int x;
    private int y;
    private int x2;
    private int y2;
    private float width;
    private float height;
    private boolean open;
    public boolean drag;
    private final ArrayList<Item> items = new ArrayList();
    private boolean hidden = false;
    private int w;

    public Component(String name, int x, int y,     boolean open) {
        super(name);
        this.x = x;
        this.y = y;
        this.width = 110;
        this.height = 18;
        this.open = open;
        this.setupItems();
    }

    public void setupItems() {
    }

    private void drag(int mouseX, int mouseY) {
        if (!this.drag) {
            return;
        }
        this.x = this.x2 + mouseX;
        this.y = this.y2 + mouseY;
    }


    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glPushMatrix();
        this.drag(mouseX, mouseY);
        float totalItemHeight = this.open ? this.getTotalItemHeight() - 2.0f : 0.0f;
        int color = new Color(255, 0, 0, 162).getRGB();
        int colorButton1 = new Color(87, 71, 87, 153).getRGB();
        int colorButton2 = new Color(87, 71, 87, 153).getRGB();
        int colorOutline1 = new Color(87, 71, 87, 153).getRGB();
        int colorOutline2 = new Color(87, 71, 87, 153).getRGB();
        if (Colors.getInstance().isEnabled()); {
                int n = color = ClickGui.getInstance().colorSync.getValue() ? Colors.INSTANCE.getCurrentColorHex() : ColorUtil.toARGB(ClickGui.getInstance().red.getValue(), ClickGui.getInstance().topGreen.getValue(), ClickGui.getInstance().topBlue.getValue(), ClickGui.getInstance().topAlpha.getValue());
            }
            if (ClickGui.getInstance().isEnabled()); {
            GuiRenderUtil.drawBorderedRect(this.x, (float)this.y - 1.5f, this.x + this.width, this.y + this.height - 6, ClickGui.getGui().getOutlineWidth() - ClickGui.getGui().getOutlineWidth()*2, new Color(255,153,234,255).getRGB(), color);
        }
        if (this.open) {
            GuiRenderUtil.drawGradientSideways(this.x, (float) this.y + 12.5f, this.x + this.width, (float) (this.y + this.height) + totalItemHeight, colorButton2 ,colorButton1);
            GuiRenderUtil.drawBorderedRect(this.x + 0.4, (float) this.y + 12.5f + 0.1 - 13, this.x + this.width + 0.1, (float) (this.y + this.height) + totalItemHeight + 0.1, ClickGui.getGui().getOutlineWidth() - ClickGui.getGui().getOutlineWidth()*2, new Color(0, 0, 0, 0).getRGB(), color);
        }
        if (!this.open)  {
            GuiRenderUtil.drawBorderedRect(this.x, (float)this.y - 1.5f, this.x + this.width, this.y + this.height - 6, ClickGui.getGui().getOutlineWidth() - ClickGui.getGui().getOutlineWidth()*2, new Color(0,0,0,0).getRGB(), color);
        }
        ZeroTwo.textManager.drawStringWithShadow(this.getName(), (float)this.x + 3.0f, (float)this.y - 4.0f - (float) zerotwoGui.getClickGui().getTextOffset(), -1);
        if (!this.open) return;
        float y = (float)(this.getY() + this.getHeight()) - 3.0f;
        Iterator<Item> iterator = this.getItems().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item.isHidden()) continue;
            item.setLocation((float)this.x + 2.0f, y);
            item.setWidth(this.getWidth() - 4);
            item.drawScreen(mouseX, mouseY, partialTicks);
            y += (float)item.getHeight() + 1.5f;
        }
        GL11.glPopMatrix();
    }

    public void rendertexturedFrame(int scale, ResourceLocation image, boolean originalColor, int color) {
        try{
            mc.getTextureManager().bindTexture(image);
            Gui.drawScaledCustomSizeModalRect((int)this.x + 3, (int)this.y - 4 - -5, 0, 0, 1, 1, 20, 20, 20, 20);
        }catch (Exception e){
            System.out.println("Error, couldn't load the texture");
            e.printStackTrace();
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && this.isHovering(mouseX, mouseY)) {
            this.x2 = this.x - mouseX;
            this.y2 = this.y - mouseY;
            zerotwoGui.getClickGui().getComponents().forEach(component -> {
                if (!component.drag) return;
                component.drag = false;
            });
            this.drag = true;
            return;
        }
        if (mouseButton == 1 && this.isHovering(mouseX, mouseY)) {
            this.open = !this.open;
            mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
            return;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseClicked(mouseX, mouseY, mouseButton));
    }

    public void mouseReleased(int mouseX, int mouseY, int releaseButton) {
        if (releaseButton == 0) {
            this.drag = false;
        }
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.mouseReleased(mouseX, mouseY, releaseButton));
    }

    public void onKeyTyped(char typedChar, int keyCode) {
        if (!this.open) {
            return;
        }
        this.getItems().forEach(item -> item.onKeyTyped(typedChar, keyCode));
    }

    public void addButton(Button button) {
        this.items.add(button);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean isHidden() {
        return this.hidden;
    }

    public boolean isOpen() {
        return this.open;
    }

    public final ArrayList<Item> getItems() {
        return this.items;
    }

    private boolean isHovering(int mouseX, int mouseY) {
        if (mouseX < this.getX()) return false;
        if (mouseX > this.getX() + this.getWidth()) return false;
        if (mouseY < this.getY()) return false;
        if (mouseY > this.getY() + this.getHeight() - (this.open ? 2 : 0)) return false;
        return true;
    }

    private float getTotalItemHeight() {
        float height = 0.0f;
        Iterator<Item> iterator = this.getItems().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            height += (float)item.getHeight() + 1.5f;
        }
        return height;
    }
}

