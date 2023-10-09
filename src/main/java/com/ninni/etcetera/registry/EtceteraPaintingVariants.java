package com.ninni.etcetera.registry;

import net.minecraft.entity.decoration.painting.PaintingVariant;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class EtceteraPaintingVariants {

    public static final PaintingVariant CALAMITY = register("calamity", new PaintingVariant(48, 32));

    private static PaintingVariant register(String id, PaintingVariant paintingVariant) {
        return Registry.register(Registries.PAINTING_VARIANT, new Identifier(MOD_ID, id), paintingVariant);
    }
}
