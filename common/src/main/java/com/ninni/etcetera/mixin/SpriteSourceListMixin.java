package com.ninni.etcetera.mixin;

import com.ninni.etcetera.Constants;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(SpriteSourceList.class)
public class SpriteSourceListMixin {
    @Unique
    private static final ResourceLocation ARMOR_TRIMS = ResourceLocation.withDefaultNamespace("armor_trims");
    @Unique
    private static final String PATTERN_DIRECTORY = "textures/trims/models/armor/";

    @Inject(method = "load", at = @At("RETURN"), cancellable = true)
    private static void etcetera$addBismuthToEveryPattern(ResourceManager manager, ResourceLocation atlas, CallbackInfoReturnable<SpriteSourceList> cir) {
        if (!atlas.equals(ARMOR_TRIMS)) return;
        List<ResourceLocation> textures = new ArrayList<>();
        manager.listResources("textures/trims/models/armor", location -> location.getPath().endsWith(".png")).keySet().forEach(location -> {
            String name = location.getPath().substring(PATTERN_DIRECTORY.length(), location.getPath().length() - ".png".length());
            if (!name.contains("/")) textures.add(location.withPath("trims/models/armor/" + name));
        });
        if (textures.isEmpty()) return;
        List<SpriteSource> sources = new ArrayList<>(((SpriteSourceListAccessor) cir.getReturnValue()).etcetera$getSources());
        sources.add(PalettedPermutationsInvoker.etcetera$create(textures, ResourceLocation.withDefaultNamespace("trims/color_palettes/trim_palette"),
                Map.of("bismuth", ResourceLocation.fromNamespaceAndPath(Constants.MOD_ID, "trims/color_palettes/bismuth"))));
        cir.setReturnValue(SpriteSourceListAccessor.etcetera$create(sources));
    }
}
