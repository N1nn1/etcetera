package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import com.ninni.etcetera.platform.services.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;

public class EtceteraStats {
    private static final RegistrationProvider<ResourceLocation> STATS =
            RegistrationProvider.get(Registries.CUSTOM_STAT, Constants.MOD_ID);

    public static final RegistryObject<ResourceLocation> ROTATE_DICE =
            STATS.register("rotate_dice", () -> ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "rotate_dice"));

    public static final RegistryObject<ResourceLocation> INTERACT_WITH_ITEM_STAND =
            STATS.register("interact_with_item_stand", () -> ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "interact_with_item_stand"));

    public static final RegistryObject<ResourceLocation> OPEN_PRICKLY_CAN =
            STATS.register("open_prickly_can", () -> ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "open_prickly_can"));

    public static void init() {
    }

    public static void setup() {
        registerStat(ROTATE_DICE, StatFormatter.DEFAULT);
        registerStat(INTERACT_WITH_ITEM_STAND, StatFormatter.DEFAULT);
        registerStat(OPEN_PRICKLY_CAN, StatFormatter.DEFAULT);
    }

    private static void registerStat(RegistryObject<ResourceLocation> stat, StatFormatter formatter) {
        Stats.CUSTOM.get(stat.get(), formatter);
    }
}