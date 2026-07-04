package com.ninni.etcetera.client.gui.screen;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.ninni.etcetera.Constants.MOD_ID;

public class PricklyCanScreen extends AbstractContainerScreen<PricklyCanScreenHandler> {
    static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(MOD_ID, "textures/gui/container/prickly_can.png");
    private final List<PricklyCanButtonWidget> buttons = Lists.newArrayList();

    public PricklyCanScreen(PricklyCanScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        ++this.imageHeight;
    }

    @Override
    public void render(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        this.renderTooltip(context, mouseX, mouseY);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        this.buttons.forEach(PricklyCanButtonWidget::tick);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        context.blit(TEXTURE, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    private <T extends AbstractWidget & PricklyCanButtonWidget> void addButton(T button) {
        this.addRenderableWidget(button);
        this.buttons.add(button);
    }

    @Override
    protected void init() {
        super.init();
        this.buttons.clear();
        this.addButton(new DeleteButtonWidget(this.leftPos + 159, this.topPos + 3));
    }

    @Override
    protected void renderLabels(@NotNull GuiGraphics context, int mouseX, int mouseY) {
        super.renderLabels(context, mouseX, mouseY);
        for (PricklyCanButtonWidget button : this.buttons) {
            if (button.shouldRenderTooltip()) {
                context.renderTooltip(this.font, Component.translatable("etcetera.container.prickly_can_delete"), mouseX - this.leftPos, mouseY - this.topPos);
                break;
            }
        }
    }

    interface PricklyCanButtonWidget {
        boolean shouldRenderTooltip();

        void tick();
    }

    abstract static class BaseButtonWidget extends Button implements PricklyCanButtonWidget {
        private boolean disabled;

        protected BaseButtonWidget(int x, int y, Component message) {
            super(x, y, 13, 13, message, button -> {
            }, Button.DEFAULT_NARRATION);
        }

        @Override
        public void renderWidget(@NotNull GuiGraphics context, int mouseX, int mouseY, float delta) {
            int j = 176;
            if (this.disabled) {
                j += 26;
            } else if (this.isHovered()) {
                j += 13;
            }
            context.blit(TEXTURE, this.getX(), this.getY(), j, 0, this.width, this.height);
        }

        public boolean isDisabled() {
            return this.disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        @Override
        public boolean shouldRenderTooltip() {
            return this.isHovered();
        }

        @Override
        public void updateWidgetNarration(@NotNull NarrationElementOutput builder) {
            this.defaultButtonNarrationText(builder);
        }
    }

    class DeleteButtonWidget extends PricklyCanScreen.BaseButtonWidget {
        public DeleteButtonWidget(int x, int y) {
            super(x, y, Component.translatable(""));
        }

        @Override
        public void tick() {
            this.setDisabled(PricklyCanScreen.this.getMenu().getInventory().isEmpty());
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (this.active && this.visible) {
                if (keyCode != 257 && keyCode != 32 && keyCode != 335) return false;
                else {
                    if (!this.isDisabled()) this.playDownSound(Minecraft.getInstance().getSoundManager());
                    this.onPress();
                    return true;
                }
            } else return false;
        }

        @Override
        public void onPress() {
            PricklyCanScreen.this.minecraft.gameMode.handleInventoryButtonClick(PricklyCanScreen.this.getMenu().containerId, 1);
        }
    }
}