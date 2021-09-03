package com.simplystacked.Teleporting;

import com.simplystacked.Block.BlockAndItemRegistry;
import com.simplystacked.CommonConfig;
import com.simplystacked.Data.DimensionJsonReloader;
import com.simplystacked.SimplyStackedDimensions;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportHandler {

	private static final HashMap<UUID, Integer> cooldownCache = new HashMap<>();

	public static void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
		LivingEntity entity = e.getEntityLiving();

		//We currently only deal with players
		if (!(entity instanceof PlayerEntity) || entity.level.isClientSide) {
			return;
		}

		updateCooldownCache();

		if (!entity.canChangeDimensions()) {
			return;
		}

		for (TeleportSetting setting : DimensionJsonReloader.dimensionConfigs) {
			if (setting.getFrom().equals(e.getEntityLiving().level.dimension().location())) {

				int cooldown = 0;
				//Load cooldown from cache
				if (cooldownCache.containsKey(entity.getUUID())) {
					cooldown = cooldownCache.get(entity.getUUID());
				}

				if (setting != null && entity.getY() <= setting.getFromYMax() && entity.getY() >= setting.getFromYMin() && cooldown <= 0) {

					RegistryKey<World> registrykey = RegistryKey.create(Registry.DIMENSION_REGISTRY, setting.getTo());
					if (registrykey == null) {
						SimplyStackedDimensions.LOGGER.error("Error when getting dimension {" + setting.getTo().toString() + "} does not exist!");
						return;
					}

					ServerWorld serverWorld = ((ServerWorld) entity.level).getServer().getLevel(registrykey);
					if (serverWorld != null) {

						//Teleport entity and set cooldown
						cooldownCache.put(entity.getUUID(), CommonConfig.TELEPORT_COOLDOWN.get());
						entity.changeDimension(serverWorld, new DimensionTeleporter(setting));
						BlockPos tplocation = new BlockPos(entity.getX(), setting.getToY(), entity.getZ());

						//Place cloud platform
						if (setting.hasCloud() && serverWorld.getBlockState(tplocation.below()).getBlock() == Blocks.AIR) {
							TeleportHandler.generateCloudPlatform(serverWorld, tplocation.below());
						}

						//Clear 1x2 for player
						serverWorld.setBlockAndUpdate(tplocation, Blocks.AIR.defaultBlockState());
						serverWorld.setBlockAndUpdate(tplocation.above(), Blocks.AIR.defaultBlockState());
					}

					return;
				}
			}
		}
	}

	/**
	 * Updates all entries in the cooldown cache. Decrements the cooldown value. Removes entries with 0 cooldown.
	 */
	private static void updateCooldownCache() {
		for (Map.Entry<UUID, Integer> entry : cooldownCache.entrySet()) {
			if (entry.getValue() <= 0) {
				cooldownCache.remove(entry.getKey());
			} else {
				cooldownCache.put(entry.getKey(), entry.getValue() - 1);
			}
		}
	}

	private static void generateCloudPlatform(ServerWorld world, BlockPos origin) {
		int radius = 2;
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				BlockPos pos = origin.offset(x, 0, z);
				if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
					world.setBlockAndUpdate(pos, BlockAndItemRegistry.CLOUD.get().defaultBlockState());
				}
			}
		}

		radius--;
		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				BlockPos pos = origin.offset(x, -1, z);
				if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
					world.setBlockAndUpdate(pos, BlockAndItemRegistry.CLOUD.get().defaultBlockState());
				}
			}
		}
	}
}
