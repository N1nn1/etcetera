package com.ninni.etcetera.entity;

import com.ninni.etcetera.Etcetera;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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

    public static final EntityType<SnailEntity> SNAIL = register(
            "snail",
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(SnailEntity::new)
                    .defaultAttributes(SnailEntity::createAttributes)
                    .spawnGroup(SpawnGroup.CREATURE)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE_WG, SnailEntity::canSpawn)
                    .dimensions(EntityDimensions.changing(0.8F, 0.8F))
                    .trackRangeChunks(10)
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, id), entityType.build());
    }
}
