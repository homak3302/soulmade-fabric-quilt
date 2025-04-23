package net.homak.homakmod.util;

import net.homak.homakmod.HomakMod;
import net.homak.homakmod.item.ModItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ModModelPredicates {
    public static void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(ModItems.FRACTURED_SLAYER, Identifier.of(HomakMod.MOD_ID, "used"),
                (stack, world, entity, seed) -> {
                    if (entity instanceof PlayerEntity player) {

                        return player.getItemCooldownManager().isCoolingDown(stack.getItem()) ? 1.0F : 0.0F;
                    }
                    return 0.0F;
                });
    }
}
