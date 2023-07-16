package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.entity.ChappleEntity;
import com.ninni.etcetera.entity.EggpleEntity;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EtceteraEntityType {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Etcetera.MOD_ID);

    public static final RegistryObject<EntityType<TurtleRaftEntity>> TURTLE_RAFT = ENTITY_TYPES.register(
            "turtle_raft", () ->
            EntityType.Builder.<TurtleRaftEntity>of(TurtleRaftEntity::new, MobCategory.MISC).sized(0.8F, 0.5625F).clientTrackingRange(10).build(new ResourceLocation(Etcetera.MOD_ID, "turtle_raft").toString())
    );

    public static final RegistryObject<EntityType<ChappleEntity>> CHAPPLE = ENTITY_TYPES.register(
            "chapple", () ->
            EntityType.Builder.of(ChappleEntity::new, MobCategory.CREATURE).sized(0.4f, 0.7f).clientTrackingRange(10).build(new ResourceLocation(Etcetera.MOD_ID, "chapple").toString())
    );

    public static final RegistryObject<EntityType<EggpleEntity>> EGGPLE = ENTITY_TYPES.register(
            "eggple", () ->
            EntityType.Builder.<EggpleEntity>of(EggpleEntity::new, MobCategory.MISC).sized(0.25f, 0.25f).clientTrackingRange(4).build(new ResourceLocation(Etcetera.MOD_ID, "eggple").toString())
    );

}
