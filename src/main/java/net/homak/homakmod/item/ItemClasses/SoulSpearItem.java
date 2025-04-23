package net.homak.homakmod.item.ItemClasses;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageSources;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class SoulSpearItem extends SwordItem {
    private static final int TELEPORT_DISTANCE = 10;
    private static final int COOLDOWN_TICKS = 7 * 20;
    private static final int DAMAGE = 3 * 2;

    public SoulSpearItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            float healAmount = DAMAGE * 0.10f;
            player.heal(healAmount);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient && !player.getItemCooldownManager().isCoolingDown(this)) {
            Vec3d start = player.getEyePos();
            Vec3d direction = player.getRotationVec(1.0F).normalize();
            Vec3d end = start.add(direction.multiply(TELEPORT_DISTANCE));
            Vec3d particlePos = player.getPos();

            // Raycast to find obstacle
            HitResult hit = world.raycast(new RaycastContext(
                    start,
                    end,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    player
            ));

            Vec3d teleportPos;

            if (hit.getType() == HitResult.Type.BLOCK) {
                // Try behind the block
                BlockHitResult blockHit = (BlockHitResult) hit;
                Direction side = blockHit.getSide();
                BlockPos behindBlock = blockHit.getBlockPos().offset(side);
                if (world.getBlockState(behindBlock).isAir()) {
                    teleportPos = Vec3d.ofCenter(behindBlock);
                } else {
                    // No space behind wall: cancel
                    return new TypedActionResult<>(ActionResult.PASS, player.getStackInHand(hand));
                }
            } else {
                // No wall: go full distance
                teleportPos = end;
            }

            BlockPos checkPos = new BlockPos((int) teleportPos.x, (int) teleportPos.y, (int) teleportPos.z);
            if (world.getBlockState(checkPos).isAir()) {
                float totalDamageDealt = 0f;

                // Damage entities along path
                Box damageBox = new Box(start, teleportPos).expand(1.0);
                for (Entity entity : world.getOtherEntities(player, damageBox)) {
                    if (!entity.isSpectator() && entity.isAttackable()) {
                        DamageSources damageSources = world.getDamageSources();
                        DamageSource source = damageSources.playerAttack(player);
                        if (entity.damage(source, DAMAGE)) {
                            totalDamageDealt += DAMAGE;
                        }
                    }
                }

                // Teleport
                player.requestTeleport(teleportPos.getX(), teleportPos.getY(), teleportPos.getZ());

                // Particles
                if (world instanceof ServerWorld) {
                    ((ServerWorld) world).spawnParticles(
                            ParticleTypes.SOUL,
                            particlePos.x, particlePos.y, particlePos.z,
                            40, 0.2, 0.4, 0.2, 0.05
                    );
                }

                // Sound
                world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_ENDERMAN_TELEPORT,
                        player.getSoundCategory(), 1.0F, 1.0F);

                // Cooldown
                player.getItemCooldownManager().set(this, COOLDOWN_TICKS);

                return new TypedActionResult<>(ActionResult.SUCCESS, player.getStackInHand(hand));
            }
        }

        return new TypedActionResult<>(ActionResult.PASS, player.getStackInHand(hand));
    }
}
