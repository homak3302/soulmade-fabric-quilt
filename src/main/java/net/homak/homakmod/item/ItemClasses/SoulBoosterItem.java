package net.homak.homakmod.item.ItemClasses;

import net.minecraft.client.sound.Sound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoulBoosterItem extends Item {

    private static final int COOLDOWN_TICKS = 5 * 20;
    private static final int RADIUS = 2;
    private static final double LAUNCH_POWER = 2.5;

    public SoulBoosterItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            if (!user.isSneaking()) {
                activateBooster(world, user);
                user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
            }
            else {
                Vec3d lookVec = user.getRotationVector().multiply(LAUNCH_POWER, LAUNCH_POWER, LAUNCH_POWER);
                user.addVelocity(lookVec.x, lookVec.y, lookVec.z);
                user.velocityModified = true;
                user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
            }

        }

        return TypedActionResult.success(stack);
    }

    private void activateBooster(World world, PlayerEntity user) {
        Vec3d velocity = user.getVelocity();
        user.setVelocity(velocity.x, 1.5, velocity.z);
        user.velocityModified = true;
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.ENTITY_WIND_CHARGE_THROW,
                net.minecraft.sound.SoundCategory.PLAYERS, 1.0F, 1.0F);

        Vec3d center = user.getPos();

        Box area = new Box(center.add(-RADIUS, -RADIUS, -RADIUS), center.add(RADIUS, RADIUS, RADIUS));

        for (Entity player : world.getEntitiesByClass(Entity.class, area, e -> e != user)) {
            if (player instanceof LivingEntity) {
                Vec3d direction = player.getPos().subtract(center).normalize();
                player.setVelocity(direction.multiply(1));
                player.setOnFireFor(40);
            }
        }

        if (world instanceof ServerWorld) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, center.x, center.y, center.z,200 , RADIUS, 0, RADIUS, 0);
        }
    }
}
