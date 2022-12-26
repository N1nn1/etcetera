package com.ninni.etcetera.entity;

import com.ninni.etcetera.EtceteraTags;
import com.ninni.etcetera.item.EtceteraItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class SnailEntity extends AnimalEntity {
    private static final TrackedData<Integer> SCARED_TICKS = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> SHELL_GROWTH = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> HAS_EATEN = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final UniformIntProvider regrowthTicks = UniformIntProvider.create(24000, 48000);

    protected SnailEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(5, new FollowParentGoal(this, 1));
        this.goalSelector.add(6, new SnailWanderGoal(this, 1));
        this.goalSelector.add(7, new SnailLookAtEntityGoal(this, PlayerEntity.class, 6));
        this.goalSelector.add(8, new SnailLookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1);
    }


    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if (itemStack.isIn(EtceteraTags.SNAIL_EATS) && !this.isScared()) {
            if (!this.world.isClient && !this.hasEaten()) {
                this.setHasEaten(true);
                if (!player.getAbilities().creativeMode) player.getStackInHand(player.getActiveHand()).decrement(1);
                this.playSound(SoundEvents.ENTITY_FOX_EAT, 1.0f, 1.0f);
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }

        return super.interactMob(player, hand);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SCARED_TICKS, 0);
        this.dataTracker.startTracking(SHELL_GROWTH, 0);
        this.dataTracker.startTracking(HAS_EATEN, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("ScaredTicks", this.getScaredTicks());
        nbt.putInt("Shelled", this.getShellGrowthTicks());
        nbt.putBoolean("HasEaten", this.hasEaten());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasEaten(nbt.getBoolean("HasEaten"));
        this.setScaredTicks(nbt.getInt("ScaredTicks"));
        this.setShellGrowthTicks(nbt.getInt("Shelled"));
    }

    public boolean hasEaten() {
        return this.dataTracker.get(HAS_EATEN);
    }
    public void setHasEaten(boolean hasEaten) {
        this.dataTracker.set(HAS_EATEN, hasEaten);
    }
    public int getScaredTicks() {
        return this.dataTracker.get(SCARED_TICKS);
    }
    public void setScaredTicks(int scaredTicks) {
        this.dataTracker.set(SCARED_TICKS, scaredTicks);
    }
    public boolean isScared() {
        return this.getScaredTicks() > 0;
    }

    public int getShellGrowthTicks() {
        return this.dataTracker.get(SHELL_GROWTH);
    }

    public void setShellGrowthTicks(int shellTicks) {
        this.dataTracker.set(SHELL_GROWTH, shellTicks);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        //code snatched with permission from orcinus (ily)
        this.world.getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(2D), this::isValidEntity).forEach(player -> this.setScaredTicks(100));

        if (this.getScaredTicks() > 0) {
            this.getNavigation().stop();
            this.setScaredTicks(this.getScaredTicks() - 1);
        }

        if (!this.world.isClient()) {
            int shellGrowthTicks = this.getShellGrowthTicks();
            if (shellGrowthTicks > 0) {
                if (shellGrowthTicks == 1) {
                    this.playSound(SoundEvents.ENTITY_ITEM_FRAME_ADD_ITEM, 1.0F, 1.0F);
                }
                this.setShellGrowthTicks(shellGrowthTicks - 1);
            }
        }
    }

    private boolean isValidEntity(PlayerEntity entity) {
        return this.getShellGrowthTicks() == 0 && !entity.isSpectator() && entity.isAlive() && !entity.getAbilities().creativeMode && !entity.isSneaking();
    }

    @Override
    public boolean isPushable() {
        return !this.isScared();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        //code snatched with permission from lunarbunten (ily)
        if (!this.world.isClient && this.getShellGrowthTicks() == 0) {
            if (source instanceof ProjectileDamageSource) {
                if (!this.isScared()) {
                    this.dropStack(new ItemStack(EtceteraItems.SNAIL_SHELL), 0.1F);
                    this.playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 1.0F);
                    this.setShellGrowthTicks(this.regrowthTicks.get(this.random));
                }
                return false;
            } else {
                this.setScaredTicks(100);
            }
        }

        if (source.getAttacker() instanceof LivingEntity && amount < 12 && !world.isClient()) {
            if (this.isScared()) {
                playSound(SoundEvents.ENTITY_SHULKER_HURT_CLOSED, 1, 1);
                return false;
            }
        }
        return super.damage(source, amount);
    }


    @Override
    public boolean isCollidable() {
        return this.isScared();
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isScared()) {
            this.setVelocity(this.getVelocity().multiply(0, 1, 0));
            movementInput = movementInput.multiply(0, 1, 0);
        }
        super.travel(movementInput);
    }

    public static class SnailWanderGoal extends WanderAroundFarGoal {
        private final SnailEntity mob;

        public SnailWanderGoal(SnailEntity mob, double d) {
            super(mob, d);
            this.mob = mob;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.mob.isScared();
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && !this.mob.isScared();
        }

        @Override
        public void tick() {
            super.tick();
            if (!mob.world.isClient && mob.hasEaten()) {
                //TODO make this mucus
                BlockState blockState = Blocks.SNOW.getDefaultState();

                for (int l = 0; l < 4; ++l) {
                    int i = MathHelper.floor(mob.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25f));
                    BlockPos blockPos2 = new BlockPos(i, MathHelper.floor(mob.getY()), MathHelper.floor(mob.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25f)));
                    if (!mob.world.getBlockState(blockPos2).isAir() || !blockState.canPlaceAt(mob.world, blockPos2)) continue;
                    mob.world.setBlockState(blockPos2, blockState);
                    mob.world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(mob, blockState));
                }
            }
        }

        @Override
        public void stop() {
            super.stop();
            if (mob.hasEaten()) mob.setHasEaten(false);
        }
    }

    public static class SnailLookAroundGoal extends LookAroundGoal {
        private final SnailEntity mob;

        public SnailLookAroundGoal(SnailEntity mob) {
            super(mob);
            this.mob = mob;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.mob.isScared();
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && !this.mob.isScared();
        }
    }

    public static class SnailLookAtEntityGoal extends LookAtEntityGoal {
        private final SnailEntity mob;

        public SnailLookAtEntityGoal(SnailEntity mob, Class<? extends LivingEntity> targetType, float range) {
            super(mob, targetType, range);
            this.mob = mob;
        }

        @Override
        public boolean canStart() {
            return super.canStart() && !this.mob.isScared();
        }

        @Override
        public boolean shouldContinue() {
            return super.shouldContinue() && !this.mob.isScared();
        }
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<SnailEntity> entitty, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return world.getBlockState(pos.down()).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) && AnimalEntity.isLightLevelValidForNaturalSpawn(world, pos);
    }
}
