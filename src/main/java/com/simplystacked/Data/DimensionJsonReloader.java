package com.simplystacked.Data;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.simplystacked.SimplyStackedDimensions;
import com.simplystacked.Teleporting.TeleportSetting;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DimensionJsonReloader extends JsonReloadListener {

	public static List<TeleportSetting> dimensionConfigs = new ArrayList<>();

	public DimensionJsonReloader() {
		super((new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create(), "config");
	}

	@Override
	public void apply(Map<ResourceLocation, JsonElement> map, IResourceManager manager, IProfiler profiler) {
		dimensionConfigs.clear();
		loadDimensionConfig(map);
	}

	private static void loadDimensionConfig(Map<ResourceLocation, JsonElement> map) {
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
				SimplyStackedDimensions.LOGGER.error("Error when loading {" + file.toString() + "} from data!: " + e.getMessage());
			}
		});
	}
}
