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
                output.add(IRIDESCENT_TERRACOTTA);
                output.add(IRIDESCENT_CONCRETE);
                output.add(IRIDESCENT_LANTERN);

                output.add(CHISEL);
                output.add(WRENCH);
                output.add(HAMMER);
                output.add(HANDBELL);

                output.add(DRUM);

                output.add(DICE);

                output.add(FRAME);

                output.add(PRICKLY_CAN);

                output.add(BOUQUET);
                output.add(TERRACOTTA_VASE);

                output.add(ITEM_STAND);

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

                output.add(CHAPPLE_SPAWN_EGG);
                output.add(EGGPLE);
                output.add(GOLDEN_EGGPLE);

                output.add(COTTON_SEEDS);
                output.add(COTTON_FLOWER);


            }).build()
    );

    static {

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Items.WAXED_OXIDIZED_CUT_COPPER_SLAB,
                    BISMUTH_BLOCK,
                    BISMUTH_BARS,
                    IRIDESCENT_GLASS,
                    IRIDESCENT_TERRACOTTA,
                    IRIDESCENT_CONCRETE
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
            entries.addAfter(Items.GLOW_ITEM_FRAME, ITEM_STAND);
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
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.addAfter(Items.EGG, EGGPLE, GOLDEN_EGGPLE);
            entries.addAfter(Items.TURTLE_HELMET, TIDAL_HELMET);
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
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.EGG, EGGPLE, GOLDEN_EGGPLE);
            entries.addAfter(Items.RAW_GOLD, RAW_BISMUTH);
            entries.addAfter(Items.GOLD_INGOT, BISMUTH_INGOT);
        });
    }

    private static ItemGroup register(String id, ItemGroup tab) {
        return Registry.register(Registries.ITEM_GROUP, new Identifier(Etcetera.MOD_ID, id), tab);
    }
}
