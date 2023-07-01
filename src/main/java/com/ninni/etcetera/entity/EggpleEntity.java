package com.ninni.etcetera.entity;

import com.ninni.etcetera.item.EggpleItem;
import com.ninni.etcetera.item.EtceteraItems;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class EggpleEntity extends ThrownItemEntity {

    public EggpleEntity(EntityType<? extends EggpleEntity> entityType, World world) {
        super(entityType, world);
    }

    public EggpleEntity(World world, LivingEntity owner) {
        super(EtceteraEntityType.EGGPLE, owner, world);
    }

    public EggpleEntity(World world, double x, double y, double z) {
        super(EtceteraEntityType.EGGPLE, x, y, z, world);
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
        entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 0.0f);
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            int i = 1;
            if (this.random.nextInt(160) == 0) {
                i = 4;
            }
            for (int j = 0; j < i; ++j) {
                ChappleEntity chapple = EtceteraEntityType.CHAPPLE.create(this.getWorld());
                chapple.setBreedingAge(-24000);
                if (this.getStack().getItem() instanceof EggpleItem eggpleItem && eggpleItem.isGolden) chapple.setType(ChappleEntity.Type.GOLDEN);
                chapple.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), 0.0f);
                this.getWorld().spawnEntity(chapple);
            }
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return EtceteraItems.EGGPLE;
    }
}

