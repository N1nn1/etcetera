package com.ninni.etcetera.registry;

import com.ninni.etcetera.Constants;
import com.ninni.etcetera.platform.services.RegistrationProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;

import java.util.Comparator;
import java.util.function.Predicate;

import static com.ninni.etcetera.registry.EtceteraItems.*;

public class EtceteraCreativeModeTab {
    public static final RegistrationProvider<CreativeModeTab> CREATIVE_MODE_TABS = RegistrationProvider.get(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);

    public static final CreativeModeTab ITEM_GROUP = register("item_group", CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(ETCETERA.get()))
            .title(Component.translatable("etcetera.item_group"))
            .displayItems((featureFlagSet, output) -> {
                output.accept(RAW_BISMUTH_BLOCK.get());
                output.accept(BISMUTH_BLOCK.get());
                output.accept(BISMUTH_BARS.get());
                output.accept(NETHER_BISMUTH_ORE.get());
                output.accept(RAW_BISMUTH.get());
                output.accept(BISMUTH_INGOT.get());
                output.accept(IRIDESCENT_GLASS.get());
                output.accept(IRIDESCENT_GLASS_PANE.get());
                output.accept(IRIDESCENT_TERRACOTTA.get());
                output.accept(IRIDESCENT_GLAZED_TERRACOTTA.get());
                output.accept(IRIDESCENT_CONCRETE.get());
                output.accept(IRIDESCENT_WOOL.get());
                output.accept(IRIDESCENT_LANTERN.get());

                output.accept(CHISEL.get());
                output.accept(WRENCH.get());
                output.accept(HAMMER.get());
                output.accept(HANDBELL.get());

                output.accept(ITEM_LABEL.get());

                output.accept(DRUM.get());

                output.accept(DICE.get());

                output.accept(FRAME.get());

                output.accept(PRICKLY_CAN.get());

                output.accept(DREAM_CATCHER.get());

                output.accept(BOUQUET.get());
                output.accept(TERRACOTTA_VASE.get());

                output.accept(ITEM_STAND.get());
                output.accept(GLOW_ITEM_STAND.get());

                featureFlagSet.holders().lookup(Registries.PAINTING_VARIANT).ifPresent((wrapper) -> addEtcPaintings(output, wrapper, (registryEntry) -> registryEntry.is(EtceteraTags.ETCETERA_PAINTING_VARIANTS)));

                output.accept(SQUID_LAMP.get());
                output.accept(TIDAL_HELMET.get());
                output.accept(TURTLE_RAFT.get());

                output.accept(MUSIC_DISC_SQUALL.get());

                output.accept(ADVENTURERS_BOOTS.get());

                output.accept(GRAVEL_PATH.get());
                output.accept(SAND_PATH.get());
                output.accept(RED_SAND_PATH.get());
                output.accept(SNOW_PATH.get());

                output.accept(CRUMBLING_STONE.get());
                output.accept(WAXED_CRUMBLING_STONE.get());
                output.accept(LEVELED_STONE.get());
                output.accept(LEVELED_STONE_STAIRS.get());
                output.accept(LEVELED_STONE_SLAB.get());

                output.accept(LIGHT_BULB.get());
                output.accept(TINTED_LIGHT_BULB.get());

                output.accept(GOLDEN_GOLEM.get());

                output.accept(WEAVER_SPAWN_EGG.get());
                output.accept(SILKEN_SLACKS.get());

                output.accept(CHAPPLE_SPAWN_EGG.get());
                output.accept(EGGPLE.get());
                output.accept(GOLDEN_EGGPLE.get());

                output.accept(COPPER_TAP.get());

                output.accept(RUBBER.get());
                output.accept(RUBBER_BLOCK.get());
                output.accept(RUBBER_BUTTON.get());
                output.accept(RUBBER_CHICKEN.get());
                output.accept(REDSTONE_WIRES.get());
                output.accept(REDSTONE_WIRE_TORCH.get());
                output.accept(REDSTONE_WIRE_COMPARATOR.get());
                output.accept(REDSTONE_WIRE_REPEATER.get());

                output.accept(COTTON_SEEDS.get());
                output.accept(COTTON_FLOWER.get());
                output.accept(WHITE_SWEATER.get());
                output.accept(LIGHT_GRAY_SWEATER.get());
                output.accept(GRAY_SWEATER.get());
                output.accept(BLACK_SWEATER.get());
                output.accept(BROWN_SWEATER.get());
                output.accept(RED_SWEATER.get());
                output.accept(ORANGE_SWEATER.get());
                output.accept(YELLOW_SWEATER.get());
                output.accept(LIME_SWEATER.get());
                output.accept(GREEN_SWEATER.get());
                output.accept(CYAN_SWEATER.get());
                output.accept(LIGHT_BLUE_SWEATER.get());
                output.accept(BLUE_SWEATER.get());
                output.accept(PURPLE_SWEATER.get());
                output.accept(MAGENTA_SWEATER.get());
                output.accept(PINK_SWEATER.get());
                output.accept(TRADER_ROBE.get());
                output.accept(WHITE_HAT.get());
                output.accept(LIGHT_GRAY_HAT.get());
                output.accept(GRAY_HAT.get());
                output.accept(BLACK_HAT.get());
                output.accept(BROWN_HAT.get());
                output.accept(RED_HAT.get());
                output.accept(ORANGE_HAT.get());
                output.accept(YELLOW_HAT.get());
                output.accept(LIME_HAT.get());
                output.accept(GREEN_HAT.get());
                output.accept(CYAN_HAT.get());
                output.accept(LIGHT_BLUE_HAT.get());
                output.accept(BLUE_HAT.get());
                output.accept(PURPLE_HAT.get());
                output.accept(MAGENTA_HAT.get());
                output.accept(PINK_HAT.get());
                output.accept(TRADER_HOOD.get());
            }).build()
    );

    private static void addEtcPaintings(CreativeModeTab.Output entries, HolderLookup.RegistryLookup<PaintingVariant> registryLookup, Predicate<Holder<PaintingVariant>> predicate) {
        Comparator<Holder<PaintingVariant>> paintingComparator = Comparator.comparing(
                (Holder<PaintingVariant> holder) -> holder.value().width() * holder.value().height()
        ).thenComparing(
                (Holder<PaintingVariant> holder) -> holder.value().width()
        );

        registryLookup.listElements()
                .filter(predicate)
                .sorted(paintingComparator)
                .forEach((variant) -> {
                    ItemStack itemStack = new ItemStack(Items.PAINTING);
                    CustomData.update(DataComponents.ENTITY_DATA, itemStack, (tag) -> {
                        tag.putString("id", "minecraft:painting");

                        variant.unwrapKey().ifPresent(key -> tag.putString("variant", key.location().toString()));
                    });
                    entries.accept(itemStack);
                });
    }

    private static CreativeModeTab register(String id, CreativeModeTab tab) {
        CREATIVE_MODE_TABS.register(id, () -> tab);
        return tab;
    }

    public static void init() {
    }
}