package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.block.entity.*;
import com.ninni.etcetera.platform.Services;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import com.ninni.etcetera.platform.services.RegistryObject;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class EtceteraBlockEntityType {
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITY_TYPES = RegistrationProvider.get(Registries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);

    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, Supplier<BlockEntityType<T>> typeSupplier) {
        return BLOCK_ENTITY_TYPES.register(id, typeSupplier);
    }

    public static void init() {
    }

    public static final RegistryObject<BlockEntityType<ItemStandBlockEntity>> ITEM_STAND = register(
            "item_stand",
            () -> Services.PLATFORM.createBlockEntityType(
                    ItemStandBlockEntity::new,
                    EtceteraBlocks.ITEM_STAND.get(),
                    EtceteraBlocks.GLOW_ITEM_STAND.get()
            )
    );

    public static final RegistryObject<BlockEntityType<TintedLightBulbBlockEntity>> TINTED_LIGHT_BULB = register(
            "tinted_light_bulb",
            () -> Services.PLATFORM.createBlockEntityType(
                    TintedLightBulbBlockEntity::new,
                    EtceteraBlocks.TINTED_LIGHT_BULB.get()
            )
    );

    public static final RegistryObject<BlockEntityType<PricklyCanBlockEntity>> PRICKLY_CAN = register(
            "prickly_can",
            () -> Services.PLATFORM.createBlockEntityType(
                    PricklyCanBlockEntity::new,
                    EtceteraBlocks.PRICKLY_CAN.get()
            )
    );

    public static final RegistryObject<BlockEntityType<DreamCatcherBlockEntity>> DREAM_CATCHER = register(
            "dream_catcher",
            () -> Services.PLATFORM.createBlockEntityType(
                    DreamCatcherBlockEntity::new,
                    EtceteraBlocks.DREAM_CATCHER.get()
            )
    );

    public static final RegistryObject<BlockEntityType<RedstoneWireComparatorBlockEntity>> REDSTONE_WIRE_COMPARATOR = register(
            "redstone_wire_comparator",
            () -> Services.PLATFORM.createBlockEntityType(
                    RedstoneWireComparatorBlockEntity::new,
                    EtceteraBlocks.REDSTONE_WIRE_COMPARATOR.get()
            )
    );

}