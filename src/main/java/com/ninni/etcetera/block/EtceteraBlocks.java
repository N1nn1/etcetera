package com.ninni.etcetera.block;

import com.ninni.etcetera.block.vanilla.PublicStairsBlock;
import com.ninni.etcetera.sound.EtceteraBlockSoundGroups;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

@SuppressWarnings("unused")
public class EtceteraBlocks {

    public static final Block RAW_BISMUTH_BLOCK = register("raw_bismuth_block", new Block(FabricBlockSettings.copyOf(Blocks.RAW_COPPER_BLOCK)));
    public static final Block BISMUTH_BLOCK = register("bismuth_block", new PillarBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(EtceteraBlockSoundGroups.BISMUTH_BLOCK)));
    public static final Block NETHER_BISMUTH_ORE = register("nether_bismuth_ore", new OreBlock(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).requiresTool().strength(3.0f, 3.0f).sounds(EtceteraBlockSoundGroups.NETHER_BISMUTH_ORE), UniformIntProvider.create(1, 8)));

    public static final Block BOUQUET = register("bouquet", new BouquetBlock(FabricBlockSettings.of(Material.PLANT, MapColor.PALE_GREEN).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
    public static final Block POTTED_BOUQUET = register("potted_bouquet", new FlowerPotBlock(BOUQUET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()));
    public static final Block TERRACOTTA_VASE = register("terracotta_vase", new TerracottaVaseBlock(FabricBlockSettings.copyOf(Blocks.TERRACOTTA).sounds(EtceteraBlockSoundGroups.TERRACOTTA_VASE)));

    public static final Block SQUID_LAMP = register("squid_lamp", new SquidLampBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.TEAL).noCollision().breakInstantly().sounds(EtceteraBlockSoundGroups.SQUID_LAMP).luminance(state -> state.get(SquidLampBlock.WATERLOGGED) ? 15 : 7)));
    public static final Block WALL_SQUID_LAMP = register("wall_squid_lamp", new WallSquidLampBlock(FabricBlockSettings.copyOf(SQUID_LAMP).dropsLike(SQUID_LAMP)));

    public static final Block CRUMBLING_STONE = register("crumbling_stone", new CrumblingStoneBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(EtceteraBlockSoundGroups.CRUMBLING_STONE).strength(0.5f, 3f)));
    public static final Block WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", new AbstractCrumblingStoneBlock(FabricBlockSettings.copyOf(CRUMBLING_STONE)));

    public static final Block LEVELED_STONE = register("leveled_stone", new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(1f, 4f)));
    public static final Block LEVELED_STONE_STAIRS = register("leveled_stone_stairs", new PublicStairsBlock(LEVELED_STONE.getDefaultState(), FabricBlockSettings.copyOf(LEVELED_STONE)));
    public static final Block LEVELED_STONE_SLAB = register("leveled_stone_slab", new SlabBlock(FabricBlockSettings.copyOf(LEVELED_STONE)));
    public static final Block SMOOTH_STONE_BRICKS = register("smooth_stone_bricks", new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SMOOTH_STONE_BRICK_WALL = register("smooth_stone_brick_wall", new WallBlock(FabricBlockSettings.copyOf(SMOOTH_STONE_BRICKS)));
    public static final Block SMOOTH_STONE_BRICK_STAIRS = register("smooth_stone_brick_stairs", new PublicStairsBlock(SMOOTH_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(SMOOTH_STONE_BRICKS)));
    public static final Block SMOOTH_STONE_BRICK_SLAB = register("smooth_stone_brick_slab", new SlabBlock(FabricBlockSettings.copyOf(SMOOTH_STONE_BRICKS)));

    private static Block register(String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, id), block);
    }
}
