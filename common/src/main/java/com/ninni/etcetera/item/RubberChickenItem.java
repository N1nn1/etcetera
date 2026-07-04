package com.ninni.etcetera.item;

import com.ninni.etcetera.entity.RubberChickenEntity;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Predicate;

public class RubberChickenItem extends Item {
    private static final Predicate<Entity> RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    public RubberChickenItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level world, Player user, @NotNull InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);

        BlockHitResult hitResult = getPlayerPOVHitResult(world, user, ClipContext.Fluid.ANY);

        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        }

        Vec3 vec3d = user.getViewVector(1.0f);
        List<Entity> list = world.getEntities(user, user.getBoundingBox().expandTowards(vec3d.scale(5.0)).inflate(1.0), RIDERS);

        if (!list.isEmpty()) {
            Vec3 vec3d2 = user.getEyePosition();
            for (Entity entity : list) {
                AABB box = entity.getBoundingBox().inflate(entity.getPickRadius());
                if (box.contains(vec3d2)) {
                    return InteractionResultHolder.pass(itemStack);
                }
            }
        }

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            RubberChickenEntity rubberChicken = this.createEntity(world, hitResult);
            float f = (float) Mth.floor((Mth.wrapDegrees(user.getYRot() - 180.0f) + 22.5f) / 45.0f) * 45.0f;
            rubberChicken.moveTo(rubberChicken.getX(), rubberChicken.getY(), rubberChicken.getZ(), f, 0.0f);

            if (!world.noCollision(rubberChicken, rubberChicken.getBoundingBox())) {
                return InteractionResultHolder.fail(itemStack);
            }

            if (!world.isClientSide) {
                world.addFreshEntity(rubberChicken);
                world.gameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                if (!user.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
            }

            user.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }

    private RubberChickenEntity createEntity(Level world, HitResult hitResult) {
        return new RubberChickenEntity(world, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
    }
}