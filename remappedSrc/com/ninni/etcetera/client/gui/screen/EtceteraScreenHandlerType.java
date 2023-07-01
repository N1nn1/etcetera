package com.ninni.etcetera.client.gui.screen;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.MOD_ID;

public class EtceteraScreenHandlerType {

    public static final ScreenHandlerType<PricklyCanScreenHandler> PRICKLY_CAN = simple("decorators_table", PricklyCanScreenHandler::createGeneric9x3);

    private static <T extends ScreenHandler> ScreenHandlerType<T> simple(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registry.SCREEN_HANDLER, new Identifier(MOD_ID, id), new ScreenHandlerType<>(factory));
    }
}
