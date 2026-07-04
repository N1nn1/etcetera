package com.ninni.etcetera.compat.emi;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.ninni.etcetera.Constants.MOD_ID;

public class BlockTransformationEmiRecipe implements EmiRecipe {
    private final ResourceLocation id;
    private final EmiRecipeCategory category;
    private final EmiIngredient inputBlock;
    private final EmiIngredient tool;
    private final EmiStack outputBlock;

    public BlockTransformationEmiRecipe(EmiRecipeCategory category, Block inputBlock, EmiIngredient tool, Block outputBlock) {
        this.category = category;
        this.inputBlock = EmiStack.of(inputBlock);
        this.tool = tool;
        this.outputBlock = EmiStack.of(outputBlock);

        ResourceLocation inputId = net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(inputBlock);
        ResourceLocation outputId = net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(outputBlock);
        this.id = ResourceLocation.fromNamespaceAndPath(MOD_ID, "/" + category.getId().getPath() + "/" + inputId.getNamespace() + "/" + inputId.getPath() + "_to_" + outputId.getPath());
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return this.category;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return this.id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(this.inputBlock, this.tool);
    }

    @Override
    public List<EmiIngredient> getCatalysts() {
        return List.of(this.tool);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(this.outputBlock);
    }

    @Override
    public int getDisplayWidth() {
        return 130;
    }

    @Override
    public int getDisplayHeight() {
        return 36;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addSlot(this.inputBlock, 5, 9);
        widgets.addTexture(EmiTexture.PLUS, 27, 13);
        widgets.addSlot(this.tool, 43, 9).catalyst(true);
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 67, 9);
        widgets.addSlot(this.outputBlock, 97, 5).recipeContext(this).large(true);
    }
}