package net.homak.homakmod.item.ItemClasses;

import net.homak.homakmod.effect.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoulCleaverItem extends AxeItem {

    private static final int FIRE_DURATION = 5; // Fire timer
    private static final int EFFECT_DURATION = 1 * 20; // Effect Duration
    private static final int COOLDOWN_TICKS = 5 * 20; // cooldown seconds
    private static final double RADIUS = 5.0; // block radius

    public SoulCleaverItem(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            activateForceField(world, user);
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
        }

        return TypedActionResult.success(stack);
    }

    private void activateForceField(World world, PlayerEntity user) {
        Vec3d center = user.getPos();
        Box area = new Box(center.add(-RADIUS, -RADIUS, -RADIUS), center.add(RADIUS, RADIUS, RADIUS));

        for (Entity entity : world.getEntitiesByClass(Entity.class, area, e -> e != user)) {
            if (entity instanceof LivingEntity) {
                Vec3d direction = entity.getPos().subtract(center).normalize();
                entity.setVelocity(direction.multiply(3.0));
                applyEffectsToEntity((LivingEntity) entity);
                world.playSound(null, user.getX(), user.getY(), user.getZ(),
                        SoundEvents.BLOCK_LAVA_EXTINGUISH,
                        net.minecraft.sound.SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }

        // Particles
        if (world instanceof ServerWorld) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.FLAME, center.x, center.y, center.z, 2000, RADIUS, RADIUS, RADIUS, 0.1);
        }
    }

    private void applyEffectsToEntity(LivingEntity entity) {

        entity.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(StatusEffects.BLINDNESS, EFFECT_DURATION, 1));

        entity.setOnFireFor(FIRE_DURATION);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient) {
            applyEffectsToEntity(target);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}
