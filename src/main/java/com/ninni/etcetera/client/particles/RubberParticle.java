package com.ninni.etcetera.client.particles;

import com.ninni.etcetera.registry.EtceteraParticleTypes;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;

@Environment(EnvType.CLIENT)
public class RubberParticle extends SpriteBillboardParticle {
    private final Fluid fluid;

    RubberParticle(ClientWorld world, double x, double y, double z, Fluid fluid) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.01F, 0.01F);
        this.gravityStrength = 0.06F;
        this.fluid = fluid;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public int getBrightness(float tint) {
        return super.getBrightness(tint);
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.updateAge();
        if (!this.dead) {
            this.velocityY -= this.gravityStrength;
            this.move(this.velocityX, this.velocityY, this.velocityZ);
            this.updateVelocity();
            if (!this.dead) {
                this.velocityX *= 0.9800000190734863;
                this.velocityY *= 0.9800000190734863;
                this.velocityZ *= 0.9800000190734863;
                if (this.fluid != Fluids.EMPTY) {
                    BlockPos blockPos = BlockPos.ofFloored(this.x, this.y, this.z);
                    FluidState fluidState = this.world.getFluidState(blockPos);
                    if (fluidState.getFluid() == this.fluid && this.y < (double)((float)blockPos.getY() + fluidState.getHeight(this.world, blockPos))) {
                        this.markDead();
                    }

                }
            }
        }
    }

    protected void updateAge() {
        if (this.maxAge-- <= 0) {
            this.markDead();
        }

    }

    protected void updateVelocity() {
    }

    public static SpriteBillboardParticle createDrippingRubber(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        RubberParticle.Dripping particle = new RubberParticle.Dripping(world, x, y, z, Fluids.EMPTY, EtceteraParticleTypes.FALLING_RUBBER);
        particle.gravityStrength *= 0.01F;
        particle.maxAge = 100;
        particle.setColor(0.9F, 0.9F, 0.9F);
        return particle;
    }

    public static SpriteBillboardParticle createFallingRubber(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        RubberParticle particle = new RubberParticle.ContinuousFalling(world, x, y, z, Fluids.EMPTY, EtceteraParticleTypes.LANDING_RUBBER);
        particle.gravityStrength = 0.01F;
        particle.setColor(0.9F, 0.9F, 0.9F);
        return particle;
    }

    public static SpriteBillboardParticle createLandingRubber(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
        RubberParticle particle = new RubberParticle.Landing(world, x, y, z, Fluids.EMPTY);
        particle.maxAge = (int)(28.0 / (Math.random() * 0.8 + 0.2));
        particle.setColor(0.9F, 0.9F, 0.9F);
        return particle;
    }


    @Environment(EnvType.CLIENT)
    private static class Dripping extends RubberParticle {
        private final ParticleEffect nextParticle;

        Dripping(ClientWorld world, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
            this.gravityStrength *= 0.02F;
            this.maxAge = 40;
        }

        @Override
        protected void updateAge() {
            if (this.maxAge-- <= 0) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, this.velocityX, this.velocityY, this.velocityZ);
            }

        }

        @Override
        protected void updateVelocity() {
            this.velocityX *= 0.02;
            this.velocityY *= 0.02;
            this.velocityZ *= 0.02;
        }
    }

    @Environment(EnvType.CLIENT)
    private static class ContinuousFalling extends Falling {
        protected final ParticleEffect nextParticle;

        ContinuousFalling(ClientWorld world, double x, double y, double z, Fluid fluid, ParticleEffect nextParticle) {
            super(world, x, y, z, fluid);
            this.nextParticle = nextParticle;
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
            }

        }
    }

    @Environment(EnvType.CLIENT)
    static class Landing extends RubberParticle {
        Landing(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
            super(clientWorld, d, e, f, fluid);
            this.maxAge = (int)(16.0 / (Math.random() * 0.8 + 0.2));
        }
    }

    @Environment(EnvType.CLIENT)
    static class Falling extends RubberParticle {
        Falling(ClientWorld clientWorld, double d, double e, double f, Fluid fluid) {
            this(clientWorld, d, e, f, fluid, (int)(64.0 / (Math.random() * 0.8 + 0.2)));
        }

        Falling(ClientWorld world, double x, double y, double z, Fluid fluid, int maxAge) {
            super(world, x, y, z, fluid);
            this.maxAge = maxAge;
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
            }

        }
    }
}
