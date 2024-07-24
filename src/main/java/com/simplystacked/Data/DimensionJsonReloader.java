package com.simplystacked.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simplystacked.SimplyStackedDimensions;
import com.simplystacked.Teleporting.TeleportSetting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DimensionJsonReloader extends SimpleJsonResourceReloadListener {

    public static List<TeleportSetting> dimensionConfigs = new ArrayList<>();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public DimensionJsonReloader() {
        super(GSON, "config");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager pResourceManager, ProfilerFiller pProfiler) {
        dimensionConfigs.clear();
        map.forEach((file, jsonElement) -> {
            try {
                JsonObject config = jsonElement.getAsJsonObject();

                ResourceLocation from = ResourceLocation.tryParse(config.get("from").getAsString());
                ResourceLocation to = ResourceLocation.tryParse(config.get("to").getAsString());
                int fromYMin = config.get("from_y_min").getAsInt();
                int fromYMax = config.get("from_y_max").getAsInt();
                int toY = config.get("to_y").getAsInt();
                boolean cloud = config.get("cloud_platform").getAsBoolean();

                dimensionConfigs.add(new TeleportSetting(from, to, fromYMin, fromYMax, toY, cloud));

            } catch (Exception e) {
                SimplyStackedDimensions.LOGGER.error("Error when loading [{}] from data!: {}", file.toString(), e.getMessage());
            }
        });
    }
}
