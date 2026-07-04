package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class RubberChickenEntity extends LivingEntity {
    public final AnimationState squeezingAnimationState = new AnimationState();
    public long lastHitTime;
    public int squeezePoseTick = 20;

    public RubberChickenEntity(EntityType<? extends RubberChickenEntity> entityType, Level world) {
        super(entityType, world);
    }

    public RubberChickenEntity(Level world, double x, double y, double z) {
        this(EtceteraEntityType.RUBBER_CHICKEN.get(), world);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3 movementInput = new Vec3(this.xxa, this.yya, this.zza);
        this.moveRelative(0.02f, movementInput);
        double fluidHeight1 = this.getFluidHeight(FluidTags.WATER);
        double multiplier = fluidHeight1 < 0.1F ? 1 : -1;
        double multiplier2 = fluidHeight1 < 0.3F ? 0.01f : 0.2f;

        if (this.isInWater()) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.5D * multiplier, 1.0D));
            this.setDeltaMovement(this.getDeltaMovement().add(0.0D, multiplier2, 0.0D));
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (squeezePoseTick > 0) {
            if (!this.level().isClientSide) squeezePoseTick--;
        }
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return EtceteraItems.RUBBER_CHICKEN.get().getDefaultInstance();
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> data) {
        super.onSyncedDataUpdated(data);
    }

    @Override
    public @NotNull InteractionResult interactAt(@NotNull Player player, @NotNull Vec3 hitPos, @NotNull InteractionHand hand) {
        this.squeezingAnimationState.start(this.tickCount);
        this.playSound(EtceteraSoundEvents.ENTITY_RUBBER_CHICKEN_SQUEEZE, 1, 1);
        squeezePoseTick = 20;
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.level().isClientSide || this.isRemoved()) {
            return false;
        }
        if (source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.kill();
            return false;
        }
        if (source.is(DamageTypeTags.IS_EXPLOSION)) {
            this.kill();
            return false;
        }
        if (source.is(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
            if (this.isOnFire()) {
                this.updateHealth(source, 0.15f);
            } else {
                this.igniteForSeconds(5);
            }
            return false;
        }
        if (source.is(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5f) {
            this.updateHealth(source, 4.0f);
            return false;
        }
        boolean bl = source.getDirectEntity() instanceof AbstractArrow;
        boolean bl2 = bl && ((AbstractArrow) source.getDirectEntity()).getPierceLevel() > 0;
        boolean bl3 = "player".equals(source.getMsgId());
        if (!bl3 && !bl) {
            return false;
        }
        Entity entity = source.getEntity();
        if (entity instanceof Player playerEntity) {
            if (!playerEntity.getAbilities().mayBuild) {
                return false;
            }
        }
        if (entity instanceof Player player && player.getAbilities().instabuild) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), EtceteraBlocks.RUBBER_BLOCK.get().defaultBlockState().getSoundType().getBreakSound(), this.getSoundSource(), 1.0f, 1.0f);
            this.kill();
            return bl2;
        }
        long l = this.level().getGameTime();
        if (l - this.lastHitTime <= 5L || bl) {
            this.breakAndDropItem();
            this.kill();
        } else {
            this.level().broadcastEntityEvent(this, (byte) 32);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
            this.lastHitTime = l;
        }
        return true;
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.gameEvent(GameEvent.ENTITY_DIE);
    }

    private void breakAndDropItem() {
        ItemStack itemStack = new ItemStack(EtceteraItems.RUBBER_CHICKEN.get());
        if (this.hasCustomName()) {
            itemStack.set(DataComponents.CUSTOM_NAME, this.getCustomName());
        }
        Block.popResource(this.level(), this.blockPosition(), itemStack);
    }

    private void updateHealth(DamageSource damageSource, float amount) {
        float f = this.getHealth();
        if ((f -= amount) <= 0.5f) {
            this.kill();
        } else {
            this.setHealth(f);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getEntity());
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 32) {
            if (this.level().isClientSide) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), EtceteraBlocks.RUBBER_BLOCK.get().defaultBlockState().getSoundType().getHitSound(), this.getSoundSource(), 0.3f, 1.0f, false);
                this.lastHitTime = this.level().getGameTime();
            }
        } else {
            super.handleEntityEvent(status);
        }
    }

    @Override
    public @NotNull Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull Iterable<ItemStack> getHandSlots() {
        return Collections.emptyList();
    }

    @Override
    public @NotNull ItemStack getItemBySlot(@NotNull EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(@NotNull EquipmentSlot slot, @NotNull ItemStack stack) {
    }

    @Override
    public @NotNull HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }
}