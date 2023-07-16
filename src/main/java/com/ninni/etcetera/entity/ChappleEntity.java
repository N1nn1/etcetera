package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.Shearable;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class ChappleEntity extends Chicken implements Shearable {
    private static final EntityDataAccessor<String> TYPE = SynchedEntityData.defineId(ChappleEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Integer> APPLE_LAY_TIME = SynchedEntityData.defineId(ChappleEntity.class, EntityDataSerializers.INT);
    private static final Ingredient BREEDING_INGREDIENT = Ingredient.of(Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS);
    @Nullable
    private UUID lightningId;

    public ChappleEntity(EntityType<? extends Chicken> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, BREEDING_INGREDIENT, false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(TYPE, Type.NORMAL.name);
        this.entityData.define(APPLE_LAY_TIME, this.random.nextInt(6000) + 6000);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag nbt) {
        super.addAdditionalSaveData(nbt);
        nbt.putString("Type", this.getChappleType().name);
        nbt.putInt("AppleLayTime", this.getAppleLayTime());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag nbt) {
        super.readAdditionalSaveData(nbt);
        this.setType(Type.fromName(nbt.getString("Type")));
        this.setAppleLayTime(this.getAppleLayTime());
    }

    public void setType(Type type) {
        this.entityData.set(TYPE, type.name);
    }
    public Type getChappleType() {
        return Type.fromName(this.entityData.get(TYPE));
    }
    public int getAppleLayTime() {
        return this.entityData.get(APPLE_LAY_TIME);
    }
    public void setAppleLayTime(int appleLayTime) {
        this.entityData.set(APPLE_LAY_TIME, appleLayTime);
    }


    @Override
    public void aiStep() {
        super.aiStep();
        this.eggTime = 1000;

        if (this.isAlive() && !this.isBaby() && !this.isChickenJockey()) this.setAppleLayTime(this.getAppleLayTime()-1);
        if (this.isAlive() && !this.isBaby() && !this.isChickenJockey() && this.getAppleLayTime() <= 0 && !this.level().isClientSide && this.getAppleLayTime() <= 0) {
            this.playSound(SoundEvents.CHICKEN_EGG, 1.0f, (this.random.nextFloat() - this.random.nextFloat()) * 0.2f + 1.0f);
            if (this.getChappleType() == Type.GOLDEN && this.random.nextInt(3) == 0) this.spawnAtLocation(Items.GOLDEN_APPLE);
            else this.spawnAtLocation(Items.APPLE);
            this.gameEvent(GameEvent.ENTITY_PLACE);
            this.setAppleLayTime(this.random.nextInt(6000) + 6000);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player2, InteractionHand hand) {
        ItemStack itemStack = player2.getItemInHand(hand);

        if (itemStack.is(Items.SHEARS) && this.readyForShearing()) {
            this.shear(SoundSource.PLAYERS);
            this.gameEvent(GameEvent.SHEAR, player2);
            if (!this.level().isClientSide) {
                itemStack.hurtAndBreak(1, player2, player -> player.broadcastBreakEvent(hand));
            }
            return InteractionResult.sidedSuccess(this.level().isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void shear(SoundSource shearedSoundCategory) {
        this.level().playSound(null, this, SoundEvents.MOOSHROOM_SHEAR, shearedSoundCategory, 1.0f, 1.0f);

        if (!this.level().isClientSide) {
            ((ServerLevel)this.level()).sendParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(0.5), this.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            this.discard();

            Chicken chicken = EntityType.CHICKEN.create(this.level());
            chicken.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            chicken.setHealth(this.getHealth());
            chicken.yBodyRot = this.yBodyRot;

            if (this.hasCustomName()) {
                chicken.setCustomName(this.getCustomName());
                chicken.setCustomNameVisible(this.isCustomNameVisible());
            }
            if (this.isPersistenceRequired()) chicken.setPersistenceRequired();
            chicken.setInvulnerable(this.isInvulnerable());

            this.level().addFreshEntity(chicken);

            for (int i = 0; i < 5; ++i) {
                this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(1.0), this.getZ(), new ItemStack(this.getChappleType().apple)));
            }
        }
    }

    @Override
    public boolean readyForShearing() {
        return this.isAlive() && !this.isBaby();
    }

    @Override
    public void thunderHit(ServerLevel world, LightningBolt lightning) {
        UUID uUID = lightning.getUUID();
        if (!uUID.equals(this.lightningId)) {
            this.setType(this.getChappleType() == Type.NORMAL ? Type.GOLDEN : Type.NORMAL);
            this.lightningId = uUID;
            this.playSound(EtceteraSoundEvents.ENTITY_CHAPPLE_CONVERT.get(), 3.0f, 1.0f);
        }
    }

    @Override
    public ChappleEntity getBreedOffspring(ServerLevel serverWorld, AgeableMob passiveEntity) {
        ChappleEntity chapple = EtceteraEntityType.CHAPPLE.get().create(serverWorld);
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
