package com.ninni.etcetera.mixin;

import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSourceList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(SpriteSourceList.class)
public interface SpriteSourceListAccessor {

    @Accessor("sources")
    List<SpriteSource> etcetera$getSources();

    @Invoker("<init>")
    static SpriteSourceList etcetera$create(List<SpriteSource> sources) {
        throw new AssertionError();
    }
}
