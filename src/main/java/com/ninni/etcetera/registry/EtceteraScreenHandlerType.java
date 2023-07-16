package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.client.gui.screen.PricklyCanScreenHandler;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EtceteraScreenHandlerType {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, Etcetera.MOD_ID);

    public static final RegistryObject<MenuType<PricklyCanScreenHandler>> PRICKLY_CAN = MENU_TYPES.register("prickly_can", () -> new MenuType<>(PricklyCanScreenHandler::createGeneric9x3, FeatureFlags.VANILLA_SET));

}
