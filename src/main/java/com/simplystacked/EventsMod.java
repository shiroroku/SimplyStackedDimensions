package com.simplystacked;

import com.simplystacked.Data.DataGenBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimplyStackedDimensions.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class EventsMod {

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(event.includeClient(), new DataGenBlocks(generator.getPackOutput(), event.getExistingFileHelper()));
    }
}
