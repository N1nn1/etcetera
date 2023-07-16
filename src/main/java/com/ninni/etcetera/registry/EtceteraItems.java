package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.item.EggpleItem;
import com.ninni.etcetera.item.HammerItem;
import com.ninni.etcetera.item.HandbellItem;
import com.ninni.etcetera.item.SweaterItem;
import com.ninni.etcetera.item.TidalHelmetItem;
import com.ninni.etcetera.item.TransformingItem;
import com.ninni.etcetera.item.TurtleRaftItem;
import com.ninni.etcetera.item.WrenchItem;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.StandingAndWallBlockItem;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.ninni.etcetera.registry.EtceteraVanillaIntegration.CHISELLING_MANAGER;
import static com.ninni.etcetera.registry.EtceteraVanillaIntegration.HAMMERING_MANAGER;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EtceteraItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Etcetera.MOD_ID);

    public static final RegistryObject<Item> ETCETERA = ITEMS.register("etcetera", () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant()));

    public static final RegistryObject<Item> RAW_BISMUTH_BLOCK = ITEMS.register("raw_bismuth_block", () -> new BlockItem(EtceteraBlocks.RAW_BISMUTH_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> BISMUTH_BLOCK = ITEMS.register("bismuth_block", () -> new BlockItem(EtceteraBlocks.BISMUTH_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> BISMUTH_BARS = ITEMS.register("bismuth_bars", () -> new BlockItem(EtceteraBlocks.BISMUTH_BARS.get(), new Item.Properties()));
    public static final RegistryObject<Item> NETHER_BISMUTH_ORE = ITEMS.register("nether_bismuth_ore", () -> new BlockItem(EtceteraBlocks.NETHER_BISMUTH_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RAW_BISMUTH = ITEMS.register("raw_bismuth", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BISMUTH_INGOT = ITEMS.register("bismuth_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_GLASS = ITEMS.register("iridescent_glass", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_GLASS.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_GLASS_PANE = ITEMS.register("iridescent_glass_pane", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_GLASS_PANE.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_TERRACOTTA = ITEMS.register("iridescent_terracotta", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_GLAZED_TERRACOTTA = ITEMS.register("iridescent_glazed_terracotta", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_GLAZED_TERRACOTTA.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_CONCRETE = ITEMS.register("iridescent_concrete", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_CONCRETE.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_WOOL = ITEMS.register("iridescent_wool", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_WOOL.get(), new Item.Properties()));
    public static final RegistryObject<Item> IRIDESCENT_LANTERN = ITEMS.register("iridescent_lantern", () -> new BlockItem(EtceteraBlocks.IRIDESCENT_LANTERN.get(), new Item.Properties()));

    public static final RegistryObject<Item> CHISEL = ITEMS.register("chisel", () -> new TransformingItem(EtceteraToolMaterials.BISMUTH, CHISELLING_MANAGER::getMap, new Item.Properties().stacksTo(1).durability(145), EtceteraSoundEvents.ITEM_CHISEL_USE.get(), EtceteraTags.CHISELLABLE));
    public static final RegistryObject<Item> WRENCH = ITEMS.register("wrench", () -> new WrenchItem(EtceteraToolMaterials.BISMUTH, new Item.Properties().stacksTo(1).durability(145)));
    public static final RegistryObject<Item> HAMMER = ITEMS.register("hammer", () -> new HammerItem(EtceteraToolMaterials.BISMUTH, (int)7.5, -3.6F, HAMMERING_MANAGER::getMap, new Item.Properties().stacksTo(1).durability(80), EtceteraSoundEvents.ITEM_HAMMER_USE.get(), EtceteraTags.HAMMERABLE));
    public static final RegistryObject<Item> HANDBELL = ITEMS.register("handbell", () -> new HandbellItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> DRUM = ITEMS.register("drum", () -> new BlockItem(EtceteraBlocks.DRUM.get(), new Item.Properties()));

    public static final RegistryObject<Item> DICE = ITEMS.register("dice", () -> new BlockItem(EtceteraBlocks.DICE.get(), new Item.Properties()));

    public static final RegistryObject<Item> FRAME = ITEMS.register("frame", () -> new BlockItem(EtceteraBlocks.FRAME.get(), new Item.Properties()));

    public static final RegistryObject<Item> PRICKLY_CAN = ITEMS.register("prickly_can", () -> new BlockItem(EtceteraBlocks.PRICKLY_CAN.get(), new Item.Properties()));

    public static final RegistryObject<Item> BOUQUET = ITEMS.register("bouquet", () -> new BlockItem(EtceteraBlocks.BOUQUET.get(), new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> TERRACOTTA_VASE = ITEMS.register("terracotta_vase", () -> new BlockItem(EtceteraBlocks.TERRACOTTA_VASE.get(), new Item.Properties()));

    public static final RegistryObject<Item> ITEM_STAND = ITEMS.register("item_stand", () -> new BlockItem(EtceteraBlocks.ITEM_STAND.get(), new Item.Properties()));
    public static final RegistryObject<Item> GLOW_ITEM_STAND = ITEMS.register("glow_item_stand", () -> new BlockItem(EtceteraBlocks.GLOW_ITEM_STAND.get(), new Item.Properties()));

    public static final RegistryObject<Item> SQUID_LAMP = ITEMS.register("squid_lamp", () -> new StandingAndWallBlockItem(EtceteraBlocks.SQUID_LAMP.get(), EtceteraBlocks.WALL_SQUID_LAMP.get(), new Item.Properties(), Direction.DOWN));
    public static final RegistryObject<Item> TIDAL_HELMET = ITEMS.register("tidal_helmet", () -> new TidalHelmetItem(EtceteraArmorMaterials.TIDAL, ArmorItem.Type.HELMET, new Item.Properties().rarity(Rarity.RARE)));
    public static final RegistryObject<Item> TURTLE_RAFT = ITEMS.register("turtle_raft", () -> new TurtleRaftItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> CRUMBLING_STONE = ITEMS.register("crumbling_stone", () -> new BlockItem(EtceteraBlocks.CRUMBLING_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> WAXED_CRUMBLING_STONE = ITEMS.register("waxed_crumbling_stone", () -> new BlockItem(EtceteraBlocks.WAXED_CRUMBLING_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LEVELED_STONE = ITEMS.register("leveled_stone", () -> new BlockItem(EtceteraBlocks.LEVELED_STONE.get(), new Item.Properties()));
    public static final RegistryObject<Item> LEVELED_STONE_STAIRS = ITEMS.register("leveled_stone_stairs", () -> new BlockItem(EtceteraBlocks.LEVELED_STONE_STAIRS.get(), new Item.Properties()));
    public static final RegistryObject<Item> LEVELED_STONE_SLAB = ITEMS.register("leveled_stone_slab", () -> new BlockItem(EtceteraBlocks.LEVELED_STONE_SLAB.get(), new Item.Properties()));

    public static final RegistryObject<Item> LIGHT_BULB = ITEMS.register("light_bulb", () -> new BlockItem(EtceteraBlocks.LIGHT_BULB.get(), new Item.Properties()));
    public static final RegistryObject<Item> TINTED_LIGHT_BULB = ITEMS.register("tinted_light_bulb", () -> new BlockItem(EtceteraBlocks.TINTED_LIGHT_BULB.get(), new Item.Properties()));

    public static final RegistryObject<Item> CHAPPLE_SPAWN_EGG = ITEMS.register("chapple_spawn_egg", () -> new ForgeSpawnEggItem(EtceteraEntityType.CHAPPLE, 0xE41826, 0x548630, new Item.Properties().stacksTo(64)));
    public static final RegistryObject<Item> EGGPLE = ITEMS.register("eggple", () -> new EggpleItem(false, new Item.Properties().stacksTo(16)));
    public static final RegistryObject<Item> GOLDEN_EGGPLE = ITEMS.register("golden_eggple", () -> new EggpleItem(true, new Item.Properties().rarity(Rarity.RARE).stacksTo(16)));

    public static final RegistryObject<Item> COTTON_SEEDS = ITEMS.register("cotton_seeds", () -> new ItemNameBlockItem(EtceteraBlocks.COTTON.get(), new Item.Properties()));
    public static final RegistryObject<Item> COTTON_FLOWER = ITEMS.register("cotton_flower", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHITE_SWEATER = ITEMS.register("white_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> LIGHT_GRAY_SWEATER = ITEMS.register("light_gray_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> GRAY_SWEATER = ITEMS.register("gray_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> BLACK_SWEATER = ITEMS.register("black_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> BROWN_SWEATER = ITEMS.register("brown_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> RED_SWEATER = ITEMS.register("red_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> ORANGE_SWEATER = ITEMS.register("orange_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> YELLOW_SWEATER = ITEMS.register("yellow_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> LIME_SWEATER = ITEMS.register("lime_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> GREEN_SWEATER = ITEMS.register("green_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> CYAN_SWEATER = ITEMS.register("cyan_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> LIGHT_BLUE_SWEATER = ITEMS.register("light_blue_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> BLUE_SWEATER = ITEMS.register("blue_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> PURPLE_SWEATER = ITEMS.register("purple_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> MAGENTA_SWEATER = ITEMS.register("magenta_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> PINK_SWEATER = ITEMS.register("pink_sweater", () -> new SweaterItem(new Item.Properties().stacksTo(1), true));
    public static final RegistryObject<Item> TRADER_ROBE = ITEMS.register("trader_robe", () -> new SweaterItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), true));
    public static final RegistryObject<Item> WHITE_HAT = ITEMS.register("white_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> LIGHT_GRAY_HAT = ITEMS.register("light_gray_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> GRAY_HAT = ITEMS.register("gray_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> BLACK_HAT = ITEMS.register("black_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> BROWN_HAT = ITEMS.register("brown_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> RED_HAT = ITEMS.register("red_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> ORANGE_HAT = ITEMS.register("orange_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> YELLOW_HAT = ITEMS.register("yellow_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> LIME_HAT = ITEMS.register("lime_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> GREEN_HAT = ITEMS.register("green_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> CYAN_HAT = ITEMS.register("cyan_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> LIGHT_BLUE_HAT = ITEMS.register("light_blue_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> BLUE_HAT = ITEMS.register("blue_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> PURPLE_HAT = ITEMS.register("purple_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> MAGENTA_HAT = ITEMS.register("magenta_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> PINK_HAT = ITEMS.register("pink_hat", () -> new SweaterItem(new Item.Properties().stacksTo(1), false));
    public static final RegistryObject<Item> TRADER_HOOD = ITEMS.register("trader_hood", () -> new SweaterItem(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), false));

}
