package com.ninni.etcetera.item;

import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.minecraft.core.component.DataComponents;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
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

public class TurtleRaftItem extends Item {
    private static final Predicate<Entity> RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    public TurtleRaftItem(Properties properties) {
        super(properties);
    }

    public int getColor(ItemStack stack) {
        DyedItemColor dyedItemColor = stack.get(DataComponents.DYED_COLOR);
        return dyedItemColor != null ? dyedItemColor.rgb() : 0x3fa442;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player user, @NotNull InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);

        BlockHitResult hitResult = getPlayerPOVHitResult(level, user, ClipContext.Fluid.ANY);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemStack);
        }

        Vec3 vec3d = user.getViewVector(1.0f);
        List<Entity> list = level.getEntities(user, user.getBoundingBox().expandTowards(vec3d.scale(5.0)).inflate(1.0), RIDERS);
        if (!list.isEmpty()) {
            Vec3 vec3d2 = user.getEyePosition();
            for (Entity entity : list) {
                AABB box = entity.getBoundingBox().inflate(entity.getPickRadius());
                if (box.contains(vec3d2)) return InteractionResultHolder.pass(itemStack);
            }
        }

        if (hitResult.getType() == HitResult.Type.BLOCK) {
            TurtleRaftEntity raft = this.createEntity(level, hitResult);
            raft.setYRot(user.getYRot());
            if (!level.noCollision(raft, raft.getBoundingBox())) return InteractionResultHolder.fail(itemStack);

            if (!level.isClientSide && itemStack.getItem() instanceof TurtleRaftItem item) {
                raft.setColor(item.getColor(itemStack));
                level.addFreshEntity(raft);
                level.gameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                if (!user.getAbilities().instabuild) itemStack.shrink(1);
            }

            user.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }

    private TurtleRaftEntity createEntity(Level level, HitResult hitResult) {
        return new TurtleRaftEntity(level, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
    }
}