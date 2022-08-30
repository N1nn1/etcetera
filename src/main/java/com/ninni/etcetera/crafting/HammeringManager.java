package com.ninni.etcetera.crafting;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ninni.etcetera.Etcetera;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.NoSuchElementException;

import static com.ninni.etcetera.Etcetera.*;

public class HammeringManager extends JsonDataLoader implements IdentifiableResourceReloadListener {
    private static final Gson GSON_INSTANCE = (new GsonBuilder()).create();
    private static final Map<Block, Block> HAMMERING = Maps.newHashMap();

    public HammeringManager() {
        super(GSON_INSTANCE, "loot_tables/gameplay");
    }

    @Override
    protected void apply(Map<Identifier, JsonElement> prepared, ResourceManager manager, Profiler profiler) {
        Identifier resourceLocation = new Identifier(MOD_ID, "loot_tables/gameplay/hammering.json");
        try {
            for (Resource iResource : manager.getAllResources(resourceLocation)) {
                try (Reader reader = new BufferedReader(new InputStreamReader(iResource.getInputStream(), StandardCharsets.UTF_8))) {
                    JsonObject jsonObject = JsonHelper.deserialize(GSON_INSTANCE, reader, JsonObject.class);
                    if (jsonObject != null) {
                        JsonArray entryList = jsonObject.get("entries").getAsJsonArray();
                        for (JsonElement entry : entryList) {
                            HAMMERING.put(Registry.BLOCK.get(new Identifier(entry.getAsJsonObject().get("block").getAsString())), Registry.BLOCK.get(new Identifier(entry.getAsJsonObject().get("hammered_block").getAsString())));
                        }
                    }
                } catch (RuntimeException | IOException exception) {
                    Etcetera.LOGGER.error("Couldn't read hammering list {} in data pack {}", resourceLocation, iResource.getResourcePackName(), exception);
                }
            }
        } catch (NoSuchElementException exception) {
            Etcetera.LOGGER.error("Couldn't read hammering from {}", resourceLocation, exception);
        }
    }

    public static Map<Block, Block> getHammering() {
        return HAMMERING;
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(MOD_ID, "loot_tables/gameplay");
    }

}
