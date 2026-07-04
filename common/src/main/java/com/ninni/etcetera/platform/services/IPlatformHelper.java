package com.ninni.etcetera.platform.services;

import com.ninni.etcetera.registry.EtceteraScreenHandlerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootTable;

import java.nio.file.Path;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public interface IPlatformHelper {

    /**
     * Gets the name of the current platform
     *
     * @return The name of the current platform.
     */
    String getPlatformName();

    /**
     * Checks if a mod with the given id is loaded.
     *
     * @param modId The mod to check if it is loaded.
     * @return True if the mod is loaded, false otherwise.
     */
    boolean isModLoaded(String modId);

    /**
     * Check if the game is currently in a development environment.
     *
     * @return True if in a development environment, false otherwise.
     */
    boolean isDevelopmentEnvironment();

    /**
     * Gets the name of the environment type as a string.
     *
     * @return The name of the environment type.
     */
    default String getEnvironmentName() {
        return isDevelopmentEnvironment() ? "development" : "production";
    }

    /**
     * Gets the configuration directory for the current platform.
     *
     * @return The path to the config directory.
     */
    Path getConfigDirectory();

    /**
     * Checks if the code is running on the physical client.
     *
     * @return True if on the client, false if on a dedicated server.
     */
    boolean isPhysicalClient();

    <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> registry, String modId);

    <T extends Mob> void registerSpawnPlacement(
            Supplier<EntityType<T>> entityType,
            SpawnPlacementType placementType,
            Heightmap.Types heightmapType,
            SpawnPlacements.SpawnPredicate<T> predicate
    );

    void registerEntityAttributes(
            Supplier<EntityType<? extends LivingEntity>> entityType,
            Supplier<AttributeSupplier.Builder> attributes
    );

    void registerWaxableBlock(Supplier<Block> unwaxed, Supplier<Block> waxed);

    void registerLootTableModifier(
            BiConsumer<ResourceKey<LootTable>, LootTable.Builder> modifier
    );

    void registerReloadListener(PreparableReloadListener listener);

    <T extends AbstractContainerMenu> MenuType<T> createMenuType(
            EtceteraScreenHandlerType.ScreenHandlerFactory<T> factory
    );

    <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(
            BlockEntitySupplier<T> supplier,
            Block... blocks
    );

    void registerFlattenableBlock(Block input, BlockState output);

    void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing trade);

    void registerWanderingTraderTrade(int level, VillagerTrades.ItemListing trade);

    @FunctionalInterface
    interface BlockEntitySupplier<T extends BlockEntity> {
        T create(BlockPos pos, BlockState state);
    }
}