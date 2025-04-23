package net.homak.homakmod.item.ItemClasses; // The code was originaly for other item, and I am lazy to rename all of the stuff

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.util.math.Box;

public class SoulCurveswordItem extends SwordItem {
    private static final double DAMAGE = 0.5 * 2;
    private static final int COOLDOWN_TICKS = 10 * 20; // Cooldown seconds
    private static final int WHIRLWIND_DURATION_TICKS = 2 * 20; // Duration
    private static final double DAMAGE_RADIUS = 3.0; // Damage radius

    public SoulCurveswordItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient && !player.getItemCooldownManager().isCoolingDown(this)) {
            player.getItemCooldownManager().set(this, COOLDOWN_TICKS);
            activateWhirlwind((ServerWorld) world, player);
            return TypedActionResult.success(player.getStackInHand(hand));
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    private void activateWhirlwind(ServerWorld world, PlayerEntity player) {
        new Thread(() -> {
            try {
                for (int i = 0; i < WHIRLWIND_DURATION_TICKS / 10; i++) {
                    world.playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK,
                            net.minecraft.sound.SoundCategory.PLAYERS, 1.0F, 1.0F);
                    // Get all entities in the radius
                    Box areaOfEffect = new Box(player.getX() - DAMAGE_RADIUS, player.getY() - DAMAGE_RADIUS, player.getZ() - DAMAGE_RADIUS,
                            player.getX() + DAMAGE_RADIUS, player.getY() + DAMAGE_RADIUS, player.getZ() + DAMAGE_RADIUS);
                    world.getEntitiesByClass(Entity.class, areaOfEffect, target -> target != player).forEach(target -> {
                        // Damage entities based on their type
                        if (target instanceof PlayerEntity || target instanceof MobEntity || target instanceof PassiveEntity) {
                            target.damage(world.getDamageSources().magic(), 4);
                            Vec3d knockback = target.getPos().subtract(player.getPos()).normalize().multiply(1.0);
                            target.addVelocity(knockback.x, 0.2, knockback.z);
                            target.velocityModified = true;
                        }
                    });
                    spawnWhirlwindParticles(world, player);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void spawnWhirlwindParticles(ServerWorld world, PlayerEntity player) {
        double radius = DAMAGE_RADIUS;
        for (int i = 0; i < 20; i++) {
            double angle = Math.toRadians(i * 18);
            double x = player.getX() + Math.cos(angle) * radius;
            double z = player.getZ() + Math.sin(angle) * radius;
            world.spawnParticles(
                    ParticleTypes.SOUL_FIRE_FLAME,
                    x,
                    player.getY(),
                    z,
                    100,
                    0.01,
                    0.5,
                    0.01,
                    0.01
            );
        }
    }
}