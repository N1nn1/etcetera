package com.ninni.etcetera.item;

import com.ninni.etcetera.client.gui.HandbellItemRenderer;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.List;
import java.util.function.Consumer;

public class HandbellItem extends Item {

    public HandbellItem(Properties settings) {
        super(settings);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        List<LivingEntity> hearingEntities = world.getEntitiesOfClass(LivingEntity.class, new AABB(player.blockPosition()).inflate(48.0));

        if (!world.isClientSide) applyGlowToFriends(player.blockPosition(), hearingEntities, player);
        else applyParticlesTooFriends(world, player.blockPosition(), hearingEntities, player);

        world.playSound(player, player.blockPosition(), EtceteraSoundEvents.ITEM_HANDBELL_RING.get(), SoundSource.PLAYERS, 0.3f, 1.0f);
        player.awardStat(Stats.ITEM_USED.get(this));

        return InteractionResultHolder.success(player.getItemInHand(hand));
    }

    private static void applyGlowToFriends(BlockPos pos, List<LivingEntity> hearingEntities, Player player) {
        hearingEntities.stream().filter(entity -> isFriendEntity(pos, entity, player)).forEach(HandbellItem::applyGlowToEntity);
    }

    private static void applyParticlesTooFriends(Level world, BlockPos pos, List<LivingEntity> hearingEntities, Player player) {
        int entiyCount = (int)hearingEntities.stream().filter(entity -> pos.closerToCenterThan(entity.position(), 48.0)).count();

        hearingEntities.stream().filter(entity -> isFriendEntity(pos, entity, player)).forEach(entity -> {
            int j = Mth.clamp((entiyCount - 21) / -2, 3, 15);

            for (int k = 0; k < j; ++k) {
                MutableInt mutableInt = new MutableInt(16700985);
                int l = mutableInt.addAndGet(5);
                double h = (double) FastColor.ARGB32.red(l) / 255.0;
                double m = (double)FastColor.ARGB32.green(l) / 255.0;
                double n = (double)FastColor.ARGB32.blue(l) / 255.0;
                world.addParticle(ParticleTypes.ENTITY_EFFECT, player.getX(), player.getY() + 1, player.getZ(), h, m, n);
            }
        });
    }

    private static void applyGlowToEntity(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 120));
    }

    private static boolean isFriendEntity(BlockPos pos, LivingEntity entity, Player player) {
        if ((entity instanceof TamableAnimal tamedEntity && tamedEntity.isTame() && tamedEntity.getOwnerUUID().equals(player.getUUID()))
        || (entity instanceof Allay allay && allay.getBrain().getMemory(MemoryModuleType.LIKED_PLAYER).isPresent() && player.getUUID().equals(allay.getBrain().getMemory(MemoryModuleType.LIKED_PLAYER).get()))) {
            teleportEntity(pos, entity, player);
            return true;
        }
        return false;
    }

    private static void teleportEntity(BlockPos pos, LivingEntity entity, Player player) {
        if (entity instanceof TamableAnimal tamedEntity && player.onGround() && !tamedEntity.isInSittingPose()) {
            tamedEntity.teleportTo(player.getX(), player.getY(), player.getZ());
            tamedEntity.getNavigation().stop();
        }
        if (entity.isAlive() && !entity.isRemoved()) {
            pos.closerToCenterThan(entity.position(), 48.0);
        }
    }
}
