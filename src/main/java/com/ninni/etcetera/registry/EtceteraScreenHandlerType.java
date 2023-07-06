package com.ninni.etcetera.registry;

import com.ninni.etcetera.client.gui.screen.PricklyCanScreenHandler;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class EtceteraScreenHandlerType {

    public static final ScreenHandlerType<PricklyCanScreenHandler> PRICKLY_CAN = simple("decorators_table", PricklyCanScreenHandler::createGeneric9x3);

    private static <T extends ScreenHandler> ScreenHandlerType<T> simple(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(MOD_ID, id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }
}
