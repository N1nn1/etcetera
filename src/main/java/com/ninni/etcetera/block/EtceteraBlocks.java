package com.ninni.etcetera.block;

import static com.ninni.etcetera.Etcetera.MOD_ID;

import com.ninni.etcetera.block.vanilla.PublicPaneBlock;
import com.ninni.etcetera.block.vanilla.PublicStairsBlock;
import com.ninni.etcetera.sound.EtceteraSoundEvents;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;

public class EtceteraBlocks {

    public static final Block RAW_BISMUTH_BLOCK = register("raw_bismuth_block", new Block(FabricBlockSettings.copyOf(Blocks.RAW_COPPER_BLOCK)));
    public static final Block BISMUTH_BLOCK = register("bismuth_block", new PillarBlock(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK).sounds(EtceteraSoundEvents.BISMUTH_BLOCK)));
    public static final Block BISMUTH_BARS = register("bismuth_bars", new PublicPaneBlock(FabricBlockSettings.copyOf(BISMUTH_BLOCK)));
    public static final Block NETHER_BISMUTH_ORE = register("nether_bismuth_ore", new OreBlock(FabricBlockSettings.of(Material.STONE, MapColor.DARK_RED).requiresTool().strength(3.0f, 3.0f).sounds(EtceteraSoundEvents.NETHER_BISMUTH_ORE), UniformIntProvider.create(1, 8)));
    public static final Block IRIDESCENT_GLASS = register("iridescent_glass", new GlassBlock(FabricBlockSettings.copyOf(Blocks.GLASS).slipperiness(1.0F)));
    public static final Block IRIDESCENT_TERRACOTTA = register("iridescent_terracotta", new Block(FabricBlockSettings.copyOf(Blocks.TERRACOTTA)));
    public static final Block IRIDESCENT_CONCRETE = register("iridescent_concrete", new Block(FabricBlockSettings.copyOf(Blocks.LIGHT_GRAY_CONCRETE)));
    public static final Block IRIDESCENT_LANTERN = register("iridescent_lantern", new Block(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));

    public static final Block DRUM = register("drum", new DrumBlock(FabricBlockSettings.copyOf(Blocks.NOTE_BLOCK)));

    public static final Block DICE = register("dice", new DiceBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK)));

    public static final Block FRAME = register("frame", new FrameBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.PALE_YELLOW).sounds(BlockSoundGroup.SCAFFOLDING).nonOpaque().suffocates(EtceteraBlocks::never).blockVision(EtceteraBlocks::never).nonOpaque()));

    public static final Block BOUQUET = register("bouquet", new BouquetBlock(FabricBlockSettings.of(Material.PLANT, MapColor.PALE_GREEN).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
    public static final Block POTTED_BOUQUET = register("potted_bouquet", new FlowerPotBlock(BOUQUET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()));
    public static final Block TERRACOTTA_VASE = register("terracotta_vase", new TerracottaVaseBlock(FabricBlockSettings.copyOf(Blocks.TERRACOTTA).sounds(EtceteraSoundEvents.TERRACOTTA_VASE)));

    public static final Block ITEM_STAND = register("item_stand", new ItemStandBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).breakInstantly().nonOpaque()));

    public static final Block SQUID_LAMP = register("squid_lamp", new SquidLampBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.TEAL).noCollision().breakInstantly().sounds(EtceteraSoundEvents.SQUID_LAMP).luminance(state -> state.get(SquidLampBlock.WATERLOGGED) ? 15 : 7)));
    public static final Block WALL_SQUID_LAMP = register("wall_squid_lamp", new WallSquidLampBlock(FabricBlockSettings.copyOf(SQUID_LAMP).dropsLike(SQUID_LAMP)));

    public static final Block CRUMBLING_STONE = register("crumbling_stone", new CrumblingStoneBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(EtceteraSoundEvents.CRUMBLING_STONE).strength(0.5f, 3f)));
    public static final Block WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", new AbstractCrumblingStoneBlock(FabricBlockSettings.copyOf(CRUMBLING_STONE)));

    public static final Block LEVELED_STONE = register("leveled_stone", new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(1f, 4f)));
    public static final Block LEVELED_STONE_STAIRS = register("leveled_stone_stairs", new PublicStairsBlock(LEVELED_STONE.getDefaultState(), FabricBlockSettings.copyOf(LEVELED_STONE)));
    public static final Block LEVELED_STONE_SLAB = register("leveled_stone_slab", new SlabBlock(FabricBlockSettings.copyOf(LEVELED_STONE)));

    public static final Block BIG_SNAIL_SHELL = register("big_snail_shell", new BigSnailShellBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.BROWN).strength(1.5f, 1200.0f).sounds(BlockSoundGroup.BONE)));
    public static final Block SNAIL_SHELL_TILES = register("snail_shell_tiles", new Block(FabricBlockSettings.copyOf(BIG_SNAIL_SHELL).strength(1f, 1200.0f)));
    public static final Block SNAIL_SHELL_TILE_STAIRS = register("snail_shell_tile_stairs", new PublicStairsBlock(SNAIL_SHELL_TILES.getDefaultState(), FabricBlockSettings.copyOf(SNAIL_SHELL_TILES)));
    public static final Block SNAIL_SHELL_TILE_SLAB = register("snail_shell_tile_slab", new SlabBlock(FabricBlockSettings.copyOf(SNAIL_SHELL_TILES)));
    public static final Block MUCUS_BLOCK = register("mucus_block", new MucusBlock(FabricBlockSettings.of(Material.ORGANIC_PRODUCT, MapColor.PALE_YELLOW).sounds(BlockSoundGroup.SLIME).nonOpaque()));

    private static Block register(String id, Block block) { return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, id), block); }
    private static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
}
