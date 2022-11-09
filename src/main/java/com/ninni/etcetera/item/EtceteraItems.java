package com.ninni.etcetera.item;

import com.ninni.etcetera.EtceteraTags;
import com.ninni.etcetera.block.EtceteraBlocks;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallStandingBlockItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

@SuppressWarnings("unused")
public class EtceteraItems {

    public static final Item ETCETERA = register("etcetera", new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC).fireproof()));

    public static final Item RAW_BISMUTH_BLOCK = register("raw_bismuth_block", new BlockItem(EtceteraBlocks.RAW_BISMUTH_BLOCK, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BISMUTH_BLOCK = register("bismuth_block", new BlockItem(EtceteraBlocks.BISMUTH_BLOCK, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BISMUTH_BARS = register("bismuth_bars", new BlockItem(EtceteraBlocks.BISMUTH_BARS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item NETHER_BISMUTH_ORE = register("nether_bismuth_ore", new BlockItem(EtceteraBlocks.NETHER_BISMUTH_ORE, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item RAW_BISMUTH = register("raw_bismuth", new Item(new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BISMUTH_INGOT = register("bismuth_ingot", new Item(new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item IRIDESCENT_GLASS = register("iridescent_glass", new BlockItem(EtceteraBlocks.IRIDESCENT_GLASS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item IRIDESCENT_TERRACOTTA = register("iridescent_terracotta", new BlockItem(EtceteraBlocks.IRIDESCENT_TERRACOTTA, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item IRIDESCENT_CONCRETE = register("iridescent_concrete", new BlockItem(EtceteraBlocks.IRIDESCENT_CONCRETE, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item IRIDESCENT_PACKED_SILT = register("iridescent_packed_silt", new BlockItem(EtceteraBlocks.IRIDESCENT_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item IRIDESCENT_LANTERN = register("iridescent_lantern", new BlockItem(EtceteraBlocks.IRIDESCENT_LANTERN, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item CHISEL = register("chisel", new TransformingItem(EtceteraToolMaterials.BISMUTH, CHISELLING_MANAGER::getMap, new FabricItemSettings().group(ITEM_GROUP).maxCount(1).maxDamage(145), EtceteraSoundEvents.ITEM_CHISEL_USE, EtceteraTags.CHISELLABLE));
    public static final Item WRENCH = register("wrench", new WrenchItem(EtceteraToolMaterials.BISMUTH, new FabricItemSettings().group(ITEM_GROUP).maxCount(1).maxDamage(145)));
    public static final Item HAMMER = register("hammer", new HammerItem(EtceteraToolMaterials.BISMUTH, (int)7.5, -3.6F, HAMMERING_MANAGER::getMap, new FabricItemSettings().group(ITEM_GROUP).maxCount(1).maxDamage(80), EtceteraSoundEvents.ITEM_HAMMER_USE, EtceteraTags.HAMMERABLE));
    public static final Item HANDBELL = register("handbell", new HandbellItem(new FabricItemSettings().group(ITEM_GROUP).maxCount(1)));

    public static final Item GRAVEL_BRICKS = register("gravel_bricks", new BlockItem(EtceteraBlocks.GRAVEL_BRICKS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GRAVEL_BRICK_STAIRS = register("gravel_brick_stairs", new BlockItem(EtceteraBlocks.GRAVEL_BRICK_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GRAVEL_BRICK_SLAB = register("gravel_brick_slab", new BlockItem(EtceteraBlocks.GRAVEL_BRICK_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GRAVEL_BRICK_WALL = register("gravel_brick_wall", new BlockItem(EtceteraBlocks.GRAVEL_BRICK_WALL, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item QUARTZ_COLUMN = register("quartz_column", new BlockItem(EtceteraBlocks.QUARTZ_COLUMN, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item STONE_COLUMN = register("stone_column", new BlockItem(EtceteraBlocks.STONE_COLUMN, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item DEEPSLATE_COLUMN = register("deepslate_column", new BlockItem(EtceteraBlocks.DEEPSLATE_COLUMN, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLACKSTONE_COLUMN = register("blackstone_column", new BlockItem(EtceteraBlocks.BLACKSTONE_COLUMN, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item COMPACTED_DRIPSTONE = register("compacted_dripstone", new BlockItem(EtceteraBlocks.COMPACTED_DRIPSTONE, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item DRUM = register("drum", new BlockItem(EtceteraBlocks.DRUM, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item DICE = register("dice", new BlockItem(EtceteraBlocks.DICE, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item FRAME = register("frame", new BlockItem(EtceteraBlocks.FRAME, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item BOUQUET = register("bouquet", new BlockItem(EtceteraBlocks.BOUQUET, new FabricItemSettings().group(ITEM_GROUP).maxCount(16)));
    public static final Item TERRACOTTA_VASE = register("terracotta_vase", new BlockItem(EtceteraBlocks.TERRACOTTA_VASE, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item SILT = register("silt", new BlockItem(EtceteraBlocks.SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SILT_BALL = register("silt_ball", new Item(new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SILT_BRICK = register("silt_brick", new Item(new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SILT_BRICKS = register("silt_bricks", new BlockItem(EtceteraBlocks.SILT_BRICKS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SILT_BRICK_STAIRS = register("silt_brick_stairs", new BlockItem(EtceteraBlocks.SILT_BRICK_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SILT_BRICK_SLAB = register("silt_brick_slab", new BlockItem(EtceteraBlocks.SILT_BRICK_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SILT_BRICK_WALL = register("silt_brick_wall", new BlockItem(EtceteraBlocks.SILT_BRICK_WALL, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item SILT_POT = register("silt_pot", new BlockItem(EtceteraBlocks.SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item WHITE_SILT_POT = register("white_silt_pot", new BlockItem(EtceteraBlocks.WHITE_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item ORANGE_SILT_POT = register("orange_silt_pot", new BlockItem(EtceteraBlocks.ORANGE_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item MAGENTA_SILT_POT = register("magenta_silt_pot", new BlockItem(EtceteraBlocks.MAGENTA_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_BLUE_SILT_POT = register("light_blue_silt_pot", new BlockItem(EtceteraBlocks.LIGHT_BLUE_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item YELLOW_SILT_POT = register("yellow_silt_pot", new BlockItem(EtceteraBlocks.YELLOW_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIME_SILT_POT = register("lime_silt_pot", new BlockItem(EtceteraBlocks.LIME_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PINK_SILT_POT = register("pink_silt_pot", new BlockItem(EtceteraBlocks.PINK_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GRAY_SILT_POT = register("gray_silt_pot", new BlockItem(EtceteraBlocks.GRAY_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_GRAY_SILT_POT = register("light_gray_silt_pot", new BlockItem(EtceteraBlocks.LIGHT_GRAY_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item CYAN_SILT_POT = register("cyan_silt_pot", new BlockItem(EtceteraBlocks.CYAN_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PURPLE_SILT_POT = register("purple_silt_pot", new BlockItem(EtceteraBlocks.PURPLE_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLUE_SILT_POT = register("blue_silt_pot", new BlockItem(EtceteraBlocks.BLUE_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BROWN_SILT_POT =  register("brown_silt_pot", new BlockItem(EtceteraBlocks.BROWN_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GREEN_SILT_POT =  register("green_silt_pot", new BlockItem(EtceteraBlocks.GREEN_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item RED_SILT_POT =  register("red_silt_pot", new BlockItem(EtceteraBlocks.RED_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLACK_SILT_POT =  register("black_silt_pot", new BlockItem(EtceteraBlocks.BLACK_SILT_POT, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item PACKED_SILT = register("packed_silt", new BlockItem(EtceteraBlocks.PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item WHITE_PACKED_SILT = register("white_packed_silt", new BlockItem(EtceteraBlocks.WHITE_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item ORANGE_PACKED_SILT = register("orange_packed_silt", new BlockItem(EtceteraBlocks.ORANGE_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item MAGENTA_PACKED_SILT = register("magenta_packed_silt", new BlockItem(EtceteraBlocks.MAGENTA_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_BLUE_PACKED_SILT = register("light_blue_packed_silt", new BlockItem(EtceteraBlocks.LIGHT_BLUE_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item YELLOW_PACKED_SILT = register("yellow_packed_silt", new BlockItem(EtceteraBlocks.YELLOW_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIME_PACKED_SILT = register("lime_packed_silt", new BlockItem(EtceteraBlocks.LIME_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PINK_PACKED_SILT = register("pink_packed_silt", new BlockItem(EtceteraBlocks.PINK_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GRAY_PACKED_SILT = register("gray_packed_silt", new BlockItem(EtceteraBlocks.GRAY_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_GRAY_PACKED_SILT = register("light_gray_packed_silt", new BlockItem(EtceteraBlocks.LIGHT_GRAY_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item CYAN_PACKED_SILT = register("cyan_packed_silt", new BlockItem(EtceteraBlocks.CYAN_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PURPLE_PACKED_SILT = register("purple_packed_silt", new BlockItem(EtceteraBlocks.PURPLE_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLUE_PACKED_SILT = register("blue_packed_silt", new BlockItem(EtceteraBlocks.BLUE_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BROWN_PACKED_SILT =  register("brown_packed_silt", new BlockItem(EtceteraBlocks.BROWN_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GREEN_PACKED_SILT =  register("green_packed_silt", new BlockItem(EtceteraBlocks.GREEN_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item RED_PACKED_SILT =  register("red_packed_silt", new BlockItem(EtceteraBlocks.RED_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLACK_PACKED_SILT =  register("black_packed_silt", new BlockItem(EtceteraBlocks.BLACK_PACKED_SILT, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item SILT_SHINGLES = register("silt_shingles", new BlockItem(EtceteraBlocks.SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item WHITE_SILT_SHINGLES = register("white_silt_shingles", new BlockItem(EtceteraBlocks.WHITE_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item ORANGE_SILT_SHINGLES = register("orange_silt_shingles", new BlockItem(EtceteraBlocks.ORANGE_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item MAGENTA_SILT_SHINGLES = register("magenta_silt_shingles", new BlockItem(EtceteraBlocks.MAGENTA_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_BLUE_SILT_SHINGLES = register("light_blue_silt_shingles", new BlockItem(EtceteraBlocks.LIGHT_BLUE_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item YELLOW_SILT_SHINGLES = register("yellow_silt_shingles", new BlockItem(EtceteraBlocks.YELLOW_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIME_SILT_SHINGLES = register("lime_silt_shingles", new BlockItem(EtceteraBlocks.LIME_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PINK_SILT_SHINGLES = register("pink_silt_shingles", new BlockItem(EtceteraBlocks.PINK_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GRAY_SILT_SHINGLES = register("gray_silt_shingles", new BlockItem(EtceteraBlocks.GRAY_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_GRAY_SILT_SHINGLES = register("light_gray_silt_shingles", new BlockItem(EtceteraBlocks.LIGHT_GRAY_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item CYAN_SILT_SHINGLES = register("cyan_silt_shingles", new BlockItem(EtceteraBlocks.CYAN_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PURPLE_SILT_SHINGLES = register("purple_silt_shingles", new BlockItem(EtceteraBlocks.PURPLE_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLUE_SILT_SHINGLES = register("blue_silt_shingles", new BlockItem(EtceteraBlocks.BLUE_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BROWN_SILT_SHINGLES =  register("brown_silt_shingles", new BlockItem(EtceteraBlocks.BROWN_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GREEN_SILT_SHINGLES =  register("green_silt_shingles", new BlockItem(EtceteraBlocks.GREEN_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item RED_SILT_SHINGLES =  register("red_silt_shingles", new BlockItem(EtceteraBlocks.RED_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLACK_SILT_SHINGLES =  register("black_silt_shingles", new BlockItem(EtceteraBlocks.BLACK_SILT_SHINGLES, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item SILT_SHINGLE_STAIRS = register("silt_shingle_stairs", new BlockItem(EtceteraBlocks.SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item WHITE_SILT_SHINGLE_STAIRS = register("white_silt_shingle_stairs", new BlockItem(EtceteraBlocks.WHITE_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item ORANGE_SILT_SHINGLE_STAIRS = register("orange_silt_shingle_stairs", new BlockItem(EtceteraBlocks.ORANGE_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item MAGENTA_SILT_SHINGLE_STAIRS = register("magenta_silt_shingle_stairs", new BlockItem(EtceteraBlocks.MAGENTA_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_BLUE_SILT_SHINGLE_STAIRS = register("light_blue_silt_shingle_stairs", new BlockItem(EtceteraBlocks.LIGHT_BLUE_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item YELLOW_SILT_SHINGLE_STAIRS = register("yellow_silt_shingle_stairs", new BlockItem(EtceteraBlocks.YELLOW_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIME_SILT_SHINGLE_STAIRS = register("lime_silt_shingle_stairs", new BlockItem(EtceteraBlocks.LIME_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PINK_SILT_SHINGLE_STAIRS = register("pink_silt_shingle_stairs", new BlockItem(EtceteraBlocks.PINK_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GRAY_SILT_SHINGLE_STAIRS = register("gray_silt_shingle_stairs", new BlockItem(EtceteraBlocks.GRAY_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_GRAY_SILT_SHINGLE_STAIRS = register("light_gray_silt_shingle_stairs", new BlockItem(EtceteraBlocks.LIGHT_GRAY_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item CYAN_SILT_SHINGLE_STAIRS = register("cyan_silt_shingle_stairs", new BlockItem(EtceteraBlocks.CYAN_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PURPLE_SILT_SHINGLE_STAIRS = register("purple_silt_shingle_stairs", new BlockItem(EtceteraBlocks.PURPLE_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLUE_SILT_SHINGLE_STAIRS = register("blue_silt_shingle_stairs", new BlockItem(EtceteraBlocks.BLUE_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BROWN_SILT_SHINGLE_STAIRS =  register("brown_silt_shingle_stairs", new BlockItem(EtceteraBlocks.BROWN_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GREEN_SILT_SHINGLE_STAIRS =  register("green_silt_shingle_stairs", new BlockItem(EtceteraBlocks.GREEN_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item RED_SILT_SHINGLE_STAIRS =  register("red_silt_shingle_stairs", new BlockItem(EtceteraBlocks.RED_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLACK_SILT_SHINGLE_STAIRS =  register("black_silt_shingle_stairs", new BlockItem(EtceteraBlocks.BLACK_SILT_SHINGLE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item SILT_SHINGLE_SLAB = register("silt_shingle_slab", new BlockItem(EtceteraBlocks.SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item WHITE_SILT_SHINGLE_SLAB = register("white_silt_shingle_slab", new BlockItem(EtceteraBlocks.WHITE_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item ORANGE_SILT_SHINGLE_SLAB = register("orange_silt_shingle_slab", new BlockItem(EtceteraBlocks.ORANGE_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item MAGENTA_SILT_SHINGLE_SLAB = register("magenta_silt_shingle_slab", new BlockItem(EtceteraBlocks.MAGENTA_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_BLUE_SILT_SHINGLE_SLAB = register("light_blue_silt_shingle_slab", new BlockItem(EtceteraBlocks.LIGHT_BLUE_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item YELLOW_SILT_SHINGLE_SLAB = register("yellow_silt_shingle_slab", new BlockItem(EtceteraBlocks.YELLOW_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIME_SILT_SHINGLE_SLAB = register("lime_silt_shingle_slab", new BlockItem(EtceteraBlocks.LIME_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PINK_SILT_SHINGLE_SLAB = register("pink_silt_shingle_slab", new BlockItem(EtceteraBlocks.PINK_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GRAY_SILT_SHINGLE_SLAB = register("gray_silt_shingle_slab", new BlockItem(EtceteraBlocks.GRAY_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_GRAY_SILT_SHINGLE_SLAB = register("light_gray_silt_shingle_slab", new BlockItem(EtceteraBlocks.LIGHT_GRAY_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item CYAN_SILT_SHINGLE_SLAB = register("cyan_silt_shingle_slab", new BlockItem(EtceteraBlocks.CYAN_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PURPLE_SILT_SHINGLE_SLAB = register("purple_silt_shingle_slab", new BlockItem(EtceteraBlocks.PURPLE_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLUE_SILT_SHINGLE_SLAB = register("blue_silt_shingle_slab", new BlockItem(EtceteraBlocks.BLUE_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BROWN_SILT_SHINGLE_SLAB =  register("brown_silt_shingle_slab", new BlockItem(EtceteraBlocks.BROWN_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GREEN_SILT_SHINGLE_SLAB =  register("green_silt_shingle_slab", new BlockItem(EtceteraBlocks.GREEN_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item RED_SILT_SHINGLE_SLAB =  register("red_silt_shingle_slab", new BlockItem(EtceteraBlocks.RED_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLACK_SILT_SHINGLE_SLAB =  register("black_silt_shingle_slab", new BlockItem(EtceteraBlocks.BLACK_SILT_SHINGLE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item SILT_SHINGLE_WALL = register("silt_shingle_wall", new BlockItem(EtceteraBlocks.SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item WHITE_SILT_SHINGLE_WALL = register("white_silt_shingle_wall", new BlockItem(EtceteraBlocks.WHITE_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item ORANGE_SILT_SHINGLE_WALL = register("orange_silt_shingle_wall", new BlockItem(EtceteraBlocks.ORANGE_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item MAGENTA_SILT_SHINGLE_WALL = register("magenta_silt_shingle_wall", new BlockItem(EtceteraBlocks.MAGENTA_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_BLUE_SILT_SHINGLE_WALL = register("light_blue_silt_shingle_wall", new BlockItem(EtceteraBlocks.LIGHT_BLUE_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item YELLOW_SILT_SHINGLE_WALL = register("yellow_silt_shingle_wall", new BlockItem(EtceteraBlocks.YELLOW_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIME_SILT_SHINGLE_WALL = register("lime_silt_shingle_wall", new BlockItem(EtceteraBlocks.LIME_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PINK_SILT_SHINGLE_WALL = register("pink_silt_shingle_wall", new BlockItem(EtceteraBlocks.PINK_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GRAY_SILT_SHINGLE_WALL = register("gray_silt_shingle_wall", new BlockItem(EtceteraBlocks.GRAY_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LIGHT_GRAY_SILT_SHINGLE_WALL = register("light_gray_silt_shingle_wall", new BlockItem(EtceteraBlocks.LIGHT_GRAY_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item CYAN_SILT_SHINGLE_WALL = register("cyan_silt_shingle_wall", new BlockItem(EtceteraBlocks.CYAN_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item PURPLE_SILT_SHINGLE_WALL = register("purple_silt_shingle_wall", new BlockItem(EtceteraBlocks.PURPLE_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLUE_SILT_SHINGLE_WALL = register("blue_silt_shingle_wall", new BlockItem(EtceteraBlocks.BLUE_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BROWN_SILT_SHINGLE_WALL =  register("brown_silt_shingle_wall", new BlockItem(EtceteraBlocks.BROWN_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GREEN_SILT_SHINGLE_WALL =  register("green_silt_shingle_wall", new BlockItem(EtceteraBlocks.GREEN_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item RED_SILT_SHINGLE_WALL =  register("red_silt_shingle_wall", new BlockItem(EtceteraBlocks.RED_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BLACK_SILT_SHINGLE_WALL =  register("black_silt_shingle_wall", new BlockItem(EtceteraBlocks.BLACK_SILT_SHINGLE_WALL, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item SQUID_LAMP = register("squid_lamp", new WallStandingBlockItem(EtceteraBlocks.SQUID_LAMP, EtceteraBlocks.WALL_SQUID_LAMP, new Item.Settings().group(ITEM_GROUP)));
    public static final Item TIDAL_HELMET = register("tidal_helmet", new ArmorItem(EtceteraArmorMaterials.TIDAL, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.UNCOMMON).group(ITEM_GROUP)));

    public static final Item CRUMBLING_STONE = register("crumbling_stone", new BlockItem(EtceteraBlocks.CRUMBLING_STONE, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", new BlockItem(EtceteraBlocks.WAXED_CRUMBLING_STONE, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item LEVELED_STONE = register("leveled_stone", new BlockItem(EtceteraBlocks.LEVELED_STONE, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LEVELED_STONE_STAIRS = register("leveled_stone_stairs", new BlockItem(EtceteraBlocks.LEVELED_STONE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LEVELED_STONE_SLAB = register("leveled_stone_slab", new BlockItem(EtceteraBlocks.LEVELED_STONE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SMOOTH_STONE_BRICKS = register("smooth_stone_bricks", new BlockItem(EtceteraBlocks.SMOOTH_STONE_BRICKS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SMOOTH_STONE_BRICK_STAIRS = register("smooth_stone_brick_stairs", new BlockItem(EtceteraBlocks.SMOOTH_STONE_BRICK_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SMOOTH_STONE_BRICK_SLAB = register("smooth_stone_brick_slab", new BlockItem(EtceteraBlocks.SMOOTH_STONE_BRICK_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SMOOTH_STONE_BRICK_WALL = register("smooth_stone_brick_wall", new BlockItem(EtceteraBlocks.SMOOTH_STONE_BRICK_WALL, new FabricItemSettings().group(ITEM_GROUP)));


    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, id), item);
    }

    static { CompostingChanceRegistry.INSTANCE.add(BOUQUET, 0.85f); }
}
