package com.ninni.etcetera.item;

import com.ninni.etcetera.block.EtceteraBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

@SuppressWarnings("unused")
public class EtceteraItems {

    public static final Item RAW_BISMUTH_BLOCK = register("raw_bismuth_block", new BlockItem(EtceteraBlocks.RAW_BISMUTH_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item BISMUTH_BLOCK = register("bismuth_block", new BlockItem(EtceteraBlocks.BISMUTH_BLOCK, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item BISMUTH_BARS = register("bismuth_bars", new BlockItem(EtceteraBlocks.BISMUTH_BARS, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
    public static final Item NETHER_BISMUTH_ORE = register("nether_bismuth_ore", new BlockItem(EtceteraBlocks.NETHER_BISMUTH_ORE, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item RAW_BISMUTH = register("raw_bismuth", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item BISMUTH_INGOT = register("bismuth_ingot", new Item(new FabricItemSettings().group(ItemGroup.MISC)));
    public static final Item SEXTANT = register("sextant", new SextantItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1)));
    public static final Item MAGNIFYING_GLASS = register("magnifying_glass", new MagnifyingGlassItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1)));
    public static final Item WRENCH = register("wrench", new WrenchItem(new FabricItemSettings().group(ItemGroup.TOOLS).maxCount(1).maxDamage(145)));
    public static final Item IRIDESCENT_GLASS = register("iridescent_glass", new BlockItem(EtceteraBlocks.IRIDESCENT_GLASS, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item IRIDESCENT_TERRACOTTA = register("iridescent_terracotta", new BlockItem(EtceteraBlocks.IRIDESCENT_TERRACOTTA, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item IRIDESCENT_CONCRETE = register("iridescent_concrete", new BlockItem(EtceteraBlocks.IRIDESCENT_CONCRETE, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item IRIDESCENT_LANTERN = register("iridescent_lantern", new BlockItem(EtceteraBlocks.IRIDESCENT_LANTERN, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));

    public static final Item COMPACTED_DRIPSTONE = register("compacted_dripstone", new BlockItem(EtceteraBlocks.COMPACTED_DRIPSTONE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));

    public static final Item DRUM = register("drum", new BlockItem(EtceteraBlocks.DRUM, new FabricItemSettings().group(ItemGroup.REDSTONE)));

    public static final Item FRAME = register("frame", new BlockItem(EtceteraBlocks.FRAME, new FabricItemSettings().group(ItemGroup.DECORATIONS)));

    public static final Item BOUQUET = register("bouquet", new BlockItem(EtceteraBlocks.BOUQUET, new FabricItemSettings().group(ItemGroup.DECORATIONS).maxCount(16)));
    public static final Item TERRACOTTA_VASE = register("terracotta_vase", new BlockItem(EtceteraBlocks.TERRACOTTA_VASE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));

    public static final Item SQUID_LAMP = register("squid_lamp", new WallStandingBlockItem(EtceteraBlocks.SQUID_LAMP, EtceteraBlocks.WALL_SQUID_LAMP, new Item.Settings().group(ItemGroup.DECORATIONS)));
    public static final Item TIDAL_HELMET = register("tidal_helmet", new ArmorItem(EtceteraArmorMaterials.TIDAL, EquipmentSlot.HEAD, new Item.Settings().group(ItemGroup.COMBAT)));

    public static final Item CRUMBLING_STONE = register("crumbling_stone", new BlockItem(EtceteraBlocks.CRUMBLING_STONE, new FabricItemSettings().group(ItemGroup.REDSTONE)));
    public static final Item WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", new BlockItem(EtceteraBlocks.WAXED_CRUMBLING_STONE, new FabricItemSettings().group(ItemGroup.REDSTONE)));

    public static final Item LEVELED_STONE = register("leveled_stone", new BlockItem(EtceteraBlocks.LEVELED_STONE, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item LEVELED_STONE_STAIRS = register("leveled_stone_stairs", new BlockItem(EtceteraBlocks.LEVELED_STONE_STAIRS, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item LEVELED_STONE_SLAB = register("leveled_stone_slab", new BlockItem(EtceteraBlocks.LEVELED_STONE_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item SMOOTH_STONE_BRICKS = register("smooth_stone_bricks", new BlockItem(EtceteraBlocks.SMOOTH_STONE_BRICKS, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item SMOOTH_STONE_BRICK_WALL = register("smooth_stone_brick_wall", new BlockItem(EtceteraBlocks.SMOOTH_STONE_BRICK_WALL, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item SMOOTH_STONE_BRICK_STAIRS = register("smooth_stone_brick_stairs", new BlockItem(EtceteraBlocks.SMOOTH_STONE_BRICK_STAIRS, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));
    public static final Item SMOOTH_STONE_BRICK_SLAB = register("smooth_stone_brick_slab", new BlockItem(EtceteraBlocks.SMOOTH_STONE_BRICK_SLAB, new FabricItemSettings().group(ItemGroup.BUILDING_BLOCKS)));


    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, id), item);
    }

    static { CompostingChanceRegistry.INSTANCE.add(BOUQUET, 0.85f); }
}
