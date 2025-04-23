package net.homak.homakmod.util.handler;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.homak.homakmod.item.ItemClasses.SoulWarperItem;
import net.minecraft.server.world.ServerWorld;

public class ServerTickHandler {
    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(world -> {
            if (!world.isClient() && world.getTime() % 2 == 0) {
                SoulWarperItem.WarpPosTracker.tick((ServerWorld) world);
            }
        });
    }
}
