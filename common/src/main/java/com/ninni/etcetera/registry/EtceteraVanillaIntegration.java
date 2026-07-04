package com.ninni.etcetera.registry;

import com.ninni.etcetera.entity.TurtleRaftEntity;
import com.ninni.etcetera.item.TurtleRaftItem;
import com.ninni.etcetera.network.EtceteraNetwork;
import com.ninni.etcetera.platform.Services;
import com.ninni.etcetera.resource.EtceteraProcessResourceManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import static com.ninni.etcetera.registry.EtceteraBlocks.CRUMBLING_STONE;
import static com.ninni.etcetera.registry.EtceteraBlocks.WAXED_CRUMBLING_STONE;

public class EtceteraVanillaIntegration {
    public static final EtceteraProcessResourceManager CHISELLING_MANAGER = new EtceteraProcessResourceManager("chiselling");
    public static final EtceteraProcessResourceManager HAMMERING_MANAGER = new EtceteraProcessResourceManager("hammering");

    public static void serverInit() {
        EtceteraNetwork.initCommon();
        if (com.ninni.etcetera.config.ModConfig.get().enableFlattenableBlocks) {
            flattenableBlockRegistry();
        }
        registerDispenserBehavior();
        registerReloadListeners();
        registerWaxables();
        if (com.ninni.etcetera.config.ModConfig.get().enableVillagerTrades) {
            registerVillagerTrades();
        }
        if (com.ninni.etcetera.config.ModConfig.get().enableLootTableModifiers) {
            registerLootTableEvents();
        }
        if (com.ninni.etcetera.config.ModConfig.get().enableCompostables) {
            registerCompostables();
        }
    }

    private static void flattenableBlockRegistry() {
        Services.PLATFORM.registerFlattenableBlock(Blocks.SAND, EtceteraBlocks.SAND_PATH.get().defaultBlockState());
        Services.PLATFORM.registerFlattenableBlock(Blocks.RED_SAND, EtceteraBlocks.RED_SAND_PATH.get().defaultBlockState());
        Services.PLATFORM.registerFlattenableBlock(Blocks.SNOW_BLOCK, EtceteraBlocks.SNOW_PATH.get().defaultBlockState());
        Services.PLATFORM.registerFlattenableBlock(Blocks.GRAVEL, EtceteraBlocks.GRAVEL_PATH.get().defaultBlockState());
    }

    private static void registerDispenserBehavior() {
        DispenserBlock.registerBehavior(EtceteraItems.EGGPLE.get(), new ProjectileDispenseBehavior(EtceteraItems.EGGPLE.get()));
        DispenserBlock.registerBehavior(EtceteraItems.GOLDEN_EGGPLE.get(), new ProjectileDispenseBehavior(EtceteraItems.GOLDEN_EGGPLE.get()));
        DispenserBlock.registerBehavior(EtceteraItems.TURTLE_RAFT.get(), new DefaultDispenseItemBehavior() {

            @Override
            protected @NotNull ItemStack execute(@NotNull BlockSource world, @NotNull ItemStack stack) {
                Direction direction = world.state().getValue(DispenserBlock.FACING);
                Level level = world.level();
                BlockPos pos = world.pos();
                double d0 = 0.5625D + (double) EtceteraEntityType.TURTLE_RAFT.get().getWidth() / 2.0D;
                double d1 = (double) pos.getX() + 0.5D + (double) direction.getStepX() * d0;
                double d2 = (double) pos.getY() + 0.5D + (double) ((float) direction.getStepY() * 1.125F);
                double d3 = (double) pos.getZ() + 0.5D + (double) direction.getStepZ() * d0;
                BlockPos blockpos = pos.relative(direction);
                TurtleRaftEntity turtleRaftEntity = new TurtleRaftEntity(level, d0, d1, d2);
                if (stack.getItem() instanceof TurtleRaftItem turtleRaftItem) {
                    turtleRaftEntity.setColor(turtleRaftItem.getColor(stack));
                }
                turtleRaftEntity.setYRot(direction.toYRot());
                double d4;
                if (level.getFluidState(blockpos).is(FluidTags.WATER)) {
                    d4 = 1.0D;
                } else {
                    if (!level.getBlockState(blockpos).isAir() || !level.getFluidState(blockpos.below()).is(FluidTags.WATER)) {
                        return super.execute(world, stack);
                    }

                    d4 = 0.0D;
                }

                turtleRaftEntity.setPos(d1, d2 + d4, d3);
                level.addFreshEntity(turtleRaftEntity);
                stack.shrink(1);
                return stack;
            }

            @Override
            protected void playSound(@NotNull BlockSource pointer) {
                super.playSound(pointer);
                pointer.level().levelEvent(1000, pointer.pos(), 0);
            }

        });

    }

