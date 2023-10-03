package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.registry.EtceteraItems.*;

public class EtceteraCreativeModeTab {

    public static final ItemGroup ITEM_GROUP = register("item_group", FabricItemGroup.builder().icon(ETCETERA::getDefaultStack).displayName(Text.translatable("etcetera.item_group")).entries((featureFlagSet, output) -> {
                output.add(RAW_BISMUTH_BLOCK);
                output.add(BISMUTH_BLOCK);
                output.add(BISMUTH_BARS);
                output.add(NETHER_BISMUTH_ORE);
                output.add(RAW_BISMUTH);
                output.add(BISMUTH_INGOT);
                output.add(IRIDESCENT_GLASS);
                output.add(IRIDESCENT_GLASS_PANE);
                output.add(IRIDESCENT_TERRACOTTA);
                output.add(IRIDESCENT_GLAZED_TERRACOTTA);
                output.add(IRIDESCENT_CONCRETE);
                output.add(IRIDESCENT_WOOL);
                output.add(IRIDESCENT_LANTERN);

                output.add(CHISEL);
                output.add(WRENCH);
                output.add(HAMMER);
                output.add(HANDBELL);

                output.add(ITEM_LABEL);

                output.add(DRUM);

                output.add(DICE);

                output.add(FRAME);

                output.add(PRICKLY_CAN);

                output.add(DREAM_CATCHER);

                output.add(BOUQUET);
                output.add(TERRACOTTA_VASE);

                output.add(ITEM_STAND);
                output.add(GLOW_ITEM_STAND);

                output.add(SQUID_LAMP);
                output.add(TIDAL_HELMET);
                output.add(TURTLE_RAFT);

                output.add(CRUMBLING_STONE);
                output.add(WAXED_CRUMBLING_STONE);
                output.add(LEVELED_STONE);
                output.add(LEVELED_STONE_STAIRS);
                output.add(LEVELED_STONE_SLAB);

                output.add(LIGHT_BULB);
                output.add(TINTED_LIGHT_BULB);

                output.add(WEAVER_SPAWN_EGG);

                output.add(CHAPPLE_SPAWN_EGG);
                output.add(EGGPLE);
                output.add(GOLDEN_EGGPLE);

                output.add(COTTON_SEEDS);
                output.add(COTTON_FLOWER);
                output.add(WHITE_SWEATER);
                output.add(LIGHT_GRAY_SWEATER);
                output.add(GRAY_SWEATER);
                output.add(BLACK_SWEATER);
                output.add(BROWN_SWEATER);
                output.add(RED_SWEATER);
                output.add(ORANGE_SWEATER);
                output.add(YELLOW_SWEATER);
                output.add(LIME_SWEATER);
                output.add(GREEN_SWEATER);
                output.add(CYAN_SWEATER );
                output.add(LIGHT_BLUE_SWEATER);
                output.add(BLUE_SWEATER);
                output.add(PURPLE_SWEATER);
                output.add(MAGENTA_SWEATER);
                output.add(PINK_SWEATER);
                output.add(TRADER_ROBE);
                output.add(WHITE_HAT);
                output.add(LIGHT_GRAY_HAT);
                output.add(GRAY_HAT);
                output.add(BLACK_HAT);
                output.add(BROWN_HAT);
                output.add(RED_HAT);
                output.add(ORANGE_HAT);
                output.add(YELLOW_HAT);
                output.add(LIME_HAT);
                output.add(GREEN_HAT);
                output.add(CYAN_HAT );
                output.add(LIGHT_BLUE_HAT);
                output.add(BLUE_HAT);
                output.add(PURPLE_HAT);
                output.add(MAGENTA_HAT);
                output.add(PINK_HAT);
                output.add(TRADER_HOOD);


            }).build()
    );

