package com.ninni.etcetera.client.gui.screen;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@OnlyIn(Dist.CLIENT)
public class PricklyCanScreen extends AbstractContainerScreen<PricklyCanScreenHandler> {
    static final ResourceLocation TEXTURE = new ResourceLocation(MOD_ID, "textures/gui/container/prickly_can.png");
    private final List<PricklyCanButtonWidget> buttons = Lists.newArrayList();

    public PricklyCanScreen(PricklyCanScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
        ++this.imageHeight;
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        this.renderTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        this.buttons.forEach(PricklyCanButtonWidget::tick);
    }

    @Override
    protected void renderBg(GuiGraphics context, float delta, int mouseX, int mouseY) {
        this.renderBackground(context);
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
    protected void renderLabels(GuiGraphics context, int mouseX, int mouseY) {
        super.renderLabels(context, mouseX, mouseY);
        for (PricklyCanButtonWidget button : this.buttons) {
            if (button.shouldRenderTooltip()) {
                context.renderTooltip(this.font, Component.translatable("etcetera.container.prickly_can_delete"), mouseX - this.leftPos, mouseY - this.topPos);
                break;
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    class DeleteButtonWidget extends PricklyCanScreen.BaseButtonWidget {

        public DeleteButtonWidget(int x, int y) {
            super(x, y, Component.translatable(""));
        }

        @Override
        public void tick() {
            this.setDisabled(PricklyCanScreen.this.getMenu().getContainer().isEmpty());
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
            PricklyCanScreen.this.getMenu().clickMenuButton(PricklyCanScreen.this.minecraft.player, 1);
        }
    }


    @OnlyIn(Dist.CLIENT)
    interface PricklyCanButtonWidget {
        boolean shouldRenderTooltip();

        void tick();
    }

    @OnlyIn(Dist.CLIENT)
    abstract static class BaseButtonWidget extends AbstractButton implements PricklyCanButtonWidget {
        private boolean disabled;

        protected BaseButtonWidget(int x, int y, Component message) {
            super(x, y, 13, 13, message);
        }

        @Override
        public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
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
            return this.isHovered;
        }

        @Override
        public void updateWidgetNarration(NarrationElementOutput output) {
            this.defaultButtonNarrationText(output);
        }
    }
}
