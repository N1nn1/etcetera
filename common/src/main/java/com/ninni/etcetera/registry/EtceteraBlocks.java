package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.block.*;
import com.ninni.etcetera.block.enums.DrumType;
import com.ninni.etcetera.block.vanilla.PublicPaneBlock;
import com.ninni.etcetera.block.vanilla.PublicStairsBlock;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import com.ninni.etcetera.platform.services.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.*;

public class EtceteraBlocks {
    public static final RegistrationProvider<Block> BLOCKS = RegistrationProvider.get(Registries.BLOCK, Constants.MOD_ID);

    public static final RegistryObject<Block> RAW_BISMUTH_BLOCK = register("raw_bismuth_block", () -> new Block(BlockBehaviour.Properties.ofFullCopy(RAW_COPPER_BLOCK)));
    public static final RegistryObject<Block> BISMUTH_BLOCK = register("bismuth_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(IRON_BLOCK).sound(EtceteraSoundEvents.BISMUTH_BLOCK)));
    public static final RegistryObject<Block> BISMUTH_BARS = register("bismuth_bars", () -> new PublicPaneBlock(BlockBehaviour.Properties.ofFullCopy(BISMUTH_BLOCK.get())));
    public static final RegistryObject<Block> NETHER_BISMUTH_ORE = register("nether_bismuth_ore", () -> new DropExperienceBlock(UniformInt.of(1, 8), BlockBehaviour.Properties.of().requiresCorrectToolForDrops().mapColor(MapColor.NETHER).strength(3.0f, 3.0f).sound(EtceteraSoundEvents.NETHER_BISMUTH_ORE)));

    public static final RegistryObject<Block> IRIDESCENT_GLASS = register("iridescent_glass", () -> new TransparentBlock(BlockBehaviour.Properties.ofFullCopy(GLASS).friction(1.0F)) {
    });
    public static final RegistryObject<Block> IRIDESCENT_GLASS_PANE = register("iridescent_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.ofFullCopy(LIGHT_GRAY_STAINED_GLASS_PANE).friction(1.0F)) {
    });
    public static final RegistryObject<Block> IRIDESCENT_TERRACOTTA = register("iridescent_terracotta", () -> new Block(BlockBehaviour.Properties.ofFullCopy(TERRACOTTA)));
    public static final RegistryObject<Block> IRIDESCENT_GLAZED_TERRACOTTA = register("iridescent_glazed_terracotta", () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.ofFullCopy(LIGHT_GRAY_GLAZED_TERRACOTTA)));
    public static final RegistryObject<Block> IRIDESCENT_CONCRETE = register("iridescent_concrete", () -> new Block(BlockBehaviour.Properties.ofFullCopy(LIGHT_GRAY_CONCRETE)));
    public static final RegistryObject<Block> IRIDESCENT_WOOL = register("iridescent_wool", () -> new Block(BlockBehaviour.Properties.ofFullCopy(LIGHT_GRAY_WOOL)));
    public static final RegistryObject<Block> IRIDESCENT_LANTERN = register("iridescent_lantern", () -> new Block(BlockBehaviour.Properties.ofFullCopy(SEA_LANTERN)));

    public static final RegistryObject<Block> DRUM = register("drum", () -> new DrumBlock(BlockBehaviour.Properties.ofFullCopy(NOTE_BLOCK)));

    public static final RegistryObject<Block> DICE = register("dice", () -> new DiceBlock(BlockBehaviour.Properties.ofFullCopy(QUARTZ_BLOCK)));

    public static final RegistryObject<Block> FRAME = register("frame", () -> new FrameBlock(BlockBehaviour.Properties.of().sound(SoundType.SCAFFOLDING).mapColor(MapColor.SAND).noOcclusion().isSuffocating((state, world, pos) -> false).isViewBlocking((state, world, pos) -> false).noOcclusion()));

    public static final RegistryObject<Block> PRICKLY_CAN = register("prickly_can", () -> new PricklyCanBlock(BlockBehaviour.Properties.ofFullCopy(CACTUS).strength(1f, 4f)));

    public static final RegistryObject<Block> DREAM_CATCHER = register("dream_catcher", () -> new DreamCatcherBlock(BlockBehaviour.Properties.of().noCollission().pushReaction(PushReaction.DESTROY).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> BOUQUET = register("bouquet", () -> new BouquetBlock(BlockBehaviour.Properties.of().sound(SoundType.GRASS).mapColor(MapColor.PLANT).noCollission().destroyTime(0.0f)));
    public static final RegistryObject<Block> POTTED_BOUQUET = register("potted_bouquet", () -> new FlowerPotBlock(BOUQUET.get(), BlockBehaviour.Properties.of().destroyTime(0.0f).noOcclusion()));
    public static final RegistryObject<Block> TERRACOTTA_VASE = register("terracotta_vase", () -> new TerracottaVaseBlock(BlockBehaviour.Properties.ofFullCopy(TERRACOTTA).sound(EtceteraSoundEvents.TERRACOTTA_VASE)));

    public static final RegistryObject<Block> ITEM_STAND = register("item_stand", () -> new ItemStandBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).destroyTime(0.0f).noOcclusion()));
    public static final RegistryObject<Block> GLOW_ITEM_STAND = register("glow_item_stand", () -> new ItemStandBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).destroyTime(0.0f).noOcclusion()));

    public static final RegistryObject<Block> SQUID_LAMP = register("squid_lamp", () -> new SquidLampBlock(BlockBehaviour.Properties.of().noCollission().mapColor(MapColor.COLOR_CYAN).destroyTime(0.0f).sound(EtceteraSoundEvents.SQUID_LAMP).lightLevel(state -> state.getValue(SquidLampBlock.WATERLOGGED) ? 15 : 7)));
    public static final RegistryObject<Block> WALL_SQUID_LAMP = register("wall_squid_lamp", () -> new WallSquidLampBlock(BlockBehaviour.Properties.ofFullCopy(SQUID_LAMP.get())));

    public static final RegistryObject<Block> FOOTSTEPS = register("footsteps", () -> new FootstepsBlock(BlockBehaviour.Properties.of().noOcclusion().destroyTime(0.0f).pushReaction(PushReaction.DESTROY).noCollission().replaceable()));

    public static final RegistryObject<Block> SAND_PATH = register("sand_path", () -> new PathBlock(SAND, true, BlockBehaviour.Properties.ofFullCopy(SAND).isViewBlocking((state, world, pos) -> true).isSuffocating((state, world, pos) -> true)));
    public static final RegistryObject<Block> RED_SAND_PATH = register("red_sand_path", () -> new PathBlock(RED_SAND, true, BlockBehaviour.Properties.ofFullCopy(RED_SAND).isViewBlocking((state, world, pos) -> true).isSuffocating((state, world, pos) -> true)));
    public static final RegistryObject<Block> SNOW_PATH = register("snow_path", () -> new PathBlock(SNOW_BLOCK, false, BlockBehaviour.Properties.ofFullCopy(SNOW_BLOCK).isViewBlocking((state, world, pos) -> true).isSuffocating((state, world, pos) -> true)));
    public static final RegistryObject<Block> GRAVEL_PATH = register("gravel_path", () -> new PathBlock(GRAVEL, true, BlockBehaviour.Properties.ofFullCopy(GRAVEL).isViewBlocking((state, world, pos) -> true).isSuffocating((state, world, pos) -> true)));

    public static final RegistryObject<Block> CRUMBLING_STONE = register("crumbling_stone", () -> new CrumblingStoneBlock(BlockBehaviour.Properties.ofFullCopy(STONE).sound(EtceteraSoundEvents.CRUMBLING_STONE).strength(0.5f, 3f)));
    public static final RegistryObject<Block> WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", () -> new AbstractCrumblingStoneBlock(BlockBehaviour.Properties.ofFullCopy(CRUMBLING_STONE.get())));

    public static final RegistryObject<Block> LEVELED_STONE = register("leveled_stone", () -> new Block(BlockBehaviour.Properties.ofFullCopy(STONE).strength(1f, 4f)));
    public static final RegistryObject<Block> LEVELED_STONE_STAIRS = register("leveled_stone_stairs", () -> new PublicStairsBlock(LEVELED_STONE.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(LEVELED_STONE.get())));
    public static final RegistryObject<Block> LEVELED_STONE_SLAB = register("leveled_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(LEVELED_STONE.get())));

    public static final RegistryObject<Block> LIGHT_BULB = register("light_bulb", () -> new LightBulbBlock(BlockBehaviour.Properties.ofFullCopy(GLASS).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> TINTED_LIGHT_BULB = register("tinted_light_bulb", () -> new TintedLightBulbBlock(BlockBehaviour.Properties.ofFullCopy(TINTED_GLASS).noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> COTTON = register("cotton", () -> new CottonBlock(BlockBehaviour.Properties.of().mapColor(state -> state.getValue(CottonBlock.AGE) == 3 ? MapColor.COLOR_GREEN : MapColor.PODZOL).noCollission().randomTicks().destroyTime(0.0f).sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> COPPER_TAP = register("copper_tap", () -> new CoppertapBlock(BlockBehaviour.Properties.ofFullCopy(COPPER_BLOCK).randomTicks().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> RUBBER_CAULDRON = register("rubber_cauldron", () -> new RubberCauldronBlock(BlockBehaviour.Properties.ofFullCopy(CAULDRON).randomTicks()));
    public static final RegistryObject<Block> RUBBER_BLOCK = register("rubber_block", () -> new RubberBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(2, 2).sound(EtceteraSoundEvents.RUBBER)));
    public static final RegistryObject<Block> RUBBER_BUTTON = register("rubber_button", () -> new RubberButtonBlock(BlockBehaviour.Properties.ofFullCopy(RUBBER_BLOCK.get()).noCollission().strength(0.5f).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> REDSTONE_WIRES = register("redstone_wires", () -> new RedstoneWiresBlock(BlockBehaviour.Properties.ofFullCopy(REDSTONE_WIRE).sound(EtceteraSoundEvents.RUBBER)));
    public static final RegistryObject<Block> REDSTONE_WIRE_TORCH = register("redstone_wire_torch", () -> new RedstoneWireTorchBlock(BlockBehaviour.Properties.ofFullCopy(REDSTONE_TORCH)));
    public static final RegistryObject<Block> REDSTONE_WIRE_WALL_TORCH = register("redstone_wire_wall_torch", () -> new WallRedstoneWireTorchBlock(BlockBehaviour.Properties.ofFullCopy(REDSTONE_WALL_TORCH)));
    public static final RegistryObject<Block> REDSTONE_WIRE_COMPARATOR = register("redstone_wire_comparator", () -> new RedstoneWireComparatorBlock(BlockBehaviour.Properties.ofFullCopy(COMPARATOR).sound(EtceteraSoundEvents.RUBBER)));
    public static final RegistryObject<Block> REDSTONE_WIRE_REPEATER = register("redstone_wire_repeater", () -> new RedstoneWireRepeaterBlock(BlockBehaviour.Properties.ofFullCopy(REPEATER).sound(EtceteraSoundEvents.RUBBER)));

    private static <T extends Block> RegistryObject<T> register(String id, Supplier<T> blockSupplier) {
        return BLOCKS.register(id, blockSupplier);
    }

    public static void init() {
        DrumType.values();
    }
}