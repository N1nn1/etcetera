package com.ninni.etcetera.entity;

import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ChappleEntity extends ChickenEntity implements Shearable {
    private static final TrackedData<String> TYPE = DataTracker.registerData(ChappleEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> APPLE_LAY_TIME = DataTracker.registerData(ChappleEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.ofItems(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    @Nullable
    private UUID lightningId;

    public ChappleEntity(EntityType<? extends ChickenEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new EscapeDangerGoal(this, 1.4));
        this.goalSelector.add(3, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));
        this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 6.0f));
        this.goalSelector.add(7, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, Type.NORMAL.name);
        this.dataTracker.startTracking(APPLE_LAY_TIME, this.random.nextInt(6000) + 6000);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("Type", this.getChappleType().name);
        nbt.putInt("AppleLayTime", this.getAppleLayTime());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setType(Type.fromName(nbt.getString("Type")));
        this.setAppleLayTime(this.getAppleLayTime());
    }

    public void setType(Type type) {
        this.dataTracker.set(TYPE, type.name);
    }
    public Type getChappleType() {
        return Type.fromName(this.dataTracker.get(TYPE));
    }
    public int getAppleLayTime() {
        return this.dataTracker.get(APPLE_LAY_TIME);
    }
    public void setAppleLayTime(int appleLayTime) {
        this.dataTracker.set(APPLE_LAY_TIME, appleLayTime);
    }


    @Override
    public void tickMovement() {
        super.tickMovement();
        this.eggLayTime = 1000;

        if (this.isAlive() && !this.isBaby() && !this.hasJockey()) this.setAppleLayTime(this.getAppleLayTime()-1);
        if (this.isAlive() && !this.isBaby() && !this.hasJockey() && this.getAppleLayTime() <= 0 && !this.world.isClient && this.getAppleLayTime() <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            if (this.getChappleType() == Type.GOLDEN && this.random.nextInt(3) == 0) this.dropItem(Items.GOLDEN_APPLE);
            else this.dropItem(Items.APPLE);
            this.emitGameEvent(GameEvent.ENTITY_PLACE);
            this.setAppleLayTime(this.random.nextInt(6000) + 6000);
        }
    }

    @Override
    public ActionResult interactMob(PlayerEntity player2, Hand hand) {
        ItemStack itemStack = player2.getStackInHand(hand);

        if (itemStack.isOf(Items.SHEARS) && this.isShearable()) {
            this.sheared(SoundCategory.PLAYERS);
            this.emitGameEvent(GameEvent.SHEAR, player2);
            if (!this.world.isClient) {
                itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
            }
            return ActionResult.success(this.world.isClient);
        }
        return ActionResult.PASS;
    }

    @Override
    public void sheared(SoundCategory shearedSoundCategory) {
        this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_MOOSHROOM_SHEAR, shearedSoundCategory, 1.0f, 1.0f);

        if (!this.world.isClient()) {
            ((ServerWorld)this.world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getBodyY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            this.discard();

            ChickenEntity chicken = EntityType.CHICKEN.create(this.world);
            chicken.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            chicken.setHealth(this.getHealth());
            chicken.bodyYaw = this.bodyYaw;

            if (this.hasCustomName()) {
                chicken.setCustomName(this.getCustomName());
                chicken.setCustomNameVisible(this.isCustomNameVisible());
            }
            if (this.isPersistent()) chicken.setPersistent();
            chicken.setInvulnerable(this.isInvulnerable());

            this.world.spawnEntity(chicken);

            for (int i = 0; i < 5; ++i) {
                this.world.spawnEntity(new ItemEntity(this.world, this.getX(), this.getBodyY(1.0), this.getZ(), new ItemStack(this.getChappleType().apple)));
            }
        }
    }

    @Override
    public boolean isShearable() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void onStruckByLightning(ServerWorld world, LightningEntity lightning) {
        UUID uUID = lightning.getUuid();
        if (!uUID.equals(this.lightningId)) {
            this.setType(this.getChappleType() == Type.NORMAL ? Type.GOLDEN : Type.NORMAL);
            this.lightningId = uUID;
            this.playSound(EtceteraSoundEvents.ENTITY_CHAPPLE_CONVERT, 3.0f, 1.0f);
        }
    }

    @Override
    public ChappleEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        ChappleEntity chapple = EtceteraEntityType.CHAPPLE.create(serverWorld);
        chapple.setType(this.chooseBabyType((ChappleEntity)passiveEntity));
        return chapple;
    }

    private Type chooseBabyType(ChappleEntity chapple) {
        Type type2;
        Type type = this.getChappleType();
        return type == (type2 = chapple.getChappleType()) && this.random.nextInt(1024) == 0 ? (type == Type.GOLDEN ? Type.NORMAL : Type.GOLDEN) : (this.random.nextBoolean() ? type : type2);
    }

    public enum Type {
        NORMAL("normal", Items.APPLE),
        GOLDEN("golden", Items.GOLDEN_APPLE);

        final String name;
        final Item apple;

        Type(String name, Item apple) {
            this.name = name;
            this.apple = apple;
        }

        static Type fromName(String name) {
            for (Type type : Type.values()) {
                if (!type.name.equals(name)) continue;
                return type;
            }
            return NORMAL;
        }
    }
}
