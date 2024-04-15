package com.ninni.etcetera.registry;

import com.ninni.etcetera.block.*;
import com.ninni.etcetera.block.vanilla.PublicPaneBlock;
import com.ninni.etcetera.block.vanilla.PublicStairsBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.BlockView;

import static com.ninni.etcetera.Etcetera.MOD_ID;
import static net.minecraft.block.Blocks.*;

public class EtceteraBlocks {

    public static final Block RAW_BISMUTH_BLOCK = register("raw_bismuth_block", new Block(FabricBlockSettings.copyOf(RAW_COPPER_BLOCK)));
    public static final Block BISMUTH_BLOCK = register("bismuth_block", new PillarBlock(FabricBlockSettings.copyOf(IRON_BLOCK).sounds(EtceteraSoundEvents.BISMUTH_BLOCK)));
    public static final Block BISMUTH_BARS = register("bismuth_bars", new PublicPaneBlock(FabricBlockSettings.copyOf(BISMUTH_BLOCK)));
    public static final Block NETHER_BISMUTH_ORE = register("nether_bismuth_ore", new ExperienceDroppingBlock(FabricBlockSettings.create().requiresTool().mapColor(MapColor.DARK_RED).strength(3.0f, 3.0f).sounds(EtceteraSoundEvents.NETHER_BISMUTH_ORE), UniformIntProvider.create(1, 8)));
    public static final Block IRIDESCENT_GLASS = register("iridescent_glass", new GlassBlock(FabricBlockSettings.copyOf(GLASS).slipperiness(1.0F)));
    public static final Block IRIDESCENT_GLASS_PANE = register("iridescent_glass_pane", new PaneBlock(FabricBlockSettings.copyOf(LIGHT_GRAY_STAINED_GLASS_PANE).slipperiness(1.0F)));
    public static final Block IRIDESCENT_TERRACOTTA = register("iridescent_terracotta", new Block(FabricBlockSettings.copyOf(TERRACOTTA)));
    public static final Block IRIDESCENT_GLAZED_TERRACOTTA = register("iridescent_glazed_terracotta", new GlazedTerracottaBlock(FabricBlockSettings.copyOf(LIGHT_GRAY_GLAZED_TERRACOTTA)));
    public static final Block IRIDESCENT_CONCRETE = register("iridescent_concrete", new Block(FabricBlockSettings.copyOf(LIGHT_GRAY_CONCRETE)));
    public static final Block IRIDESCENT_WOOL = register("iridescent_wool", new Block(FabricBlockSettings.copyOf(LIGHT_GRAY_WOOL)));
    public static final Block IRIDESCENT_LANTERN = register("iridescent_lantern", new Block(FabricBlockSettings.copyOf(SEA_LANTERN)));

    public static final Block DRUM = register("drum", new DrumBlock(FabricBlockSettings.copyOf(NOTE_BLOCK)));

    public static final Block DICE = register("dice", new DiceBlock(FabricBlockSettings.copyOf(QUARTZ_BLOCK)));

