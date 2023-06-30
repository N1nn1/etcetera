package com.ninni.etcetera;

import static com.ninni.etcetera.block.EtceteraBlocks.CRUMBLING_STONE;
import static com.ninni.etcetera.block.EtceteraBlocks.WAXED_CRUMBLING_STONE;

import java.util.LinkedHashMap;

import com.ninni.etcetera.client.gui.screen.EtceteraScreenHandlerType;
import com.ninni.etcetera.entity.EggpleEntity;
import com.ninni.etcetera.network.EtceteraNetwork;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.Util;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Maps;
import com.google.common.reflect.Reflection;
import com.ninni.etcetera.block.EtceteraBlocks;
import com.ninni.etcetera.block.entity.EtceteraBlockEntityType;
import com.ninni.etcetera.entity.EtceteraEntityType;
import com.ninni.etcetera.item.EtceteraItems;
import com.ninni.etcetera.resource.EtceteraProcessResourceManager;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import com.ninni.etcetera.stat.EtceteraStats;

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
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.gen.GenerationStep;

public class Etcetera implements ModInitializer {

	public static final String MOD_ID = "etcetera";
	public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "item_group"), () -> new ItemStack(EtceteraItems.ETCETERA));
	public static final Logger LOGGER = LogManager.getLogger();

	public static final EtceteraProcessResourceManager CHISELLING_MANAGER = new EtceteraProcessResourceManager("chiselling");
	public static final EtceteraProcessResourceManager HAMMERING_MANAGER = new EtceteraProcessResourceManager("hammering");

	@Override
	public void onInitialize() {
		Reflection.initialize(
				EtceteraSoundEvents.class,
				EtceteraStats.class,
				EtceteraBlockEntityType.class,
				EtceteraScreenHandlerType.class,
				EtceteraEntityType.class,
				EtceteraItems.class,
				EtceteraBlocks.class
		);

		EtceteraNetwork.initCommon();

		DispenserBlock.registerBehavior(EtceteraItems.EGGPLE, new ProjectileDispenserBehavior(){
			@Override
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				return Util.make(new EggpleEntity(world, position.getX(), position.getY(), position.getZ()), entity -> entity.setItem(stack));
			}
		});
		DispenserBlock.registerBehavior(EtceteraItems.GOLDEN_EGGPLE, new ProjectileDispenserBehavior(){
			@Override
			protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
				return Util.make(new EggpleEntity(world, position.getX(), position.getY(), position.getZ()), entity -> entity.setItem(stack));
			}
		});

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

		BiomeModifications.addFeature(BiomeSelectors.foundInTheNether(), GenerationStep.Feature.UNDERGROUND_ORES, RegistryKey.of(Registry.PLACED_FEATURE_KEY, new Identifier(MOD_ID, "nether_bismuth_ore")));
	}
}