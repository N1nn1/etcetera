package com.ninni.etcetera.block;

import static com.ninni.etcetera.Etcetera.MOD_ID;

import com.ninni.etcetera.block.vanilla.PublicPaneBlock;
import com.ninni.etcetera.block.vanilla.PublicStairsBlock;
import com.ninni.etcetera.sound.EtceteraSoundEvents;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.block.PillarBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.WallBlock;
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
    public static final Block IRIDESCENT_PACKED_SILT = register("iridescent_packed_silt", new Block(FabricBlockSettings.copyOf(Blocks.TERRACOTTA).mapColor(MapColor.LIGHT_GRAY).sounds(EtceteraSoundEvents.PACKED_SILT)));
    public static final Block IRIDESCENT_LANTERN = register("iridescent_lantern", new Block(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN)));

    public static final Block GRAVEL_BRICKS = register("gravel_bricks", new Block(FabricBlockSettings.of(Material.AGGREGATE, MapColor.STONE_GRAY).strength(0.8f).sounds(EtceteraSoundEvents.GRAVEL_BRICKS)));
    public static final Block GRAVEL_BRICK_STAIRS = register("gravel_brick_stairs", new PublicStairsBlock(GRAVEL_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(GRAVEL_BRICKS)));
    public static final Block GRAVEL_BRICK_SLAB = register("gravel_brick_slab", new SlabBlock(FabricBlockSettings.copyOf(GRAVEL_BRICKS)));
    public static final Block GRAVEL_BRICK_WALL = register("gravel_brick_wall", new WallBlock(FabricBlockSettings.copyOf(GRAVEL_BRICKS)));

    public static final Block QUARTZ_COLUMN = register("quartz_column", new ColumnBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_BRICKS)));
    public static final Block STONE_COLUMN = register("stone_column", new ColumnBlock(FabricBlockSettings.copyOf(Blocks.STONE_BRICKS)));
    public static final Block DEEPSLATE_COLUMN = register("deepslate_column", new ColumnBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE_BRICKS)));
    public static final Block BLACKSTONE_COLUMN = register("blackstone_column", new ColumnBlock(FabricBlockSettings.copyOf(Blocks.POLISHED_BLACKSTONE_BRICKS)));

    public static final Block COMPACTED_DRIPSTONE = register("compacted_dripstone", new CompactedDripstoneBlock(FabricBlockSettings.copyOf(Blocks.DRIPSTONE_BLOCK)));

    public static final Block DRUM = register("drum", new DrumBlock(FabricBlockSettings.copyOf(Blocks.NOTE_BLOCK)));

    public static final Block DICE = register("dice", new DiceBlock(FabricBlockSettings.copyOf(Blocks.QUARTZ_BLOCK)));

    public static final Block FRAME = register("frame", new FrameBlock(FabricBlockSettings.of(Material.DECORATION, MapColor.PALE_YELLOW).sounds(BlockSoundGroup.SCAFFOLDING).nonOpaque().suffocates(EtceteraBlocks::never).blockVision(EtceteraBlocks::never).nonOpaque()));

    public static final Block BOUQUET = register("bouquet", new BouquetBlock(FabricBlockSettings.of(Material.PLANT, MapColor.PALE_GREEN).sounds(BlockSoundGroup.GRASS).noCollision().breakInstantly()));
    public static final Block POTTED_BOUQUET = register("potted_bouquet", new FlowerPotBlock(BOUQUET, FabricBlockSettings.of(Material.DECORATION).breakInstantly().nonOpaque()));
    public static final Block TERRACOTTA_VASE = register("terracotta_vase", new TerracottaVaseBlock(FabricBlockSettings.copyOf(Blocks.TERRACOTTA).sounds(EtceteraSoundEvents.TERRACOTTA_VASE)));

    public static final Block ITEM_STAND = register("item_stand", new ItemStandBlock(FabricBlockSettings.of(Material.STONE, MapColor.STONE_GRAY).breakInstantly().nonOpaque()));

    public static final Block SILT = register("silt", new PillarBlock(FabricBlockSettings.copyOf(Blocks.CLAY).mapColor(MapColor.BROWN).sounds(EtceteraSoundEvents.SILT)));
    public static final Block SILT_BRICKS = register("silt_bricks", new Block(FabricBlockSettings.copyOf(Blocks.BRICKS).mapColor(MapColor.PALE_YELLOW)));
    public static final Block SILT_BRICK_STAIRS = register("silt_brick_stairs", new PublicStairsBlock(SILT_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(SILT_BRICKS)));
    public static final Block SILT_BRICK_SLAB = register("silt_brick_slab", new SlabBlock(FabricBlockSettings.copyOf(SILT_BRICKS)));
    public static final Block SILT_BRICK_WALL = register("silt_brick_wall", new WallBlock(FabricBlockSettings.copyOf(SILT_BRICKS)));

    public static final Block SILT_POT = register("silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(Blocks.TERRACOTTA).strength(0.5f, 2f)));
    public static final Block WHITE_SILT_POT = register("white_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.WHITE)));
    public static final Block ORANGE_SILT_POT = register("orange_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.ORANGE)));
    public static final Block MAGENTA_SILT_POT = register("magenta_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.MAGENTA)));
    public static final Block LIGHT_BLUE_SILT_POT = register("light_blue_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.LIGHT_BLUE)));
    public static final Block YELLOW_SILT_POT = register("yellow_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.YELLOW)));
    public static final Block LIME_SILT_POT = register("lime_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.LIME)));
    public static final Block PINK_SILT_POT = register("pink_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.PINK)));
    public static final Block GRAY_SILT_POT = register("gray_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.GRAY)));
    public static final Block LIGHT_GRAY_SILT_POT = register("light_gray_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.LIGHT_GRAY)));
    public static final Block CYAN_SILT_POT = register("cyan_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.CYAN)));
    public static final Block PURPLE_SILT_POT = register("purple_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.PURPLE)));
    public static final Block BLUE_SILT_POT = register("blue_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.BLUE)));
    public static final Block BROWN_SILT_POT = register("brown_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.BROWN)));
    public static final Block GREEN_SILT_POT = register("green_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.GREEN)));
    public static final Block RED_SILT_POT = register("red_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.RED)));
    public static final Block BLACK_SILT_POT = register("black_silt_pot", new SiltPotBlock(FabricBlockSettings.copyOf(SILT_POT).mapColor(MapColor.BLACK)));

    public static final Block PACKED_SILT = register("packed_silt", new Block(FabricBlockSettings.copyOf(Blocks.TERRACOTTA).sounds(EtceteraSoundEvents.PACKED_SILT)));
    public static final Block WHITE_PACKED_SILT = register("white_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.WHITE)));
    public static final Block ORANGE_PACKED_SILT = register("orange_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.ORANGE)));
    public static final Block MAGENTA_PACKED_SILT = register("magenta_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.MAGENTA)));
    public static final Block LIGHT_BLUE_PACKED_SILT = register("light_blue_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.LIGHT_BLUE)));
    public static final Block YELLOW_PACKED_SILT = register("yellow_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.YELLOW)));
    public static final Block LIME_PACKED_SILT = register("lime_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.LIME)));
    public static final Block PINK_PACKED_SILT = register("pink_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.PINK)));
    public static final Block GRAY_PACKED_SILT = register("gray_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.GRAY)));
    public static final Block LIGHT_GRAY_PACKED_SILT = register("light_gray_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.LIGHT_GRAY)));
    public static final Block CYAN_PACKED_SILT = register("cyan_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.CYAN)));
    public static final Block PURPLE_PACKED_SILT = register("purple_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.PURPLE)));
    public static final Block BLUE_PACKED_SILT = register("blue_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.BLUE)));
    public static final Block BROWN_PACKED_SILT = register("brown_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.BROWN)));
    public static final Block GREEN_PACKED_SILT = register("green_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.GREEN)));
    public static final Block RED_PACKED_SILT = register("red_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.RED)));
    public static final Block BLACK_PACKED_SILT = register("black_packed_silt", new Block(FabricBlockSettings.copyOf(PACKED_SILT).mapColor(MapColor.BLACK)));

    public static final Block SILT_SHINGLES = register("silt_shingles", new Block(FabricBlockSettings.copyOf(Blocks.TERRACOTTA).sounds(EtceteraSoundEvents.SILT_SHINGLES)));
    public static final Block WHITE_SILT_SHINGLES = register("white_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.WHITE)));
    public static final Block ORANGE_SILT_SHINGLES = register("orange_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.ORANGE)));
    public static final Block MAGENTA_SILT_SHINGLES = register("magenta_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.MAGENTA)));
    public static final Block LIGHT_BLUE_SILT_SHINGLES = register("light_blue_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.LIGHT_BLUE)));
    public static final Block YELLOW_SILT_SHINGLES = register("yellow_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.YELLOW)));
    public static final Block LIME_SILT_SHINGLES = register("lime_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.LIME)));
    public static final Block PINK_SILT_SHINGLES = register("pink_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.PINK)));
    public static final Block GRAY_SILT_SHINGLES = register("gray_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.GRAY)));
    public static final Block LIGHT_GRAY_SILT_SHINGLES = register("light_gray_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.LIGHT_GRAY)));
    public static final Block CYAN_SILT_SHINGLES = register("cyan_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.CYAN)));
    public static final Block PURPLE_SILT_SHINGLES = register("purple_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.PURPLE)));
    public static final Block BLUE_SILT_SHINGLES = register("blue_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.BLUE)));
    public static final Block BROWN_SILT_SHINGLES = register("brown_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.BROWN)));
    public static final Block GREEN_SILT_SHINGLES = register("green_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.GREEN)));
    public static final Block RED_SILT_SHINGLES = register("red_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.RED)));
    public static final Block BLACK_SILT_SHINGLES = register("black_silt_shingles", new Block(FabricBlockSettings.copyOf(SILT_SHINGLES).mapColor(MapColor.BLACK)));

    public static final Block SILT_SHINGLE_STAIRS = register("silt_shingle_stairs", new PublicStairsBlock(SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(SILT_SHINGLES)));
    public static final Block WHITE_SILT_SHINGLE_STAIRS = register("white_silt_shingle_stairs", new PublicStairsBlock(WHITE_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(WHITE_SILT_SHINGLES)));
    public static final Block ORANGE_SILT_SHINGLE_STAIRS = register("orange_silt_shingle_stairs", new PublicStairsBlock(ORANGE_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(ORANGE_SILT_SHINGLES)));
    public static final Block MAGENTA_SILT_SHINGLE_STAIRS = register("magenta_silt_shingle_stairs", new PublicStairsBlock(MAGENTA_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(MAGENTA_SILT_SHINGLES)));
    public static final Block LIGHT_BLUE_SILT_SHINGLE_STAIRS = register("light_blue_silt_shingle_stairs", new PublicStairsBlock(LIGHT_BLUE_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(LIGHT_BLUE_SILT_SHINGLES)));
    public static final Block YELLOW_SILT_SHINGLE_STAIRS = register("yellow_silt_shingle_stairs", new PublicStairsBlock(YELLOW_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(YELLOW_SILT_SHINGLES)));
    public static final Block LIME_SILT_SHINGLE_STAIRS = register("lime_silt_shingle_stairs", new PublicStairsBlock(LIME_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(LIME_SILT_SHINGLES)));
    public static final Block PINK_SILT_SHINGLE_STAIRS = register("pink_silt_shingle_stairs", new PublicStairsBlock(PINK_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(PINK_SILT_SHINGLES)));
    public static final Block GRAY_SILT_SHINGLE_STAIRS = register("gray_silt_shingle_stairs", new PublicStairsBlock(GRAY_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(GRAY_SILT_SHINGLES)));
    public static final Block LIGHT_GRAY_SILT_SHINGLE_STAIRS = register("light_gray_silt_shingle_stairs", new PublicStairsBlock(LIGHT_GRAY_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(LIGHT_GRAY_SILT_SHINGLES)));
    public static final Block CYAN_SILT_SHINGLE_STAIRS = register("cyan_silt_shingle_stairs", new PublicStairsBlock(CYAN_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(CYAN_SILT_SHINGLES)));
    public static final Block PURPLE_SILT_SHINGLE_STAIRS = register("purple_silt_shingle_stairs", new PublicStairsBlock(PURPLE_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(PURPLE_SILT_SHINGLES)));
    public static final Block BLUE_SILT_SHINGLE_STAIRS = register("blue_silt_shingle_stairs", new PublicStairsBlock(BLUE_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(BLUE_SILT_SHINGLES)));
    public static final Block BROWN_SILT_SHINGLE_STAIRS = register("brown_silt_shingle_stairs", new PublicStairsBlock(BROWN_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(BROWN_SILT_SHINGLES)));
    public static final Block GREEN_SILT_SHINGLE_STAIRS = register("green_silt_shingle_stairs", new PublicStairsBlock(GREEN_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(GREEN_SILT_SHINGLES)));
    public static final Block RED_SILT_SHINGLE_STAIRS = register("red_silt_shingle_stairs", new PublicStairsBlock(RED_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(RED_SILT_SHINGLES)));
    public static final Block BLACK_SILT_SHINGLE_STAIRS = register("black_silt_shingle_stairs", new PublicStairsBlock(BLACK_SILT_SHINGLES.getDefaultState(), FabricBlockSettings.copyOf(BLACK_SILT_SHINGLES)));

    public static final Block SILT_SHINGLE_SLAB = register("silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(SILT_SHINGLES)));
    public static final Block WHITE_SILT_SHINGLE_SLAB = register("white_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(WHITE_SILT_SHINGLES)));
    public static final Block ORANGE_SILT_SHINGLE_SLAB = register("orange_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(ORANGE_SILT_SHINGLES)));
    public static final Block MAGENTA_SILT_SHINGLE_SLAB = register("magenta_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(MAGENTA_SILT_SHINGLES)));
    public static final Block LIGHT_BLUE_SILT_SHINGLE_SLAB = register("light_blue_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(LIGHT_BLUE_SILT_SHINGLES)));
    public static final Block YELLOW_SILT_SHINGLE_SLAB = register("yellow_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(YELLOW_SILT_SHINGLES)));
    public static final Block LIME_SILT_SHINGLE_SLAB = register("lime_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(LIME_SILT_SHINGLES)));
    public static final Block PINK_SILT_SHINGLE_SLAB = register("pink_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(PINK_SILT_SHINGLES)));
    public static final Block GRAY_SILT_SHINGLE_SLAB = register("gray_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(GRAY_SILT_SHINGLES)));
    public static final Block LIGHT_GRAY_SILT_SHINGLE_SLAB = register("light_gray_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(LIGHT_GRAY_SILT_SHINGLES)));
    public static final Block CYAN_SILT_SHINGLE_SLAB = register("cyan_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(CYAN_SILT_SHINGLES)));
    public static final Block PURPLE_SILT_SHINGLE_SLAB = register("purple_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(PURPLE_SILT_SHINGLES)));
    public static final Block BLUE_SILT_SHINGLE_SLAB = register("blue_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(BLUE_SILT_SHINGLES)));
    public static final Block BROWN_SILT_SHINGLE_SLAB = register("brown_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(BROWN_SILT_SHINGLES)));
    public static final Block GREEN_SILT_SHINGLE_SLAB = register("green_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(GREEN_SILT_SHINGLES)));
    public static final Block RED_SILT_SHINGLE_SLAB = register("red_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(RED_SILT_SHINGLES)));
    public static final Block BLACK_SILT_SHINGLE_SLAB = register("black_silt_shingle_slab", new SlabBlock(FabricBlockSettings.copyOf(BLACK_SILT_SHINGLES)));

    public static final Block SILT_SHINGLE_WALL = register("silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(SILT_SHINGLES)));
    public static final Block WHITE_SILT_SHINGLE_WALL = register("white_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(WHITE_SILT_SHINGLES)));
    public static final Block ORANGE_SILT_SHINGLE_WALL = register("orange_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(ORANGE_SILT_SHINGLES)));
    public static final Block MAGENTA_SILT_SHINGLE_WALL = register("magenta_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(MAGENTA_SILT_SHINGLES)));
    public static final Block LIGHT_BLUE_SILT_SHINGLE_WALL = register("light_blue_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(LIGHT_BLUE_SILT_SHINGLES)));
    public static final Block YELLOW_SILT_SHINGLE_WALL = register("yellow_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(YELLOW_SILT_SHINGLES)));
    public static final Block LIME_SILT_SHINGLE_WALL = register("lime_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(LIME_SILT_SHINGLES)));
    public static final Block PINK_SILT_SHINGLE_WALL = register("pink_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(PINK_SILT_SHINGLES)));
    public static final Block GRAY_SILT_SHINGLE_WALL = register("gray_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(GRAY_SILT_SHINGLES)));
    public static final Block LIGHT_GRAY_SILT_SHINGLE_WALL = register("light_gray_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(LIGHT_GRAY_SILT_SHINGLES)));
    public static final Block CYAN_SILT_SHINGLE_WALL = register("cyan_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(CYAN_SILT_SHINGLES)));
    public static final Block PURPLE_SILT_SHINGLE_WALL = register("purple_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(PURPLE_SILT_SHINGLES)));
    public static final Block BLUE_SILT_SHINGLE_WALL = register("blue_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(BLUE_SILT_SHINGLES)));
    public static final Block BROWN_SILT_SHINGLE_WALL = register("brown_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(BROWN_SILT_SHINGLES)));
    public static final Block GREEN_SILT_SHINGLE_WALL = register("green_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(GREEN_SILT_SHINGLES)));
    public static final Block RED_SILT_SHINGLE_WALL = register("red_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(RED_SILT_SHINGLES)));
    public static final Block BLACK_SILT_SHINGLE_WALL = register("black_silt_shingle_wall", new WallBlock(FabricBlockSettings.copyOf(BLACK_SILT_SHINGLES)));

    public static final Block SQUID_LAMP = register("squid_lamp", new SquidLampBlock(FabricBlockSettings.of(Material.SOLID_ORGANIC, MapColor.TEAL).noCollision().breakInstantly().sounds(EtceteraSoundEvents.SQUID_LAMP).luminance(state -> state.get(SquidLampBlock.WATERLOGGED) ? 15 : 7)));
    public static final Block WALL_SQUID_LAMP = register("wall_squid_lamp", new WallSquidLampBlock(FabricBlockSettings.copyOf(SQUID_LAMP).dropsLike(SQUID_LAMP)));

    public static final Block CRUMBLING_STONE = register("crumbling_stone", new CrumblingStoneBlock(FabricBlockSettings.copyOf(Blocks.STONE).sounds(EtceteraSoundEvents.CRUMBLING_STONE).strength(0.5f, 3f)));
    public static final Block WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", new AbstractCrumblingStoneBlock(FabricBlockSettings.copyOf(CRUMBLING_STONE)));

    public static final Block LEVELED_STONE = register("leveled_stone", new Block(FabricBlockSettings.copyOf(Blocks.STONE).strength(1f, 4f)));
    public static final Block LEVELED_STONE_STAIRS = register("leveled_stone_stairs", new PublicStairsBlock(LEVELED_STONE.getDefaultState(), FabricBlockSettings.copyOf(LEVELED_STONE)));
    public static final Block LEVELED_STONE_SLAB = register("leveled_stone_slab", new SlabBlock(FabricBlockSettings.copyOf(LEVELED_STONE)));
    public static final Block SMOOTH_STONE_BRICKS = register("smooth_stone_bricks", new Block(FabricBlockSettings.copyOf(Blocks.STONE)));
    public static final Block SMOOTH_STONE_BRICK_STAIRS = register("smooth_stone_brick_stairs", new PublicStairsBlock(SMOOTH_STONE_BRICKS.getDefaultState(), FabricBlockSettings.copyOf(SMOOTH_STONE_BRICKS)));
    public static final Block SMOOTH_STONE_BRICK_SLAB = register("smooth_stone_brick_slab", new SlabBlock(FabricBlockSettings.copyOf(SMOOTH_STONE_BRICKS)));
    public static final Block SMOOTH_STONE_BRICK_WALL = register("smooth_stone_brick_wall", new WallBlock(FabricBlockSettings.copyOf(SMOOTH_STONE_BRICKS)));

    private static Block register(String id, Block block) { return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, id), block); }
    private static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
}
