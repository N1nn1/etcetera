package com.ninni.etcetera;

import com.ninni.etcetera.block.EtceteraBlocks;
import com.ninni.etcetera.client.model.entity.TidalArmorRenderer;
import com.ninni.etcetera.client.render.TidalEyeRenderer;
import com.ninni.etcetera.item.EtceteraItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.render.RenderLayer;

public class EtceteraClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
	ArmorRenderer.register(new TidalArmorRenderer(), EtceteraItems.TIDAL_HELMET);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
			EtceteraBlocks.IRIDESCENT_GLASS
		);
		BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
			EtceteraBlocks.BISMUTH_BARS,
			EtceteraBlocks.COMPACTED_DRIPSTONE,
			EtceteraBlocks.BOUQUET,
			EtceteraBlocks.POTTED_BOUQUET,
			EtceteraBlocks.FRAME
		);

		HudRenderCallback.EVENT.register(new TidalEyeRenderer());
	}
}
