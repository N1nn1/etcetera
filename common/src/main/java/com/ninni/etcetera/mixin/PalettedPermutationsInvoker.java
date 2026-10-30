package com.ninni.etcetera.mixin;

import net.minecraft.client.renderer.texture.atlas.sources.PalettedPermutations;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;
import java.util.Map;

@Mixin(PalettedPermutations.class)
public interface PalettedPermutationsInvoker {

    @Invoker("<init>")
    static PalettedPermutations etcetera$create(List<ResourceLocation> textures, ResourceLocation paletteKey, Map<String, ResourceLocation> permutations) {
        throw new AssertionError();
    }
}
