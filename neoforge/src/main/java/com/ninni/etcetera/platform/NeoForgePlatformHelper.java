package com.ninni.etcetera.platform;

import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.mixin.LootTableAccessor;
import com.ninni.etcetera.platform.services.IPlatformHelper;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import com.ninni.etcetera.platform.services.RegistryObject;
import com.ninni.etcetera.registry.EtceteraScreenHandlerType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
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
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class NeoForgePlatformHelper implements IPlatformHelper {
    private static final Map<Block, BlockState> CUSTOM_FLATTENABLES = new HashMap<>();
    private static final List<Consumer<RegisterSpawnPlacementsEvent>> SPAWN_PLACEMENTS = new ArrayList<>();
    private static final List<Consumer<EntityAttributeCreationEvent>> ATTRIBUTE_REGISTRATIONS = new ArrayList<>();
    private static final List<BiConsumer<ResourceKey<LootTable>, LootTable.Builder>> LOOT_MODIFIERS = new ArrayList<>();
    private static final List<PreparableReloadListener> RELOAD_LISTENERS = new ArrayList<>();
    private static final List<VillagerTradeRegistration> VILLAGER_TRADES = new ArrayList<>();
    private static final List<WanderingTraderTradeRegistration> WANDERING_TRADER_TRADES = new ArrayList<>();

    public NeoForgePlatformHelper() {
        Etcetera.MOD_EVENT_BUS.addListener(this::onRegisterSpawnPlacements);
        Etcetera.MOD_EVENT_BUS.addListener(this::onEntityAttributeCreation);
        NeoForge.EVENT_BUS.addListener(this::onLootTableLoad);
        NeoForge.EVENT_BUS.addListener(this::onAddReloadListeners);
        NeoForge.EVENT_BUS.addListener(this::onVillagerTrades);
        NeoForge.EVENT_BUS.addListener(this::onWanderingTraderTrades);
        NeoForge.EVENT_BUS.addListener(this::onToolModification);
    }

    private void onToolModification(BlockEvent.BlockToolModificationEvent event) {
        if (event.getItemAbility() == ItemAbilities.SHOVEL_FLATTEN) {
            BlockState currentState = event.getState();
            BlockState newState = CUSTOM_FLATTENABLES.get(currentState.getBlock());

            if (newState != null) {
                event.setFinalState(newState);
            }
        }
    }

    private void onVillagerTrades(VillagerTradesEvent event) {
        for (VillagerTradeRegistration reg : VILLAGER_TRADES) {
            if (event.getType() == reg.profession) {
                event.getTrades().get(reg.level).add(reg.trade);
            }
        }
    }

    private void onWanderingTraderTrades(WandererTradesEvent event) {
        for (WanderingTraderTradeRegistration reg : WANDERING_TRADER_TRADES) {
            if (reg.level == 1) {
                event.getGenericTrades().add(reg.trade);
            } else if (reg.level == 2) {
                event.getRareTrades().add(reg.trade);
            }
        }
    }

    @Override
    public String getPlatformName() {
        return "NeoForge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public boolean isPhysicalClient() {
        return FMLLoader.getDist() == Dist.CLIENT;
    }

    @Override
    public <T> RegistrationProvider<T> createRegistrationProvider(ResourceKey<? extends Registry<T>> registryKey, String modId) {
        return new NeoForgeRegistrationProvider<>(registryKey, modId);
    }

    private void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        SPAWN_PLACEMENTS.forEach(consumer -> consumer.accept(event));
    }

    private void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        ATTRIBUTE_REGISTRATIONS.forEach(consumer -> consumer.accept(event));
    }

    private void onLootTableLoad(LootTableLoadEvent event) {
        LootTable.Builder builder = LootTable.lootTable();
        ResourceKey<LootTable> key = ResourceKey.create(Registries.LOOT_TABLE, event.getName());

        LOOT_MODIFIERS.forEach(modifier -> modifier.accept(key, builder));
        LootTable tempTable = builder.build();
        List<LootPool> tempPools = ((LootTableAccessor) tempTable).getPools();
        if (tempPools != null && !tempPools.isEmpty()) {
            List<LootPool> originalPools = ((LootTableAccessor) event.getTable()).getPools();
            List<LootPool> newPools = new ArrayList<>(originalPools != null ? originalPools : List.of());
            newPools.addAll(tempPools);
            ((LootTableAccessor) event.getTable()).setPools(newPools);
        }
    }

    private void onAddReloadListeners(AddReloadListenerEvent event) {
        RELOAD_LISTENERS.forEach(event::addListener);
    }

    @Override
    public void registerWaxableBlock(Supplier<Block> unwaxed, Supplier<Block> waxed) {
        // No-op: handled by data maps
    }

    @Override
    public <T extends Mob> void registerSpawnPlacement(
            Supplier<EntityType<T>> entityType,
            SpawnPlacementType placementType,
            Heightmap.Types heightmapType,
            SpawnPlacements.SpawnPredicate<T> predicate
    ) {
        SPAWN_PLACEMENTS.add(event -> event.register(entityType.get(), placementType, heightmapType, predicate, RegisterSpawnPlacementsEvent.Operation.REPLACE));
    }

    @Override
    public void registerEntityAttributes(
            Supplier<EntityType<? extends LivingEntity>> entityType,
            Supplier<AttributeSupplier.Builder> attributes
    ) {
        ATTRIBUTE_REGISTRATIONS.add(event -> event.put(entityType.get(), attributes.get().build()));
    }

    @Override
    public void registerLootTableModifier(
            BiConsumer<ResourceKey<LootTable>, LootTable.Builder> modifier
    ) {
        LOOT_MODIFIERS.add(modifier);
    }

    @Override
    public void registerReloadListener(PreparableReloadListener listener) {
        RELOAD_LISTENERS.add(listener);
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
        CUSTOM_FLATTENABLES.put(input, output);
    }

    @Override
    public void registerVillagerTrade(VillagerProfession profession, int level, VillagerTrades.ItemListing trade) {
        VILLAGER_TRADES.add(new VillagerTradeRegistration(profession, level, trade));
    }

    @Override
    public void registerWanderingTraderTrade(int level, VillagerTrades.ItemListing trade) {
        WANDERING_TRADER_TRADES.add(new WanderingTraderTradeRegistration(level, trade));
    }

    private record VillagerTradeRegistration(
            VillagerProfession profession,
            int level,
            VillagerTrades.ItemListing trade
    ) {
    }

    private record WanderingTraderTradeRegistration(
            int level,
            VillagerTrades.ItemListing trade
    ) {
    }

    private static class NeoForgeRegistrationProvider<T> implements RegistrationProvider<T> {
        private final DeferredRegister<T> register;

        public NeoForgeRegistrationProvider(ResourceKey<? extends Registry<T>> registryKey, String modId) {
            this.register = DeferredRegister.create(registryKey, modId);
            this.register.register(Etcetera.MOD_EVENT_BUS);
        }

        @Override
        public <I extends T> RegistryObject<I> register(String name, Supplier<? extends I> supplier) {
            DeferredHolder<T, I> holder = register.register(name, supplier);
            return new RegistryObject<>() {
                @Override
                public ResourceLocation getId() {
                    return holder.getId();
                }

                @Override
                public I get() {
                    return holder.get();
                }
            };
        }

        @Override
        public Collection<RegistryObject<T>> getEntries() {
            return register.getEntries().stream()
                    .map(holder -> (RegistryObject<T>) new RegistryObject<T>() {
                        @Override
                        public ResourceLocation getId() {
                            return holder.getId();
                        }

                        @Override
                        public T get() {
                            return holder.value();
                        }
                    })
                    .collect(Collectors.toList());
        }
    }
}