package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class TurtleRaftEntity extends Boat {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(TurtleRaftEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> BANNER_STACK = SynchedEntityData.defineId(TurtleRaftEntity.class, EntityDataSerializers.ITEM_STACK);

    public TurtleRaftEntity(EntityType<? extends TurtleRaftEntity> entityType, Level world) {
        super(entityType, world);
    }

    public TurtleRaftEntity(Level world, double x, double y, double z) {
        this(EtceteraEntityType.TURTLE_RAFT.get(), world);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!this.level().isClientSide) {
            if (this.getBanner().isEmpty() && stack.getItem() instanceof BannerItem && !player.isCrouching()) {
                ItemStack copy = stack.copy();
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                copy.setCount(1);
                this.playSound(EtceteraSoundEvents.ITEM_BANNER_EQUIP.get(), 1.0F, 1.0F);
                this.gameEvent(GameEvent.ENTITY_INTERACT, player);
                this.setBanner(copy);
                return InteractionResult.SUCCESS;
            } else if (!this.getBanner().isEmpty() && stack.isEmpty() && player.isShiftKeyDown()) {
                ItemStack copy = this.getBanner();
                player.setItemInHand(hand, copy);
                this.playSound(EtceteraSoundEvents.ITEM_BANNER_EQUIP.get(), 1.0F, 1.0F);
                this.gameEvent(GameEvent.ENTITY_INTERACT, player);
                this.setBanner(ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }
        }
        return super.interact(player, hand);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(COLOR, 0);
        this.entityData.define(BANNER_STACK, ItemStack.EMPTY);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.put("BannerStack", this.entityData.get(BANNER_STACK).save(new CompoundTag()));
        nbt.putInt("Color", this.getColor());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.setBanner(ItemStack.of(nbt.getCompound("BannerStack")));
        this.setColor(nbt.getInt("Color"));
    }

    public ItemStack getBanner() {
        return this.entityData.get(BANNER_STACK);
    }

    public void setBanner(ItemStack stack) {
        this.entityData.set(BANNER_STACK, stack);
    }

    public int getColor() {
        return this.entityData.get(COLOR);
    }

    public void setColor(int color) {
        this.entityData.set(COLOR, color);
    }

    @Override
    protected float getSinglePassengerXOffset() {
        return 0.0F;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.3D;
    }

    @Override
    public void onAboveBubbleCol(boolean drag) {
    }

    @Override
    public Item getDropItem() {
        return EtceteraItems.TURTLE_RAFT.get();
    }

    @Override
    protected void destroy(DamageSource source) {
        ItemStack stack = new ItemStack(EtceteraItems.TURTLE_RAFT.get());
        ItemStack bannerStack = this.getBanner();
        stack.getOrCreateTagElement(DyeableLeatherItem.TAG_DISPLAY).putInt(DyeableLeatherItem.TAG_COLOR, this.getColor());
        this.spawnAtLocation(stack);
        if (!bannerStack.isEmpty()) {
            this.spawnAtLocation(bannerStack);
        }
    }

    @Override
    protected int getMaxPassengers() {
        return 1;
    }
}