    private static void registerReloadListeners() {
        Services.PLATFORM.registerReloadListener(CHISELLING_MANAGER);
        Services.PLATFORM.registerReloadListener(HAMMERING_MANAGER);
    }

    private static void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(EtceteraItems.BOUQUET.get(), 0.85f);
        ComposterBlock.COMPOSTABLES.put(EtceteraItems.COTTON_SEEDS.get(), 0.3f);
        ComposterBlock.COMPOSTABLES.put(EtceteraItems.COTTON_FLOWER.get(), 0.65f);
    }

    private static void registerLootTableEvents() {
        Services.PLATFORM.registerLootTableModifier((id, tableBuilder) -> {
            if (id.equals(BuiltInLootTables.BASTION_OTHER)) {
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.GOLDEN_EGGPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))));
            }
            if (id.equals(BuiltInLootTables.BASTION_TREASURE)) {
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.GOLDEN_EGGPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))));
            }
            if (id.equals(BuiltInLootTables.PILLAGER_OUTPOST)) {
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.EGGPLE.get()).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))));
            }
            if (id.equals(BuiltInLootTables.VILLAGE_PLAINS_HOUSE)) {
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.EGGPLE.get()).setWeight(1).apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 1)))));
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.COTTON_SEEDS.get()).setWeight(3).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 5)))));
            }
            if (id.equals(BuiltInLootTables.ABANDONED_MINESHAFT)) {
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.ITEM_LABEL.get()).setWeight(3)));
            }
            if (id.equals(BuiltInLootTables.SIMPLE_DUNGEON) || id.equals(BuiltInLootTables.WOODLAND_MANSION)) {
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.ITEM_LABEL.get()).setWeight(2)));
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.ADVENTURERS_BOOTS.get()).setWeight(4)));
            }
            if (id.equals(BuiltInLootTables.ANCIENT_CITY) || id.equals(BuiltInLootTables.ANCIENT_CITY_ICE_BOX)) {
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.ITEM_LABEL.get()).setWeight(2)));
            }
            if (id.equals(BuiltInLootTables.UNDERWATER_RUIN_BIG)) {
                tableBuilder.withPool(LootPool.lootPool().add(LootItem.lootTableItem(EtceteraItems.MUSIC_DISC_SQUALL.get()).setWeight(2)));
            }
        });
    }

    private static void registerWaxables() {
        Services.PLATFORM.registerWaxableBlock(CRUMBLING_STONE, WAXED_CRUMBLING_STONE);
    }

    private static void registerVillagerTrades() {
        Services.PLATFORM.registerVillagerTrade(VillagerProfession.ARMORER, 2, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 18), new ItemStack(EtceteraItems.HANDBELL.get()), 6, 3, 0.2f));
        Services.PLATFORM.registerVillagerTrade(VillagerProfession.ARMORER, 3, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 22), new ItemStack(EtceteraItems.ADVENTURERS_BOOTS.get()), 6, 3, 0.2f));
        Services.PLATFORM.registerVillagerTrade(VillagerProfession.WEAPONSMITH, 2, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 18), new ItemStack(EtceteraItems.HANDBELL.get()), 6, 3, 0.2f));
        Services.PLATFORM.registerVillagerTrade(VillagerProfession.TOOLSMITH, 2, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 18), new ItemStack(EtceteraItems.HANDBELL.get()), 6, 3, 0.2f));
        Services.PLATFORM.registerVillagerTrade(VillagerProfession.TOOLSMITH, 3, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 16), new ItemStack(EtceteraItems.HAMMER.get()), 6, 2, 0.2f));
        Services.PLATFORM.registerVillagerTrade(VillagerProfession.FARMER, 1, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 2), new ItemStack(EtceteraItems.COTTON_FLOWER.get()), 18, 2, 0.05f));
        Services.PLATFORM.registerVillagerTrade(VillagerProfession.FARMER, 1, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 2), new ItemStack(EtceteraItems.COTTON_SEEDS.get()), 28, 2, 0.05f));
        Services.PLATFORM.registerWanderingTraderTrade(1, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 1), new ItemStack(EtceteraItems.COTTON_SEEDS.get()), 1, 12, 0.05f));
        Services.PLATFORM.registerWanderingTraderTrade(1, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 26), new ItemStack(EtceteraItems.TRADER_ROBE.get()), 1, 12, 0.05f));
        Services.PLATFORM.registerWanderingTraderTrade(1, (entity, random) -> new MerchantOffer(new ItemCost(Items.EMERALD, 20), new ItemStack(EtceteraItems.TRADER_HOOD.get()), 1, 10, 0.05f));
    }
}