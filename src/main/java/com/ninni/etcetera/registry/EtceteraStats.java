package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EtceteraStats {
    public static final DeferredRegister<ResourceLocation> STATS = DeferredRegister.create(Registries.CUSTOM_STAT, Etcetera.MOD_ID);

    public static final RegistryObject<ResourceLocation> ROTATE_DICE = register("rotate_dice", StatFormatter.DEFAULT);
    public static final RegistryObject<ResourceLocation> INTERACT_WITH_ITEM_STAND = register("interact_with_item_stand", StatFormatter.DEFAULT);
    public static final RegistryObject<ResourceLocation> OPEN_PRICKLY_CAN = register("open_prickly_can", StatFormatter.DEFAULT);

    private static RegistryObject<ResourceLocation> register(String id, StatFormatter formatter) {
        ResourceLocation identifier = new ResourceLocation(id);
        return STATS.register(id, () -> identifier);
    }
}
