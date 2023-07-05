package com.ninni.etcetera.client.gui.screen;

import com.google.common.collect.Lists;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(EnvType.CLIENT)
public class PricklyCanScreen extends HandledScreen<PricklyCanScreenHandler> {
    static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/gui/container/prickly_can.png");
    private final List<PricklyCanButtonWidget> buttons = Lists.newArrayList();

    public PricklyCanScreen(PricklyCanScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        ++this.backgroundHeight;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    public void handledScreenTick() {
        super.handledScreenTick();
        this.buttons.forEach(PricklyCanButtonWidget::tick);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        this.renderBackground(context);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    private <T extends ClickableWidget & PricklyCanButtonWidget> void addButton(T button) {
        this.addDrawableChild(button);
        this.buttons.add(button);
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        this.addButton(new DeleteButtonWidget(this.x + 159, this.y + 3));
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        super.drawForeground(context, mouseX, mouseY);
        for (PricklyCanButtonWidget button : this.buttons) {
            if (button.shouldRenderTooltip()) {
                context.drawTooltip(this.textRenderer, Text.translatable("etcetera.container.prickly_can_delete"), mouseX - this.x, mouseY - this.y);
                break;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    class DeleteButtonWidget extends PricklyCanScreen.BaseButtonWidget {

        public DeleteButtonWidget(int x, int y) {
            super(x, y, Text.translatable(""));
        }

        @Override
        public void tick() {
            this.setDisabled(PricklyCanScreen.this.getScreenHandler().getInventory().isEmpty());
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (this.active && this.visible) {
                if (keyCode != 257 && keyCode != 32 && keyCode != 335) return false;
                 else {
                    if (!this.isDisabled()) this.playDownSound(MinecraftClient.getInstance().getSoundManager());
                    this.onPress();
                    return true;
                }
            } else return false;
        }

        @Override
        public void onPress() {
            PricklyCanScreen.this.getScreenHandler().onButtonClick(PricklyCanScreen.this.client.player, 1);
        }
    }


    @Environment(EnvType.CLIENT)
    interface PricklyCanButtonWidget {
        boolean shouldRenderTooltip();

        void tick();
    }

    @Environment(EnvType.CLIENT)
    abstract static class BaseButtonWidget extends PressableWidget implements PricklyCanButtonWidget {
        private boolean disabled;

        protected BaseButtonWidget(int x, int y, Text message) {
            super(x, y, 13, 13, message);
        }

        @Override
        public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
            int j = 176;
            if (this.disabled) {
                j += 26;
            } else if (this.isHovered()) {
                j += 13;
            }
            context.drawTexture(TEXTURE, this.getX(), this.getY(), j, 0, this.width, this.height);
        }

        public boolean isDisabled() {
            return this.disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        @Override
        public boolean shouldRenderTooltip() {
            return this.hovered;
        }

        @Override
        protected void appendClickableNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }
    }
}
