package com.ninni.etcetera.entity;

import com.ninni.etcetera.Etcetera;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EtceteraEntityType {

    public static final EntityType<TurtleRaftEntity> TURTLE_RAFT = register(
            "turtle_raft",
            FabricEntityTypeBuilder.create()
                    .<TurtleRaftEntity>entityFactory(TurtleRaftEntity::new)
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.8f, 0.5625f))
                    .trackRangeChunks(10)
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType) {
        return Registry.register(Registry.ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, id), entityType.build());
    }
}
