package com.ninni.etcetera;

import com.ninni.etcetera.block.EtceteraBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class EtceteraClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		BlockRenderLayerMap.INSTANCE.putBlocks(
			RenderLayer.getCutout(),

			EtceteraBlocks.POTTED_BOUQUET
		);
	}
}
