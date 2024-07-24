package com.simplystacked.Teleporting;


import net.minecraft.resources.ResourceLocation;

public class TeleportSetting {

    private final ResourceLocation from;
    private final ResourceLocation to;
    private final int fromYMin;
    private final int fromYMax;
    private final int toY;
    private final boolean cloud;

    public TeleportSetting(ResourceLocation from, ResourceLocation to, int fromYMin, int fromYMax, int toY, boolean cloud) {
        this.from = from;
        this.to = to;
        this.fromYMin = fromYMin;
        this.fromYMax = fromYMax;
        this.toY = toY;
        this.cloud = cloud;
    }

    /**
     * Gets the "from" dimension ID, where the entity triggers the teleport.
     */
    public ResourceLocation getFrom() {
        return from;
    }

    /**
     * Gets the "to" dimension ID, where the entity is teleported to.
     */
    public ResourceLocation getTo() {
        return to;
    }

    /**
     * Minimum height the entity must be to trigger teleport.
     */
    public int getFromYMin() {
        return fromYMin;
    }

    /**
     * Maximum height the entity must be to trigger teleport.
     */
    public int getFromYMax() {
        return fromYMax;
    }

    /**
     * The Y to teleport the entity to.
     */
    public int getToY() {
        return toY;
    }

    /**
     * If a cloud should generate beneath the entity upon entering the "to" dimension.
     */
    public boolean hasCloud() {
        return cloud;
    }
}
