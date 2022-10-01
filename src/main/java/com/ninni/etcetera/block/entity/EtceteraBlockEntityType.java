package com.ninni.etcetera.block.entity;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.block.EtceteraBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EtceteraBlockEntityType {
    public static final BlockEntityType<SiltPotBlockEntity> SILT_POT = Registry.register(
        Registry.BLOCK_ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, "silt_pot"),
        FabricBlockEntityTypeBuilder.create(SiltPotBlockEntity::new,
            EtceteraBlocks.SILT_POT,
            EtceteraBlocks.BLACK_SILT_POT,
            EtceteraBlocks.BLUE_SILT_POT,
            EtceteraBlocks.BROWN_SILT_POT,
            EtceteraBlocks.CYAN_SILT_POT,
            EtceteraBlocks.GRAY_SILT_POT,
            EtceteraBlocks.GREEN_SILT_POT,
            EtceteraBlocks.LIGHT_BLUE_SILT_POT,
            EtceteraBlocks.LIGHT_GRAY_SILT_POT,
            EtceteraBlocks.LIME_SILT_POT,
            EtceteraBlocks.MAGENTA_SILT_POT,
            EtceteraBlocks.ORANGE_SILT_POT,
            EtceteraBlocks.PINK_SILT_POT,
            EtceteraBlocks.PURPLE_SILT_POT,
            EtceteraBlocks.RED_SILT_POT,
            EtceteraBlocks.WHITE_SILT_POT,
            EtceteraBlocks.YELLOW_SILT_POT
        ).build(null)
    );

}
