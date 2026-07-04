package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class CobwebProjectileEntity extends ThrowableItemProjectile {

    public CobwebProjectileEntity(EntityType<? extends CobwebProjectileEntity> entityType, Level world) {
        super(entityType, world);
    }

    public CobwebProjectileEntity(Level world, LivingEntity owner) {
        super(EtceteraEntityType.COBWEB.get(), owner, world);
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double) this.random.nextFloat() - 0.5) * 0.18, ((double) this.random.nextFloat() - 0.5) * 0.12, ((double) this.random.nextFloat() - 0.5) * 0.18);
            }
        }
    }

    @Override
    protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);

        if (!this.level().isClientSide && this.level().getBlockState(entityHitResult.getEntity().blockPosition()).canBeReplaced()) {
            this.level().setBlockAndUpdate(entityHitResult.getEntity().blockPosition(), Blocks.COBWEB.defaultBlockState());
        }
        entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 5.0f);
        this.discard();
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.level().broadcastEntityEvent(this, (byte) 3);
            if (hitResult.getType() == HitResult.Type.ENTITY) {
                this.level().playSound(null, this.blockPosition(), EtceteraSoundEvents.ENTITY_WEAVER_LAND, SoundSource.PLAYERS, 1, 1);
            }
            this.discard();
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return Items.COBWEB;
    }
}