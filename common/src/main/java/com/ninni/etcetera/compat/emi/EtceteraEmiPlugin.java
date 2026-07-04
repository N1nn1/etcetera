package com.ninni.etcetera.compat.emi;

import com.ninni.etcetera.registry.EtceteraItems;
import com.ninni.etcetera.registry.EtceteraVanillaIntegration;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.Map;

import static com.ninni.etcetera.Constants.MOD_ID;

@EmiEntrypoint
public class EtceteraEmiPlugin implements EmiPlugin {

    public static final EmiRecipeCategory HAMMERING = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "hammering"),
            EmiStack.of(EtceteraItems.HAMMER.get())
    );

    public static final EmiRecipeCategory CHISELING = new EmiRecipeCategory(
            ResourceLocation.fromNamespaceAndPath(MOD_ID, "chiseling"),
            EmiStack.of(EtceteraItems.CHISEL.get())
    );

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(HAMMERING);
        registry.addCategory(CHISELING);

        EmiIngredient hammerStack = EmiStack.of(EtceteraItems.HAMMER.get());
        registry.addWorkstation(HAMMERING, hammerStack);

        EmiIngredient chiselStack = EmiStack.of(EtceteraItems.CHISEL.get());
        registry.addWorkstation(CHISELING, chiselStack);

        Map<Block, Block> hammerMap = EtceteraVanillaIntegration.HAMMERING_MANAGER.getMap();
        for (Map.Entry<Block, Block> entry : hammerMap.entrySet()) {
            registry.addRecipe(new BlockTransformationEmiRecipe(
                    HAMMERING,
                    entry.getKey(),
                    hammerStack,
                    entry.getValue()
            ));
        }

        Map<Block, Block> chiselMap = EtceteraVanillaIntegration.CHISELLING_MANAGER.getMap();
        for (Map.Entry<Block, Block> entry : chiselMap.entrySet()) {
            registry.addRecipe(new BlockTransformationEmiRecipe(
                    CHISELING,
                    entry.getKey(),
                    chiselStack,
                    entry.getValue()
            ));
        }
    }
}