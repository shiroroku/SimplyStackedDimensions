package com.simplystacked;

import com.simplystacked.Block.BlockAndItemRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = SimplyStackedDimensions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientSetup {

	public static void init(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(BlockAndItemRegistry.CLOUD.get(), RenderType.translucent());
	}

}
