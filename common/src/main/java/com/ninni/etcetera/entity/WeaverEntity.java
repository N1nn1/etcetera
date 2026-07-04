package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WeaverEntity extends Monster implements RangedAttackMob {
    public WeaverEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, com.ninni.etcetera.config.ModConfig.get().weaverMaxHealth)
                .add(Attributes.MOVEMENT_SPEED, com.ninni.etcetera.config.ModConfig.get().weaverMovementSpeed)
                .add(Attributes.ATTACK_DAMAGE, com.ninni.etcetera.config.ModConfig.get().weaverAttackDamage);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new WeaverCobwebGoal(this, 1, 20, 18));
        this.goalSelector.addGoal(2, new WeaverPounceGoal(this, 0.3f));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1f, false));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, false));
    }

    @Override
    public void makeStuckInBlock(BlockState state, @NotNull Vec3 multiplier) {
        if (!state.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(state, multiplier);
        }
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.is(MobEffects.POISON)) {
            return false;
        }
        return super.canBeAffected(effect);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return EtceteraSoundEvents.ENTITY_WEAVER_IDLE;
    }

    @Override
    protected @NotNull SoundEvent getHurtSound(@NotNull DamageSource source) {
        return EtceteraSoundEvents.ENTITY_WEAVER_HURT;
    }

    @Override
    protected @NotNull SoundEvent getDeathSound() {
        return EtceteraSoundEvents.ENTITY_WEAVER_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
        this.playSound(EtceteraSoundEvents.ENTITY_WEAVER_STEP, 0.15f, 1.0f);
    }

    @Override
    public void performRangedAttack(LivingEntity target, float pullProgress) {
        CobwebProjectileEntity cobwebProjectileEntity = new CobwebProjectileEntity(this.level(), this);
        double d = target.getX() - this.getX();
        double e = target.getY(0.3333333333333333) - cobwebProjectileEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);

        cobwebProjectileEntity.shoot(d, e + g * (double) 0.2f, f, 1.6f, 14 - this.level().getDifficulty().getId() * 4);
        this.playSound(EtceteraSoundEvents.ENTITY_WEAVER_SPIT, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        this.level().addFreshEntity(cobwebProjectileEntity); // spawnEntity -> addFreshEntity
    }

    @Override
    public boolean doHurtTarget(@NotNull Entity target) {
        this.playSound(EtceteraSoundEvents.ENTITY_WEAVER_ATTACK, 1.0f, 1.0f / (this.getRandom().nextFloat() * 0.4f + 0.8f));
        return super.doHurtTarget(target);
    }

    private boolean inWeb(@Nullable LivingEntity target) {
        if (target == null) {
            return false;
        }
        boolean flag = false;
        AABB box = target.getBoundingBox();
        BlockPos blockPos = BlockPos.containing(box.minX + 1.0E-7, box.minY + 1.0E-7, box.minZ + 1.0E-7);
        BlockPos blockPos2 = BlockPos.containing(box.maxX - 1.0E-7, box.maxY - 1.0E-7, box.maxZ - 1.0E-7);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        for (int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
            for (int j = blockPos.getY(); j <= blockPos2.getY(); ++j) {
                for (int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
                    mutable.set(i, j, k);
                    BlockState blockState = this.level().getBlockState(mutable);
                    if (blockState.is(Blocks.COBWEB)) {
                        flag = true;
                    }
                }
            }
        }
        return flag;
    }

    public class WeaverPounceGoal extends LeapAtTargetGoal {

        public WeaverPounceGoal(Mob mob, float velocity) {
            super(mob, velocity);
        }

        @Override
        public boolean canUse() {
            return WeaverEntity.this.inWeb(WeaverEntity.this.getTarget()) && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return WeaverEntity.this.inWeb(WeaverEntity.this.getTarget()) && super.canContinueToUse();
        }
    }

    public class WeaverCobwebGoal extends RangedAttackGoal {

        public WeaverCobwebGoal(RangedAttackMob mob, double mobSpeed, int intervalTicks, float maxShootRange) {
            super(mob, mobSpeed, intervalTicks, maxShootRange);
        }

        @Override
        public boolean canUse() {
            return !WeaverEntity.this.inWeb(WeaverEntity.this.getTarget()) && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !WeaverEntity.this.inWeb(WeaverEntity.this.getTarget()) && super.canContinueToUse();
        }
    }
}