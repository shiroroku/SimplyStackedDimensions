package com.simplystacked;

import com.simplystacked.Data.DimensionJsonReloader;
import com.simplystacked.Teleporting.TeleportHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;

public class Events {

	public static void init() {
		MinecraftForge.EVENT_BUS.addListener(Events::onAddReloadListenerEvent);
		MinecraftForge.EVENT_BUS.addListener(TeleportHandler::onLivingUpdate);
	}

	public static void onAddReloadListenerEvent(AddReloadListenerEvent e) {
		e.addListener(new DimensionJsonReloader());
	}

}
