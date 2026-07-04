package com.ninni.etcetera;

import com.ninni.etcetera.client.ClientConfigSetup;
import com.ninni.etcetera.client.TidalHelmetHud;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import static com.ninni.etcetera.registry.EtceteraItems.*;

@Mod(Constants.MOD_ID)
public class Etcetera {
    public static IEventBus MOD_EVENT_BUS;

    public Etcetera(IEventBus modEventBus, ModContainer modContainer) {
        MOD_EVENT_BUS = modEventBus;
        CommonClass.init();

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::buildCreativeTabs);

        if (FMLEnvironment.dist.isClient()) {
            ClientConfigSetup.register(modContainer);
            modEventBus.addListener(this::registerGuiLayers);
        }
    }

    private void registerGuiLayers(RegisterGuiLayersEvent event) {
        event.registerAbove(
                VanillaGuiLayers.FOOD_LEVEL,
                ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "tidal_eye"),
                (graphics, deltaTracker) -> new TidalHelmetHud().render(graphics, deltaTracker.getGameTimeDeltaTicks())
        );
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(CommonClass::setup);
    }

    private void buildCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        var tabKey = event.getTabKey();
        if (tabKey == CreativeModeTabs.BUILDING_BLOCKS) {
            addAfter(event, Items.SMOOTH_QUARTZ_SLAB, BISMUTH_BLOCK.get(), BISMUTH_BARS.get());
            addAfter(event, Items.SMOOTH_STONE_SLAB, LEVELED_STONE.get(), CRUMBLING_STONE.get(), WAXED_CRUMBLING_STONE.get(), LEVELED_STONE_STAIRS.get(), LEVELED_STONE_SLAB.get());
            addAfter(event, Items.MUD_BRICK_WALL, RUBBER_BLOCK.get(), RUBBER_BUTTON.get());
        } else if (tabKey == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            addAfter(event, Items.SOUL_LANTERN, LIGHT_BULB.get(), TINTED_LIGHT_BULB.get());
            addAfter(event, Items.END_ROD, SQUID_LAMP.get());
            addAfter(event, Items.SEA_LANTERN, IRIDESCENT_LANTERN.get());
            addAfter(event, Items.JUKEBOX, DRUM.get());
            addAfter(event, Items.SCAFFOLDING, FRAME.get());
            addAfter(event, Items.DECORATED_POT, TERRACOTTA_VASE.get());
            addAfter(event, Items.GLOW_ITEM_FRAME, ITEM_STAND.get(), GLOW_ITEM_STAND.get());
            addAfter(event, Items.ENDER_CHEST, PRICKLY_CAN.get());
            addAfter(event, Items.SUSPICIOUS_GRAVEL, CRUMBLING_STONE.get(), WAXED_CRUMBLING_STONE.get());
            addAfter(event, Items.BELL, DREAM_CATCHER.get());
            addAfter(event, Items.CAULDRON, COPPER_TAP.get());
            addBefore(event, Items.SKELETON_SKULL, RUBBER_CHICKEN.get());
        } else if (tabKey == CreativeModeTabs.REDSTONE_BLOCKS) {
            addAfter(event, Items.COMPARATOR, REDSTONE_WIRES.get(), REDSTONE_WIRE_TORCH.get(), REDSTONE_WIRE_REPEATER.get(), REDSTONE_WIRE_COMPARATOR.get());
            addAfter(event, Items.HEAVY_WEIGHTED_PRESSURE_PLATE, DRUM.get());
            addAfter(event, Items.WHITE_WOOL, DICE.get());
            addAfter(event, Items.BARREL, PRICKLY_CAN.get());
            addAfter(event, Items.STONE_BUTTON, RUBBER_BUTTON.get());
        } else if (tabKey == CreativeModeTabs.SPAWN_EGGS) {
            addAfter(event, Items.CAVE_SPIDER_SPAWN_EGG, CHAPPLE_SPAWN_EGG.get());
            addAfter(event, Items.WARDEN_SPAWN_EGG, WEAVER_SPAWN_EGG.get());
        } else if (tabKey == CreativeModeTabs.COMBAT) {
            addAfter(event, Items.EGG, EGGPLE.get(), GOLDEN_EGGPLE.get());
            addAfter(event, Items.TURTLE_HELMET,
                    TIDAL_HELMET.get(),
                    SILKEN_SLACKS.get(),
                    ADVENTURERS_BOOTS.get(),
                    WHITE_HAT.get(),
                    TRADER_HOOD.get(),
                    WHITE_SWEATER.get(),
                    TRADER_ROBE.get()
            );
            addAfter(event, Items.TRIDENT, HAMMER.get());
            addBefore(event, Items.TOTEM_OF_UNDYING, GOLDEN_GOLEM.get());
        } else if (tabKey == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            addAfter(event, Items.BRUSH, HAMMER.get(), CHISEL.get(), WRENCH.get(), HANDBELL.get());
            addAfter(event, Items.BAMBOO_CHEST_RAFT, TURTLE_RAFT.get());
            addAfter(event, Items.NAME_TAG, ITEM_LABEL.get());
            addBefore(event, Items.MUSIC_DISC_OTHERSIDE, MUSIC_DISC_SQUALL.get());
        } else if (tabKey == CreativeModeTabs.NATURAL_BLOCKS) {
            addAfter(event, Items.GRAVEL, GRAVEL_PATH.get());
            addAfter(event, Items.SAND, SAND_PATH.get());
            addAfter(event, Items.RED_SAND, RED_SAND_PATH.get());
            addAfter(event, Items.SNOW, SNOW_PATH.get());
            addAfter(event, Items.NETHER_GOLD_ORE, NETHER_BISMUTH_ORE.get());
            addAfter(event, Items.RAW_GOLD_BLOCK, RAW_BISMUTH_BLOCK.get());
            addAfter(event, Items.PINK_PETALS, BOUQUET.get());
            addAfter(event, Items.BEETROOT_SEEDS, COTTON_SEEDS.get());
        } else if (tabKey == CreativeModeTabs.COLORED_BLOCKS) {
            addBefore(event, Items.WHITE_CONCRETE, IRIDESCENT_CONCRETE.get());
            addBefore(event, Items.WHITE_WOOL, IRIDESCENT_WOOL.get());
            addBefore(event, Items.WHITE_GLAZED_TERRACOTTA, IRIDESCENT_GLAZED_TERRACOTTA.get());
            addAfter(event, Items.TERRACOTTA, IRIDESCENT_TERRACOTTA.get());
            addAfter(event, Items.GLASS, IRIDESCENT_GLASS.get());
            addAfter(event, Items.GLASS_PANE, IRIDESCENT_GLASS_PANE.get());
            addAfter(event, Items.PINK_BED,
                    WHITE_SWEATER.get(), LIGHT_GRAY_SWEATER.get(), GRAY_SWEATER.get(), BLACK_SWEATER.get(), BROWN_SWEATER.get(), RED_SWEATER.get(),
                    ORANGE_SWEATER.get(), YELLOW_SWEATER.get(), LIME_SWEATER.get(), GREEN_SWEATER.get(), CYAN_SWEATER.get(), LIGHT_BLUE_SWEATER.get(),
                    BLUE_SWEATER.get(), PURPLE_SWEATER.get(), MAGENTA_SWEATER.get(), PINK_SWEATER.get(), WHITE_HAT.get(), LIGHT_GRAY_HAT.get(),
                    GRAY_HAT.get(), BLACK_HAT.get(), BROWN_HAT.get(), RED_HAT.get(), ORANGE_HAT.get(), YELLOW_HAT.get(), LIME_HAT.get(), GREEN_HAT.get(),
                    CYAN_HAT.get(), LIGHT_BLUE_HAT.get(), BLUE_HAT.get(), PURPLE_HAT.get(), MAGENTA_HAT.get(), PINK_HAT.get()
            );
        } else if (tabKey == CreativeModeTabs.INGREDIENTS) {
            addAfter(event, Items.NETHERITE_INGOT, RUBBER.get());
            addAfter(event, Items.EGG, EGGPLE.get(), GOLDEN_EGGPLE.get());
            addAfter(event, Items.RAW_GOLD, RAW_BISMUTH.get());
            addAfter(event, Items.GOLD_INGOT, BISMUTH_INGOT.get());
            addAfter(event, Items.WHEAT, COTTON_FLOWER.get());
        }
    }

    private void addAfter(BuildCreativeModeTabContentsEvent event, Item anchor, Item... items) {
        ItemStack anchorStack = new ItemStack(anchor);
        for (Item item : items) {
            ItemStack newStack = new ItemStack(item);
            event.insertAfter(anchorStack, newStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            anchorStack = newStack;
        }
    }

    private void addBefore(BuildCreativeModeTabContentsEvent event, Item anchor, Item... items) {
        ItemStack anchorStack = new ItemStack(anchor);
        for (int i = items.length - 1; i >= 0; i--) {
            ItemStack newStack = new ItemStack(items[i]);
            event.insertBefore(anchorStack, newStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            anchorStack = newStack;
        }
    }
}