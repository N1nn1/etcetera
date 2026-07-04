package com.ninni.etcetera.client.particles;

import com.ninni.etcetera.registry.EtceteraParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class RubberParticle extends TextureSheetParticle {
    private final Fluid fluid;

    RubberParticle(ClientLevel world, double x, double y, double z, Fluid fluid) {
        super(world, x, y, z);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.fluid = fluid;
    }

    public static TextureSheetParticle createDrippingRubber(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        RubberParticle.Dripping particle = new RubberParticle.Dripping(world, x, y, z, Fluids.EMPTY, EtceteraParticleTypes.FALLING_RUBBER);
        particle.gravity *= 0.01F;
        particle.lifetime = 100;
        particle.setColor(0.9F, 0.9F, 0.9F);
        return particle;
    }

    public static TextureSheetParticle createFallingRubber(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        RubberParticle particle = new RubberParticle.ContinuousFalling(world, x, y, z, Fluids.EMPTY, EtceteraParticleTypes.LANDING_RUBBER);
        particle.gravity = 0.01F;
        particle.setColor(0.9F, 0.9F, 0.9F);
        return particle;
    }

    public static TextureSheetParticle createLandingRubber(ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        RubberParticle particle = new RubberParticle.Landing(world, x, y, z, Fluids.EMPTY);
        particle.lifetime = (int) (28.0 / (Math.random() * 0.8 + 0.2));
        particle.setColor(0.9F, 0.9F, 0.9F);
        return particle;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getLightColor(float tint) {
        return super.getLightColor(tint);
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.updateAge();
        if (!this.removed) {
            this.yd -= this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.updateVelocity();
            if (!this.removed) {
                this.xd *= 0.9800000190734863;
                this.yd *= 0.9800000190734863;
                this.zd *= 0.9800000190734863;
                if (this.fluid != Fluids.EMPTY) {
                    BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
                    FluidState fluidState = this.level.getFluidState(blockPos);
                    if (fluidState.getType() == this.fluid && this.y < (double) ((float) blockPos.getY() + fluidState.getHeight(this.level, blockPos))) {
                        this.remove();
                    }

                }
            }
        }
    }

    protected void updateAge() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }

    }

    protected void updateVelocity() {
    }

    private static class Dripping extends RubberParticle {
        private final ParticleOptions nextParticle;

        Dripping(ClientLevel world, double x, double y, double z, Fluid fluid, ParticleOptions nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
            this.gravity *= 0.02F;
            this.lifetime = 40;
        }

        @Override
        protected void updateAge() {
            if (this.lifetime-- <= 0) {
                this.remove();
                this.level.addParticle(this.nextParticle, this.x, this.y, this.z, this.xd, this.yd, this.zd);
            }

        }

        @Override
        protected void updateVelocity() {
            this.xd *= 0.02;
            this.yd *= 0.02;
            this.zd *= 0.02;
        }
    }


    private static class ContinuousFalling extends Falling {
        protected final ParticleOptions nextParticle;

        ContinuousFalling(ClientLevel world, double x, double y, double z, Fluid fluid, ParticleOptions nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.remove();
                this.level.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            }

        }
    }

    static class Landing extends RubberParticle {
        Landing(ClientLevel clientWorld, double d, double e, double f, Fluid fluid) {
            super(clientWorld, d, e, f, fluid);
            this.lifetime = (int) (16.0 / (Math.random() * 0.8 + 0.2));
        }
    }

    static class Falling extends RubberParticle {
        Falling(ClientLevel clientWorld, double d, double e, double f, Fluid fluid) {
            this(clientWorld, d, e, f, fluid, (int) (64.0 / (Math.random() * 0.8 + 0.2)));
        }

        Falling(ClientLevel world, double x, double y, double z, Fluid fluid, int lifetime) {
            super(world, x, y, z, fluid);
            this.lifetime = lifetime;
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.remove();
            }

        }
    }
}