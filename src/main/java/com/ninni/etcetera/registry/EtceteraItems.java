package com.ninni.etcetera.registry;

import com.ninni.etcetera.item.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.Direction;

import static com.ninni.etcetera.Etcetera.MOD_ID;
import static com.ninni.etcetera.registry.EtceteraVanillaIntegration.CHISELLING_MANAGER;
import static com.ninni.etcetera.registry.EtceteraVanillaIntegration.HAMMERING_MANAGER;

@SuppressWarnings("unused")
public class EtceteraItems {

    public static final Item ETCETERA = register("etcetera", new Item(new FabricItemSettings().maxCount(1).rarity(Rarity.EPIC).fireproof()));

    public static final Item RAW_BISMUTH_BLOCK = register("raw_bismuth_block", new BlockItem(EtceteraBlocks.RAW_BISMUTH_BLOCK, new FabricItemSettings()));
    public static final Item BISMUTH_BLOCK = register("bismuth_block", new BlockItem(EtceteraBlocks.BISMUTH_BLOCK, new FabricItemSettings()));
    public static final Item BISMUTH_BARS = register("bismuth_bars", new BlockItem(EtceteraBlocks.BISMUTH_BARS, new FabricItemSettings()));
    public static final Item NETHER_BISMUTH_ORE = register("nether_bismuth_ore", new BlockItem(EtceteraBlocks.NETHER_BISMUTH_ORE, new FabricItemSettings()));
    public static final Item RAW_BISMUTH = register("raw_bismuth", new Item(new FabricItemSettings()));
    public static final Item BISMUTH_INGOT = register("bismuth_ingot", new Item(new FabricItemSettings()));
    public static final Item IRIDESCENT_GLASS = register("iridescent_glass", new BlockItem(EtceteraBlocks.IRIDESCENT_GLASS, new FabricItemSettings()));
    public static final Item IRIDESCENT_TERRACOTTA = register("iridescent_terracotta", new BlockItem(EtceteraBlocks.IRIDESCENT_TERRACOTTA, new FabricItemSettings()));
    public static final Item IRIDESCENT_CONCRETE = register("iridescent_concrete", new BlockItem(EtceteraBlocks.IRIDESCENT_CONCRETE, new FabricItemSettings()));
    public static final Item IRIDESCENT_LANTERN = register("iridescent_lantern", new BlockItem(EtceteraBlocks.IRIDESCENT_LANTERN, new FabricItemSettings()));

    public static final Item CHISEL = register("chisel", new TransformingItem(EtceteraToolMaterials.BISMUTH, CHISELLING_MANAGER::getMap, new FabricItemSettings().maxCount(1).maxDamage(145), EtceteraSoundEvents.ITEM_CHISEL_USE, EtceteraTags.CHISELLABLE));
    public static final Item WRENCH = register("wrench", new WrenchItem(EtceteraToolMaterials.BISMUTH, new FabricItemSettings().maxCount(1).maxDamage(145)));
    public static final Item HAMMER = register("hammer", new HammerItem(EtceteraToolMaterials.BISMUTH, (int)7.5, -3.6F, HAMMERING_MANAGER::getMap, new FabricItemSettings().maxCount(1).maxDamage(80), EtceteraSoundEvents.ITEM_HAMMER_USE, EtceteraTags.HAMMERABLE));
    public static final Item HANDBELL = register("handbell", new HandbellItem(new FabricItemSettings().maxCount(1)));

    public static final Item DRUM = register("drum", new BlockItem(EtceteraBlocks.DRUM, new FabricItemSettings()));

    public static final Item DICE = register("dice", new BlockItem(EtceteraBlocks.DICE, new FabricItemSettings()));

    public static final Item FRAME = register("frame", new BlockItem(EtceteraBlocks.FRAME, new FabricItemSettings()));

    public static final Item PRICKLY_CAN = register("prickly_can", new BlockItem(EtceteraBlocks.PRICKLY_CAN, new FabricItemSettings()));

    public static final Item BOUQUET = register("bouquet", new BlockItem(EtceteraBlocks.BOUQUET, new FabricItemSettings().maxCount(16)));
    public static final Item TERRACOTTA_VASE = register("terracotta_vase", new BlockItem(EtceteraBlocks.TERRACOTTA_VASE, new FabricItemSettings()));

    public static final Item ITEM_STAND = register("item_stand", new BlockItem(EtceteraBlocks.ITEM_STAND, new FabricItemSettings()));

