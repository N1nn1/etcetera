package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.client.gui.screen.PricklyCanScreenHandler;
import com.ninni.etcetera.platform.Services;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class EtceteraScreenHandlerType {
    public static final RegistrationProvider<MenuType<?>> SCREEN_HANDLER_TYPES = RegistrationProvider.get(Registries.MENU, Constants.MOD_ID);
    public static final MenuType<PricklyCanScreenHandler> PRICKLY_CAN = simple("prickly_can", PricklyCanScreenHandler::createGeneric9x3);

    private static <T extends AbstractContainerMenu> MenuType<T> simple(String id, ScreenHandlerFactory<T> factory) {
        MenuType<T> menuType = Services.PLATFORM.createMenuType(factory);
        SCREEN_HANDLER_TYPES.register(id, () -> menuType);
        return menuType;
    }

    @FunctionalInterface
    public interface ScreenHandlerFactory<T extends AbstractContainerMenu> {
        T create(int syncId, Inventory inventory);
    }

    public static void init() {
    }
}