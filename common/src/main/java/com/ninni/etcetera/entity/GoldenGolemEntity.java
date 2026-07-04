package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraParticleTypes;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GoldenGolemEntity extends PathfinderMob {
    private static final EntityDataAccessor<Optional<UUID>> DEFENDING_UUID = SynchedEntityData.defineId(GoldenGolemEntity.class, EntityDataSerializers.OPTIONAL_UUID);
    private static final EntityDataAccessor<Integer> HEALING_AMOUNT = SynchedEntityData.defineId(GoldenGolemEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HEALING_COOLDOWN = SynchedEntityData.defineId(GoldenGolemEntity.class, EntityDataSerializers.INT);
    public final AnimationState grantingAnimationState = new AnimationState();
    public int grantPoseTick = 28;

    public GoldenGolemEntity(EntityType<? extends PathfinderMob> entityType, Level world) {
        super(entityType, world);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, com.ninni.etcetera.config.ModConfig.get().goldenGolemMaxHealth)
                .add(Attributes.FLYING_SPEED, (float) com.ninni.etcetera.config.ModConfig.get().goldenGolemFlyingSpeed)
                .add(Attributes.MOVEMENT_SPEED, com.ninni.etcetera.config.ModConfig.get().goldenGolemMovementSpeed)
                .add(Attributes.ATTACK_DAMAGE, com.ninni.etcetera.config.ModConfig.get().goldenGolemAttackDamage)
                .add(Attributes.FOLLOW_RANGE, com.ninni.etcetera.config.ModConfig.get().goldenGolemFollowRange);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new HealDefendingGoal());
        this.goalSelector.addGoal(2, new FollowDefendingEntityGoal(1.0, 5.0f, 2.0f));
        this.goalSelector.addGoal(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 8.0f));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
    }

    @Override
    protected @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemInHand = player.getItemInHand(hand);
        if (itemInHand.is(net.minecraft.world.item.Items.LEAD)) {
            if (this.getDefendingUuid() == null || !this.getDefendingUuid().equals(player.getUUID())) {
                return InteractionResult.PASS;
            }
        }

        List<UUID> list = new ArrayList<>();
        this.level().players().forEach(player1 -> list.add(player1.getUUID()));
        if (!this.level().isClientSide && player.isShiftKeyDown()) {
            if (list.contains(this.getDefendingUuid())) {
                for (UUID uuid2 : list) {
                    if (this.getDefendingUuid() != null && this.getDefendingUuid().equals(uuid2)) {
                        if (!uuid2.equals(player.getUUID())) {
                            return InteractionResult.PASS;
                        } else {
                            this.dropAsItem(false);
                            return InteractionResult.SUCCESS;
                        }
                    }
                }
            } else {
                this.dropAsItem(false);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    public void dropAsItem(boolean broken) {
        ItemStack stack = new ItemStack(EtceteraItems.GOLDEN_GOLEM.get());

        CompoundTag nbt = new CompoundTag();
        nbt.putInt("HealingAmount", this.getHealingAmount());
        nbt.putInt("HealingCooldown", this.getHealingCooldown());
        nbt.putBoolean("Broken", broken);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(nbt));

        if (this.hasCustomName())
            stack.set(DataComponents.CUSTOM_NAME, this.getCustomName());

        this.playSound(EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_ITEM, this.getSoundVolume(), this.getVoicePitch());
        this.spawnAtLocation(stack);
        this.discard();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.random.nextFloat() < 0.05f && this.getHealingCooldown() == 0) {
            this.level().addParticle(EtceteraParticleTypes.GOLDEN_SHEEN, this.getRandomX(0.8), this.getRandomY(), this.getRandomZ(0.8), 0.0, 0.0, 0.0);
        }

        if (this.getPose() == Pose.CROAKING) {
            if (!this.level().isClientSide) grantPoseTick--;
        } else {
            grantPoseTick = 28;
        }
        if (grantPoseTick == 0) this.setPose(Pose.STANDING);

        if (this.getHealingAmount() == 0) {
            this.dropAsItem(true);
        }
        if (this.getHealingCooldown() > 0) this.setHealingCooldown(this.getHealingCooldown() - 1);
        if (!this.level().isClientSide) {
            LivingEntity defendedEntity = this.getDefendedEntity();
            if (defendedEntity != null && defendedEntity.isDeadOrDying()) {
                this.dropAsItem(false);
            }
        }

        if (!this.level().isClientSide && this.isAlive() && this.tickCount % 10 == 0) {
            this.heal(1.0f);
        }
    }

    public void grantHealing() {
        this.setPose(Pose.CROAKING);
        this.playSound(EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_GRANT, 1, 1);
        this.getDefendedEntity().addEffect(new MobEffectInstance(MobEffects.ABSORPTION, com.ninni.etcetera.config.ModConfig.get().goldenGolemAbsorptionDuration, com.ninni.etcetera.config.ModConfig.get().goldenGolemAbsorptionAmplifier, true, false));
        this.getDefendedEntity().heal((float) com.ninni.etcetera.config.ModConfig.get().goldenGolemHealingAmount);
        this.setHealingAmount(this.getHealingAmount() - 1);
        this.setHealingCooldown(com.ninni.etcetera.config.ModConfig.get().goldenGolemHealingCooldown);

        if (this.level() instanceof ServerLevel serverWorld) {
            for (int i = 0; i < 7; ++i) {
                serverWorld.sendParticles(
                        EtceteraParticleTypes.GOLDEN_HEART,
                        this.getDefendedEntity().getRandomX(0.8),
                        this.getDefendedEntity().getRandomY(),
                        this.getDefendedEntity().getRandomZ(0.8),
                        1,
                        0,
                        0.1f,
                        0,
                        1);

            }
        }
    }

    @Override
    public boolean removeWhenFarAway(double distanceSquared) {
        return false;
    }

    @Override
    public boolean requiresCustomPersistence() {
        return true;
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> data) {
        if (Entity.DATA_POSE.equals(data)) {
            Pose entityPose = this.getPose();
            if (entityPose == Pose.CROAKING) {
                this.grantingAnimationState.start(this.tickCount);
            } else {
                this.grantingAnimationState.stop();
            }
        }
        super.onSyncedDataUpdated(data);
    }

    @Nullable
    public UUID getDefendingUuid() {
        return this.entityData.get(DEFENDING_UUID).orElse(null);
    }

    public void setDefendingUuid(@Nullable UUID uuid) {
        this.entityData.set(DEFENDING_UUID, Optional.ofNullable(uuid));
    }

    public int getHealingAmount() {
        return this.entityData.get(HEALING_AMOUNT);
    }

    public void setHealingAmount(int amount) {
        this.entityData.set(HEALING_AMOUNT, amount);
    }

    public int getHealingCooldown() {
        return this.entityData.get(HEALING_COOLDOWN);
    }

    public void setHealingCooldown(int cooldown) {
        this.entityData.set(HEALING_COOLDOWN, cooldown);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DEFENDING_UUID, Optional.empty());
        builder.define(HEALING_AMOUNT, com.ninni.etcetera.config.ModConfig.get().goldenGolemHealingCharges);
        builder.define(HEALING_COOLDOWN, 0);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        if (this.getDefendingUuid() != null) nbt.putUUID("Defending", this.getDefendingUuid());
        nbt.putInt("HealingAmount", this.getHealingAmount());
        nbt.putInt("HealingCooldown", this.getHealingCooldown());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        if (nbt.hasUUID("Defending")) {
            this.setDefendingUuid(nbt.getUUID("Defending"));
        }
        if (nbt.contains("HealingAmount")) {
            this.setHealingAmount(nbt.getInt("HealingAmount"));
        }
        if (nbt.contains("HealingCooldown")) {
            this.setHealingCooldown(nbt.getInt("HealingCooldown"));
        }
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level world) {
        FlyingPathNavigation birdNavigation = new FlyingPathNavigation(this, world);
        birdNavigation.setCanOpenDoors(false);
        birdNavigation.setCanFloat(true);
        birdNavigation.setCanPassDoors(true);
        return birdNavigation;
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return EtceteraItems.GOLDEN_GOLEM.get().getDefaultInstance();
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        Entity entity = source.getEntity();
        if (entity == this.getDefendedEntity()) {
            return false;
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean canBeLeashed() {
        return this.getDefendingUuid() != null;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_IDLE;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(@NotNull DamageSource source) {
        return EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_DEATH;
    }

    @Override
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState state) {
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, @NotNull BlockState state, @NotNull BlockPos landedPosition) {
    }

    @Nullable
    public LivingEntity getDefendedEntity() {
        if (this.level() instanceof ServerLevel serverWorld) {
            UUID uuid = this.getDefendingUuid();
            if (uuid != null) {
                Player player = serverWorld.getPlayerByUUID(uuid);
                if (player != null) {
                    return player;
                } else {
                    if (serverWorld.getEntity(uuid) instanceof LivingEntity livingEntity) {
                        return livingEntity;
                    }
                }
            }
        }
        return null;
    }

    public class HealDefendingGoal extends Goal {
        private final GoldenGolemEntity golem;
        @Nullable
        private LivingEntity defending;

        public HealDefendingGoal() {
            this.golem = GoldenGolemEntity.this;
        }

        @Override
        public boolean canUse() {
            if (this.cannotFollow()) {
                return false;
            }
            this.defending = this.golem.getDefendedEntity();
            return this.defending != null && this.defending.getHealth() < this.defending.getMaxHealth() / 3 && this.golem.getHealingAmount() > 0 && this.golem.getHealingCooldown() == 0;
        }

        @Override
        public void tick() {
            super.tick();
            this.golem.getNavigation().moveTo(defending, 1);
        }

        @Override
        public boolean canContinueToUse() {
            if (this.golem.distanceTo(this.defending) < 3.0F) {
                return false;
            }
            return !this.cannotFollow();
        }

        private boolean cannotFollow() {
            return this.golem.isPassenger() || this.golem.isLeashed();
        }

        @Override
        public void stop() {
            super.stop();
            this.golem.grantHealing();
        }
    }

    public class FollowDefendingEntityGoal extends Goal {
        private final GoldenGolemEntity golem;
        private final LevelReader world;
        private final double speed;
        private final PathNavigation navigation;
        private final float maxDistance;
        private final float minDistance;
        @Nullable
        private LivingEntity defending;
        private int updateCountdownTicks;
        private float oldWaterPathfindingPenalty;

        public FollowDefendingEntityGoal(double speed, float minDistance, float maxDistance) {
            this.golem = GoldenGolemEntity.this;
            this.world = golem.level();
            this.speed = speed;
            this.defending = this.golem.getDefendedEntity();
            this.navigation = golem.getNavigation();
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity livingEntity = this.golem.getDefendedEntity();
            if (livingEntity == null) return false;
            if (livingEntity.isSpectator()) return false;
            if (this.cannotFollow()) return false;
            if (this.golem.distanceToSqr(livingEntity) < (double) (this.minDistance * this.minDistance)) {
                return false;
            }
            this.defending = livingEntity;
            return true;
        }

        @Override
        public boolean canContinueToUse() {
            if (this.navigation.isDone()) return false;
            if (this.cannotFollow()) return false;
            return !(this.golem.distanceToSqr(this.defending) <= (double) (this.maxDistance * this.maxDistance));
        }

        private boolean cannotFollow() {
            return this.golem.isPassenger() || this.golem.isLeashed();
        }

        @Override
        public void start() {
            this.updateCountdownTicks = 0;
            this.oldWaterPathfindingPenalty = this.golem.getPathfindingMalus(PathType.WATER);
            this.golem.setPathfindingMalus(PathType.WATER, 0.0f);
        }

        @Override
        public void stop() {
            this.defending = null;
            this.navigation.stop();
            this.golem.setPathfindingMalus(PathType.WATER, this.oldWaterPathfindingPenalty);
        }

        @Override
        public void tick() {
            this.golem.getLookControl().setLookAt(this.defending, 10.0f, this.golem.getMaxHeadXRot());
            if (--this.updateCountdownTicks > 0) {
                return;
            }
            this.updateCountdownTicks = this.adjustedTickDelay(10);
            if (this.golem.distanceToSqr(this.defending) >= 144.0) {
                this.tryTeleport();
            } else {
                this.navigation.moveTo(this.defending, this.speed);
            }
        }

        private void tryTeleport() {
            BlockPos blockPos = this.defending.blockPosition();
            for (int i = 0; i < 10; ++i) {
                int j = this.getRandomInt(-3, 3);
                int k = this.getRandomInt(-1, 1);
                int l = this.getRandomInt(-3, 3);
                boolean bl = this.tryTeleportTo(blockPos.getX() + j, blockPos.getY() + k, blockPos.getZ() + l);
                if (!bl) continue;
                return;
            }
        }

        private boolean tryTeleportTo(int x, int y, int z) {
            if (Math.abs((double) x - this.defending.getX()) < 2.0 && Math.abs((double) z - this.defending.getZ()) < 2.0) {
                return false;
            }
            if (!this.canTeleportTo(new BlockPos(x, y, z))) {
                return false;
            }
            this.golem.moveTo((double) x + 0.5, y, (double) z + 0.5, this.golem.getYRot(), this.golem.getXRot());
            this.navigation.stop();
            return true;
        }

        private boolean canTeleportTo(BlockPos pos) {
            PathType pathNodeType = WalkNodeEvaluator.getPathTypeStatic(this.golem, pos.mutable());
            if (pathNodeType != PathType.WALKABLE) {
                return false;
            }
            BlockPos blockPos = pos.subtract(this.golem.blockPosition());
            return this.world.noCollision(this.golem, this.golem.getBoundingBox().move(blockPos));
        }

        private int getRandomInt(int min, int max) {
            return this.golem.getRandom().nextInt(max - min + 1) + min;
        }
    }
}