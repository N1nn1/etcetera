package com.ninni.etcetera;

import com.google.common.reflect.Reflection;
import com.ninni.etcetera.registry.*;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Etcetera implements ModInitializer {
	public static final String MOD_ID = "etcetera";
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public void onInitialize() {
		Reflection.initialize(
				EtceteraSoundEvents.class,
				EtceteraStats.class,
				EtceteraBlockEntityType.class,
				EtceteraScreenHandlerType.class,
				EtceteraStatusEffects.class,
				EtceteraEntityType.class,
				EtceteraCreativeModeTab.class,
				EtceteraItems.class,
				EtceteraBlocks.class
		);

		EtceteraVanillaIntegration.serverInit();
		EtceteraWorldgen.init();
	}
}