    public static final Block FRAME = register("frame", new FrameBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.SCAFFOLDING).mapColor(MapColor.PALE_YELLOW).nonOpaque().suffocates(EtceteraBlocks::never).blockVision(EtceteraBlocks::never).nonOpaque()));

    public static final Block PRICKLY_CAN = register("prickly_can", new PricklyCanBlock(FabricBlockSettings.copyOf(CACTUS).strength(1f, 4f)));

    public static final Block DREAM_CATCHER = register("dream_catcher", new DreamCatcherBlock(FabricBlockSettings.create().noCollision().burnable().pistonBehavior(PistonBehavior.DESTROY).sounds(BlockSoundGroup.WOOL)));

    public static final Block BOUQUET = register("bouquet", new BouquetBlock(FabricBlockSettings.create().sounds(BlockSoundGroup.GRASS).mapColor(MapColor.PALE_GREEN).noCollision().breakInstantly()));
    public static final Block POTTED_BOUQUET = register("potted_bouquet", new FlowerPotBlock(BOUQUET, FabricBlockSettings.create().breakInstantly().nonOpaque()));
    public static final Block TERRACOTTA_VASE = register("terracotta_vase", new TerracottaVaseBlock(FabricBlockSettings.copyOf(TERRACOTTA).sounds(EtceteraSoundEvents.TERRACOTTA_VASE)));

    public static final Block ITEM_STAND = register("item_stand", new ItemStandBlock(FabricBlockSettings.create().mapColor(MapColor.STONE_GRAY).breakInstantly().nonOpaque()));
    public static final Block GLOW_ITEM_STAND = register("glow_item_stand", new ItemStandBlock(FabricBlockSettings.create().mapColor(MapColor.STONE_GRAY).breakInstantly().nonOpaque()));

    public static final Block SQUID_LAMP = register("squid_lamp", new SquidLampBlock(FabricBlockSettings.create().noCollision().mapColor(MapColor.TEAL).breakInstantly().sounds(EtceteraSoundEvents.SQUID_LAMP).luminance(state -> state.get(SquidLampBlock.WATERLOGGED) ? 15 : 7)));
    public static final Block WALL_SQUID_LAMP = register("wall_squid_lamp", new WallSquidLampBlock(FabricBlockSettings.copyOf(SQUID_LAMP).dropsLike(SQUID_LAMP)));

    public static final Block FOOTSTEPS = register("footsteps", new FootstepsBlock(FabricBlockSettings.create().nonOpaque().breakInstantly().pistonBehavior(PistonBehavior.DESTROY).noCollision().dropsNothing().noBlockBreakParticles().replaceable()));
   
    public static final Block SAND_PATH = register("sand_path", new PathBlock(SAND, true, FabricBlockSettings.copyOf(SAND).blockVision(Blocks::always).suffocates(Blocks::always)));
    public static final Block RED_SAND_PATH = register("red_sand_path", new PathBlock(RED_SAND,true, FabricBlockSettings.copyOf(RED_SAND).blockVision(Blocks::always).suffocates(Blocks::always)));
    public static final Block SNOW_PATH = register("snow_path", new PathBlock(SNOW_BLOCK,false, FabricBlockSettings.copyOf(SNOW_BLOCK).blockVision(Blocks::always).suffocates(Blocks::always)));
    public static final Block GRAVEL_PATH = register("gravel_path", new PathBlock(GRAVEL,true, FabricBlockSettings.copyOf(GRAVEL).blockVision(Blocks::always).suffocates(Blocks::always)));

    public static final Block CRUMBLING_STONE = register("crumbling_stone", new CrumblingStoneBlock(FabricBlockSettings.copyOf(STONE).sounds(EtceteraSoundEvents.CRUMBLING_STONE).strength(0.5f, 3f)));
    public static final Block WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", new AbstractCrumblingStoneBlock(FabricBlockSettings.copyOf(CRUMBLING_STONE)));

    public static final Block LEVELED_STONE = register("leveled_stone", new Block(FabricBlockSettings.copyOf(STONE).strength(1f, 4f)));
    public static final Block LEVELED_STONE_STAIRS = register("leveled_stone_stairs", new PublicStairsBlock(LEVELED_STONE.getDefaultState(), FabricBlockSettings.copyOf(LEVELED_STONE)));
    public static final Block LEVELED_STONE_SLAB = register("leveled_stone_slab", new SlabBlock(FabricBlockSettings.copyOf(LEVELED_STONE)));

    public static final Block LIGHT_BULB = register("light_bulb", new LightBulbBlock(FabricBlockSettings.copyOf(GLASS).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block TINTED_LIGHT_BULB = register("tinted_light_bulb", new TintedLightBulbBlock(FabricBlockSettings.copyOf(TINTED_GLASS).nonOpaque().pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block COTTON = register("cotton", new CottonBlock(AbstractBlock.Settings.create().mapColor(state -> state.get(CottonBlock.AGE) == 3 ? MapColor.GREEN : MapColor.SPRUCE_BROWN).noCollision().ticksRandomly().breakInstantly().sounds(BlockSoundGroup.CROP).pistonBehavior(PistonBehavior.DESTROY)));

    public static final Block COPPER_TAP = register("copper_tap", new CoppertapBlock(FabricBlockSettings.copyOf(COPPER_BLOCK).ticksRandomly().pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block RUBBER_CAULDRON = register("rubber_cauldron", new RubberCauldronBlock(FabricBlockSettings.copyOf(CAULDRON).ticksRandomly()));
    public static final Block RUBBER_BLOCK = register("rubber_block", new Block(FabricBlockSettings.create().mapColor(MapColor.TERRACOTTA_BROWN).strength(2, 2).sounds(EtceteraSoundEvents.RUBBER)));
    public static final Block RUBBER_BUTTON = register("rubber_button", new RubberButtonBlock(FabricBlockSettings.copyOf(RUBBER_BLOCK).noCollision().strength(0.5f).pistonBehavior(PistonBehavior.DESTROY)));
    public static final Block REDSTONE_WIRES = register("redstone_wires", new RedstoneWiresBlock(FabricBlockSettings.copyOf(REDSTONE_WIRE).collidable(true).sounds(EtceteraSoundEvents.RUBBER)));
    public static final Block REDSTONE_WIRE_TORCH = register("redstone_wire_torch", new RedstoneWireTorchBlock(FabricBlockSettings.copyOf(REDSTONE_TORCH)));
    public static final Block REDSTONE_WIRE_WALL_TORCH = register("redstone_wire_wall_torch", new WallRedstoneWireTorchBlock(FabricBlockSettings.copyOf(REDSTONE_WALL_TORCH).dropsLike(REDSTONE_WIRE_TORCH)));
    public static final Block REDSTONE_WIRE_COMPARATOR = register("redstone_wire_comparator", new RedstoneWireComparatorBlock(FabricBlockSettings.copyOf(COMPARATOR).sounds(EtceteraSoundEvents.RUBBER)));
    public static final Block REDSTONE_WIRE_REPEATER = register("redstone_wire_repeater", new RedstoneWireRepeaterBlock(FabricBlockSettings.copyOf(REPEATER).sounds(EtceteraSoundEvents.RUBBER)));

    private static Block register(String id, Block block) { return Registry.register(Registries.BLOCK, new Identifier(MOD_ID, id), block); }
    private static boolean never(BlockState state, BlockView world, BlockPos pos) { return false; }
}
