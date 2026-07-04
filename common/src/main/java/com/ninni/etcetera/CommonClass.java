package com.ninni.etcetera;

import com.ninni.etcetera.config.ModConfig;
import com.ninni.etcetera.registry.*;

public class CommonClass {

    public static void init() {
        ModConfig.load();
        EtceteraSoundEvents.init();
        EtceteraStats.init();
        EtceteraBlockEntityType.init();
        EtceteraScreenHandlerType.init();
        EtceteraStatusEffects.init();
        EtceteraEntityType.init();
        EtceteraCreativeModeTab.init();
        EtceteraItems.init();
        EtceteraBlocks.init();
        EtceteraParticleTypes.init();
    }

    public static void setup() {
        EtceteraStats.setup();
        EtceteraVanillaIntegration.serverInit();
    }
}