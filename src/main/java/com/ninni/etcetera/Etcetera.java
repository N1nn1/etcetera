package com.ninni.etcetera;

import com.google.common.collect.Maps;
import com.google.common.reflect.Reflection;
import com.ninni.etcetera.block.EtceteraBlocks;
import com.ninni.etcetera.item.EtceteraItems;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreConfiguredFeatures;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.placementmodifier.CountPlacementModifier;
import net.minecraft.world.gen.placementmodifier.HeightRangePlacementModifier;
import net.minecraft.world.gen.placementmodifier.SquarePlacementModifier;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static com.ninni.etcetera.block.EtceteraBlocks.*;

public class Etcetera implements ModInitializer {
	public static final String MOD_ID = "etcetera";

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public void onInitialize() {
		Reflection.initialize(
			EtceteraSoundEvents.class,
			EtceteraItems.class,
			EtceteraBlocks.class
		);

		LinkedHashMap<Block, Block> crumblingStone = Maps.newLinkedHashMap();
		crumblingStone.put(CRUMBLING_STONE, WAXED_CRUMBLING_STONE);
		crumblingStone.forEach(OxidizableBlocksRegistry::registerWaxableBlockPair);

		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MOD_ID, "nether_bismuth_ore"), NETHER_BISMUTH_ORE_CONFIGURED_FEATURE);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(MOD_ID, "nether_bismuth_ore"), NETHER_BISMUTH_ORE_PLACED_FEATURE);
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MOD_ID, "nether_bismuth_ore")));
	}

	private static final ConfiguredFeature<?, ?> NETHER_BISMUTH_ORE_CONFIGURED_FEATURE = new ConfiguredFeature<> (Feature.ORE, new OreFeatureConfig(OreConfiguredFeatures.NETHERRACK, NETHER_BISMUTH_ORE.getDefaultState(),9));
	public static PlacedFeature NETHER_BISMUTH_ORE_PLACED_FEATURE = new PlacedFeature( RegistryEntry.of(NETHER_BISMUTH_ORE_CONFIGURED_FEATURE), Arrays.asList( CountPlacementModifier.of(20), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))));
}
