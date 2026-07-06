package com.ninni.etcetera.mixin.compat;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class EtceteraCommonMixinPlugin implements IMixinConfigPlugin {

    private static boolean isModLoaded(String modId) {
        try {
            Class<?> fabricLoaderClass = Class.forName("net.fabricmc.loader.api.FabricLoader");
            Object fabricLoader = fabricLoaderClass.getMethod("getInstance").invoke(null);
            return (boolean) fabricLoaderClass.getMethod("isModLoaded", String.class).invoke(fabricLoader, modId);
        } catch (Throwable ignored) {
        }
        try {
            Class<?> fmlLoaderClass = Class.forName("net.neoforged.fml.loading.FMLLoader");
            Object loadingModList = fmlLoaderClass.getMethod("getLoadingModList").invoke(null);
            Object modFile = loadingModList.getClass().getMethod("getModFileById", String.class).invoke(loadingModList, modId);
            return modFile != null;
        } catch (Throwable ignored) {
        }
        return false;
    }

    private static boolean isClient() {
        try {
            Class<?> fabricLoaderClass = Class.forName("net.fabricmc.loader.api.FabricLoader");
            Object fabricLoader = fabricLoaderClass.getMethod("getInstance").invoke(null);
            Object envType = fabricLoaderClass.getMethod("getEnvironmentType").invoke(fabricLoader);
            return "CLIENT".equals(envType.toString());
        } catch (Throwable ignored) {}
        try {
            Class<?> fmlLoaderClass = Class.forName("net.neoforged.fml.loading.FMLLoader");
            Object dist = fmlLoaderClass.getMethod("getDist").invoke(null);
            return "CLIENT".equals(dist.toString());
        } catch (Throwable ignored) {}
        return false;
    }

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.contains("EasyAnvilsCompatMixin") || mixinClassName.contains("C2SNameTagUpdateMessageMixin")) {
            return isModLoaded("easyanvils");
        }
        if (mixinClassName.contains("NameTagEditScreenMixin")) {
            return isModLoaded("easyanvils") && isClient();
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }
}
