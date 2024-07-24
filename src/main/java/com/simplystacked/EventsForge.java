package com.simplystacked;

import com.simplystacked.Data.DimensionJsonReloader;
import com.simplystacked.Teleporting.TeleportHandler;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = SimplyStackedDimensions.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventsForge {

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingTickEvent e) {
        TeleportHandler.onLivingTick(e);
    }

    @SubscribeEvent
    public static void onAddReloadListenerEvent(AddReloadListenerEvent e) {
        e.addListener(new DimensionJsonReloader());
    }
}
