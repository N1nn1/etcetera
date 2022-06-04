package com.ninni.etcetera.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static com.ninni.etcetera.Etcetera.*;

@SuppressWarnings("unused")
public class EtceteraBlocks {

    public static final Block BOUQUET = register("bouquet", new BouquetBlock(AbstractBlock.Settings.of(Material.PLANT)));
    public static final Block POTTED_BOUQUET = register("potted_bouquet", new FlowerPotBlock(BOUQUET, AbstractBlock.Settings.of(Material.DECORATION).breakInstantly().nonOpaque()));


    private static Block register(String id, Block block) {
        return Registry.register(Registry.BLOCK, new Identifier(MOD_ID, id), block);
    }
}
