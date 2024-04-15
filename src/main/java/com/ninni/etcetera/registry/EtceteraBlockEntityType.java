package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.block.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class EtceteraBlockEntityType {
    public static final BlockEntityType<ItemStandBlockEntity> ITEM_STAND = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, "item_stand"),
            FabricBlockEntityTypeBuilder.create(ItemStandBlockEntity::new,
                    EtceteraBlocks.ITEM_STAND,
                    EtceteraBlocks.GLOW_ITEM_STAND
            ).build(null)
    );
    public static final BlockEntityType<TintedLightBulbBlockEntity> TINTED_LIGHT_BULB = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, "tinted_light_bulb"),
            FabricBlockEntityTypeBuilder.create(TintedLightBulbBlockEntity::new,
                    EtceteraBlocks.TINTED_LIGHT_BULB
            ).build(null)
    );
    public static final BlockEntityType<PricklyCanBlockEntity> PRICKLY_CAN = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, "prickly_can"),
            FabricBlockEntityTypeBuilder.create(PricklyCanBlockEntity::new,
                    EtceteraBlocks.PRICKLY_CAN
            ).build(null)
    );
    public static final BlockEntityType<DreamCatcherBlockEntity> DREAM_CATCHER = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, "dream_catcher"),
            FabricBlockEntityTypeBuilder.create(DreamCatcherBlockEntity::new,
                    EtceteraBlocks.DREAM_CATCHER
            ).build(null)
    );
    public static final BlockEntityType<RedstoneWireComparatorBlockEntity> REDSTONE_WIRE_COMPARATOR = Registry.register(
            Registries.BLOCK_ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, "redstone_wire_comparator"),
            FabricBlockEntityTypeBuilder.create(RedstoneWireComparatorBlockEntity::new,
                    EtceteraBlocks.REDSTONE_WIRE_COMPARATOR
            ).build(null)
    );
}
