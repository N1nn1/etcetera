package com.ninni.etcetera;

import com.google.common.collect.Maps;
import com.google.common.reflect.Reflection;
import com.ninni.etcetera.block.EtceteraBlocks;
import com.ninni.etcetera.item.EtceteraItems;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.minecraft.block.Block;

import java.util.LinkedHashMap;
import java.util.List;

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
	}
}
