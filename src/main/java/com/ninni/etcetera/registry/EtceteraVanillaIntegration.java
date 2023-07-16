package com.ninni.etcetera.registry;

import com.ninni.etcetera.network.EtceteraNetwork;
import com.ninni.etcetera.resource.EtceteraProcessResourceManager;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraft.world.item.HoneycombItem;
import net.minecraft.world.level.block.ComposterBlock;

import static com.ninni.etcetera.registry.EtceteraBlocks.CRUMBLING_STONE;
import static com.ninni.etcetera.registry.EtceteraBlocks.WAXED_CRUMBLING_STONE;

public class EtceteraVanillaIntegration {

    public static final EtceteraProcessResourceManager CHISELLING_MANAGER = new EtceteraProcessResourceManager("chiselling");
    public static final EtceteraProcessResourceManager HAMMERING_MANAGER = new EtceteraProcessResourceManager("hammering");

    public static void serverInit() {
        EtceteraNetwork.init();
        registerCompostables();
        EtceteraStats.STATS.getEntries().forEach(resourceLocationRegistryObject -> Stats.CUSTOM.get(resourceLocationRegistryObject.get(), StatFormatter.DEFAULT));
    }

    private static void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(EtceteraItems.BOUQUET.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(EtceteraItems.COTTON_SEEDS.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(EtceteraItems.COTTON_FLOWER.get(), 0.65f);
    }

}
