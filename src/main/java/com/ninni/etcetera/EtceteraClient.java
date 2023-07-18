package com.ninni.etcetera;

import com.ninni.etcetera.registry.EtceteraVanillaIntegration;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class EtceteraClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		EtceteraVanillaIntegration.clientInit();
	}
}