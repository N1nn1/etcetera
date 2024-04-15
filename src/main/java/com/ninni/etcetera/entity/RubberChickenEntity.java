package com.ninni.etcetera.entity;

import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

public class RubberChickenEntity extends LivingEntity {
    public long lastHitTime;
    public final AnimationState squeezingAnimationState = new AnimationState();
    public int squeezePoseTick = 20;

    public RubberChickenEntity(EntityType<? extends RubberChickenEntity> entityType, World world) {
        super(entityType, world);
    }

    public RubberChickenEntity(World world, double x, double y, double z) {
        this(EtceteraEntityType.RUBBER_CHICKEN, world);
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d movementInput = new Vec3d(this.sidewaysSpeed, this.upwardSpeed, this.forwardSpeed);
        this.updateVelocity(0.02f, movementInput);
        double fluidHeight1 = this.getFluidHeight(FluidTags.WATER);
        double multiplier = fluidHeight1 < 0.1F ? 1 : -1;
        double multiplier2 = fluidHeight1 < 0.3F ? 0.01f : 0.2f;

        if (this.isTouchingWater()) {
            this.setVelocity(this.getVelocity().multiply(1.0D, 0.5D * multiplier, 1.0D));
            this.addVelocity(0.0D, multiplier2, 0.0D);
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if (squeezePoseTick > 0) {
            if (!this.getWorld().isClient) squeezePoseTick--;
        }

    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return EtceteraItems.RUBBER_CHICKEN.getDefaultStack();
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
            this.squeezingAnimationState.start(this.age);
            this.playSound(EtceteraSoundEvents.ENTITY_RUBBER_CHICKEN_SQUEEZE, 1, 1);
            squeezePoseTick = 20;
            return ActionResult.SUCCESS;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getWorld().isClient || this.isRemoved()) {
            return false;
        }
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            this.kill();
            return false;
        }
        if (source.isIn(DamageTypeTags.IS_EXPLOSION)) {
            this.kill();
            return false;
        }
        if (source.isIn(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
            if (this.isOnFire()) {
                this.updateHealth(source, 0.15f);
            } else {
                this.setOnFireFor(5);
            }
            return false;
        }
        if (source.isIn(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5f) {
            this.updateHealth(source, 4.0f);
            return false;
        }
        boolean bl = source.getSource() instanceof PersistentProjectileEntity;
        boolean bl2 = bl && ((PersistentProjectileEntity)source.getSource()).getPierceLevel() > 0;
        boolean bl3 = "player".equals(source.getName());
        if (!bl3 && !bl) {
            return false;
        }
        Entity entity = source.getAttacker();
        if (entity instanceof PlayerEntity playerEntity) {
            if (!playerEntity.getAbilities().allowModifyWorld) {
                return false;
            }
        }
        if (source.isSourceCreativePlayer()) {
            this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), EtceteraBlocks.RUBBER_BLOCK.getSoundGroup(EtceteraBlocks.RUBBER_BLOCK.getDefaultState()).getBreakSound(), this.getSoundCategory(), 1.0f, 1.0f);
            this.kill();
            return bl2;
        }
        long l = this.getWorld().getTime();
        if (l - this.lastHitTime <= 5L || bl) {
            this.breakAndDropItem();
            this.kill();
        } else {
            this.getWorld().sendEntityStatus(this, (byte)32);
            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
            this.lastHitTime = l;
        }
        return true;
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.emitGameEvent(GameEvent.ENTITY_DIE);
    }

    private void breakAndDropItem() {
        ItemStack itemStack = new ItemStack(EtceteraItems.RUBBER_CHICKEN);
        if (this.hasCustomName()) {
            itemStack.setCustomName(this.getCustomName());
        }
        Block.dropStack(this.getWorld(), this.getBlockPos(), itemStack);
    }

    private void updateHealth(DamageSource damageSource, float amount) {
        float f = this.getHealth();
        if ((f -= amount) <= 0.5f) {
            this.kill();
        } else {
            this.setHealth(f);
            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, damageSource.getAttacker());
        }
    }

    @Override
    public void handleStatus(byte status) {
        if (status == 32) {
            if (this.getWorld().isClient) {
                this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), EtceteraBlocks.RUBBER_BLOCK.getSoundGroup(EtceteraBlocks.RUBBER_BLOCK.getDefaultState()).getHitSound(), this.getSoundCategory(), 0.3f, 1.0f, false);
                this.lastHitTime = this.getWorld().getTime();
            }
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public Iterable<ItemStack> getArmorItems() {
        return Collections.emptyList();
    }

    @Override
    public Iterable<ItemStack> getHandItems() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }
}
