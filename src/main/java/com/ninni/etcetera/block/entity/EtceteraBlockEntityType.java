package com.ninni.etcetera.block.entity;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.block.EtceteraBlocks;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EtceteraBlockEntityType {
    public static final BlockEntityType<ItemStandBlockEntity> ITEM_STAND = Registry.register(
            Registry.BLOCK_ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, "item_stand"),
            FabricBlockEntityTypeBuilder.create(ItemStandBlockEntity::new,
                    EtceteraBlocks.ITEM_STAND
            ).build(null)
    );
    public static final BlockEntityType<TintedLightBulbBlockEntity> TINTED_LIGHT_BULB = Registry.register(
            Registry.BLOCK_ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, "tinted_light_bulb"),
            FabricBlockEntityTypeBuilder.create(TintedLightBulbBlockEntity::new,
                    EtceteraBlocks.TINTED_LIGHT_BULB
            ).build(null)
    );
}
