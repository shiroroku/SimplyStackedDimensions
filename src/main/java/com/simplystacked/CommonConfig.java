package com.simplystacked;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class CommonConfig {
	public static final ForgeConfigSpec COMMONCONFIG;

	public static final ForgeConfigSpec.IntValue TELEPORT_COOLDOWN;
	public static final ForgeConfigSpec.BooleanValue ALLOW_SNEAK;
    public static final ForgeConfigSpec.BooleanValue RESET_FALL_DISTANCE;

	static {
		ForgeConfigSpec.Builder commonBuilder = new ForgeConfigSpec.Builder();

		commonBuilder.push("Teleporting");
		TELEPORT_COOLDOWN = commonBuilder.comment("How long in ticks before the entity can teleport again").defineInRange("cooldown", 60, 0, Integer.MAX_VALUE);
		ALLOW_SNEAK = commonBuilder.comment("Whether sneaking halves the amount of time it takes to switch dimensions").define("allow_sneak", true);
        RESET_FALL_DISTANCE = commonBuilder.comment("Reset the fall distance when teleporting, prevents dying from hitting clouds/unseen blocks in next dimension").define("reset_fall_distance", true);
		commonBuilder.pop();

		COMMONCONFIG = commonBuilder.build();
	}
}
