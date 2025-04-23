package net.homak.homakmod.item.ItemClasses;

import net.homak.homakmod.component.ModDataComponentTypes;
import net.homak.homakmod.effect.ModEffects;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class SoulHiderItem extends Item {

    private static final int COOLDOWN_TICKS = 40 * 20;// cooldown seconds
    private static final int DURATION_TICKS = 20 * 20;
    public SoulHiderItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            activateSoulHider(world, user);
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
        }

        return TypedActionResult.success(stack);
    }

    private void activateSoulHider(World world, PlayerEntity user) {
        world.playSound(null, user.getX(), user.getY(), user.getZ(),
                SoundEvents.BLOCK_LAVA_EXTINGUISH,
                net.minecraft.sound.SoundCategory.PLAYERS, 1.0F, 1.0F);
        Vec3d center = user.getPos();
        user.addStatusEffect(new net.minecraft.entity.effect.StatusEffectInstance(ModEffects.HIDEOUT, DURATION_TICKS, 0));

        if (world instanceof ServerWorld) {
            ((ServerWorld) world).spawnParticles(ParticleTypes.SMOKE, center.x, center.y, center.z, 5000, 0.7, 0.2, 0.7, 0.1);
        }
    }
}