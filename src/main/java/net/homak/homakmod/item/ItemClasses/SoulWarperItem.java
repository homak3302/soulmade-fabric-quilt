package net.homak.homakmod.item.ItemClasses;

import net.homak.homakmod.component.ModDataComponentTypes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SoulWarperItem extends Item {
    public SoulWarperItem(Settings settings) {
        super(settings);
    }

    private static final int COOLDOWN_TICKS = 60 * 20;

    public static void setWarpCoords(ItemStack stack, BlockPos pos, RegistryKey<World> dimension) {
        stack.set(ModDataComponentTypes.WARP_COORDS, pos);
        stack.set(ModDataComponentTypes.WARP_DIMENSION, dimension);
    }

    public class WarpPosTracker {
        public static final Map<BlockPos, Integer> ACTIVE_WARP_POSITIONS = new HashMap<>();

        public static void tick(ServerWorld world) {
            ACTIVE_WARP_POSITIONS.forEach((pos, age) -> {
                if (age > 200) { // 10 second lifespan
                    ACTIVE_WARP_POSITIONS.remove(pos);
                } else {
                    world.spawnParticles(
                            ParticleTypes.SMOKE,
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5,
                            5,
                            0.2, 0.2, 0.2,
                            0.02
                    );
                    ACTIVE_WARP_POSITIONS.put(pos, age + 1);
                }
            });
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (user.isSneaking()) {
            // Set warp coordinates when sneaking
            setWarpCoords(stack, user.getBlockPos(), world.getRegistryKey());
            if (!world.isClient()) {
                user.sendMessage(Text.literal("Warp location set to " + user.getBlockPos().toShortString() + " in " + world.getRegistryKey().getValue()), false);
            }
            return TypedActionResult.success(stack);
        } else {
            // Teleport when not sneaking
            if (!world.isClient() && user instanceof ServerPlayerEntity serverPlayer) {
                BlockPos warpPos = stack.get(ModDataComponentTypes.WARP_COORDS);
                RegistryKey<World> warpDimension = stack.get(ModDataComponentTypes.WARP_DIMENSION);

                if (warpPos != null && warpDimension != null) {
                    MinecraftServer server = serverPlayer.getServer();
                    if (server == null) {
                        return TypedActionResult.fail(stack);
                    }

                    ServerWorld targetWorld = server.getWorld(warpDimension);
                    if (targetWorld == null) {
                        user.sendMessage(Text.literal("Target dimension no longer exists!"), false);
                        return TypedActionResult.fail(stack);
                    }

                    // Teleport the player to the target dimension
                    serverPlayer.teleport(
                            targetWorld,
                            warpPos.getX() + 0.5, // Center on block
                            warpPos.getY(),
                            warpPos.getZ() + 0.5,
                            serverPlayer.getYaw(),
                            serverPlayer.getPitch()
                    );

                    user.setHealth(user.getHealth() - user.getHealth() / 2);
                    // Play effects
                    serverPlayer.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);
                    serverPlayer.addStatusEffect(new StatusEffectInstance(
                            StatusEffects.BLINDNESS, 20, 0, false, false));
                    user.playSound(SoundEvents.ENTITY_ENDERMAN_TELEPORT, 1.0F, 1.0F);

                    user.getItemCooldownManager().set(this, COOLDOWN_TICKS);

                    return TypedActionResult.success(stack);
                } else {
                    user.sendMessage(Text.literal("No warp location set! Sneak + use to set one"), false);
                }
            }
        }

        return TypedActionResult.pass(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);

        BlockPos warpPos = stack.get(ModDataComponentTypes.WARP_COORDS);
        RegistryKey<World> warpDimension = stack.get(ModDataComponentTypes.WARP_DIMENSION);

        if (warpPos == null || warpDimension == null) {
            tooltip.add(Text.translatable("item.soul_warper.tooltip.no_pos_set")
                    .formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable("item.soul_warper.tooltip.pos_set")
                    .formatted(Formatting.GOLD));
            tooltip.add(Text.literal(String.format("§7X: §f%d §7Y: §f%d §7Z: §f%d",
                    warpPos.getX(), warpPos.getY(), warpPos.getZ())));
            tooltip.add(Text.literal("§7Dimension: §f" + warpDimension.getValue()));
        }
    }
}