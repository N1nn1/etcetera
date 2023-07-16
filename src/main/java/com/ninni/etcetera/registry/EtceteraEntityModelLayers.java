package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class EtceteraEntityModelLayers {

    public static final ModelLayerLocation TURTLE_RAFT = main("turtle_raft");
    public static final ModelLayerLocation CHAPPLE = main("chapple");
    public static final ModelLayerLocation PLAYER_COTTON = main("cotton");

    private static ModelLayerLocation main(String id) {
        return new ModelLayerLocation(new ResourceLocation(Etcetera.MOD_ID, id), "main");
    }
}
