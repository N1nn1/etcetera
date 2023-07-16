package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EtceteraCreativeModeTab {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Etcetera.MOD_ID);

    public static final RegistryObject<CreativeModeTab> ITEM_GROUP = CREATIVE_MODE_TABS.register("item_group", () -> CreativeModeTab.builder().icon(EtceteraItems.ETCETERA.get()::getDefaultInstance).title(Component.translatable("etcetera.item_group")).displayItems((featureFlagSet, output) -> {
                output.accept(EtceteraItems.RAW_BISMUTH_BLOCK.get());
                output.accept(EtceteraItems.BISMUTH_BLOCK.get());
                output.accept(EtceteraItems.BISMUTH_BARS.get());
                output.accept(EtceteraItems.NETHER_BISMUTH_ORE.get());
                output.accept(EtceteraItems.RAW_BISMUTH.get());
                output.accept(EtceteraItems.BISMUTH_INGOT.get());
                output.accept(EtceteraItems.IRIDESCENT_GLASS.get());
                output.accept(EtceteraItems.IRIDESCENT_GLASS_PANE.get());
                output.accept(EtceteraItems.IRIDESCENT_TERRACOTTA.get());
                output.accept(EtceteraItems.IRIDESCENT_GLAZED_TERRACOTTA.get());
                output.accept(EtceteraItems.IRIDESCENT_CONCRETE.get());
                output.accept(EtceteraItems.IRIDESCENT_WOOL.get());
                output.accept(EtceteraItems.IRIDESCENT_LANTERN.get());

                output.accept(EtceteraItems.CHISEL.get());
                output.accept(EtceteraItems.WRENCH.get());
                output.accept(EtceteraItems.HAMMER.get());
                output.accept(EtceteraItems.HANDBELL.get());

                output.accept(EtceteraItems.DRUM.get());

                output.accept(EtceteraItems.DICE.get());

                output.accept(EtceteraItems.FRAME.get());

                output.accept(EtceteraItems.PRICKLY_CAN.get());

                output.accept(EtceteraItems.BOUQUET.get());
                output.accept(EtceteraItems.TERRACOTTA_VASE.get());

                output.accept(EtceteraItems.ITEM_STAND.get());
                output.accept(EtceteraItems.GLOW_ITEM_STAND.get());

                output.accept(EtceteraItems.SQUID_LAMP.get());
                output.accept(EtceteraItems.TIDAL_HELMET.get());
                output.accept(EtceteraItems.TURTLE_RAFT.get());

                output.accept(EtceteraItems.CRUMBLING_STONE.get());
                output.accept(EtceteraItems.WAXED_CRUMBLING_STONE.get());
                output.accept(EtceteraItems.LEVELED_STONE.get());
                output.accept(EtceteraItems.LEVELED_STONE_STAIRS.get());
                output.accept(EtceteraItems.LEVELED_STONE_SLAB.get());

                output.accept(EtceteraItems.LIGHT_BULB.get());
                output.accept(EtceteraItems.TINTED_LIGHT_BULB.get());

                output.accept(EtceteraItems.CHAPPLE_SPAWN_EGG.get());
                output.accept(EtceteraItems.EGGPLE.get());
                output.accept(EtceteraItems.GOLDEN_EGGPLE.get());

                output.accept(EtceteraItems.COTTON_SEEDS.get());
                output.accept(EtceteraItems.COTTON_FLOWER.get());
                output.accept(EtceteraItems.WHITE_SWEATER.get());
                output.accept(EtceteraItems.LIGHT_GRAY_SWEATER.get());
                output.accept(EtceteraItems.GRAY_SWEATER.get());
                output.accept(EtceteraItems.BLACK_SWEATER.get());
                output.accept(EtceteraItems.BROWN_SWEATER.get());
                output.accept(EtceteraItems.RED_SWEATER.get());
                output.accept(EtceteraItems.ORANGE_SWEATER.get());
                output.accept(EtceteraItems.YELLOW_SWEATER.get());
                output.accept(EtceteraItems.LIME_SWEATER.get());
                output.accept(EtceteraItems.GREEN_SWEATER.get());
                output.accept(EtceteraItems.CYAN_SWEATER.get() );
                output.accept(EtceteraItems.LIGHT_BLUE_SWEATER.get());
                output.accept(EtceteraItems.BLUE_SWEATER.get());
                output.accept(EtceteraItems.PURPLE_SWEATER.get());
                output.accept(EtceteraItems.MAGENTA_SWEATER.get());
                output.accept(EtceteraItems.PINK_SWEATER.get());
                output.accept(EtceteraItems.TRADER_ROBE.get());
                output.accept(EtceteraItems.WHITE_HAT.get());
                output.accept(EtceteraItems.LIGHT_GRAY_HAT.get());
                output.accept(EtceteraItems.GRAY_HAT.get());
                output.accept(EtceteraItems.BLACK_HAT.get());
                output.accept(EtceteraItems.BROWN_HAT.get());
                output.accept(EtceteraItems.RED_HAT.get());
                output.accept(EtceteraItems.ORANGE_HAT.get());
                output.accept(EtceteraItems.YELLOW_HAT.get());
                output.accept(EtceteraItems.LIME_HAT.get());
                output.accept(EtceteraItems.GREEN_HAT.get());
                output.accept(EtceteraItems.CYAN_HAT.get() );
                output.accept(EtceteraItems.LIGHT_BLUE_HAT.get());
                output.accept(EtceteraItems.BLUE_HAT.get());
                output.accept(EtceteraItems.PURPLE_HAT.get());
                output.accept(EtceteraItems.MAGENTA_HAT.get());
                output.accept(EtceteraItems.PINK_HAT.get());
                output.accept(EtceteraItems.TRADER_HOOD.get());


            }).build()
    );
}
