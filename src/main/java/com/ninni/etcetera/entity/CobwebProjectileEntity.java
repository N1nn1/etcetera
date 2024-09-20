package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityStatuses;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class CobwebProjectileEntity extends ThrownItemEntity {

    public CobwebProjectileEntity(EntityType<? extends CobwebProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public CobwebProjectileEntity(World world, LivingEntity owner) {
        super(EtceteraEntityType.COBWEB, owner, world);
    }


    @Override
    public void handleStatus(byte status) {
        if (status == EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES) {
            for (int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, this.getStack()), this.getX(), this.getY(), this.getZ(), ((double)this.random.nextFloat() - 0.5) * 0.18, ((double)this.random.nextFloat() - 0.5) * 0.12, ((double)this.random.nextFloat() - 0.5) * 0.18);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        super.onEntityHit(entityHitResult);


        if (!this.getWorld().isClient && this.getWorld().getBlockState(entityHitResult.getEntity().getBlockPos()).isReplaceable()) this.getWorld().setBlockState(entityHitResult.getEntity().getBlockPos(), Blocks.COBWEB.getDefaultState());
        entityHitResult.getEntity().damage(this.getDamageSources().thrown(this, this.getOwner()), 5.0f);
        this.discard();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, EntityStatuses.PLAY_DEATH_SOUND_OR_ADD_PROJECTILE_HIT_PARTICLES);
            if (hitResult.getType() == HitResult.Type.ENTITY) this.getWorld().playSound(null, this.getBlockPos(), EtceteraSoundEvents.ENTITY_WEAVER_LAND, SoundCategory.PLAYERS, 1, 1);
            this.discard();
        }
    }

    @Override
    protected Item getDefaultItem() {
        return Items.COBWEB;
    }
}
