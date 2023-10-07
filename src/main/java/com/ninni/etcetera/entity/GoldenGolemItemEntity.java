package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class GoldenGolemItemEntity extends ThrownItemEntity {

    public GoldenGolemItemEntity(EntityType<? extends GoldenGolemItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public GoldenGolemItemEntity(World world, LivingEntity owner) {
        super(EtceteraEntityType.THROWN_GOLDEN_GOLEM, owner, world);
    }


    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            for (int i = 0; i < 20; ++i) {
                this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()),
                        this.getParticleX(1),
                        this.getRandomBodyY(),
                        this.getParticleZ(1),
                        ((double)this.random.nextFloat() - 0.5) * 0.08,
                        ((double)this.random.nextFloat() - 0.5) * 0.08,
                        ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            GoldenGolemEntity goldenGolem = EtceteraEntityType.GOLDEN_GOLEM.create(this.getWorld());
            goldenGolem.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0f);

            if (hitResult instanceof EntityHitResult entityHitResult) {
                goldenGolem.setDefendingUuid(entityHitResult.getEntity().getUuid());
            } else {
                if (this.getOwner() != null) {
                    goldenGolem.setDefendingUuid(this.getOwner().getUuid());
                }
            }

            if (this.getItem().hasNbt() && this.getItem().getNbt().contains("HealingAmount")) goldenGolem.setHealingAmount(this.getItem().getNbt().getInt("HealingAmount"));
            if (this.getItem().hasNbt() && this.getItem().getNbt().contains("HealingCooldown")) goldenGolem.setHealingCooldown(this.getItem().getNbt().getInt("HealingCooldown"));
            else goldenGolem.setHealingAmount(10);
            this.getWorld().spawnEntity(goldenGolem);

            this.getWorld().playSound(null, this.getBlockPos(), EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_LAND, SoundCategory.NEUTRAL, 1, 1);
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);

            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return EtceteraItems.GOLDEN_GOLEM;
    }
}

