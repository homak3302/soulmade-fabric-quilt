package net.homak.homakmod.item.ItemClasses;

import net.homak.homakmod.item.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.minecraft.util.math.Vec3d;

public class HarbingerGlaiveItem extends SwordItem {


    public HarbingerGlaiveItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) attacker;

            // Apply bonus damage to low-health enemies
            if (target.getHealth() <= target.getMaxHealth() * 0.3f) {
                float bonusDamage = 4.0f; // Adjust this value as needed
                target.damage(player.getDamageSources().playerAttack(player), bonusDamage);
            }

            // Check if the target died and is a player
            if (target.isDead() && target instanceof LivingEntity) {
                ItemStack soulBottle = new ItemStack(ModItems.SOUL_BOTTLE);
                if (!player.getInventory().insertStack(soulBottle)) {
                    player.dropItem(soulBottle, false);
                }
            }
        }

        return super.postHit(stack, target, attacker);
    }

    // Optional: Override method for increased reach (depends on your reach system)
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Optional: Manual extended reach attack (like a raycast polearm poke)
        if (!world.isClient) {
            double reach = 6.0; // default is 3.0 for players
            Vec3d start = user.getCameraPosVec(1.0f);
            Vec3d look = user.getRotationVec(1.0f);
            Vec3d end = start.add(look.multiply(reach));

            HitResult result = user.raycast(reach, 1.0F, false);
            if (result.getType() == HitResult.Type.ENTITY && result instanceof EntityHitResult entityHitResult) {
                Entity target = entityHitResult.getEntity();
                if (target instanceof LivingEntity living) {
                    user.attack(living);
                    user.swingHand(hand);
                    return TypedActionResult.success(user.getStackInHand(hand));
                }
            }
        }

        return super.use(world, user, hand);
    }
}
