package com.ninni.etcetera.entity;

import com.ninni.etcetera.item.EggpleItem;
import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

public class EggpleEntity extends ThrowableItemProjectile {

    public EggpleEntity(EntityType<? extends EggpleEntity> entityType, Level world) {
        super(entityType, world);
    }

    public EggpleEntity(Level world, LivingEntity owner) {
        super(EtceteraEntityType.EGGPLE.get(), owner, world);
    }

    public EggpleEntity(Level world, double x, double y, double z) {
        super(EtceteraEntityType.EGGPLE.get(), x, y, z, world);
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            for (int i = 0; i < 8; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08, ((double)this.random.nextFloat() - 0.5) * 0.08);
            }
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        super.onHitEntity(entityHitResult);
        entityHitResult.getEntity().hurt(this.damageSources().thrown(this, this.getOwner()), 0.0f);
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            int i = 1;
            if (this.random.nextInt(160) == 0) {
                i = 4;
            }
            for (int j = 0; j < i; ++j) {
                ChappleEntity chapple = EtceteraEntityType.CHAPPLE.get().create(this.level());
                chapple.setAge(-24000);
                if (this.getItem().getItem() instanceof EggpleItem eggpleItem && eggpleItem.isGolden) chapple.setType(ChappleEntity.Type.GOLDEN);
                chapple.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0f);
                this.level().addFreshEntity(chapple);
            }
            this.level().broadcastEntityEvent(this, (byte)3);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return EtceteraItems.EGGPLE.get();
    }
}

