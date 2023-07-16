package com.ninni.etcetera.events;

import com.google.common.collect.Lists;
import com.ninni.etcetera.Etcetera;
import com.ninni.etcetera.client.TidalHelmetHud;
import com.ninni.etcetera.client.gui.HandbellItemRenderer;
import com.ninni.etcetera.client.gui.screen.PricklyCanScreen;
import com.ninni.etcetera.client.model.ChappleModel;
import com.ninni.etcetera.client.model.CottonArmorModel;
import com.ninni.etcetera.client.model.TurtleRaftModel;
import com.ninni.etcetera.client.renderer.block.entity.ItemStandBlockEntityRenderer;
import com.ninni.etcetera.client.renderer.entity.ChappleRenderer;
import com.ninni.etcetera.client.renderer.entity.TurtleRaftRenderer;
import com.ninni.etcetera.item.HandbellItem;
import com.ninni.etcetera.registry.EtceteraBlockEntityType;
import com.ninni.etcetera.registry.EtceteraEntityModelLayers;
import com.ninni.etcetera.registry.EtceteraEntityType;
import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraScreenHandlerType;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.DyeableArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Etcetera.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {
    private static final Function<ItemLike, ItemStack> FUNCTION = ItemStack::new;

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        MenuScreens.register(EtceteraScreenHandlerType.PRICKLY_CAN.get(), PricklyCanScreen::new);
        IEventBus bus = MinecraftForge.EVENT_BUS;
        bus.register(new TidalHelmetHud());
    }

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableLeatherItem)stack.getItem()).getColor(stack), EtceteraItems.TURTLE_RAFT.get());
    }

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(EtceteraBlockEntityType.ITEM_STAND.get(), ItemStandBlockEntityRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.TURTLE_RAFT.get(), TurtleRaftRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.CHAPPLE.get(), ChappleRenderer::new);
        event.registerEntityRenderer(EtceteraEntityType.EGGPLE.get(), ThrownItemRenderer::new);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(EtceteraEntityModelLayers.TURTLE_RAFT, TurtleRaftModel::createLayerDefinition);
        event.registerLayerDefinition(EtceteraEntityModelLayers.CHAPPLE, ChappleModel::createLayerDefinition);
        event.registerLayerDefinition(EtceteraEntityModelLayers.PLAYER_COTTON, CottonArmorModel::createLayerDefinition);
    }

    @SubscribeEvent
    public static void registerAdditionalModels(ModelEvent.RegisterAdditional event) {
        event.register(HandbellItemRenderer.INVENTORY_IN_HAND_MODEL_ID);
    }

    @SubscribeEvent
    public static void onCreativeModeTabsBuilt(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tabKey = event.getTabKey();
        MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries = event.getEntries();
        if (tabKey == CreativeModeTabs.BUILDING_BLOCKS) {
            addAfter(entries, Items.SMOOTH_QUARTZ_SLAB,
                    EtceteraItems.BISMUTH_BLOCK.get(),
                    EtceteraItems.BISMUTH_BARS.get()
            );
            addAfter(entries, Items.SMOOTH_STONE_SLAB,
                    EtceteraItems.LEVELED_STONE.get(),
                    EtceteraItems.CRUMBLING_STONE.get(),
                    EtceteraItems.WAXED_CRUMBLING_STONE.get(),
                    EtceteraItems.LEVELED_STONE_STAIRS.get(),
                    EtceteraItems.LEVELED_STONE_SLAB.get()
            );
        }
        if (tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            addAfter(entries, Items.SOUL_LANTERN, EtceteraItems.LIGHT_BULB.get(), EtceteraItems.TINTED_LIGHT_BULB.get());
            addAfter(entries, Items.END_ROD, EtceteraItems.SQUID_LAMP.get());
            addAfter(entries, Items.SEA_LANTERN, EtceteraItems.IRIDESCENT_LANTERN.get());
            addAfter(entries, Items.JUKEBOX, EtceteraItems.DRUM.get());
            addAfter(entries, Items.SCAFFOLDING, EtceteraItems.FRAME.get());
            addAfter(entries, Items.DECORATED_POT, EtceteraItems.TERRACOTTA_VASE.get());
            addAfter(entries, Items.GLOW_ITEM_FRAME, EtceteraItems.ITEM_STAND.get(), EtceteraItems.GLOW_ITEM_STAND.get());
            addAfter(entries, Items.ENDER_CHEST, EtceteraItems.PRICKLY_CAN.get());
            addAfter(entries, Items.SUSPICIOUS_GRAVEL, EtceteraItems.CRUMBLING_STONE.get(), EtceteraItems.WAXED_CRUMBLING_STONE.get());
        }
        if (tabKey == CreativeModeTabs.REDSTONE_BLOCKS) {
            addAfter(entries, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, EtceteraItems.DRUM.get());
            addAfter(entries, Items.WHITE_WOOL, EtceteraItems.DICE.get());
            addAfter(entries, Items.BARREL, EtceteraItems.PRICKLY_CAN.get());
        }
        if (tabKey == CreativeModeTabs.SPAWN_EGGS) {
            addAfter(entries, Items.CAVE_SPIDER_SPAWN_EGG, EtceteraItems.CHAPPLE_SPAWN_EGG.get());
        }
        if (tabKey == CreativeModeTabs.COMBAT) {
            addAfter(entries, Items.EGG, EtceteraItems.EGGPLE.get(), EtceteraItems.GOLDEN_EGGPLE.get());
            addAfter(entries, Items.TURTLE_HELMET,
                    EtceteraItems.TIDAL_HELMET.get(),
                    EtceteraItems.WHITE_SWEATER.get(),
                    EtceteraItems.TRADER_ROBE.get(),
                    EtceteraItems.WHITE_HAT.get(),
                    EtceteraItems.TRADER_HOOD.get()
            );
            addAfter(entries, Items.TRIDENT, EtceteraItems.HAMMER.get());
        }
        if (tabKey == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            addAfter(entries, Items.BRUSH,
                    EtceteraItems.HAMMER.get(),
                    EtceteraItems.CHISEL.get(),
                    EtceteraItems.WRENCH.get(),
                    EtceteraItems.HANDBELL.get()
            );
            addAfter(entries, Items.BAMBOO_CHEST_RAFT, EtceteraItems.TURTLE_RAFT.get());
        }
        if (tabKey == CreativeModeTabs.NATURAL_BLOCKS) {
            addAfter(entries, Items.NETHER_GOLD_ORE, EtceteraItems.NETHER_BISMUTH_ORE.get());
            addAfter(entries, Items.RAW_GOLD_BLOCK, EtceteraItems.RAW_BISMUTH_BLOCK.get());
            addAfter(entries, Items.PINK_PETALS, EtceteraItems.BOUQUET.get());
            addAfter(entries, Items.BEETROOT_SEEDS, EtceteraItems.COTTON_SEEDS.get());
        }
        if (tabKey == CreativeModeTabs.COLORED_BLOCKS) {
            addBefore(entries, Items.WHITE_CONCRETE, EtceteraItems.IRIDESCENT_CONCRETE.get());
            addBefore(entries, Items.WHITE_WOOL, EtceteraItems.IRIDESCENT_WOOL.get());
            addBefore(entries, Items.WHITE_GLAZED_TERRACOTTA, EtceteraItems.IRIDESCENT_GLAZED_TERRACOTTA.get());
            addAfter(entries, Items.TERRACOTTA, EtceteraItems.IRIDESCENT_TERRACOTTA.get());
            addAfter(entries, Items.GLASS, EtceteraItems.IRIDESCENT_GLASS.get());
            addAfter(entries, Items.GLASS_PANE, EtceteraItems.IRIDESCENT_GLASS_PANE.get());
            addAfter(entries, Items.PINK_BED,
                    EtceteraItems.WHITE_SWEATER.get(),
                    EtceteraItems.LIGHT_GRAY_SWEATER.get(),
                    EtceteraItems.GRAY_SWEATER.get(),
                    EtceteraItems.BLACK_SWEATER.get(),
                    EtceteraItems.BROWN_SWEATER.get(),
                    EtceteraItems.RED_SWEATER.get(),
                    EtceteraItems.ORANGE_SWEATER.get(),
                    EtceteraItems.YELLOW_SWEATER.get(),
                    EtceteraItems.LIME_SWEATER.get(),
                    EtceteraItems.GREEN_SWEATER.get(),
                    EtceteraItems.CYAN_SWEATER.get() ,
                    EtceteraItems.LIGHT_BLUE_SWEATER.get(),
                    EtceteraItems.BLUE_SWEATER.get(),
                    EtceteraItems.PURPLE_SWEATER.get(),
                    EtceteraItems.MAGENTA_SWEATER.get(),
                    EtceteraItems.PINK_SWEATER.get(),
                    EtceteraItems.WHITE_HAT.get(),
                    EtceteraItems.LIGHT_GRAY_HAT.get(),
                    EtceteraItems.GRAY_HAT.get(),
                    EtceteraItems.BLACK_HAT.get(),
                    EtceteraItems.BROWN_HAT.get(),
                    EtceteraItems.RED_HAT.get(),
                    EtceteraItems.ORANGE_HAT.get(),
                    EtceteraItems.YELLOW_HAT.get(),
                    EtceteraItems.LIME_HAT.get(),
                    EtceteraItems.GREEN_HAT.get(),
                    EtceteraItems.CYAN_HAT.get() ,
                    EtceteraItems.LIGHT_BLUE_HAT.get(),
                    EtceteraItems.BLUE_HAT.get(),
                    EtceteraItems.PURPLE_HAT.get(),
                    EtceteraItems.MAGENTA_HAT.get(),
                    EtceteraItems.PINK_HAT.get()
            );
        }
        if (tabKey == CreativeModeTabs.INGREDIENTS) {
            addAfter(entries, Items.EGG, EtceteraItems.EGGPLE.get(), EtceteraItems.GOLDEN_EGGPLE.get());
            addAfter(entries, Items.RAW_GOLD, EtceteraItems.RAW_BISMUTH.get());
            addAfter(entries, Items.GOLD_INGOT, EtceteraItems.BISMUTH_INGOT.get());
            addAfter(entries, Items.WHEAT, EtceteraItems.COTTON_FLOWER.get());
        }
    }

    private static void addAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike after, ItemLike... block) {
        List<ItemLike> stream = Lists.newArrayList(Arrays.stream(block).toList());
        Collections.reverse(stream);
        stream.forEach(blk -> addAfter(map, after, blk));
    }

    private static void addBefore(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike before, ItemLike... block) {
        List<ItemLike> stream = Lists.newArrayList(Arrays.stream(block).toList());
        Collections.reverse(stream);
        stream.forEach(blk -> addBefore(map, before, blk));
    }

    private static void addAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike after, ItemLike block) {
        map.putAfter(FUNCTION.apply(after), FUNCTION.apply(block), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void addBefore(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> map, ItemLike before, ItemLike block) {
        map.putBefore(FUNCTION.apply(before), FUNCTION.apply(block), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

}
