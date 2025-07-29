package com.simplystacked;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonConfig {
	public static final ForgeConfigSpec COMMONCONFIG;

	public static final ForgeConfigSpec.IntValue TELEPORT_COOLDOWN;

	public static final ForgeConfigSpec.BooleanValue ALLOW_SNEAK;

	static {
		ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();

		commonBuilder.push("Teleporting");
		TELEPORT_COOLDOWN = commonBuilder.comment("How long in ticks before the entity can teleport again").defineInRange("cooldown", 60, 0, Integer.MAX_VALUE);
		commonBuilder.pop();

		commonBuilder.push("Allow Sneak");
		ALLOW_SNEAK = commonBuilder.comment("Whether sneaking doubles the amount of time it takes to switch dimensions").define("allow_sneak", true);
		commonBuilder.pop();
		
		COMMONCONFIG = commonBuilder.build();
	}
}
