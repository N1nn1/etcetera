package com.ninni.etcetera.block.entity;

import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import com.ninni.etcetera.registry.EtceteraStatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.phys.AABB;

import java.util.List;

public class DreamCatcherBlockEntity extends BlockEntity {

    public DreamCatcherBlockEntity(BlockPos pos, BlockState state) {
        super(EtceteraBlockEntityType.DREAM_CATCHER.get(), pos, state);
    }

    public static void tick(Level world, BlockPos pos, BlockState state, DreamCatcherBlockEntity blockEntity) {
        long timeOfDay = world.getDayTime() % 24000;
        if (state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER && (timeOfDay > 13000 || timeOfDay < 20)) {
            double rx = com.ninni.etcetera.config.ModConfig.get().dreamCatcherRangeX;
            double ry = com.ninni.etcetera.config.ModConfig.get().dreamCatcherRangeY;
            double rz = com.ninni.etcetera.config.ModConfig.get().dreamCatcherRangeZ;
            List<Player> list = world.getEntitiesOfClass(Player.class, new AABB(pos).inflate(rx, ry, rz), player -> !player.isCreative());
            for (Player player : list) {
                if (timeOfDay < 20) {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, com.ninni.etcetera.config.ModConfig.get().dreamCatcherRegenerationDuration, com.ninni.etcetera.config.ModConfig.get().dreamCatcherRegenerationAmplifier, false, true));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, com.ninni.etcetera.config.ModConfig.get().dreamCatcherStrengthDuration, com.ninni.etcetera.config.ModConfig.get().dreamCatcherStrengthAmplifier, false, true));
                }
                player.addEffect(new MobEffectInstance(EtceteraStatusEffects.DROWSY, com.ninni.etcetera.config.ModConfig.get().dreamCatcherDrowsyDuration, 0, true, true));
            }
        }
    }

}