package net.homak.homakmod.item.ItemClasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoulRocketLauncherItem extends Item {
    private static final float LAUNCH_POWER = 2.0f;
    private static final float PROJECTILE_SPAWN_DISTANCE = 4.0f;
    private static final float EXPLOSION_POWER = 1.0f;
    private static final int COOLDOWN_TICKS = 5 * 20;

    public SoulRocketLauncherItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);

        if (!world.isClient) {
            // Launch player backward

            Vec3d lookVec = user.getRotationVector().multiply(-LAUNCH_POWER, -LAUNCH_POWER, -LAUNCH_POWER);
            user.addVelocity(lookVec.x, lookVec.y, lookVec.z);
            user.velocityModified = true;

            // Spawn wind charge 4 blocks in front
            Vec3d spawnPos = user.getPos()
                    .add(user.getRotationVector().multiply(PROJECTILE_SPAWN_DISTANCE))
                    .add(0, user.getEyeHeight(user.getPose()), 0);

            // Create explosion
            world.createExplosion(
                    user,
                    spawnPos.x,
                    spawnPos.y,
                    spawnPos.z,
                    EXPLOSION_POWER,
                    World.ExplosionSourceType.NONE
            );

            // Play sounds
            world.playSound(
                    null,
                    user.getX(),
                    user.getY(),
                    user.getZ(),
                    SoundEvents.ENTITY_WIND_CHARGE_WIND_BURST,
                    SoundCategory.PLAYERS,
                    0.7f,
                    0.8f
            );

            world.playSound(
                    null,
                    spawnPos.x,
                    spawnPos.y,
                    spawnPos.z,
                    SoundEvents.PARTICLE_SOUL_ESCAPE,
                    SoundCategory.BLOCKS,
                    1.0f,
                    1.2f
            );

                user.getItemCooldownManager().set(this, COOLDOWN_TICKS);


            if (user instanceof ServerPlayerEntity) {
                ((ServerPlayerEntity) user).incrementStat(Stats.USED.getOrCreateStat(this));
            }
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }
}