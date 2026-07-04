package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.item.*;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import com.ninni.etcetera.platform.services.RegistryObject;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.function.Supplier;

import static com.ninni.etcetera.registry.EtceteraVanillaIntegration.CHISELLING_MANAGER;
import static com.ninni.etcetera.registry.EtceteraVanillaIntegration.HAMMERING_MANAGER;

@SuppressWarnings("unused")
public class EtceteraItems {
    public static final RegistrationProvider<Item> ITEMS = RegistrationProvider.get(Registries.ITEM, Constants.MOD_ID);

    public static final RegistryObject<Item> ETCETERA = register(Constants.MOD_ID, () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> RAW_BISMUTH_BLOCK = register("raw_bismuth_block", () -> new BlockItem(EtceteraBlocks.RAW_BISMUTH_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> BISMUTH_BLOCK = register("bismuth_block", () -> new BlockItem(EtceteraBlocks.BISMUTH_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> BISMUTH_BARS = register("bismuth_bars", () -> new BlockItem(EtceteraBlocks.BISMUTH_BARS.get(), new Item.Properties()));
    public static final RegistryObject<Item> NETHER_BISMUTH_ORE = register("nether_bismuth_ore", () -> new BlockItem(EtceteraBlocks.NETHER_BISMUTH_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAW_BISMUTH = register("raw_bismuth", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BISMUTH_INGOT = register("bismuth_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_GLASS = register("iridescent_glass", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_GLASS_PANE = register("iridescent_glass_pane", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_GLASS_PANE.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_TERRACOTTA = register("iridescent_terracotta", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_GLAZED_TERRACOTTA = register("iridescent_glazed_terracotta", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_GLAZED_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_CONCRETE = register("iridescent_concrete", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_WOOL = register("iridescent_wool", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_WOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_LANTERN = register("iridescent_lantern", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_LANTERN.get(), new Item.Properties()));

    public static final RegistryObject<Item> CHISEL = register("chisel", () -> new TransformingItem(EtceteraToolMaterials.BISMUTH, CHISELLING_MANAGER::getMap, new Item.Properties().stacksTo(1).durability(145), EtceteraSoundEvents.ITEM_CHISEL_USE, EtceteraTags.CHISELLABLE));
    public static final RegistryObject<Item> WRENCH = register("wrench", () -> new WrenchItem(EtceteraToolMaterials.BISMUTH, new Item.Properties().stacksTo(1).durability(145)));
    public static final RegistryObject<Item> HAMMER = register("hammer", () -> new HammerItem(EtceteraToolMaterials.BISMUTH, (int) 7.5, -3.6F, HAMMERING_MANAGER::getMap, new Item.Properties().stacksTo(1).durability(80), EtceteraSoundEvents.ITEM_HAMMER_USE, EtceteraTags.HAMMERABLE));
    public static final RegistryObject<Item> HANDBELL = register("handbell", () -> new HandbellItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ITEM_LABEL = register("item_label", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRUM = register("drum", () -> new BlockItem(EtceteraBlocks.DRUM.get(), new Item.Properties()));

    public static final RegistryObject<Item> DICE = register("dice", () -> new BlockItem(EtceteraBlocks.DICE.get(), new Item.Properties()));

    public static final RegistryObject<Item> FRAME = register("frame", () -> new BlockItem(EtceteraBlocks.FRAME.get(), new Item.Properties()));

    public static final RegistryObject<Item> PRICKLY_CAN = register("prickly_can", () -> new BlockItem(EtceteraBlocks.PRICKLY_CAN.get(), new Item.Properties()));

    public static final RegistryObject<Item> DREAM_CATCHER = register("dream_catcher", () -> new DreamCatcherItem(EtceteraBlocks.DREAM_CATCHER.get(), new Item.Properties()));

    public static final RegistryObject<Item> BOUQUET = register("bouquet", () -> new BlockItem(EtceteraBlocks.BOUQUET.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> TERRACOTTA_VASE = register("terracotta_vase", () -> new BlockItem(EtceteraBlocks.TERRACOTTA_VASE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ITEM_STAND = register("item_stand", () -> new BlockItem(EtceteraBlocks.ITEM_STAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOW_ITEM_STAND = register("glow_item_stand", () -> new BlockItem(EtceteraBlocks.GLOW_ITEM_STAND.get(), new Item.Properties()));

    public static final RegistryObject<Item> SQUID_LAMP = register("squid_lamp", () -> new StandingAndWallBlockItem(EtceteraBlocks.SQUID_LAMP.get(), EtceteraBlocks.WALL_SQUID_LAMP.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> TIDAL_HELMET = register("tidal_helmet", () -> new ArmorItem(EtceteraArmorMaterials.TIDAL, ArmorItem.Type.HELMET, new Item.Properties().stacksTo(1).rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TURTLE_RAFT = register("turtle_raft", () -> new TurtleRaftItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MUSIC_DISC_SQUALL = register("music_disc_squall", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "squall")))));

    public static final RegistryObject<Item> ADVENTURERS_BOOTS = register("adventurers_boots", () -> new ArmorItem(EtceteraArmorMaterials.ADVENTURER, ArmorItem.Type.BOOTS, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> SAND_PATH = register("sand_path", () -> new BlockItem(EtceteraBlocks.SAND_PATH.get(), new Item.Properties()));
    public static final RegistryObject<Item> RED_SAND_PATH = register("red_sand_path", () -> new BlockItem(EtceteraBlocks.RED_SAND_PATH.get(), new Item.Properties()));
    public static final RegistryObject<Item> GRAVEL_PATH = register("gravel_path", () -> new BlockItem(EtceteraBlocks.GRAVEL_PATH.get(), new Item.Properties()));
    public static final RegistryObject<Item> SNOW_PATH = register("snow_path", () -> new BlockItem(EtceteraBlocks.SNOW_PATH.get(), new Item.Properties()));

    public static final RegistryObject<Item> CRUMBLING_STONE = register("crumbling_stone", () -> new BlockItem(EtceteraBlocks.CRUMBLING_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", () -> new BlockItem(EtceteraBlocks.WAXED_CRUMBLING_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LEVELED_STONE = register("leveled_stone", () -> new BlockItem(EtceteraBlocks.LEVELED_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LEVELED_STONE_STAIRS = register("leveled_stone_stairs", () -> new BlockItem(EtceteraBlocks.LEVELED_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LEVELED_STONE_SLAB = register("leveled_stone_slab", () -> new BlockItem(EtceteraBlocks.LEVELED_STONE_SLAB.get(), new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_BULB = register("light_bulb", () -> new BlockItem(EtceteraBlocks.LIGHT_BULB.get(), new Item.Properties()));
    public static final RegistryObject<Item> TINTED_LIGHT_BULB = register("tinted_light_bulb", () -> new BlockItem(EtceteraBlocks.TINTED_LIGHT_BULB.get(), new Item.Properties()));

    public static final RegistryObject<Item> GOLDEN_GOLEM = register("golden_golem", () -> new GoldenGolemItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> WEAVER_SPAWN_EGG = register("weaver_spawn_egg", () -> new SpawnEggItem(EtceteraEntityType.WEAVER.get(), 0x191919, 0xFF1B00, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> SILKEN_SLACKS = register("silken_slacks", () -> new ArmorItem(EtceteraArmorMaterials.SILK, ArmorItem.Type.LEGGINGS, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CHAPPLE_SPAWN_EGG = register("chapple_spawn_egg", () -> new SpawnEggItem(EtceteraEntityType.CHAPPLE.get(), 0xE41826, 0x548630, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> EGGPLE = register("eggple", () -> new EggpleItem(false, new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> GOLDEN_EGGPLE = register("golden_eggple", () -> new EggpleItem(true, new Item.Properties().rarity(Rarity.RARE).stacksTo(16)));

    public static final RegistryObject<Item> COPPER_TAP = register("copper_tap", () -> new BlockItem(EtceteraBlocks.COPPER_TAP.get(), new Item.Properties()));

    public static final RegistryObject<Item> RUBBER = register("rubber", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RUBBER_CHICKEN = register("rubber_chicken", () -> new RubberChickenItem(new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> RUBBER_BLOCK = register("rubber_block", () -> new BlockItem(EtceteraBlocks.RUBBER_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> RUBBER_BUTTON = register("rubber_button", () -> new BlockItem(EtceteraBlocks.RUBBER_BUTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_WIRES = register("redstone_wires", () -> new BlockItem(EtceteraBlocks.REDSTONE_WIRES.get(), new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_WIRE_TORCH = register("redstone_wire_torch", () -> new StandingAndWallBlockItem(EtceteraBlocks.REDSTONE_WIRE_TORCH.get(), EtceteraBlocks.REDSTONE_WIRE_WALL_TORCH.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> REDSTONE_WIRE_COMPARATOR = register("redstone_wire_comparator", () -> new BlockItem(EtceteraBlocks.REDSTONE_WIRE_COMPARATOR.get(), new Item.Properties()));
    public static final RegistryObject<Item> REDSTONE_WIRE_REPEATER = register("redstone_wire_repeater", () -> new BlockItem(EtceteraBlocks.REDSTONE_WIRE_REPEATER.get(), new Item.Properties()));

    public static final RegistryObject<Item> COTTON_SEEDS = register("cotton_seeds", () -> new ItemNameBlockItem(EtceteraBlocks.COTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> COTTON_FLOWER = register("cotton_flower", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_SWEATER = register("white_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> LIGHT_GRAY_SWEATER = register("light_gray_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> GRAY_SWEATER = register("gray_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> BLACK_SWEATER = register("black_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> BROWN_SWEATER = register("brown_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> RED_SWEATER = register("red_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> ORANGE_SWEATER = register("orange_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> YELLOW_SWEATER = register("yellow_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> LIME_SWEATER = register("lime_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> GREEN_SWEATER = register("green_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> CYAN_SWEATER = register("cyan_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> LIGHT_BLUE_SWEATER = register("light_blue_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> BLUE_SWEATER = register("blue_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> PURPLE_SWEATER = register("purple_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> MAGENTA_SWEATER = register("magenta_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> PINK_SWEATER = register("pink_sweater", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> TRADER_ROBE = register("trader_robe", () -> new CottonArmorItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> WHITE_HAT = register("white_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> LIGHT_GRAY_HAT = register("light_gray_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> GRAY_HAT = register("gray_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> BLACK_HAT = register("black_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> BROWN_HAT = register("brown_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> RED_HAT = register("red_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> ORANGE_HAT = register("orange_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> YELLOW_HAT = register("yellow_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> LIME_HAT = register("lime_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> GREEN_HAT = register("green_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> CYAN_HAT = register("cyan_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> LIGHT_BLUE_HAT = register("light_blue_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> BLUE_HAT = register("blue_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> PURPLE_HAT = register("purple_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> MAGENTA_HAT = register("magenta_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> PINK_HAT = register("pink_hat", () -> new CottonArmorItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> TRADER_HOOD = register("trader_hood", () -> new CottonArmorItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), false));

    private static <T extends Item> RegistryObject<T> register(String id, Supplier<T> itemSupplier) {
        return ITEMS.register(id, itemSupplier);
    }

    public static void init() {
    }
}