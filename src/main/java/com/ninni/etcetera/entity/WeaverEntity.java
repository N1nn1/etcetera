package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WeaverEntity extends HostileEntity implements RangedAttackMob {
    public WeaverEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new WeaverCobwebGoal(this, 1, 20, 18));
        this.goalSelector.add(2, new WeaverPounceGoal(this, 0.3f));
        this.goalSelector.add(3, new AttackGoal(this));
        this.goalSelector.add(4, new MeleeAttackGoal(this, 1f, false));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, IronGolemEntity.class, false));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 50.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0);
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.65f;
    }

    @Override
    public EntityGroup getGroup() {
        return EntityGroup.ARTHROPOD;
    }

    @Override
    public void slowMovement(BlockState state, Vec3d multiplier) {
        if (!state.isOf(Blocks.COBWEB)) {
            super.slowMovement(state, multiplier);
        }
    }

    @Override
    public boolean canHaveStatusEffect(StatusEffectInstance effect) {
        if (effect.getEffectType() == StatusEffects.POISON) {
            return false;
        }
        return super.canHaveStatusEffect(effect);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return EtceteraSoundEvents.ENTITY_WEAVER_IDLE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return EtceteraSoundEvents.ENTITY_WEAVER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return EtceteraSoundEvents.ENTITY_WEAVER_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(EtceteraSoundEvents.ENTITY_WEAVER_STEP, 0.15f, 1.0f);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        CobwebProjectileEntity cobwebProjectileEntity = new CobwebProjectileEntity(this.getWorld(), this);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333) - cobwebProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        cobwebProjectileEntity.setVelocity(d, e + g * (double)0.2f, f, 1.6f, 14 - this.getWorld().getDifficulty().getId() * 4);
        this.playSound(EtceteraSoundEvents.ENTITY_WEAVER_SPIT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.getWorld().spawnEntity(cobwebProjectileEntity);
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.playSound(EtceteraSoundEvents.ENTITY_WEAVER_ATTACK, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        return super.tryAttack(target);
    }

    public class WeaverPounceGoal extends PounceAtTargetGoal {

        public WeaverPounceGoal(MobEntity mob, float velocity) {
            super(mob, velocity);
        }

        @Override
        public boolean canStart() {
            return  (WeaverEntity.this.getTarget() != null && WeaverEntity.this.getWorld().getBlockState(WeaverEntity.this.getTarget().getBlockPos()).isOf(Blocks.COBWEB)) && super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            return (WeaverEntity.this.getTarget() != null && WeaverEntity.this.getWorld().getBlockState(WeaverEntity.this.getTarget().getBlockPos()).isOf(Blocks.COBWEB)) && super.shouldContinue();
        }
    }

    public class WeaverCobwebGoal extends ProjectileAttackGoal {

        public WeaverCobwebGoal(RangedAttackMob mob, double mobSpeed, int intervalTicks, float maxShootRange) {
            super(mob, mobSpeed, intervalTicks, maxShootRange);
        }

        @Override
        public boolean canStart() {
            return (WeaverEntity.this.getTarget() != null && !WeaverEntity.this.getWorld().getBlockState(WeaverEntity.this.getTarget().getBlockPos()).isOf(Blocks.COBWEB)) && super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            return (WeaverEntity.this.getTarget() != null && !WeaverEntity.this.getWorld().getBlockState(WeaverEntity.this.getTarget().getBlockPos()).isOf(Blocks.COBWEB)) && super.shouldContinue();
        }
    }

}
