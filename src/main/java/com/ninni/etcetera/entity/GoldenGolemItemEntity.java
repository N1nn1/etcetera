package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
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
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);
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
                goldenGolem.setDefendingUuid(this.getOwner().getUuid());
            }

            if (this.getItem().hasNbt() && this.getItem().getNbt().contains("HealingAmount")) goldenGolem.setHealingAmount(this.getItem().getNbt().getInt("HealingAmount"));
            else goldenGolem.setHealingAmount(20);
            this.getWorld().spawnEntity(goldenGolem);


            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return EtceteraItems.GOLDEN_GOLEM;
    }
}

