package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.block.AbstractCrumblingStoneBlock;
import com.ninni.etcetera.block.BouquetBlock;
import com.ninni.etcetera.block.CottonBlock;
import com.ninni.etcetera.block.CrumblingStoneBlock;
import com.ninni.etcetera.block.DiceBlock;
import com.ninni.etcetera.block.DrumBlock;
import com.ninni.etcetera.block.FrameBlock;
import com.ninni.etcetera.block.ItemStandBlock;
import com.ninni.etcetera.block.LightBulbBlock;
import com.ninni.etcetera.block.PricklyCanBlock;
import com.ninni.etcetera.block.SquidLampBlock;
import com.ninni.etcetera.block.TerracottaVaseBlock;
import com.ninni.etcetera.block.TintedLightBulbBlock;
import com.ninni.etcetera.block.WallSquidLampBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.GlassBlock;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EtceteraBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Etcetera.MOD_ID);

    public static final RegistryObject<Block> RAW_BISMUTH_BLOCK = BLOCKS.register("raw_bismuth_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.RAW_COPPER_BLOCK)));
    public static final RegistryObject<Block> BISMUTH_BLOCK = BLOCKS.register("bismuth_block", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).sound(EtceteraSoundEvents.BISMUTH_BLOCK)));
    public static final RegistryObject<Block> BISMUTH_BARS = BLOCKS.register("bismuth_bars", () -> new IronBarsBlock(BlockBehaviour.Properties.copy(BISMUTH_BLOCK.get())));
    public static final RegistryObject<Block> NETHER_BISMUTH_ORE = BLOCKS.register("nether_bismuth_ore", () -> new DropExperienceBlock(BlockBehaviour.Properties.of().requiresCorrectToolForDrops().mapColor(MapColor.NETHER).strength(3.0f, 3.0f).sound(EtceteraSoundEvents.NETHER_BISMUTH_ORE), UniformInt.of(1, 8)));
    public static final RegistryObject<Block> IRIDESCENT_GLASS = BLOCKS.register("iridescent_glass", () -> new GlassBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).friction(1.0F)));
    public static final RegistryObject<Block> IRIDESCENT_GLASS_PANE = BLOCKS.register("iridescent_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_STAINED_GLASS_PANE).friction(1.0F)));
    public static final RegistryObject<Block> IRIDESCENT_TERRACOTTA = BLOCKS.register("iridescent_terracotta", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA)));
    public static final RegistryObject<Block> IRIDESCENT_GLAZED_TERRACOTTA = BLOCKS.register("iridescent_glazed_terracotta", () -> new GlazedTerracottaBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA)));
    public static final RegistryObject<Block> IRIDESCENT_CONCRETE = BLOCKS.register("iridescent_concrete", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_CONCRETE)));
    public static final RegistryObject<Block> IRIDESCENT_WOOL = BLOCKS.register("iridescent_wool", () -> new Block(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_WOOL)));
    public static final RegistryObject<Block> IRIDESCENT_LANTERN = BLOCKS.register("iridescent_lantern", () -> new Block(BlockBehaviour.Properties.copy(Blocks.SEA_LANTERN)));

    public static final RegistryObject<Block> DRUM = BLOCKS.register("drum", () -> new DrumBlock(BlockBehaviour.Properties.copy(Blocks.NOTE_BLOCK)));

    public static final RegistryObject<Block> DICE = BLOCKS.register("dice", () -> new DiceBlock(BlockBehaviour.Properties.copy(Blocks.QUARTZ_BLOCK)));

    public static final RegistryObject<Block> FRAME = BLOCKS.register("frame", () -> new FrameBlock(BlockBehaviour.Properties.of().sound(SoundType.SCAFFOLDING).mapColor(MapColor.SAND).noOcclusion().isSuffocating(EtceteraBlocks::never).isViewBlocking(EtceteraBlocks::never).noOcclusion()));

    public static final RegistryObject<Block> PRICKLY_CAN = BLOCKS.register("prickly_can", () -> new PricklyCanBlock(BlockBehaviour.Properties.copy(Blocks.CACTUS).strength(1f, 4f)));

    public static final RegistryObject<Block> BOUQUET = BLOCKS.register("bouquet", () -> new BouquetBlock(BlockBehaviour.Properties.of().sound(SoundType.GRASS).mapColor(MapColor.GRASS).noCollission().instabreak()));
    public static final RegistryObject<Block> POTTED_BOUQUET = BLOCKS.register("potted_bouquet", () -> new FlowerPotBlock(BOUQUET.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));
    public static final RegistryObject<Block> TERRACOTTA_VASE = BLOCKS.register("terracotta_vase", () -> new TerracottaVaseBlock(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA).sound(EtceteraSoundEvents.TERRACOTTA_VASE)));

    public static final RegistryObject<Block> ITEM_STAND = BLOCKS.register("item_stand", () -> new ItemStandBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instabreak().noOcclusion()));
    public static final RegistryObject<Block> GLOW_ITEM_STAND = BLOCKS.register("glow_item_stand", () -> new ItemStandBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instabreak().noOcclusion()));

    public static final RegistryObject<Block> SQUID_LAMP = BLOCKS.register("squid_lamp", () -> new SquidLampBlock(BlockBehaviour.Properties.of().noCollission().mapColor(MapColor.WARPED_NYLIUM).instabreak().sound(EtceteraSoundEvents.SQUID_LAMP).lightLevel(state -> state.getValue(SquidLampBlock.WATERLOGGED) ? 15 : 7)));
    public static final RegistryObject<Block> WALL_SQUID_LAMP = BLOCKS.register("wall_squid_lamp", () -> new WallSquidLampBlock(BlockBehaviour.Properties.copy(EtceteraBlocks.SQUID_LAMP.get()).dropsLike(SQUID_LAMP.get())));

    public static final RegistryObject<Block> CRUMBLING_STONE = BLOCKS.register("crumbling_stone", () -> new CrumblingStoneBlock(BlockBehaviour.Properties.copy(Blocks.STONE).sound(EtceteraSoundEvents.CRUMBLING_STONE).strength(0.5f, 3f)));
    public static final RegistryObject<Block> WAXED_CRUMBLING_STONE = BLOCKS.register("waxed_crumbling_stone", () -> new AbstractCrumblingStoneBlock(BlockBehaviour.Properties.copy(EtceteraBlocks.CRUMBLING_STONE.get())));

    public static final RegistryObject<Block> LEVELED_STONE = BLOCKS.register("leveled_stone", () -> new Block(BlockBehaviour.Properties.copy(Blocks.STONE).strength(1f, 4f)));
    public static final RegistryObject<Block> LEVELED_STONE_STAIRS = BLOCKS.register("leveled_stone_stairs", () -> new StairBlock(LEVELED_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(EtceteraBlocks.LEVELED_STONE.get())));
    public static final RegistryObject<Block> LEVELED_STONE_SLAB = BLOCKS.register("leveled_stone_slab", () -> new SlabBlock(BlockBehaviour.Properties.copy(EtceteraBlocks.LEVELED_STONE.get())));

    public static final RegistryObject<Block> LIGHT_BULB = BLOCKS.register("light_bulb", () -> new LightBulbBlock(BlockBehaviour.Properties.copy(Blocks.GLASS).noOcclusion().pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<Block> TINTED_LIGHT_BULB = BLOCKS.register("tinted_light_bulb", () -> new TintedLightBulbBlock(BlockBehaviour.Properties.copy(Blocks.TINTED_GLASS).noOcclusion().pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<Block> COTTON = BLOCKS.register("cotton", () -> new CottonBlock(BlockBehaviour.Properties.of().mapColor(state -> state.getValue(CottonBlock.AGE) == 3 ? MapColor.COLOR_GREEN : MapColor.PODZOL).noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    private static boolean never(BlockState state, BlockGetter world, BlockPos pos) { return false; }
}
