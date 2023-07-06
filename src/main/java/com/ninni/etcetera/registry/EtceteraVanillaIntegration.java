package com.ninni.etcetera.registry;

import com.google.common.collect.Maps;
import com.google.common.reflect.Reflection;
import com.ninni.etcetera.client.TidalHelmetHud;
import com.ninni.etcetera.client.gui.screen.PricklyCanScreen;
import com.ninni.etcetera.client.renderer.block.entity.ItemStandBlockEntityRenderer;
import com.ninni.etcetera.client.renderer.entity.ChappleRenderer;
import com.ninni.etcetera.client.renderer.entity.CottonArmorRenderer;
import com.ninni.etcetera.client.renderer.entity.TidalArmorRenderer;
import com.ninni.etcetera.client.renderer.entity.TurtleRaftRenderer;
import com.ninni.etcetera.entity.EggpleEntity;
import com.ninni.etcetera.network.EtceteraNetwork;
import com.ninni.etcetera.resource.EtceteraProcessResourceManager;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ArmorRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.fabricmc.fabric.api.registry.CompostingChanceRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.block.Block;
import net.minecraft.block.DispenserBlock;
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
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Util;
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
                tableBuilder.pool(LootPool.builder().with(ItemEntry.builder(EtceteraItems.EGGPLE).apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(0, 3)))).build());
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
        TradeOfferHelper.registerVillagerOffers(VillagerProfession.FARMER, 1, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 26), ItemStack.EMPTY, new ItemStack(EtceteraItems.COTTON_FLOWER), 16, 2, 0.05f)));
        TradeOfferHelper.registerWanderingTraderOffers(1, factories -> factories.add((entity, random) -> new TradeOffer(new ItemStack(Items.EMERALD, 1), ItemStack.EMPTY, new ItemStack(EtceteraItems.COTTON_FLOWER), 1, 12, 0.05f)));
    }

    //client
    @SuppressWarnings("deprecation")
    private static void registerBlockEntityRenderer() {
        BlockEntityRendererRegistry.register(EtceteraBlockEntityType.ITEM_STAND, ItemStandBlockEntityRenderer::new);
    }

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
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.CYAN_SWEATER );
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.LIGHT_BLUE_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.BLUE_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.PURPLE_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.MAGENTA_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.PINK_SWEATER);
        ArmorRenderer.register(new CottonArmorRenderer(), EtceteraItems.TRADER_ROBE);
        ArmorRenderer.register(new TidalArmorRenderer(), EtceteraItems.TIDAL_HELMET);
        TidalHelmetHud.init();
    }

    private static void registerBlockRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getTranslucent(),
                EtceteraBlocks.IRIDESCENT_GLASS,
                EtceteraBlocks.LIGHT_BULB,
                EtceteraBlocks.TINTED_LIGHT_BULB
        );
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutout(),
                EtceteraBlocks.BISMUTH_BARS,
                EtceteraBlocks.BOUQUET,
                EtceteraBlocks.COTTON,
                EtceteraBlocks.POTTED_BOUQUET,
                EtceteraBlocks.ITEM_STAND,
                EtceteraBlocks.FRAME,
                EtceteraBlocks.PRICKLY_CAN
        );
    }

    private static void registerScreens() {
        HandledScreens.register(EtceteraScreenHandlerType.PRICKLY_CAN, PricklyCanScreen::new);
    }

    private static void registerEntityModelLayers() {
        Reflection.initialize(EtceteraEntityModelLayers.class);
        EntityRendererRegistry.register(EtceteraEntityType.TURTLE_RAFT, TurtleRaftRenderer::new);
        EntityRendererRegistry.register(EtceteraEntityType.CHAPPLE, ChappleRenderer::new);
        EntityRendererRegistry.register(EtceteraEntityType.EGGPLE, FlyingItemEntityRenderer::new);
    }

    private static void registerColorProviders() {
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem)stack.getItem()).getColor(stack), EtceteraItems.TURTLE_RAFT);
    }
}
