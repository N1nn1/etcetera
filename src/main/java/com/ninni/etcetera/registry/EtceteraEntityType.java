package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.entity.ChappleEntity;
import com.ninni.etcetera.entity.EggpleEntity;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.world.Heightmap;

public class EtceteraEntityType {

    public static final EntityType<TurtleRaftEntity> TURTLE_RAFT = register(
            "turtle_raft",
            FabricEntityTypeBuilder.create()
                    .<TurtleRaftEntity>entityFactory(TurtleRaftEntity::new)
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.8f, 0.5625f))
                    .trackRangeChunks(10)
    );

    public static final EntityType<ChappleEntity> CHAPPLE = register(
            "chapple",
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(ChappleEntity::new)
                    .defaultAttributes(ChickenEntity::createChickenAttributes)
                    .spawnGroup(SpawnGroup.CREATURE)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE_WG, AnimalEntity::canMobSpawn)
                    .dimensions(EntityDimensions.changing(0.4f, 0.7f))
                    .trackRangeChunks(10)
    );

    public static final EntityType<EggpleEntity> EGGPLE = register(
            "eggple",
            FabricEntityTypeBuilder.create()
                    .<EggpleEntity>entityFactory(EggpleEntity::new)
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeChunks(4)
    );


    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, id), entityType.build());
    }
}
