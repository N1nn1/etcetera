package com.ninni.etcetera.platform;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.platform.services.IPlatformHelper;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import com.ninni.etcetera.platform.services.RegistryObject;
import com.ninni.etcetera.registry.EtceteraScreenHandlerType;
import com.ninni.etcetera.resource.EtceteraProcessResourceManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.loot.LootTable;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FabricPlatformHelper implements IPlatformHelper {
    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public boolean isPhysicalClient() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT;
    }

    @Override
    public <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        return new FabricRegistrationProvider<>(registryKey, modId);
    }

    @Override
    public <T extends Mob> void registerSpawnPlacement(
            Supplier<EntityType<T>> entityType,
            SpawnPlacementType placementType,
            Heightmap.Types heightmapType,
            SpawnPlacements.SpawnPredicate<T> predicate
    ) {
        SpawnPlacements.register(entityType.get(), placementType, heightmapType, predicate);
    }

    @Override
    public void registerEntityAttributes(
            Supplier<EntityType<? extends LivingEntity>> entityType,
            Supplier<AttributeSupplier.Builder> attributes
    ) {
        FabricDefaultAttributeRegistry.register(entityType.get(), attributes.get());
    }

    @Override
    public void registerLootTableModifier(
            BiConsumer<ResourceKey<LootTable>, LootTable.Builder> modifier
    ) {
        LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
            modifier.accept(key, tableBuilder);
        });
    }

    @Override
    public void registerReloadListener(PreparableReloadListener listener) {
        ResourceManagerHelper.get(PackType.SERVER_DATA)
                .registerReloadListener(new IdentifiableResourceReloadListener() {
                    @Override
                    public ResourceLocation getFabricId() {
                        if (listener instanceof EtceteraProcessResourceManager manager) {
                            return manager.getFabricId();
                        }
                        return ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "reload_listener");
                    }

                    @Override
                    public @NotNull CompletableFuture<Void> reload(
                            PreparableReloadListener.PreparationBarrier barrier,
                            ResourceManager manager,
                            ProfilerFiller preparationsProfiler,
                            ProfilerFiller reloadProfiler,
                            Executor preparationsExecutor,
                            Executor reloadExecutor
                    ) {
                        return listener.reload(barrier, manager, preparationsProfiler, reloadProfiler, preparationsExecutor, reloadExecutor);
                    }
                });
    }

    @Override
    public <T extends AbstractContainerMenu> MenuType<T> createMenuType(
            EtceteraScreenHandlerType.ScreenHandlerFactory<T> factory
    ) {
        return new MenuType<>(factory::create, FeatureFlags.DEFAULT_FLAGS);
    }

    @Override
    public <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(
            BlockEntitySupplier<T> supplier,
            Block... blocks
    ) {
        return BlockEntityType.Builder.of(supplier::create, blocks).build(null);
    }

    @Override
    public void registerFlattenableBlock(Block input, BlockState output) {
        FlattenableBlockRegistry.register(input, output);
    }

    @Override
    public void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing trade) {
        TradeOfferHelper.registerVillagerOffers(profession, level, factories -> factories.add(trade));
    }

    @Override
    public void registerWanderingTraderTrade(int level, VillagerTrades.ItemListing trade) {
        TradeOfferHelper.registerWanderingTraderOffers(level, factories -> factories.add(trade));
    }

    @Override
    public void registerWaxableBlock(Supplier<Block> unwaxed, Supplier<Block> waxed) {
        OxidizableBlocksRegistry.registerWaxableBlockPair(unwaxed.get(), waxed.get());
    }

    private static class FabricRegistrationProvider<T> implements RegistrationProvider<T> {
        private final ResourceKey<? extends Registry<T>> registryKey;
        private final String modId;
        private final List<RegistryObject<T>> entries = new ArrayList<>();

        public FabricRegistrationProvider(ResourceKey<? extends Registry<T>> registryKey, String modId) {
            this.registryKey = registryKey;
            this.modId = modId;
        }

        @SuppressWarnings("unchecked")
        @Override
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(modId, name);
            Registry<T> registry = (Registry<T>) BuiltInRegistries.REGISTRY.get(registryKey.location());
            if (registry == null) {
                throw new IllegalStateException("Registry not found: " + registryKey);
            }
            I value = supplier.get();
            Registry.register(registry, id, value);
            RegistryObject<I> obj = new RegistryObject<>() {
                @Override
                public ResourceLocation getId() {
                    return id;
                }

                @Override
                public I get() {
                    return value;
                }
            };
            entries.add((RegistryObject<T>) obj);
            return obj;
        }

        @Override
        public Collection<RegistryObject<T>> getEntries() {
            return Collections.unmodifiableList(entries);
        }
    }
}