    public static final Item SQUID_LAMP = register("squid_lamp", new VerticallyAttachableBlockItem(EtceteraBlocks.SQUID_LAMP, EtceteraBlocks.WALL_SQUID_LAMP, new FabricItemSettings(), Direction.DOWN));
    public static final Item TIDAL_HELMET = register("tidal_helmet", new ArmorItem(EtceteraArmorMaterials.TIDAL, ArmorItem.Type.HELMET, new FabricItemSettings().rarity(Rarity.RARE)));
    public static final Item TURTLE_RAFT = register("turtle_raft", new TurtleRaftItem(new FabricItemSettings().maxCount(1)));

    public static final Item CRUMBLING_STONE = register("crumbling_stone", new BlockItem(EtceteraBlocks.CRUMBLING_STONE, new FabricItemSettings()));
    public static final Item WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", new BlockItem(EtceteraBlocks.WAXED_CRUMBLING_STONE, new FabricItemSettings()));
    public static final Item LEVELED_STONE = register("leveled_stone", new BlockItem(EtceteraBlocks.LEVELED_STONE, new FabricItemSettings()));
    public static final Item LEVELED_STONE_STAIRS = register("leveled_stone_stairs", new BlockItem(EtceteraBlocks.LEVELED_STONE_STAIRS, new FabricItemSettings()));
    public static final Item LEVELED_STONE_SLAB = register("leveled_stone_slab", new BlockItem(EtceteraBlocks.LEVELED_STONE_SLAB, new FabricItemSettings()));

    public static final Item LIGHT_BULB = register("light_bulb", new BlockItem(EtceteraBlocks.LIGHT_BULB, new FabricItemSettings()));
    public static final Item TINTED_LIGHT_BULB = register("tinted_light_bulb", new BlockItem(EtceteraBlocks.TINTED_LIGHT_BULB, new FabricItemSettings()));

    public static final Item CHAPPLE_SPAWN_EGG = register("chapple_spawn_egg", new SpawnEggItem(EtceteraEntityType.CHAPPLE, 0xE41826, 0x548630, new FabricItemSettings().maxCount(64)));
    public static final Item EGGPLE = register("eggple", new EggpleItem(false, new FabricItemSettings().maxCount(16)));
    public static final Item GOLDEN_EGGPLE = register("golden_eggple", new EggpleItem(true, new FabricItemSettings().rarity(Rarity.RARE).maxCount(16)));

    public static final Item COTTON_SEEDS = register("cotton_seeds", new AliasedBlockItem(EtceteraBlocks.COTTON, new FabricItemSettings()));
    public static final Item COTTON_FLOWER = register("cotton_flower", new Item(new FabricItemSettings()));
    public static final Item WHITE_SWEATER = register("white_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item LIGHT_GRAY_SWEATER = register("light_gray_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item GRAY_SWEATER = register("gray_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item BLACK_SWEATER = register("black_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item BROWN_SWEATER = register("brown_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item RED_SWEATER = register("red_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item ORANGE_SWEATER = register("orange_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item YELLOW_SWEATER = register("yellow_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item LIME_SWEATER = register("lime_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item GREEN_SWEATER = register("green_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item CYAN_SWEATER = register("cyan_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item LIGHT_BLUE_SWEATER = register("light_blue_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item BLUE_SWEATER = register("blue_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item PURPLE_SWEATER = register("purple_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item MAGENTA_SWEATER = register("magenta_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item PINK_SWEATER = register("pink_sweater", new SweaterItem(new FabricItemSettings().maxCount(1), true));
    public static final Item TRADER_ROBE = register("trader_robe", new SweaterItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON), true));
    public static final Item WHITE_HAT = register("white_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item LIGHT_GRAY_HAT = register("light_gray_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item GRAY_HAT = register("gray_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item BLACK_HAT = register("black_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item BROWN_HAT = register("brown_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item RED_HAT = register("red_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item ORANGE_HAT = register("orange_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item YELLOW_HAT = register("yellow_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item LIME_HAT = register("lime_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item GREEN_HAT = register("green_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item CYAN_HAT = register("cyan_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item LIGHT_BLUE_HAT = register("light_blue_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item BLUE_HAT = register("blue_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item PURPLE_HAT = register("purple_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item MAGENTA_HAT = register("magenta_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item PINK_HAT = register("pink_hat", new SweaterItem(new FabricItemSettings().maxCount(1), false));
    public static final Item TRADER_HOOD = register("trader_hood", new SweaterItem(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON), false));


    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MOD_ID, id), item);
    }
}
