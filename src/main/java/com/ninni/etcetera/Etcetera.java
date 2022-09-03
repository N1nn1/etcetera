package com.ninni.etcetera;

import com.google.common.collect.Maps;
import com.google.common.reflect.Reflection;
import com.ninni.etcetera.block.EtceteraBlocks;
import com.ninni.etcetera.item.EtceteraItems;
import com.ninni.etcetera.resource.EtceteraProcessResourceManager;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.LinkedHashMap;

import static com.ninni.etcetera.block.EtceteraBlocks.*;

public class Etcetera implements ModInitializer {
	public static final String MOD_ID = "etcetera";
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "item_group"), () -> new ItemStack(EtceteraItems.ETCETERA));
	public static final Logger LOGGER = LogManager.getLogger();

	public static final EtceteraProcessResourceManager CHISELLING_MANAGER = new EtceteraProcessResourceManager("chiselling");
	public static final EtceteraProcessResourceManager HAMMERING_MANAGER = new EtceteraProcessResourceManager("hammering");

	@SuppressWarnings("UnstableApiUsage")
	@Override
	public void onInitialize() {
		Reflection.initialize(
			EtceteraSoundEvents.class,
			EtceteraItems.class,
			EtceteraBlocks.class
		);

		ResourceManagerHelper resourceManager = ResourceManagerHelper.get(ResourceType.SERVER_DATA);
		resourceManager.registerReloadListener(CHISELLING_MANAGER);
		resourceManager.registerReloadListener(HAMMERING_MANAGER);

		LinkedHashMap<Block, Block> crumblingStone = Maps.newLinkedHashMap();
		crumblingStone.put(CRUMBLING_STONE, WAXED_CRUMBLING_STONE);
		crumblingStone.forEach(OxidizableBlocksRegistry::registerWaxableBlockPair);

		TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 2, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 18), ItemStack.EMPTY, new ItemStack(EtceteraItems.HANDBELL), 6, 3, 0.2f)));
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 2, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 18), ItemStack.EMPTY, new ItemStack(EtceteraItems.HANDBELL), 6, 3, 0.2f)));
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 18), ItemStack.EMPTY, new ItemStack(EtceteraItems.HANDBELL), 6, 3, 0.2f)));
		TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 3, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 16), ItemStack.EMPTY, new ItemStack(EtceteraItems.HAMMER), 6, 2, 0.2f)));

		Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MOD_ID, "nether_bismuth_ore"), NETHER_BISMUTH_ORE_CONFIGURED_FEATURE);
		Registry.register(BuiltinRegistries.PLACED_FEATURE, new Identifier(MOD_ID, "nether_bismuth_ore"), NETHER_BISMUTH_ORE_PLACED_FEATURE);
		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MOD_ID, "nether_bismuth_ore")));
	}

	private static final ConfiguredFeature<?, ?> NETHER_BISMUTH_ORE_CONFIGURED_FEATURE = new ConfiguredFeature<> (Feature.ORE, new OreFeatureConfig(OreConfiguredFeatures.NETHERRACK, NETHER_BISMUTH_ORE.getDefaultState(),9));
	public static PlacedFeature NETHER_BISMUTH_ORE_PLACED_FEATURE = new PlacedFeature( RegistryEntry.of(NETHER_BISMUTH_ORE_CONFIGURED_FEATURE), Arrays.asList( CountPlacementModifier.of(10), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.getBottom(), YOffset.fixed(64))));
}
