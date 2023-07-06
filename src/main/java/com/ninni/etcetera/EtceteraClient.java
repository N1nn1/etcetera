package com.ninni.etcetera;

import com.ninni.etcetera.registry.EtceteraVanillaIntegration;
import net.fabricmc.api.ClientModInitializer;

public class EtceteraClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EtceteraVanillaIntegration.clientInit();
	}
}