package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;

public class TurtleRaftEntity extends Boat {
    private static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(TurtleRaftEntity.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<ItemStack> BANNER_STACK = SynchedEntityData.defineId(TurtleRaftEntity.class, EntityDataSerializers.ITEM_STACK);

    public TurtleRaftEntity(EntityType<? extends Boat> entityType, Level world) {
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
    public @NotNull InteractionResult interact(Player player, @NotNull InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!this.level().isClientSide) {
            if (this.getBanner().isEmpty() && stack.getItem() instanceof BannerItem && !player.isShiftKeyDown()) {
                ItemStack copy = stack.copy();
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                }
                copy.setCount(1);
                this.playSound(EtceteraSoundEvents.ITEM_BANNER_EQUIP, 1.0F, 1.0F);
                this.gameEvent(GameEvent.ENTITY_INTERACT, player);
                this.setBanner(copy);
                return InteractionResult.SUCCESS;
            } else if (!this.getBanner().isEmpty() && stack.isEmpty() && player.isShiftKeyDown()) {
                ItemStack copy = this.getBanner();
                player.setItemInHand(hand, copy);
                this.playSound(EtceteraSoundEvents.ITEM_BANNER_COLLECT, 1.0F, 1.0F);
                this.gameEvent(GameEvent.ENTITY_INTERACT, player);
                this.setBanner(ItemStack.EMPTY);
                return InteractionResult.SUCCESS;
            }
        }
        return super.interact(player, hand);
    }

    @Override
    protected void checkFallDamage(double heightDifference, boolean onGround, @NotNull BlockState state, @NotNull BlockPos landedPosition) {
        if (!this.isPassenger()) {
            if (onGround) {
                if (this.fallDistance > 10.0F) {
                    this.causeFallDamage(this.fallDistance, 1.0F, this.damageSources().fall());
                    if (!this.level().isClientSide && !this.isRemoved()) {
                        this.kill();
                        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
                            int i;
                            for (i = 0; i < 5; ++i) {
                                this.spawnAtLocation(Items.TURTLE_SCUTE);
                            }
                        }
                    }
                }

                this.resetFallDistance();
            } else if (!this.level().getFluidState(this.blockPosition().below()).is(FluidTags.WATER) && heightDifference < 0.0) {
                this.fallDistance -= (float) heightDifference;
            }
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NotNull Builder builder) {
        super.defineSynchedData(builder);
        builder.define(COLOR, 0);
        builder.define(BANNER_STACK, ItemStack.EMPTY);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag nbt) {
        nbt.put("BannerStack", this.entityData.get(BANNER_STACK).saveOptional(this.registryAccess()));
        nbt.putInt("Color", this.getColor());
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag nbt) {
        this.setBanner(ItemStack.parseOptional(this.registryAccess(), nbt.getCompound("BannerStack")));
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
    public void onAboveBubbleCol(boolean isDownwards) {
    }

    @Override
    public @NotNull Item getDropItem() {
        return EtceteraItems.TURTLE_RAFT.get();
    }

    @Override
    protected void destroy(@NotNull DamageSource source) {
        ItemStack stack = new ItemStack(EtceteraItems.TURTLE_RAFT.get());
        stack.set(DataComponents.DYED_COLOR, new DyedItemColor(this.getColor(), true));
        this.spawnAtLocation(stack);
        if (!this.getBanner().isEmpty()) {
            this.spawnAtLocation(this.getBanner());
        }
    }

    @Override
    protected int getMaxPassengers() {
        return 1;
    }
}