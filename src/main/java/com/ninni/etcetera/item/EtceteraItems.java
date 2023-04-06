package com.ninni.etcetera.item;

import com.ninni.etcetera.EtceteraTags;
import com.ninni.etcetera.block.EtceteraBlocks;
import com.ninni.etcetera.entity.EtceteraEntityType;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
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
    public static final Item IRIDESCENT_LANTERN = register("iridescent_lantern", new BlockItem(EtceteraBlocks.IRIDESCENT_LANTERN, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item CHISEL = register("chisel", new TransformingItem(EtceteraToolMaterials.BISMUTH, CHISELLING_MANAGER::getMap, new FabricItemSettings().group(ITEM_GROUP).maxCount(1).maxDamage(145), EtceteraSoundEvents.ITEM_CHISEL_USE, EtceteraTags.CHISELLABLE));
    public static final Item WRENCH = register("wrench", new WrenchItem(EtceteraToolMaterials.BISMUTH, new FabricItemSettings().group(ITEM_GROUP).maxCount(1).maxDamage(145)));
    public static final Item HAMMER = register("hammer", new HammerItem(EtceteraToolMaterials.BISMUTH, (int)7.5, -3.6F, HAMMERING_MANAGER::getMap, new FabricItemSettings().group(ITEM_GROUP).maxCount(1).maxDamage(80), EtceteraSoundEvents.ITEM_HAMMER_USE, EtceteraTags.HAMMERABLE));
    public static final Item HANDBELL = register("handbell", new HandbellItem(new FabricItemSettings().group(ITEM_GROUP).maxCount(1)));

    public static final Item DRUM = register("drum", new BlockItem(EtceteraBlocks.DRUM, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item DICE = register("dice", new BlockItem(EtceteraBlocks.DICE, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item FRAME = register("frame", new BlockItem(EtceteraBlocks.FRAME, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item PRICKLY_CAN = register("prickly_can", new BlockItem(EtceteraBlocks.PRICKLY_CAN, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item BOUQUET = register("bouquet", new BlockItem(EtceteraBlocks.BOUQUET, new FabricItemSettings().group(ITEM_GROUP).maxCount(16)));
    public static final Item TERRACOTTA_VASE = register("terracotta_vase", new BlockItem(EtceteraBlocks.TERRACOTTA_VASE, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item ITEM_STAND = register("item_stand", new BlockItem(EtceteraBlocks.ITEM_STAND, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item SQUID_LAMP = register("squid_lamp", new WallStandingBlockItem(EtceteraBlocks.SQUID_LAMP, EtceteraBlocks.WALL_SQUID_LAMP, new Item.Settings().group(ITEM_GROUP)));
    public static final Item TIDAL_HELMET = register("tidal_helmet", new ArmorItem(EtceteraArmorMaterials.TIDAL, EquipmentSlot.HEAD, new Item.Settings().rarity(Rarity.UNCOMMON).group(ITEM_GROUP)));
    public static final Item TURTLE_RAFT = register("turtle_raft", new TurtleRaftItem(new Item.Settings().group(ITEM_GROUP).maxCount(1)));

    public static final Item CRUMBLING_STONE = register("crumbling_stone", new BlockItem(EtceteraBlocks.CRUMBLING_STONE, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item WAXED_CRUMBLING_STONE = register("waxed_crumbling_stone", new BlockItem(EtceteraBlocks.WAXED_CRUMBLING_STONE, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item LEVELED_STONE = register("leveled_stone", new BlockItem(EtceteraBlocks.LEVELED_STONE, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LEVELED_STONE_STAIRS = register("leveled_stone_stairs", new BlockItem(EtceteraBlocks.LEVELED_STONE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item LEVELED_STONE_SLAB = register("leveled_stone_slab", new BlockItem(EtceteraBlocks.LEVELED_STONE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item LIGHT_BULB = register("light_bulb", new BlockItem(EtceteraBlocks.LIGHT_BULB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item TINTED_LIGHT_BULB = register("tinted_light_bulb", new BlockItem(EtceteraBlocks.TINTED_LIGHT_BULB, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item SNAIL_SPAWN_EGG = register("snail_spawn_egg", new SpawnEggItem(EtceteraEntityType.SNAIL, 0x5D3F30, 0xF6DEA2, new Item.Settings().maxCount(64).group(ITEM_GROUP)));
    public static final Item SNAIL_SHELL = register("snail_shell", new Item(new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item BIG_SNAIL_SHELL = register("big_snail_shell", new BlockItem(EtceteraBlocks.BIG_SNAIL_SHELL, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SNAIL_SHELL_TILES = register("snail_shell_tiles", new BlockItem(EtceteraBlocks.SNAIL_SHELL_TILES, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SNAIL_SHELL_TILE_STAIRS = register("snail_shell_tile_stairs", new BlockItem(EtceteraBlocks.SNAIL_SHELL_TILE_STAIRS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item SNAIL_SHELL_TILE_SLAB = register("snail_shell_tile_slab", new BlockItem(EtceteraBlocks.SNAIL_SHELL_TILE_SLAB, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item ESCARGOT = register("escargot", new EscargotItem(new FabricItemSettings().recipeRemainder(SNAIL_SHELL).group(ITEM_GROUP).maxCount(1).food(new FoodComponent.Builder().hunger(4).saturationModifier(0.8f).build())));
    public static final Item POTTED_SWEET_BERRIES = register("potted_sweet_berries", new BlockItem(EtceteraBlocks.POTTED_SWEET_BERRY_BUSH, new FabricItemSettings().recipeRemainder(SNAIL_SHELL).group(ITEM_GROUP).maxCount(16)));
    public static final Item MUCUS = register("mucus", new BlockItem(EtceteraBlocks.MUCUS, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item MUCUS_BLOCK = register("mucus_block", new BlockItem(EtceteraBlocks.MUCUS_BLOCK, new FabricItemSettings().group(ITEM_GROUP)));
    public static final Item GHOSTLY_MUCUS_BLOCK = register("ghostly_mucus_block", new BlockItem(EtceteraBlocks.GHOSTLY_MUCUS_BLOCK, new FabricItemSettings().group(ITEM_GROUP)));

    public static final Item CHAPPLE_SPAWN_EGG = register("chapple_spawn_egg", new SpawnEggItem(EtceteraEntityType.CHAPPLE, 0xE41826, 0x548630, new Item.Settings().maxCount(64).group(ITEM_GROUP)));
    public static final Item EGGPLE = register("eggple", new EggpleItem(false, new Item.Settings().maxCount(16).group(ITEM_GROUP)));
    public static final Item GOLDEN_EGGPLE = register("golden_eggple", new EggpleItem(true, new Item.Settings().rarity(Rarity.RARE).maxCount(16).group(ITEM_GROUP)));

    static {
        CompostingChanceRegistry.INSTANCE.add(BOUQUET, 0.85f);


        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(LootTables.BASTION_OTHER_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(GOLDEN_EGGPLE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1)))).build());
            }
            if (id.equals(LootTables.BASTION_TREASURE_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(GOLDEN_EGGPLE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)))).build());
            }
            if (id.equals(LootTables.PILLAGER_OUTPOST_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EGGPLE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1)))).build());
            }
            if (id.equals(LootTables.VILLAGE_PLAINS_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EGGPLE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 3)))).build());
            }
        });

    }

    private static Item register(String id, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(MOD_ID, id), item);
    }
}
