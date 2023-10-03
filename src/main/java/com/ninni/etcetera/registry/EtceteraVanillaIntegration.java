package com.ninni.etcetera.registry;

import com.google.common.collect.Maps;
import com.google.common.reflect.Reflection;
import com.ninni.etcetera.client.TidalHelmetHud;
import com.ninni.etcetera.client.gui.screen.PricklyCanScreen;
import com.ninni.etcetera.client.renderer.block.entity.ItemStandBlockEntityRenderer;
import com.ninni.etcetera.client.renderer.entity.*;
import com.ninni.etcetera.entity.EggpleEntity;
import com.ninni.etcetera.entity.TurtleRaftEntity;
import com.ninni.etcetera.item.TurtleRaftItem;
import com.ninni.etcetera.network.EtceteraNetwork;
import com.ninni.etcetera.resource.EtceteraProcessResourceManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.FlattenableBlockRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.FlyingItemEntityRenderer;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTables;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;

import java.util.LinkedHashMap;

import static com.ninni.etcetera.registry.EtceteraBlocks.CRUMBLING_STONE;
import static com.ninni.etcetera.registry.EtceteraBlocks.WAXED_CRUMBLING_STONE;

public class EtceteraVanillaIntegration {

    public static final EtceteraProcessResourceManager CHISELLING_MANAGER = new EtceteraProcessResourceManager("chiselling");
    public static final EtceteraProcessResourceManager HAMMERING_MANAGER = new EtceteraProcessResourceManager("hammering");

    public static void serverInit() {
        EtceteraNetwork.initCommon();
        itemTooltipCallback();
        flattenableBlockRegistry();
        registerDispenserBehavior();
        registerReloadListeners();
        registerWaxables();
        registerVillagerTrades();
        registerLootTableEvents();
        registerCompostables();
    }

    public static void clientInit() {
        registerBlockEntityRenderer();
        registerArmor();
        registerBlockRenderLayers();
        registerScreens();
        registerEntityModelLayers();
        registerColorProviders();
    }

    //server

    private static void flattenableBlockRegistry(){
        FlattenableBlockRegistry.register(Blocks.SAND, EtceteraBlocks.SAND_PATH.getDefaultState());
        FlattenableBlockRegistry.register(Blocks.RED_SAND, EtceteraBlocks.RED_SAND_PATH.getDefaultState());
        FlattenableBlockRegistry.register(Blocks.SNOW_BLOCK, EtceteraBlocks.SNOW_PATH.getDefaultState());
        FlattenableBlockRegistry.register(Blocks.GRAVEL, EtceteraBlocks.GRAVEL_PATH.getDefaultState());
    }

    private static void itemTooltipCallback(){

        ItemTooltipCallback.EVENT.register((stack, context1, lines) ->{
            int color = 0x959595;
            switch (stack.getRarity()){
                case COMMON -> color=0x959595;
                case UNCOMMON -> color=0xbb7d2b;
                case RARE -> color=Formatting.DARK_AQUA.getColorValue();
                case EPIC -> color= Formatting.DARK_PURPLE.getColorValue();
            }
            Style style = Style.EMPTY.withColor(color).withItalic(true);

            for (int row = 1; row < 5; row++) {
                if (stack.hasNbt() && stack.getNbt().contains("Label" + row)) lines.add(row, Text.literal(stack.getNbt().getString("Label" + row)).setStyle(style));
            }
        });
    }

