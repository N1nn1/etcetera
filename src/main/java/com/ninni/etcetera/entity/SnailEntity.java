package com.ninni.etcetera.entity;

import com.google.common.collect.Lists;
import com.ninni.etcetera.EtceteraTags;
import com.ninni.etcetera.block.EtceteraBlocks;
import com.ninni.etcetera.block.SnailEggsBlock;
import com.ninni.etcetera.item.EtceteraItems;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.GameRules;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SnailEntity extends AnimalEntity {
    private static final TrackedData<Integer> SCARED_TICKS = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> WET_TICKS = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> SHELL_GROWTH = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> HAS_EGG = DataTracker.registerData(SnailEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private final UniformIntProvider regrowthTicks = UniformIntProvider.create(12000, 24000);
    private int cooldown = 2;

    protected SnailEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(EtceteraTags.SNAIL_TEMPT_ITEMS);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(2, new SnailLayEggGoal(this));
        this.goalSelector.add(2, new TemptGoal(this, 1.25, Ingredient.fromTag(EtceteraTags.SNAIL_TEMPT_ITEMS), false));
        this.goalSelector.add(5, new FollowParentGoal(this, 1));
        this.goalSelector.add(6, new SnailWanderGoal(this, 1));
        this.goalSelector.add(7, new SnailLookAtEntityGoal(this, PlayerEntity.class, 6));
        this.goalSelector.add(8, new SnailLookAroundGoal(this));
    }

    @Override
    public void breed(ServerWorld world, AnimalEntity other) {
        if(other instanceof SnailEntity otherSnail) {
            ServerPlayerEntity serverPlayerEntity = this.getLovingPlayer();
            if (serverPlayerEntity == null) serverPlayerEntity = otherSnail.getLovingPlayer();
            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, this, otherSnail, null);
                Criteria.BRED_ANIMALS.trigger(serverPlayerEntity, otherSnail, this, null);
            }
            this.setBreedingAge(6000);
            otherSnail.setBreedingAge(6000);
            this.resetLoveTicks();
            otherSnail.resetLoveTicks();
            this.setHasEgg(true);
            otherSnail.setHasEgg(true);
            world.sendEntityStatus(this, EntityStatuses.ADD_BREEDING_PARTICLES);
            if (world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                world.spawnEntity(new ExperienceOrbEntity(world, this.getX(), this.getY(), this.getZ(), this.getRandom().nextInt(7) + 1));
            }
        }
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 6.0)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.1);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SCARED_TICKS, 0);
        this.dataTracker.startTracking(WET_TICKS, 0);
        this.dataTracker.startTracking(SHELL_GROWTH, 0);
        this.dataTracker.startTracking(HAS_EGG, false);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("ScaredTicks", this.getScaredTicks());
        nbt.putInt("WetTicks", this.getWetTicks());
        nbt.putInt("Shelled", this.getShellGrowthTicks());
        nbt.putBoolean("HasEgg", this.hasEgg());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setScaredTicks(nbt.getInt("ScaredTicks"));
        this.setWetTicks(nbt.getInt("WetTicks"));
        this.setShellGrowthTicks(nbt.getInt("Shelled"));
        this.setHasEgg(nbt.getBoolean("HasEgg"));
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
    public boolean hasEgg() {
        return this.dataTracker.get(HAS_EGG);
    }
    public void setHasEgg(boolean hasEgg) {
        this.dataTracker.set(HAS_EGG, hasEgg);
    }

    public int getWetTicks() {
        return this.dataTracker.get(WET_TICKS);
    }
    public void setWetTicks(int wetTicks) {this.dataTracker.set(WET_TICKS, wetTicks);}
    public void addWetTicks(int wetTicks) {
        this.setWetTicks(this.getWetTicks() + wetTicks);
    }

    public int getShellGrowthTicks() {
        return this.dataTracker.get(SHELL_GROWTH);
    }
    public void setShellGrowthTicks(int shellTicks) {
        this.dataTracker.set(SHELL_GROWTH, shellTicks);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {

        ItemStack itemStack = player.getStackInHand(hand);

        if (this.isBreedingItem(itemStack) && this.getBreedingAge() == 0) {
            this.playSound(EtceteraSoundEvents.ENTITY_SNAIL_EAT, 1, 1);
        }

            if (itemStack.isOf(Items.WATER_BUCKET) && !this.isScared() && this.getWetTicks() == 0) {
            if (!this.world.isClient) {
                if (!player.getAbilities().creativeMode) player.setStackInHand(player.getActiveHand(), Items.BUCKET.getDefaultStack());
                this.addWetTicks(300);
                this.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1, 1);
                return ActionResult.SUCCESS;
            }
            return ActionResult.CONSUME;
        }

        return super.interactMob(player, hand);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (getWetTicks() > 0) {
            this.addWetTicks(-1);
            if (random.nextInt(10) == 0) {
                world.addParticle(ParticleTypes.FALLING_WATER, this.getParticleX(0.6), this.getY() + random.nextDouble(), this.getParticleZ(0.6), 0.0, 0.0, 0.0);
            }
        }
        if (this.getSteppingBlockState().isIn(EtceteraTags.MUCUS_SOLIDIFIER) || this.isTouchingWaterOrRain()) this.addWetTicks(1);

        if (!this.world.isClient && this.isOnGround() && this.getWetTicks() > 0) {
            BlockState blockState = EtceteraBlocks.MUCUS.getDefaultState();
            for (int l = 0; l < 4; ++l) {
                if (cooldown < 0) {
                    int i = MathHelper.floor(this.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25f));
                    BlockPos blockPos2 = new BlockPos(i, MathHelper.floor(this.getY()), MathHelper.floor(this.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25f)));
                    if (!this.world.getBlockState(blockPos2).isAir() || !blockState.canPlaceAt(this.world, blockPos2)) continue;
                    this.world.setBlockState(blockPos2, blockState);
                    cooldown = 10;
                    this.playStepSound(blockPos2, blockState);
                    this.world.emitGameEvent(GameEvent.BLOCK_PLACE, blockPos2, GameEvent.Emitter.of(this, blockState));
                }
            }
            cooldown--;
        }

        //code snatched with permission from orcinus (ily)
        this.world.getEntitiesByClass(PlayerEntity.class, this.getBoundingBox().expand(2D), this::isValidEntity).forEach(player -> this.setScaredTicks(100));

        if (this.getScaredTicks() > 0) {
            this.getNavigation().stop();
            this.setScaredTicks(this.getScaredTicks() - 1);
        }

        if (!this.world.isClient()) {
            int shellGrowthTicks = this.getShellGrowthTicks();
            if (shellGrowthTicks > 0 && !this.isBaby()) {
                if (shellGrowthTicks == 1) {
                    this.playSound(EtceteraSoundEvents.ENTITY_SNAIL_SHELL_GROW, 1.0F, 1.0F);
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
                    this.playSound(EtceteraSoundEvents.ENTITY_SNAIL_HURT_HIDDEN, 1.0F, 1.0F);
                    this.setShellGrowthTicks(this.regrowthTicks.get(this.random));
                }
                return false;
            } else {
                this.setScaredTicks(100);
            }
        }

        if (source.getAttacker() instanceof LivingEntity && amount < 12 && !world.isClient()) {
            if (this.isScared()) {
                playSound(EtceteraSoundEvents.ENTITY_SNAIL_HURT_HIDDEN, 1, 1);
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

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return "Gary".equals(Formatting.strip(this.getName().getString())) ? SoundEvents.ENTITY_CAT_AMBIENT : super.getAmbientSound();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return EtceteraSoundEvents.ENTITY_SNAIL_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return this.isScared() ? EtceteraSoundEvents.ENTITY_SNAIL_DEATH_HIDDEN : EtceteraSoundEvents.ENTITY_SNAIL_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(EtceteraSoundEvents.ENTITY_SNAIL_SLIDE, 0.15f, 1.0f);
    }


    public static class SnailLayEggGoal extends Goal {
        private final SnailEntity snail;
        private BlockPos layPos;

        SnailLayEggGoal(SnailEntity snail) {
            this.snail = snail;
        }

        @Override
        public boolean canStart() {
            BlockPos layPos = this.getLayPosition();
            if (this.layPos == null && layPos != null) {
                this.layPos = layPos;
            }
            return this.layPos != null && this.snail.hasEgg();
        }

        @Override
        public boolean shouldContinue() {
            return this.layPos != null && this.snail.world.getBlockState(this.layPos).isIn(BlockTags.DIRT) && this.snail.world.getBlockState(this.layPos.up()).isAir();
        }

        @Override
        public void start() {
            if (this.layPos != null) {
                Vec3d vec3d = Vec3d.ofCenter(this.layPos);
                this.snail.getNavigation().startMovingTo(vec3d.x, vec3d.y, vec3d.z, 1.0F);
                this.snail.getLookControl().lookAt(vec3d);
            }
        }

        @Override
        public void tick() {
            if (this.layPos != null) {
                Vec3d vec3d = Vec3d.ofCenter(this.layPos);
                double distance = MathHelper.sqrt((float) this.snail.squaredDistanceTo(vec3d));
                if (distance <= 2) {
                    this.snail.playSound(EtceteraSoundEvents.ENTITY_SNAIL_LAY_EGGS, 1, 1);
                    this.snail.world.setBlockState(this.layPos, EtceteraBlocks.SNAIL_EGGS.getDefaultState().with(SnailEggsBlock.getProperty(Direction.DOWN), true), 2);
                    this.snail.setHasEgg(false);
                }
            }
        }

        @Nullable
        private BlockPos getLayPosition() {
            List<BlockPos> list = Lists.newArrayList();
            int range = 8;
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos position = new BlockPos(this.snail.getX() + x, this.snail.getY(), this.snail.getZ() + z);
                    if (this.snail.world.getBlockState(position.down()).isIn(BlockTags.DIRT) && this.snail.world.getBlockState(position).isAir()) {
                        list.add(position);
                    }
                }
            }
            if (list.isEmpty()) {
                return null;
            }
            return list.get(this.snail.random.nextInt(list.size()));
        }
    }

    public static class SnailWanderGoal extends WanderAroundFarGoal {
        private final SnailEntity mob;

        public SnailWanderGoal(SnailEntity mob, double d) {
            super(mob, d);
            this.mob = mob;
        }

        @Override
        public void start() {
            super.start();
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
