package com.ninni.etcetera.item;

import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;

public class HandbellItem extends Item {

    public HandbellItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        List<LivingEntity> hearingEntities = world.getNonSpectatingEntities(LivingEntity.class, new Box(player.getBlockPos()).expand(48.0));

        if (!world.isClient) applyGlowToFriends(player.getBlockPos(), hearingEntities, player);
        else applyParticlesTooFriends(world, player.getBlockPos(), hearingEntities, player);

        world.playSound(player, player.getBlockPos(), EtceteraSoundEvents.ITEM_HANDBELL_RING, SoundCategory.PLAYERS, 0.3f, 1.0f);
        player.incrementStat(Stats.USED.getOrCreateStat(this));

        return TypedActionResult.success(player.getStackInHand(hand));
    }

    private static void applyGlowToFriends(BlockPos pos, List<LivingEntity> hearingEntities, PlayerEntity player) {
        hearingEntities.stream().filter(entity -> isFriendEntity(pos, entity, player)).forEach(HandbellItem::applyGlowToEntity);
    }

    private static void applyParticlesTooFriends(World world, BlockPos pos, List<LivingEntity> hearingEntities, PlayerEntity player) {
        int entiyCount = (int)hearingEntities.stream().filter(entity -> pos.isWithinDistance(entity.getPos(), 48.0)).count();

        hearingEntities.stream().filter(entity -> isFriendEntity(pos, entity, player)).forEach(entity -> {
            int j = MathHelper.clamp((entiyCount - 21) / -2, 3, 15);

            for (int k = 0; k < j; ++k) {
                MutableInt mutableInt = new MutableInt(16700985);
                int l = mutableInt.addAndGet(5);
                double h = (double) ColorHelper.Argb.getRed(l) / 255.0;
                double m = (double)ColorHelper.Argb.getGreen(l) / 255.0;
                double n = (double)ColorHelper.Argb.getBlue(l) / 255.0;
                world.addParticle(ParticleTypes.ENTITY_EFFECT, player.getX(), player.getY() + 1, player.getZ(), h, m, n);
            }
        });
    }

    private static void applyGlowToEntity(LivingEntity entity) {
        entity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 120));
    }

    private static boolean isFriendEntity(BlockPos pos, LivingEntity entity, PlayerEntity player) {
        if ((entity instanceof TameableEntity tamedEntity && tamedEntity.isTamed() && tamedEntity.getOwnerUuid().equals(player.getUuid()))
        || (entity instanceof AllayEntity allay && allay.getBrain().getOptionalMemory(MemoryModuleType.LIKED_PLAYER).isPresent() && player.getUuid().equals(allay.getBrain().getOptionalMemory(MemoryModuleType.LIKED_PLAYER).get()))) {
            teleportEntity(pos, entity, player);
            return true;
        }
        return false;
    }

    private static void teleportEntity(BlockPos pos, LivingEntity entity, PlayerEntity player) {
        if (entity instanceof TameableEntity tamedEntity && player.isOnGround() && !tamedEntity.isSitting()) {
            tamedEntity.teleport(player.getX(), player.getY(), player.getZ());
            tamedEntity.getNavigation().stop();
        }
        if (entity.isAlive() && !entity.isRemoved()) {
            pos.isWithinDistance(entity.getPos(), 48.0);
        }
    }
}
