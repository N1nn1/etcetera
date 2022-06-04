package com.ninni.etcetera;

import com.google.common.reflect.Reflection;
import com.ninni.etcetera.block.EtceteraBlocks;
import com.ninni.etcetera.item.EtceteraItems;
import com.ninni.etcetera.sound.EtceteraSoundEvents;
import net.fabricmc.api.ModInitializer;

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
	}
}
