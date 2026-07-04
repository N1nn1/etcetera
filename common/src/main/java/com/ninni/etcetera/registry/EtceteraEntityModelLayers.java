package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;


public interface EtceteraEntityModelLayers {

    ModelLayerLocation TURTLE_RAFT = main("turtle_raft");
    ModelLayerLocation CHAPPLE = main("chapple");
    ModelLayerLocation WEAVER = main("weaver");
    ModelLayerLocation COBWEB = main("cobweb");
    ModelLayerLocation PLAYER_COTTON = main("cotton");
    ModelLayerLocation GOLDEN_GOLEM = main("golden_golem");
    ModelLayerLocation RUBBER_CHICKEN = main("rubber_chicken");

    private static ModelLayerLocation register(String id, String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, id), name);
    }

    private static ModelLayerLocation main(String id) {
        return register(id, "main");
    }
}