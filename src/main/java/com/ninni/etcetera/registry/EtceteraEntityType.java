package com.ninni.etcetera.registry;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.entity.*;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
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

    public static final EntityType<WeaverEntity> WEAVER = register(
            "weaver",
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(WeaverEntity::new)
                    .defaultAttributes(WeaverEntity::createAttributes)
                    .spawnGroup(SpawnGroup.MONSTER)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark)
                    .dimensions(EntityDimensions.changing(1.6f, 1.4f))
                    .trackRangeChunks(10)
    );

    public static final EntityType<GoldenGolemEntity> GOLDEN_GOLEM = register(
            "golden_golem",
            FabricEntityTypeBuilder.createMob()
                    .entityFactory(GoldenGolemEntity::new)
                    .defaultAttributes(GoldenGolemEntity::createAttributes)
                    .spawnGroup(SpawnGroup.MISC)
                    .spawnRestriction(SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, PathAwareEntity::canMobSpawn)
                    .dimensions(EntityDimensions.changing(0.35f, 0.6f))
                    .trackRangeChunks(10)
                    .trackedUpdateRate(2)
    );

    public static final EntityType<EggpleEntity> EGGPLE = register(
            "eggple",
            FabricEntityTypeBuilder.create()
                    .<EggpleEntity>entityFactory(EggpleEntity::new)
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeChunks(4)
    );

    public static final EntityType<CobwebProjectileEntity> COBWEB = register(
            "cobweb",
            FabricEntityTypeBuilder.create()
                    .<CobwebProjectileEntity>entityFactory(CobwebProjectileEntity::new)
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.3f, 0.3f))
                    .trackRangeChunks(4)
    );

    public static final EntityType<GoldenGolemItemEntity> THROWN_GOLDEN_GOLEM = register(
            "thrown_golden_golem",
            FabricEntityTypeBuilder.create()
                    .<GoldenGolemItemEntity>entityFactory(GoldenGolemItemEntity::new)
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .trackRangeChunks(4)
    );

    public static final EntityType<RubberChickenEntity> RUBBER_CHICKEN = register(
            "rubber_chicken",
            FabricEntityTypeBuilder.createLiving()
                    .<RubberChickenEntity>entityFactory(RubberChickenEntity::new)
                    .defaultAttributes(LivingEntity::createLivingAttributes)
                    .spawnGroup(SpawnGroup.MISC)
                    .dimensions(EntityDimensions.fixed(0.3F, 0.3F))
                    .trackRangeChunks(4)
    );

    private static <T extends Entity> EntityType<T> register(String id, FabricEntityTypeBuilder<T> entityType) {
        return Registry.register(Registries.ENTITY_TYPE, new Identifier(Etcetera.MOD_ID, id), entityType.build());
    }
}
