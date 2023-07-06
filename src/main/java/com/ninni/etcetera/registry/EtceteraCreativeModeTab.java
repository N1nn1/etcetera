package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.ninni.etcetera.registry.EtceteraItems.*;

public class EtceteraCreativeModeTab {

    public static final ItemGroup ITEM_GROUP = register("item_group", FabricItemGroup.builder().icon(ETCETERA::getDefaultStack).displayName(Text.translatable("spawn.item_group")).entries((featureFlagSet, output) -> {
                output.add(RAW_BISMUTH_BLOCK);
                output.add(BISMUTH_BLOCK);
                output.add(BISMUTH_BARS);
                output.add(NETHER_BISMUTH_ORE);
                output.add(RAW_BISMUTH);
                output.add(BISMUTH_INGOT);
                output.add(IRIDESCENT_GLASS);
                output.add(IRIDESCENT_TERRACOTTA);
                output.add(IRIDESCENT_CONCRETE);
                output.add(IRIDESCENT_LANTERN);

                output.add(CHISEL);
                output.add(WRENCH);
                output.add(HAMMER);
                output.add(HANDBELL);

                output.add(DRUM);

                output.add(DICE);

                output.add(FRAME);

                output.add(PRICKLY_CAN);

                output.add(BOUQUET);
                output.add(TERRACOTTA_VASE);

                output.add(ITEM_STAND);

                output.add(SQUID_LAMP);
                output.add(TIDAL_HELMET);
                output.add(TURTLE_RAFT);

                output.add(CRUMBLING_STONE);
                output.add(WAXED_CRUMBLING_STONE);
                output.add(LEVELED_STONE);
                output.add(LEVELED_STONE_STAIRS);
                output.add(LEVELED_STONE_SLAB);

                output.add(LIGHT_BULB);
                output.add(TINTED_LIGHT_BULB);

                output.add(CHAPPLE_SPAWN_EGG);
                output.add(EGGPLE);
                output.add(GOLDEN_EGGPLE);


            }).build()
    );

    private static ItemGroup register(String id, ItemGroup tab) {
        return Registry.register(Registries.ITEM_GROUP, new Identifier(Etcetera.MOD_ID, id), tab);
    }
}
