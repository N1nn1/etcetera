package com.ninni.etcetera.resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.ninni.etcetera.Etcetera;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.Collections;
import java.util.Map;

import static com.ninni.etcetera.Etcetera.*;

public class EtceteraProcessResourceManager extends JsonDataLoader implements IdentifiableResourceReloadListener {
    public static final String FOLDER_KEY = "processes";
    private static final Gson GSON = new GsonBuilder().create();

    private final String id;
    private Data data;

    public EtceteraProcessResourceManager(String id) {
        super(GSON, FOLDER_KEY);
        this.id = id;
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        try {
            if (prepared.get(new Identifier(MOD_ID, this.id)) instanceof JsonObject jsonObject) {
                this.data = Data.CODEC.parse(JsonOps.INSTANCE, jsonObject).result().orElseThrow();
            } else {
                throw new RuntimeException("Was not a json object");
            }
        }  catch (Exception exception) {
            Etcetera.LOGGER.error("Couldn't read list {}", id, exception);
        }
    }

    public Map<Block, Block> getMap() {
        return this.data == null ? Collections.emptyMap() : this.data.map();
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(MOD_ID, id);
    }

    public record Data(Map<Block, Block> map) {
        public static final Codec<Data> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                Codec.unboundedMap(Registries.BLOCK.getCodec(), Registries.BLOCK.getCodec())
                     .fieldOf("entries")
                     .forGetter(Data::map)
            ).apply(instance, Data::new));
    }
}
