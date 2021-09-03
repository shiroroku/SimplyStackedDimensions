package com.simplystacked.Teleporting;

import com.simplystacked.Teleporting.TeleportSetting;
import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class DimensionTeleporter implements ITeleporter {

	private final TeleportSetting setting;

	public DimensionTeleporter(TeleportSetting setting) {
		this.setting = setting;
	}

	public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
		return repositionEntity.apply(false);
	}

	public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
		return new PortalInfo(new Vector3d(entity.getX(), setting.getToY(), entity.getZ()), Vector3d.ZERO, entity.yRot, entity.xRot);
	}

	public boolean playTeleportSound(ServerPlayerEntity player, ServerWorld sourceWorld, ServerWorld destWorld) {
		return false;
	}

}
