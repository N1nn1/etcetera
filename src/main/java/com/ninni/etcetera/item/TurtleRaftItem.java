package com.ninni.etcetera.item;

import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.function.Predicate;

public class TurtleRaftItem extends Item implements DyeableLeatherItem {
    private static final Predicate<Entity> RIDERS = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);

    public TurtleRaftItem(Properties settings) {
        super(settings);
    }

    @Override
    public int getColor(ItemStack stack) {
        CompoundTag nbtCompound = stack.getTagElement("display");
        if (nbtCompound != null && nbtCompound.contains("color", 99)) return nbtCompound.getInt("color");
        return 0x3fa442;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
        ItemStack itemStack = user.getItemInHand(hand);
        BlockHitResult hitResult = BoatItem.getPlayerPOVHitResult(world, user, ClipContext.Fluid.ANY);
        if (((HitResult)hitResult).getType() == HitResult.Type.MISS) return InteractionResultHolder.pass(itemStack);
        Vec3 vec3d = user.getViewVector(1.0f);
        List<Entity> list = world.getEntities(user, user.getBoundingBox().expandTowards(vec3d.scale(5.0)).inflate(1.0), RIDERS);
        if (!list.isEmpty()) {
            Vec3 vec3d2 = user.getEyePosition();
            for (Entity entity : list) {
                AABB box = entity.getBoundingBox().inflate(entity.getPickRadius());
                if (!box.contains(vec3d2)) continue;
                return InteractionResultHolder.pass(itemStack);
            }
        }
        if (((HitResult)hitResult).getType() == HitResult.Type.BLOCK) {

            TurtleRaftEntity raft = this.createEntity(world, hitResult);
            raft.setYRot(user.getYRot());
            if (!world.noCollision(raft, raft.getBoundingBox())) return InteractionResultHolder.fail(itemStack);
            if (!world.isClientSide && itemStack.getItem() instanceof TurtleRaftItem item) {
                raft.setColor(item.getColor(itemStack));
                world.addFreshEntity(raft);
                world.gameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getLocation());
                if (!user.getAbilities().instabuild) itemStack.shrink(1);
            }
            user.awardStat(Stats.ITEM_USED.get(this));
            return InteractionResultHolder.sidedSuccess(itemStack, world.isClientSide());
        }
        return InteractionResultHolder.pass(itemStack);
    }

    private TurtleRaftEntity createEntity(Level world, HitResult hitResult) {
        return new TurtleRaftEntity(world, hitResult.getLocation().x, hitResult.getLocation().y, hitResult.getLocation().z);
    }
}
