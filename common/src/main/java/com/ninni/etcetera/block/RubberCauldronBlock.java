package com.ninni.etcetera.block;

import com.mojang.serialization.MapCodec;
import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

public class RubberCauldronBlock extends AbstractCauldronBlock {
    public static final IntegerProperty SOLID = EtceteraProperties.SOLID;
    public static final MapCodec<RubberCauldronBlock> CODEC = simpleCodec(RubberCauldronBlock::new);

    public RubberCauldronBlock(BlockBehaviour.Properties properties) {
        super(properties, CauldronInteraction.EMPTY);
        this.registerDefaultState(this.stateDefinition.any().setValue(SOLID, 1));
    }

    @Override
    protected @NotNull InteractionResult useWithoutItem(BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull BlockHitResult hit) {
        if (state.getValue(SOLID) == 3) {
            player.getInventory().add(EtceteraItems.RUBBER_BLOCK.get().getDefaultInstance());
            world.setBlock(pos, Blocks.CAULDRON.defaultBlockState(), 3);
            return InteractionResult.sidedSuccess(world.isClientSide);
        }
        return InteractionResult.PASS;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, @NotNull RandomSource random) {
        int i = state.getValue(SOLID);
        BlockState state2 = world.getBlockState(pos.above());
        if (i < 3 && state2.is(EtceteraBlocks.COPPER_TAP.get()) && state2.getValue(CoppertapBlock.POWERED)) {
            if (random.nextInt(com.ninni.etcetera.config.ModConfig.get().rubberCauldronSolidifyChance) == 0) {
                world.setBlock(pos, state.setValue(SOLID, i + 1), 2);
            }
        }
    }

    @Override
    public boolean isFull(@NotNull BlockState state) {
        return true;
    }

    @Override
    public @NotNull ItemStack getCloneItemStack(@NotNull LevelReader level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return Items.CAULDRON.getDefaultInstance();
    }

    @Override
    public int getAnalogOutputSignal(BlockState state, @NotNull Level world, @NotNull BlockPos pos) {
        return state.getValue(SOLID);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(SOLID));
    }

    @Override
    protected @NotNull MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }
}