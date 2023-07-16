package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.block.entity.ItemStandBlockEntity;
import com.ninni.etcetera.block.entity.PricklyCanBlockEntity;
import com.ninni.etcetera.block.entity.TintedLightBulbBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EtceteraBlockEntityType {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Etcetera.MOD_ID);

    public static final RegistryObject<BlockEntityType<ItemStandBlockEntity>> ITEM_STAND = BLOCK_ENTITY_TYPES.register(
            "item_stand", () ->
            BlockEntityType.Builder.of(ItemStandBlockEntity::new,
                    EtceteraBlocks.ITEM_STAND.get(),
                    EtceteraBlocks.GLOW_ITEM_STAND.get()
            ).build(null)
    );
    public static final RegistryObject<BlockEntityType<TintedLightBulbBlockEntity>> TINTED_LIGHT_BULB = BLOCK_ENTITY_TYPES.register(
            "tinted_light_bulb", () ->
            BlockEntityType.Builder.of(TintedLightBulbBlockEntity::new,
                    EtceteraBlocks.TINTED_LIGHT_BULB.get()
            ).build(null)
    );
    public static final RegistryObject<BlockEntityType<PricklyCanBlockEntity>> PRICKLY_CAN = BLOCK_ENTITY_TYPES.register(
            "prickly_can", () ->
            BlockEntityType.Builder.of(PricklyCanBlockEntity::new,
                    EtceteraBlocks.PRICKLY_CAN.get()
            ).build(null)
    );
}