    private static void registerDispenserBehavior() {

        DispenserBlock.registerBehavior(EtceteraItems.EGGPLE, new ProjectileDispenserBehavior(){
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new EggpleEntity(world, position.getX(), position.getY(), position.getZ()), entity -> entity.setItem(stack));
            }
        });
        DispenserBlock.registerBehavior(EtceteraItems.GOLDEN_EGGPLE, new ProjectileDispenserBehavior(){
            @Override
            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new EggpleEntity(world, position.getX(), position.getY(), position.getZ()), entity -> entity.setItem(stack));
            }
        });
        DispenserBlock.registerBehavior(EtceteraItems.TURTLE_RAFT, new ItemDispenserBehavior() {
            private final ItemDispenserBehavior defaultDispenseItemBehavior = new ItemDispenserBehavior();

            @Override
            protected ItemStack dispenseSilently(BlockPointer world, ItemStack stack) {
                Direction direction = world.getBlockState().get(DispenserBlock.FACING);
                World level = world.getWorld();
                double d0 = 0.5625D + (double) EtceteraEntityType.TURTLE_RAFT.getWidth() / 2.0D;
                double d1 = world.getX() + (double)direction.getOffsetX() * d0;
                double d2 = world.getY() + (double)((float)direction.getOffsetY() * 1.125F);
                double d3 = world.getZ() + (double)direction.getOffsetZ() * d0;
                BlockPos blockpos = world.getPos().offset(direction);
                TurtleRaftEntity turtleRaftEntity = new TurtleRaftEntity(level, d0, d1, d2);
                if (stack.getItem() instanceof TurtleRaftItem turtleRaftItem) {
                    turtleRaftEntity.setColor(turtleRaftItem.getColor(stack));
                }
                turtleRaftEntity.setYaw(direction.asRotation());
                double d4;
                if (level.getFluidState(blockpos).isIn(FluidTags.WATER)) {
                    d4 = 1.0D;
                } else {
                    if (!level.getBlockState(blockpos).isAir() || !level.getFluidState(blockpos.down()).isIn(FluidTags.WATER)) {
                        return this.defaultDispenseItemBehavior.dispense(world, stack);
                    }

                    d4 = 0.0D;
                }

                turtleRaftEntity.setPos(d1, d2 + d4, d3);
                level.spawnEntity(turtleRaftEntity);
                stack.decrement(1);
                return stack;
            }

            @Override
            protected void playSound(BlockPointer pointer) {
                super.playSound(pointer);
                pointer.getWorld().syncWorldEvent(1000, pointer.getPos(), 0);
            }

        });

    }

    private static void registerReloadListeners() {
        ResourceManagerHelper resourceManager = ResourceManagerHelper.get(ResourceType.SERVER_DATA);
        resourceManager.registerReloadListener(CHISELLING_MANAGER);
        resourceManager.registerReloadListener(HAMMERING_MANAGER);
    }

    private static void registerCompostables() {
        CompostingChanceRegistry.INSTANCE.add(EtceteraItems.BOUQUET, 0.85f);
        CompostingChanceRegistry.INSTANCE.add(EtceteraItems.COTTON_SEEDS, 0.3f);
        CompostingChanceRegistry.INSTANCE.add(EtceteraItems.COTTON_FLOWER, 0.65f);
    }

    private static void registerLootTableEvents() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(LootTables.BASTION_OTHER_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EtceteraItems.GOLDEN_EGGPLE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1)))).build());
            }
            if (id.equals(LootTables.BASTION_TREASURE_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EtceteraItems.GOLDEN_EGGPLE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1, 1)))).build());
            }
            if (id.equals(LootTables.PILLAGER_OUTPOST_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EtceteraItems.EGGPLE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1)))).build());
            }
            if (id.equals(LootTables.VILLAGE_PLAINS_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EtceteraItems.EGGPLE).weight(1).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 1)))).build());
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EtceteraItems.COTTON_SEEDS).weight(3).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(3, 5)))).build());
            }

            if (id.equals(LootTables.ABANDONED_MINESHAFT_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EtceteraItems.ITEM_LABEL).weight(30)).build());
            }
            if (id.equals(LootTables.SIMPLE_DUNGEON_CHEST) || id.equals(LootTables.WOODLAND_MANSION_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EtceteraItems.ITEM_LABEL).weight(20)).build());
            }
            if (id.equals(LootTables.ANCIENT_CITY_CHEST) || id.equals(LootTables.ANCIENT_CITY_ICE_BOX_CHEST)) {
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EtceteraItems.ITEM_LABEL).weight(2)).build());
            }

        });
    }

    private static void registerWaxables() {
        LinkedHashMap<Block, Block> crumblingStone = Maps.newLinkedHashMap();
        crumblingStone.put(CRUMBLING_STONE, WAXED_CRUMBLING_STONE);
        crumblingStone.forEach(OxidizableBlocksRegistry::registerWaxableBlockPair);
    }

    private static void registerVillagerTrades() {
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.ARMORER, 2, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 18), ItemStack.EMPTY, new ItemStack(EtceteraItems.HANDBELL), 6, 3, 0.2f)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.WEAPONSMITH, 2, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 18), ItemStack.EMPTY, new ItemStack(EtceteraItems.HANDBELL), 6, 3, 0.2f)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 2, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 18), ItemStack.EMPTY, new ItemStack(EtceteraItems.HANDBELL), 6, 3, 0.2f)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.TOOLSMITH, 3, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 16), ItemStack.EMPTY, new ItemStack(EtceteraItems.HAMMER), 6, 2, 0.2f)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 2), ItemStack.EMPTY, new ItemStack(EtceteraItems.COTTON_FLOWER), 18, 2, 0.05f)));
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 2), ItemStack.EMPTY, new ItemStack(EtceteraItems.COTTON_SEEDS), 28, 2, 0.05f)));
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 1), ItemStack.EMPTY, new ItemStack(EtceteraItems.COTTON_SEEDS), 1, 12, 0.05f)));
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 26), ItemStack.EMPTY, new ItemStack(EtceteraItems.TRADER_ROBE), 1, 12, 0.05f)));
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 20), ItemStack.EMPTY, new ItemStack(EtceteraItems.TRADER_HOOD), 1, 10, 0.05f)));
    }

    //client
    @SuppressWarnings("deprecation")
    private static void registerBlockEntityRenderer() {
        BlockEntityRendererRegistry.register(EtceteraBlockEntityType.ITEM_STAND, ItemStandBlockEntityRenderer::new);
    }

    private static void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                EtceteraBlocks.IRIDESCENT_GLASS,
                EtceteraBlocks.IRIDESCENT_GLASS_PANE,
                EtceteraBlocks.LIGHT_BULB,
                EtceteraBlocks.TINTED_LIGHT_BULB
        );
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                EtceteraBlocks.BISMUTH_BARS,
                EtceteraBlocks.BOUQUET,
                EtceteraBlocks.COTTON,
                EtceteraBlocks.POTTED_BOUQUET,
                EtceteraBlocks.ITEM_STAND,
                EtceteraBlocks.GLOW_ITEM_STAND,
                EtceteraBlocks.FRAME,
                EtceteraBlocks.DREAM_CATCHER,
                EtceteraBlocks.PRICKLY_CAN
        );
    }

    @Environment(EnvType.CLIENT)
    private static void registerArmor() {
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.WHITE_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.LIGHT_GRAY_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.GRAY_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.BLACK_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.BROWN_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.RED_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.ORANGE_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.YELLOW_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.LIME_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.GREEN_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.CYAN_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.LIGHT_BLUE_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.BLUE_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.PURPLE_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.MAGENTA_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.PINK_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.TRADER_ROBE);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.WHITE_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.LIGHT_GRAY_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.GRAY_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.BLACK_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.BROWN_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.RED_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.ORANGE_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.YELLOW_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.LIME_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.GREEN_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.CYAN_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.LIGHT_BLUE_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.BLUE_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.PURPLE_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.MAGENTA_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.PINK_HAT);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.TRADER_HOOD);
        ArmorRenderer.register(new TidalArmorRenderer(), EtceteraItems.TIDAL_HELMET);
        ArmorRenderer.register(new SilkArmorRenderer(), EtceteraItems.SILKEN_SLACKS);
        TidalHelmetHud.init();
    }

    private static void registerScreens() {
        HandledScreens.register(EtceteraScreenHandlerType.PRICKLY_CAN, PricklyCanScreen::new);
    }

    private static void registerEntityModelLayers() {
        Reflection.initialize(EtceteraEntityModelLayers.class);
        EntityRendererRegistry.register(EtceteraEntityType.TURTLE_RAFT, TurtleRaftRenderer::new);
        EntityRendererRegistry.register(EtceteraEntityType.CHAPPLE, ChappleRenderer::new);
        EntityRendererRegistry.register(EtceteraEntityType.EGGPLE, FlyingItemEntityRenderer::new);
        EntityRendererRegistry.register(EtceteraEntityType.WEAVER, WeaverRenderer::new);
        EntityRendererRegistry.register(EtceteraEntityType.COBWEB, CobwebProjectileEntityRenderer::new);
    }

    private static void registerColorProviders() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack), EtceteraItems.TURTLE_RAFT);
    }
}
