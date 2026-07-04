package com.ninni.etcetera;

import com.ninni.etcetera.registry.EtceteraEntityType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.GenerationStep;

import static com.ninni.etcetera.registry.EtceteraItems.*;

public class Etcetera implements ModInitializer {

    @Override
    public void onInitialize() {
        CommonClass.init();
        CommonClass.setup();

        BiomeModifications.addFeature(
                BiomeSelectors.foundInTheNether(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "nether_bismuth_ore"))
        );
        BiomeModifications.addSpawn(
                BiomeSelectors.foundInOverworld(),
                MobCategory.MONSTER,
                EtceteraEntityType.WEAVER.get(),
                20, 1, 2
        );
        registerCreativeTabs();
    }

    private void registerCreativeTabs() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.BUILDING_BLOCKS).register(entries -> {
            entries.addAfter(Items.SMOOTH_QUARTZ_SLAB, BISMUTH_BLOCK.get(), BISMUTH_BARS.get());
            entries.addAfter(Items.SMOOTH_STONE_SLAB, LEVELED_STONE.get(), CRUMBLING_STONE.get(), WAXED_CRUMBLING_STONE.get(), LEVELED_STONE_STAIRS.get(), LEVELED_STONE_SLAB.get());
            entries.addAfter(Items.MUD_BRICK_WALL, RUBBER_BLOCK.get(), RUBBER_BUTTON.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FUNCTIONAL_BLOCKS).register(entries -> {
            entries.addAfter(Items.SOUL_LANTERN, LIGHT_BULB.get(), TINTED_LIGHT_BULB.get());
            entries.addAfter(Items.END_ROD, SQUID_LAMP.get());
            entries.addAfter(Items.SEA_LANTERN, IRIDESCENT_LANTERN.get());
            entries.addAfter(Items.JUKEBOX, DRUM.get());
            entries.addAfter(Items.SCAFFOLDING, FRAME.get());
            entries.addAfter(Items.DECORATED_POT, TERRACOTTA_VASE.get());
            entries.addAfter(Items.GLOW_ITEM_FRAME, ITEM_STAND.get(), GLOW_ITEM_STAND.get());
            entries.addAfter(Items.ENDER_CHEST, PRICKLY_CAN.get());
            entries.addAfter(Items.SUSPICIOUS_GRAVEL, CRUMBLING_STONE.get(), WAXED_CRUMBLING_STONE.get());
            entries.addAfter(Items.BELL, DREAM_CATCHER.get());
            entries.addAfter(Items.CAULDRON, COPPER_TAP.get());
            entries.addBefore(Items.SKELETON_SKULL, RUBBER_CHICKEN.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(entries -> {
            entries.addAfter(Items.COMPARATOR, REDSTONE_WIRES.get(), REDSTONE_WIRE_TORCH.get(), REDSTONE_WIRE_REPEATER.get(), REDSTONE_WIRE_COMPARATOR.get());
            entries.addAfter(Items.HEAVY_WEIGHTED_PRESSURE_PLATE, DRUM.get());
            entries.addAfter(Items.WHITE_WOOL, DICE.get());
            entries.addAfter(Items.BARREL, PRICKLY_CAN.get());
            entries.addAfter(Items.STONE_BUTTON, RUBBER_BUTTON.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.SPAWN_EGGS).register(entries -> {
            entries.addAfter(Items.CAVE_SPIDER_SPAWN_EGG, CHAPPLE_SPAWN_EGG.get());
            entries.addAfter(Items.WARDEN_SPAWN_EGG, WEAVER_SPAWN_EGG.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.addAfter(Items.EGG, EGGPLE.get(), GOLDEN_EGGPLE.get());
            entries.addAfter(Items.TURTLE_HELMET,
                    TIDAL_HELMET.get(),
                    SILKEN_SLACKS.get(),
                    ADVENTURERS_BOOTS.get(),
                    WHITE_HAT.get(),
                    TRADER_HOOD.get(),
                    WHITE_SWEATER.get(),
                    TRADER_ROBE.get()
            );
            entries.addAfter(Items.TRIDENT, HAMMER.get());
            entries.addBefore(Items.TOTEM_OF_UNDYING, GOLDEN_GOLEM.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(entries -> {
            entries.addAfter(Items.BRUSH, HAMMER.get(), CHISEL.get(), WRENCH.get(), HANDBELL.get());
            entries.addAfter(Items.BAMBOO_CHEST_RAFT, TURTLE_RAFT.get());
            entries.addAfter(Items.NAME_TAG, ITEM_LABEL.get());
            entries.addBefore(Items.MUSIC_DISC_OTHERSIDE, MUSIC_DISC_SQUALL.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.NATURAL_BLOCKS).register(entries -> {
            entries.addAfter(Items.GRAVEL, GRAVEL_PATH.get());
            entries.addAfter(Items.SAND, SAND_PATH.get());
            entries.addAfter(Items.RED_SAND, RED_SAND_PATH.get());
            entries.addAfter(Items.SNOW, SNOW_PATH.get());
            entries.addAfter(Items.NETHER_GOLD_ORE, NETHER_BISMUTH_ORE.get());
            entries.addAfter(Items.RAW_GOLD_BLOCK, RAW_BISMUTH_BLOCK.get());
            entries.addAfter(Items.PINK_PETALS, BOUQUET.get());
            entries.addAfter(Items.BEETROOT_SEEDS, COTTON_SEEDS.get());
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COLORED_BLOCKS).register(entries -> {
            entries.addBefore(Items.WHITE_CONCRETE, IRIDESCENT_CONCRETE.get());
            entries.addBefore(Items.WHITE_WOOL, IRIDESCENT_WOOL.get());
            entries.addBefore(Items.WHITE_GLAZED_TERRACOTTA, IRIDESCENT_GLAZED_TERRACOTTA.get());
            entries.addAfter(Items.TERRACOTTA, IRIDESCENT_TERRACOTTA.get());
            entries.addAfter(Items.GLASS, IRIDESCENT_GLASS.get());
            entries.addAfter(Items.GLASS_PANE, IRIDESCENT_GLASS_PANE.get());
            entries.addAfter(Items.PINK_BED,
                    WHITE_SWEATER.get(), LIGHT_GRAY_SWEATER.get(), GRAY_SWEATER.get(), BLACK_SWEATER.get(), BROWN_SWEATER.get(), RED_SWEATER.get(),
                    ORANGE_SWEATER.get(), YELLOW_SWEATER.get(), LIME_SWEATER.get(), GREEN_SWEATER.get(), CYAN_SWEATER.get(), LIGHT_BLUE_SWEATER.get(),
                    BLUE_SWEATER.get(), PURPLE_SWEATER.get(), MAGENTA_SWEATER.get(), PINK_SWEATER.get(), WHITE_HAT.get(), LIGHT_GRAY_HAT.get(),
                    GRAY_HAT.get(), BLACK_HAT.get(), BROWN_HAT.get(), RED_HAT.get(), ORANGE_HAT.get(), YELLOW_HAT.get(), LIME_HAT.get(), GREEN_HAT.get(),
                    CYAN_HAT.get(), LIGHT_BLUE_HAT.get(), BLUE_HAT.get(), PURPLE_HAT.get(), MAGENTA_HAT.get(), PINK_HAT.get()
            );
        });

        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.INGREDIENTS).register(entries -> {
            entries.addAfter(Items.NETHERITE_INGOT, RUBBER.get());
            entries.addAfter(Items.EGG, EGGPLE.get(), GOLDEN_EGGPLE.get());
            entries.addAfter(Items.RAW_GOLD, RAW_BISMUTH.get());
            entries.addAfter(Items.GOLD_INGOT, BISMUTH_INGOT.get());
            entries.addAfter(Items.WHEAT, COTTON_FLOWER.get());
        });
    }
}