package com.ninni.etcetera;

import com.ninni.etcetera.events.MiscEvents;
import com.ninni.etcetera.events.MobEvents;
import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import com.ninni.etcetera.registry.EtceteraBlocks;
import com.ninni.etcetera.registry.EtceteraCreativeModeTab;
import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraScreenHandlerType;
import com.ninni.etcetera.registry.EtceteraSoundEvents;
import com.ninni.etcetera.registry.EtceteraStats;
import com.ninni.etcetera.registry.EtceteraVanillaIntegration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Etcetera.MOD_ID)
public class Etcetera {
	public static final String MOD_ID = "etcetera";
	public static final Logger LOGGER = LogManager.getLogger();

	public Etcetera() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus eventBus = MinecraftForge.EVENT_BUS;
		modEventBus.addListener(this::commonSetup);

		EtceteraBlocks.BLOCKS.register(modEventBus);
		EtceteraBlockEntityType.BLOCK_ENTITY_TYPES.register(modEventBus);
		EtceteraEntityType.ENTITY_TYPES.register(modEventBus);
		EtceteraItems.ITEMS.register(modEventBus);
		EtceteraScreenHandlerType.MENU_TYPES.register(modEventBus);
		EtceteraStats.STATS.register(modEventBus);
		EtceteraCreativeModeTab.CREATIVE_MODE_TABS.register(modEventBus);
		EtceteraSoundEvents.SOUND_EVENTS.register(modEventBus);

		eventBus.register(this);
		eventBus.register(new MiscEvents());
		eventBus.register(new MobEvents());
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(EtceteraVanillaIntegration::serverInit);
	}

}