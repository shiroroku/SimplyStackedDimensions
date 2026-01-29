package com.simplystacked.Teleporting;

import com.simplystacked.CommonConfig;
import com.simplystacked.Data.DimensionJsonReloader;
import com.simplystacked.SimplyStackedDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportHandler {

    private static final HashMap<UUID, Integer> cooldownCache = new HashMap<>();

    public static void onLivingTick(LivingEvent.LivingTickEvent e) {
        LivingEntity entity = e.getEntity();

        //We currently only deal with players
        if (!(entity instanceof Player) || entity.level().isClientSide()) {
            return;
        }

        updateCooldownCache();

        if (!entity.canChangeDimensions()) {
            return;
        }

        for (TeleportSetting setting : DimensionJsonReloader.dimensionConfigs) {
            if (setting.getFrom().equals(e.getEntity().level().dimension().location())) {

                int cooldown = 0;
                //Load cooldown from cache
                if (cooldownCache.containsKey(entity.getUUID())) {
                    cooldown = cooldownCache.get(entity.getUUID());
                }

                if (setting != null && entity.getY() <= setting.getFromYMax() && entity.getY() >= setting.getFromYMin() && cooldown <= 0) {

                    ResourceKey<Level> registrykey = ResourceKey.create(Registries.DIMENSION, setting.getTo());
                    if (registrykey == null) {
                        SimplyStackedDimensions.LOGGER.error("Error when getting dimension [{}] does not exist!", setting.getTo().toString());
                        return;
                    }

                    ServerLevel serverWorld = ((ServerLevel) entity.level()).getServer().getLevel(registrykey);
                    if (serverWorld != null) {

                        //Teleport entity and set cooldown
                        cooldownCache.put(entity.getUUID(), CommonConfig.TELEPORT_COOLDOWN.get());
                        entity.changeDimension(serverWorld, new DimensionTeleporter(setting));
                        BlockPos tplocation = new BlockPos(entity.getBlockX(), setting.getToY(), entity.getBlockZ());

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
        Iterator<Map.Entry<UUID, Integer>> it = cooldownCache.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<UUID, Integer> entry = it.next();
            int value = entry.getValue() - 1;
            if (value <= 0) {
                it.remove();
            } else {
                entry.setValue(value);
            }
        }
    }

    private static void generateCloudPlatform(ServerLevel world, BlockPos origin) {
        int radius = 2;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos pos = origin.offset(x, 0, z);
                if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
                    world.setBlockAndUpdate(pos, SimplyStackedDimensions.CLOUD.get().defaultBlockState());
                }
            }
        }

        radius--;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                BlockPos pos = origin.offset(x, -1, z);
                if (world.getBlockState(pos).getBlock() == Blocks.AIR) {
                    world.setBlockAndUpdate(pos, SimplyStackedDimensions.CLOUD.get().defaultBlockState());
                }
            }
        }
    }
}