    static {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Items.SMOOTH_QUARTZ_SLAB,
                    BISMUTH_BLOCK,
                    BISMUTH_BARS
            );
            entries.addAfter(Items.SMOOTH_STONE_SLAB,
                    LEVELED_STONE,
                    CRUMBLING_STONE,
                    WAXED_CRUMBLING_STONE,
                    LEVELED_STONE_STAIRS,
                    LEVELED_STONE_SLAB
            );
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
            entries.addAfter(Items.SOUL_LANTERN, LIGHT_BULB, TINTED_LIGHT_BULB);
            entries.addAfter(Items.END_ROD, SQUID_LAMP);
            entries.addAfter(Items.SEA_LANTERN, IRIDESCENT_LANTERN);
            entries.addAfter(Items.JUKEBOX, DRUM);
            entries.addAfter(Items.SCAFFOLDING, FRAME);
            entries.addAfter(Items.DECORATED_POT, TERRACOTTA_VASE);
            entries.addAfter(Items.GLOW_ITEM_FRAME, ITEM_STAND, GLOW_ITEM_STAND);
            entries.addAfter(Items.ENDER_CHEST, PRICKLY_CAN);
            entries.addAfter(Items.SUSPICIOUS_GRAVEL, CRUMBLING_STONE, WAXED_CRUMBLING_STONE);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {
            entries.addAfter(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, DRUM);
            entries.addAfter(Items.WHITE_WOOL, DICE);
            entries.addAfter(Items.BARREL, PRICKLY_CAN);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(entries -> {
            entries.addAfter(Items.CAVE_SPIDER_SPAWN_EGG, CHAPPLE_SPAWN_EGG);
            entries.addAfter(Items.WARDEN_SPAWN_EGG, WEAVER_SPAWN_EGG);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.addAfter(Items.EGG, EGGPLE, GOLDEN_EGGPLE);
            entries.addAfter(Items.TURTLE_HELMET,
                    TIDAL_HELMET,
                    WHITE_HAT,
                    TRADER_HOOD,
                    WHITE_SWEATER,
                    TRADER_ROBE
            );
            entries.addAfter(Items.TRIDENT, HAMMER);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.addAfter(Items.BRUSH,
                    HAMMER,
                    CHISEL,
                    WRENCH,
                    HANDBELL
            );
            entries.addAfter(Items.BAMBOO_CHEST_RAFT, TURTLE_RAFT);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(entries -> {
            entries.addAfter(Items.NETHER_GOLD_ORE, NETHER_BISMUTH_ORE);
            entries.addAfter(Items.RAW_GOLD_BLOCK, RAW_BISMUTH_BLOCK);
            entries.addAfter(Items.PINK_PETALS, BOUQUET);
            entries.addAfter(Items.BEETROOT_SEEDS, COTTON_SEEDS);
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register(entries -> {
            entries.addBefore(Items.WHITE_CONCRETE, IRIDESCENT_CONCRETE);
            entries.addBefore(Items.WHITE_WOOL, IRIDESCENT_WOOL);
            entries.addBefore(Items.WHITE_GLAZED_TERRACOTTA, IRIDESCENT_GLAZED_TERRACOTTA);
            entries.addAfter(Items.TERRACOTTA, IRIDESCENT_TERRACOTTA);
            entries.addAfter(Items.GLASS, IRIDESCENT_GLASS);
            entries.addAfter(Items.GLASS_PANE, IRIDESCENT_GLASS_PANE);
            entries.addAfter(Items.PINK_BED,
                    WHITE_SWEATER,
                    LIGHT_GRAY_SWEATER,
                    GRAY_SWEATER,
                    BLACK_SWEATER,
                    BROWN_SWEATER,
                    RED_SWEATER,
                    ORANGE_SWEATER,
                    YELLOW_SWEATER,
                    LIME_SWEATER,
                    GREEN_SWEATER,
                    CYAN_SWEATER ,
                    LIGHT_BLUE_SWEATER,
                    BLUE_SWEATER,
                    PURPLE_SWEATER,
                    MAGENTA_SWEATER,
                    PINK_SWEATER,
                    WHITE_HAT,
                    LIGHT_GRAY_HAT,
                    GRAY_HAT,
                    BLACK_HAT,
                    BROWN_HAT,
                    RED_HAT,
                    ORANGE_HAT,
                    YELLOW_HAT,
                    LIME_HAT,
                    GREEN_HAT,
                    CYAN_HAT ,
                    LIGHT_BLUE_HAT,
                    BLUE_HAT,
                    PURPLE_HAT,
                    MAGENTA_HAT,
                    PINK_HAT
            );
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.EGG, EGGPLE, GOLDEN_EGGPLE);
            entries.addAfter(Items.RAW_GOLD, RAW_BISMUTH);
            entries.addAfter(Items.GOLD_INGOT, BISMUTH_INGOT);
            entries.addAfter(Items.WHEAT, COTTON_FLOWER);
        });
    }

    private static ItemGroup register(String id, ItemGroup tab) {
        return Registry.register(Registries.ITEM_GROUP, new Identifier(Etcetera.MOD_ID, id), tab);
    }
}
