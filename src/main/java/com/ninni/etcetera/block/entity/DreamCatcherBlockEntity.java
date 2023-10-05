package com.ninni.etcetera.block.entity;

import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import com.ninni.etcetera.registry.EtceteraStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class DreamCatcherBlockEntity extends BlockEntity {

    public DreamCatcherBlockEntity(BlockPos pos, BlockState state) {
        super(EtceteraBlockEntityType.DREAM_CATCHER, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, DreamCatcherBlockEntity blockEntity) {
        if (state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.LOWER && world.getTimeOfDay() > 13000 && world.getTimeOfDay() < 24020) {
            List<PlayerEntity> list = world.getEntitiesByClass(PlayerEntity.class, new Box(pos).expand(8.0D, 5.0D, 8.0D), player -> !player.isCreative());
            for (PlayerEntity player : list) {
                if (world.getTimeOfDay() > 24000) {
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20 * 15, 2, false, true));
                    player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 20 * 90, 0, false, true));
                }
                player.addStatusEffect(new StatusEffectInstance(EtceteraStatusEffects.DROWSY, 10, 0, true, true));
            }
        }
    }

}
