package com.ninni.etcetera.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.PressableWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

@Environment(EnvType.CLIENT)
public class PricklyCanScreen extends HandledScreen<Generic3x3ContainerScreenHandler> {
    static final Identifier TEXTURE = new Identifier(MOD_ID, "textures/gui/container/prickly_can.png");

    public PricklyCanScreen(Generic3x3ContainerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = 176;
        this.backgroundHeight = 166;
    }

    @Override
    protected void init() {
        this.addDrawableChild(new PricklyCanScreen.CancelButtonWidget(this.x + 176, this.y));
        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }


    @Environment(EnvType.CLIENT)
    class CancelButtonWidget extends IconButtonWidget {
        public CancelButtonWidget(int x, int y) {
            super(x, y, 112, 220, ScreenTexts.CANCEL);
        }

        @Override
        public void onPress() {
            //TODO
        }

        @Override
        public void tick(int level) {
        }
    }

    @Environment(EnvType.CLIENT)
    interface PricklyCanButtonWidget {
        boolean shouldRenderTooltip();

        void renderTooltip(MatrixStack matrices, int mouseX, int mouseY);

        void tick(int level);
    }

    @Environment(EnvType.CLIENT)
    abstract class IconButtonWidget extends BaseButtonWidget {
        private final int u;
        private final int v;

        protected IconButtonWidget(int x, int y, int u, int v, Text message) {
            super(x, y, message);
            this.u = u;
            this.v = v;
        }

        @Override
        protected void renderExtra(MatrixStack matrices) {
            this.drawTexture(matrices, this.x + 2, this.y + 2, this.u, this.v, 18, 18);
        }

        @Override
        public void renderTooltip(MatrixStack matrices, int mouseX, int mouseY) {
            PricklyCanScreen.this.renderTooltip(matrices, PricklyCanScreen.this.title, mouseX, mouseY);
        }
    }

    @Environment(EnvType.CLIENT)
    abstract static class BaseButtonWidget extends PressableWidget implements PricklyCanButtonWidget {
        private boolean disabled;

        protected BaseButtonWidget(int x, int y) {
            super(x, y, 22, 22, ScreenTexts.EMPTY);
        }

        protected BaseButtonWidget(int x, int y, Text message) {
            super(x, y, 22, 22, message);
        }

        @Override
        public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, TEXTURE);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            int j = 0;
            if (!this.active) {
                j += this.width * 2;
            } else if (this.disabled) {
                j += this.width;
            } else if (this.isHovered()) {
                j += this.width * 3;
            }

            this.drawTexture(matrices, this.x, this.y, j, 219, this.width, this.height);
            this.renderExtra(matrices);
        }

        protected abstract void renderExtra(MatrixStack matrices);

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
        public void appendNarrations(NarrationMessageBuilder builder) {
            this.appendDefaultNarrations(builder);
        }
    }
}
