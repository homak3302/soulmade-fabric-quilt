package net.homak.homakmod.item.ItemClasses;

import net.homak.homakmod.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class SoulHideoutItem extends Item {

    private static final int DURATION_TICKS = 20 * 20;

    public SoulHideoutItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);

        if (!world.isClient && entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;

            // Check if player has the item in their inventory
            boolean hasItem = false;
            for (ItemStack item : player.getInventory().main) {
                if (item.getItem() == this) {
                    hasItem = true;
                    break;
                }
            }

            // Apply or remove effect based on item presence
            if (hasItem) {
                // Apply effect with short duration that will be refreshed every tick
                player.addStatusEffect(new StatusEffectInstance(ModEffects.HIDEOUT, 1, 0, false, false));
            } else {
                // Remove effect if item is no longer in inventory
                player.removeStatusEffect(ModEffects.HIDEOUT);
            }
        }
    }
}
