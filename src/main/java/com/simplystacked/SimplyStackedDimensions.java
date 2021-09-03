package com.simplystacked;

import com.simplystacked.Block.BlockAndItemRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SimplyStackedDimensions.MODID)
public class SimplyStackedDimensions {

	public static final String MODID = "simplystacked";
	public static final Logger LOGGER = LogManager.getLogger();

	public SimplyStackedDimensions() {
		BlockAndItemRegistry.init();
		Events.init();

		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.COMMONCONFIG);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientSetup::init);
	}
}
