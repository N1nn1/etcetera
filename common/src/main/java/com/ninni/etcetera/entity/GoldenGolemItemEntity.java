package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public class GoldenGolemItemEntity extends ThrowableItemProjectile {

    public GoldenGolemItemEntity(EntityType<? extends GoldenGolemItemEntity> entityType, Level world) {
        super(entityType, world);
    }

    public GoldenGolemItemEntity(Level world, LivingEntity owner) {
        super(EtceteraEntityType.THROWN_GOLDEN_GOLEM.get(), owner, world);
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            for (int i = 0; i < 20; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                        this.getRandomX(1),
                        this.getRandomY(),
                        this.getRandomZ(1),
                        ((double) this.random.nextFloat() - 0.5) * 0.08,
                        ((double) this.random.nextFloat() - 0.5) * 0.08,
                        ((double) this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void onHit(@NotNull HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            GoldenGolemEntity goldenGolem = EtceteraEntityType.GOLDEN_GOLEM.get().create(this.level());
            if (goldenGolem != null) {
                goldenGolem.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0f);

                if (hitResult instanceof EntityHitResult entityHitResult) {
                    goldenGolem.setDefendingUuid(entityHitResult.getEntity().getUUID());
                } else {
                    if (this.getOwner() != null) {
                        goldenGolem.setDefendingUuid(this.getOwner().getUUID());
                    }
                }

                CustomData customData = this.getItem().getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
                if (customData.contains("HealingAmount"))
                    goldenGolem.setHealingAmount(customData.copyTag().getInt("HealingAmount"));
                else goldenGolem.setHealingAmount(10);

                if (customData.contains("HealingCooldown"))
                    goldenGolem.setHealingCooldown(customData.copyTag().getInt("HealingCooldown"));

                if (this.getItem().has(DataComponents.CUSTOM_NAME))
                    goldenGolem.setCustomName(this.getItem().getHoverName());

                this.level().addFreshEntity(goldenGolem);
            }

            this.level().playSound(null, this.blockPosition(), EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_LAND, SoundSource.NEUTRAL, 1, 1);
            this.level().broadcastEntityEvent(this, (byte) 3);

            this.discard();
        }
    }

    @Override
    protected @NotNull Item getDefaultItem() {
        return EtceteraItems.GOLDEN_GOLEM.get();
    }
}