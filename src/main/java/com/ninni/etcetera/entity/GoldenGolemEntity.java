package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraParticleTypes;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.control.FlightMoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class GoldenGolemEntity extends PathAwareEntity {
    private static final TrackedData<Optional<UUID>> DEFENDING_UUID = DataTracker.registerData(GoldenGolemEntity.class, TrackedDataHandlerRegistry.OPTIONAL_UUID);
    private static final TrackedData<Integer> HEALING_AMOUNT = DataTracker.registerData(GoldenGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> HEALING_COOLDOWN = DataTracker.registerData(GoldenGolemEntity.class, TrackedDataHandlerRegistry.INTEGER);
    public final AnimationState grantingAnimationState = new AnimationState();
    public int grantPoseTick = 28;


    public GoldenGolemEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
        this.moveControl = new FlightMoveControl(this, 20, true);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new HealDefendingGoal());
        this.goalSelector.add(2, new FollowDefendingEntityGoal(1.0, 5.0f, 2.0f));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0f));
        this.goalSelector.add(4, new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0).add(EntityAttributes.GENERIC_FLYING_SPEED, 1f).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 2.0).add(EntityAttributes.GENERIC_FOLLOW_RANGE, 48.0);
    }

    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        List<UUID> list = new ArrayList<>();
        this.getWorld().getPlayers().forEach(player1 -> list.add(player1.getUuid()));
        if (!this.getWorld().isClient && player.isSneaking()) {
            if (list.contains(this.getDefendingUuid())) {
                for (UUID uuid2 : list) {
                    if (this.getDefendingUuid() == uuid2) {
                        if (uuid2 != player.getUuid()) {
                            return ActionResult.PASS;
                        } else {
                            this.dropAsItem(false);
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            } else {
                this.dropAsItem(false);
                return ActionResult.SUCCESS;
            }
        }
        return super.interactMob(player, hand);
    }

    public void dropAsItem(boolean broken) {
        ItemStack stack = new ItemStack(EtceteraItems.GOLDEN_GOLEM);
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("HealingAmount", this.getHealingAmount());
        nbt.putInt("HealingCooldown", this.getHealingCooldown());
        nbt.putBoolean("Broken", broken);
        if (this.hasCustomName()) stack.setCustomName(this.getCustomName());
        this.playSound(EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_ITEM, this.getSoundVolume(), this.getSoundPitch());
        this.dropStack(stack);
        this.discard();
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (this.random.nextFloat() < 0.05f && this.getHealingCooldown() == 0) {
            this.getWorld().addParticle(EtceteraParticleTypes.GOLDEN_SHEEN, this.getParticleX(0.8), this.getRandomBodyY(), this.getParticleZ(0.8), 0.0, 0.0, 0.0);
        }

        if (this.getPose() == EntityPose.CROAKING) {
            if (!this.getWorld().isClient) grantPoseTick--;
        } else {
            grantPoseTick = 28;
        }
        if (grantPoseTick == 0) this.setPose(EntityPose.STANDING);


        if (this.getHealingAmount() == 0) {
            this.dropAsItem(true);
        }
        if (this.getHealingCooldown() > 0) this.setHealingCooldown(this.getHealingCooldown()-1);
        if (!this.getWorld().isClient) {
            LivingEntity defendedEntity = this.getDefendedEntity();
            if (defendedEntity != null && defendedEntity.isDead()) {
                this.dropAsItem(false);
            }
        }

        if (!this.getWorld().isClient && this.isAlive() && this.age % 10 == 0) {
            this.heal(1.0f);
        }
    }

    public void grantHealing() {
        this.setPose(EntityPose.CROAKING);
        this.playSound(EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_GRANT, 1, 1);
        this.getDefendedEntity().addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 1, true, false));
        this.getDefendedEntity().heal(8);
        this.setHealingAmount(this.getHealingAmount()-1);
        this.setHealingCooldown(20 * 120);

        if (this.getWorld() instanceof ServerWorld serverWorld) {
            for (int i = 0; i < 7; ++i) {
                serverWorld.spawnParticles(
                        EtceteraParticleTypes.GOLDEN_HEART,
                        this.getDefendedEntity().getParticleX(0.8),
                        this.getDefendedEntity().getRandomBodyY(),
                        this.getDefendedEntity().getParticleZ(0.8),
                        1,
                        0,
                        0.1f,
                        0,
                        1);

            }
        }
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return false;
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (POSE.equals(data)) {
            EntityPose entityPose = this.getPose();
            if (entityPose == EntityPose.CROAKING) {
                this.grantingAnimationState.start(this.age);
            } else {
                this.grantingAnimationState.stop();
            }
        }
        super.onTrackedDataSet(data);
    }

    @Nullable
    public UUID getDefendingUuid() {
        return this.dataTracker.get(DEFENDING_UUID).orElse(null);
    }
    public void setDefendingUuid(@Nullable UUID uuid) {
        this.dataTracker.set(DEFENDING_UUID, Optional.ofNullable(uuid));
    }
    public int getHealingAmount() {
        return this.dataTracker.get(HEALING_AMOUNT);
    }
    public void setHealingAmount(int amount) {
        this.dataTracker.set(HEALING_AMOUNT, amount);
    }
    public int getHealingCooldown() {
        return this.dataTracker.get(HEALING_COOLDOWN);
    }
    public void setHealingCooldown(int cooldown) {
        this.dataTracker.set(HEALING_COOLDOWN, cooldown);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(DEFENDING_UUID, Optional.empty());
        this.dataTracker.startTracking(HEALING_AMOUNT, 10);
        this.dataTracker.startTracking(HEALING_COOLDOWN, 0);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.getDefendingUuid() != null) nbt.putUuid("Defending", this.getDefendingUuid());
        nbt.putInt("HealingAmount", this.getHealingAmount());
        nbt.putInt("HealingCooldown", this.getHealingCooldown());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.containsUuid("Defending")) {
            this.setDefendingUuid(nbt.getUuid("Defending"));
        }
        if (nbt.contains("HealingAmount")) {
            this.setHealingAmount(nbt.getInt("HealingAmount"));
        }
        if (nbt.contains("HealingCooldown")) {
            this.setHealingCooldown(nbt.getInt("HealingCooldown"));
        }
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        BirdNavigation birdNavigation = new BirdNavigation(this, world);
        birdNavigation.setCanPathThroughDoors(false);
        birdNavigation.setCanSwim(true);
        birdNavigation.setCanEnterOpenDoors(true);
        return birdNavigation;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        Entity entity = source.getAttacker();
        if (entity == this.getDefendedEntity()) {
            return false;
        }
        return super.damage(source, amount);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return this.getDefendingUuid() == player.getUuid();
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_IDLE;
    }
    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_HURT;
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return EtceteraSoundEvents.ENTITY_GOLDEN_GOLEM_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    @Nullable
    public LivingEntity getDefendedEntity() {
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            UUID uuid = this.getDefendingUuid();
            if (uuid != null) {
                PlayerEntity player = serverWorld.getPlayerByUuid(uuid);
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
        public boolean canStart() {
            if (this.cannotFollow()) {
                return false;
            }
            this.defending = this.golem.getDefendedEntity();
            return this.defending != null && this.defending.getHealth() < this.defending.getMaxHealth()/3 && this.golem.getHealingAmount() > 0 && this.golem.getHealingCooldown() == 0;
        }

        @Override
        public void tick() {
            super.tick();
            this.golem.getNavigation().startMovingTo(defending, 1);
        }

        @Override
        public boolean shouldContinue() {
            if (this.golem.distanceTo(this.defending) < 3.0F) {
                return false;
            }
            return !this.cannotFollow();
        }

        private boolean cannotFollow() {
            return this.golem.hasVehicle() || this.golem.isLeashed();
        }

        @Override
        public void stop() {
            super.stop();
            this.golem.grantHealing();
        }
    }


    public class FollowDefendingEntityGoal extends Goal {
        private final GoldenGolemEntity golem;
        @Nullable
        private LivingEntity defending;
        private final WorldView world;
        private final double speed;
        private final EntityNavigation navigation;
        private int updateCountdownTicks;
        private final float maxDistance;
        private final float minDistance;
        private float oldWaterPathfindingPenalty;

        public FollowDefendingEntityGoal(double speed, float minDistance, float maxDistance) {
            this.golem = GoldenGolemEntity.this;
            this.world = golem.getWorld();
            this.speed = speed;
            this.defending = this.golem.getDefendedEntity();
            this.navigation = golem.getNavigation();
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity livingEntity = this.golem.getDefendedEntity();
            if (livingEntity == null) return false;
            if (livingEntity.isSpectator()) return false;
            if (this.cannotFollow()) return false;
            if (this.golem.squaredDistanceTo(livingEntity) < (double)(this.minDistance * this.minDistance)) {
                return false;
            }
            this.defending = livingEntity;
            return true;
        }

        @Override
        public boolean shouldContinue() {
            if (this.navigation.isIdle()) return false;
            if (this.cannotFollow()) return false;
            return !(this.golem.squaredDistanceTo(this.defending) <= (double)(this.maxDistance * this.maxDistance));
        }

        private boolean cannotFollow() {
            return this.golem.hasVehicle() || this.golem.isLeashed();
        }

        @Override
        public void start() {
            this.updateCountdownTicks = 0;
            this.oldWaterPathfindingPenalty = this.golem.getPathfindingPenalty(PathNodeType.WATER);
            this.golem.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        }

        @Override
        public void stop() {
            this.defending = null;
            this.navigation.stop();
            this.golem.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
        }

        @Override
        public void tick() {

            this.golem.getLookControl().lookAt(this.defending, 10.0f, this.golem.getMaxLookPitchChange());
            if (--this.updateCountdownTicks > 0) {
                return;
            }
            this.updateCountdownTicks = this.getTickCount(10);
            if (this.golem.squaredDistanceTo(this.defending) >= 144.0) {
                this.tryTeleport();
            } else {
                this.navigation.startMovingTo(this.defending, this.speed);
            }
        }

        private void tryTeleport() {
            BlockPos blockPos = this.defending.getBlockPos();
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
            if (Math.abs((double)x - this.defending.getX()) < 2.0 && Math.abs((double)z - this.defending.getZ()) < 2.0) {
                return false;
            }
            if (!this.canTeleportTo(new BlockPos(x, y, z))) {
                return false;
            }
            this.golem.refreshPositionAndAngles((double)x + 0.5, y, (double)z + 0.5, this.golem.getYaw(), this.golem.getPitch());
            this.navigation.stop();
            return true;
        }

        private boolean canTeleportTo(BlockPos pos) {
            PathNodeType pathNodeType = LandPathNodeMaker.getLandNodeType(this.world, pos.mutableCopy());
            if (pathNodeType != PathNodeType.WALKABLE) {
                return false;
            }
            BlockPos blockPos = pos.subtract(this.golem.getBlockPos());
            return this.world.isSpaceEmpty(this.golem, this.golem.getBoundingBox().offset(blockPos));
        }

        private int getRandomInt(int min, int max) {
            return this.golem.getRandom().nextInt(max - min + 1) + min;
        }
    }
}
