package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class TurtleRaftEntity extends BoatEntity {
    private static final TrackedData<Integer> COLOR = DataTracker.registerData(TurtleRaftEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<ItemStack> BANNER_STACK = DataTracker.registerData(TurtleRaftEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    public TurtleRaftEntity(EntityType<? extends BoatEntity> entityType, World world) {
        super(entityType, world);
    }

    public TurtleRaftEntity(World world, double x, double y, double z) {
        this(EtceteraEntityType.TURTLE_RAFT, world);
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!this.getWorld().isClient) {
            if (this.getBanner().isEmpty() && stack.getItem() instanceof BannerItem && !player.isSneaking()) {
                ItemStack copy = stack.copy();
                if (!player.getAbilities().creativeMode) {
                    stack.decrement(1);
                }
                copy.setCount(1);
                this.playSound(EtceteraSoundEvents.ITEM_BANNER_EQUIP, 1.0F, 1.0F);
                this.emitGameEvent(GameEvent.ENTITY_INTERACT, player);
                this.setBanner(copy);
                return ActionResult.SUCCESS;
            } else if (!this.getBanner().isEmpty() && stack.isEmpty() && player.isSneaking()) {
                ItemStack copy = this.getBanner();
                player.setStackInHand(hand, copy);
                this.playSound(EtceteraSoundEvents.ITEM_BANNER_COLLECT, 1.0F, 1.0F);
                this.emitGameEvent(GameEvent.ENTITY_INTERACT, player);
                this.setBanner(ItemStack.EMPTY);
                return ActionResult.SUCCESS;
            }
        }
        return super.interact(player, hand);
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        if (!this.hasVehicle()) {
            if (onGround) {
                if (this.fallDistance > 10.0F) {
                    this.handleFallDamage(this.fallDistance, 1.0F, this.getDamageSources().fall());
                    if (!this.getWorld().isClient && !this.isRemoved()) {
                        this.kill();
                        if (this.getWorld().getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            int i;
                            for(i = 0; i < 5; ++i) {
                                this.dropItem(Items.SCUTE);
                            }
                        }
                    }
                }

                this.onLanding();
            } else if (!this.getWorld().getFluidState(this.getBlockPos().down()).isIn(FluidTags.WATER) && heightDifference < 0.0) {
                this.fallDistance -= (float)heightDifference;
            }

        }
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COLOR, 0);
        this.dataTracker.startTracking(BANNER_STACK, ItemStack.EMPTY);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.put("BannerStack", this.dataTracker.get(BANNER_STACK).writeNbt(new NbtCompound()));
        nbt.putInt("Color", this.getColor());
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setBanner(ItemStack.fromNbt(nbt.getCompound("BannerStack")));
        this.setColor(nbt.getInt("Color"));
    }

    public ItemStack getBanner() {
        return this.dataTracker.get(BANNER_STACK);
    }

    public void setBanner(ItemStack stack) {
        this.dataTracker.set(BANNER_STACK, stack);
    }

    public int getColor() {
        return this.dataTracker.get(COLOR);
    }

    public void setColor(int color) {
        this.dataTracker.set(COLOR, color);
    }

    @Override
    protected float getPassengerHorizontalOffset() {
        return 0.0f;
    }

    @Override
    public double getMountedHeightOffset() {
        return 0.3;
    }

    @Override
    public void onBubbleColumnSurfaceCollision(boolean drag) { }

    @Override
    public Item asItem() {
        return EtceteraItems.TURTLE_RAFT;
    }

    @Override
    protected void dropItems(DamageSource source) {
        ItemStack stack = new ItemStack(EtceteraItems.TURTLE_RAFT);
        stack.getOrCreateSubNbt(DyeableItem.DISPLAY_KEY).putInt(DyeableItem.COLOR_KEY, this.getColor());
        this.dropStack(stack);
        if (!this.getBanner().isEmpty()) {
            this.dropStack(this.getBanner());
        }
    }

    @Override
    protected int getMaxPassengers() {
        return 1;
    }
}
