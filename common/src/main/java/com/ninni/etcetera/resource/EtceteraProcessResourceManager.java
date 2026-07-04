package com.ninni.etcetera.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ninni.etcetera.Constants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.block.Block;

import java.util.Collections;
import java.util.Map;

import static com.ninni.etcetera.Constants.MOD_ID;

public class EtceteraProcessResourceManager extends SimpleJsonResourceReloadListener {
    public static final String FOLDER_KEY = "process";
    private static final Gson GSON = new GsonBuilder().create();

    private final String id;
    private Data data;

    public EtceteraProcessResourceManager(String id) {
        super(GSON, FOLDER_KEY);
        this.id = id;
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> prepared, ResourceManager manager, ProfilerFiller profiler) {
        try {
            if (prepared.get(ResourceLocation.fromNamespaceAndPath(MOD_ID, this.id)) instanceof JsonObject jsonObject) {
                this.data = Data.CODEC.parse(JsonOps.INSTANCE, jsonObject).result().orElseThrow();
            } else {
                throw new RuntimeException("Was not a json object");
            }
        } catch (Exception exception) {
            Constants.LOG.error("Couldn't read list {}", id, exception);
        }
    }

    public Map<Block, Block> getMap() {
        return this.data == null ? Collections.emptyMap() : this.data.map();
    }

    public ResourceLocation getFabricId() {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    public record Data(Map<Block, Block> map) {
        public static final Codec<Data> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                        Codec.unboundedMap(BuiltInRegistries.BLOCK.byNameCodec(), BuiltInRegistries.BLOCK.byNameCodec())
                                .fieldOf("entries")
                                .forGetter(Data::map)
                ).apply(instance, Data::new));
    }
}