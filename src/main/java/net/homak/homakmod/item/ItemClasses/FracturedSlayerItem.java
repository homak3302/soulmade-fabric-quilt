package net.homak.homakmod.item.ItemClasses;

import net.homak.homakmod.component.ModDataComponentTypes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

public class FracturedSlayerItem extends SwordItem {
    private static final int DURATION_TICKS = 5 * 20; // seconds
    private static final int COOLDOWN_TICKS = 15 * 20; // seconds cooldown
    private static final double RANGE = 50.0; // beam range
    private static final double WIDTH = 0.7; // beam width
    private static final float DAMAGE = 9.0f; // damage
    private static final float KNOCKBACK = 0.0f; // knockback strength
    private static final int DAMAGE_INTERVAL = 0; // damage interval
    private static final float VOLUME = 70.0F;
    private static final int MAX_SOULS = 30;

    public FracturedSlayerItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    private int getSouls(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.FRACTURED_SOULS, 0);
    }

    private void setSouls(ItemStack stack, int amount) {
        stack.set(ModDataComponentTypes.FRACTURED_SOULS, MathHelper.clamp(amount, 0, MAX_SOULS));
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return getSouls(stack) > -1;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) getSouls(stack) * 13.0f / (float) MAX_SOULS);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        float f = Math.max(0.0f, (float) getSouls(stack) / (float) MAX_SOULS);
        return MathHelper.hsvToRgb(f / 3.0f, 1.0f, 1.0f);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int currentSouls = getSouls(stack);
        if (currentSouls < MAX_SOULS && target.isDead()) {
            setSouls(stack, currentSouls + 1);
        }
        return super.postHit(stack, target, attacker);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        int fracturedSouls = getSouls(stack);

        if (!world.isClient && fracturedSouls >= MAX_SOULS) {
            setSouls(stack, fracturedSouls - MAX_SOULS);

            world.playSound(
                    null,
                    user.getX(), user.getY(), user.getZ(),
                    SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK,
                    SoundCategory.PLAYERS,
                    VOLUME,
                    3.0f
            );

            startBeamEffect((ServerWorld) world, user);
            user.getItemCooldownManager().set(this, COOLDOWN_TICKS);
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    private void startBeamEffect(ServerWorld world, PlayerEntity user) {
        BeamHandler handler = new BeamHandler(world, user);
        handler.start();
    }

    private class BeamHandler {
        private final ServerWorld world;
        private final PlayerEntity user;
        private int ticksRemaining = DURATION_TICKS;
        private int damageTickCounter = 0;
        private Vec3d beamEndPos;

        public BeamHandler(ServerWorld world, PlayerEntity user) {
            this.world = world;
            this.user = user;
        }

        public void start() {
            world.getServer().execute(this::tick);
        }

        private void tick() {
            if (ticksRemaining-- <= 0 || user.isRemoved()) {
                return;
            }

            Vec3d startPos = user.getEyePos();
            Vec3d lookVec = user.getRotationVec(1.0F);

            // Perform raycast to find where the beam hits a block
            BlockHitResult blockHit = world.raycast(new RaycastContext(
                    startPos,
                    startPos.add(lookVec.multiply(RANGE)),
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    user
            ));

            if (blockHit.getType() == HitResult.Type.BLOCK) {
                // Beam hits a block, end position is the block hit position
                beamEndPos = blockHit.getPos();
            } else {
                // No block hit, use full range
                beamEndPos = startPos.add(lookVec.multiply(RANGE));
            }

            spawnBeamParticles(startPos, beamEndPos);

            // Damage and knockback logic
            damageTickCounter++;
            if (damageTickCounter >= DAMAGE_INTERVAL) {
                damageTickCounter = 0;
                affectEntitiesInBeam(startPos, beamEndPos);
            }

            // Schedule next tick
            world.getServer().execute(this::tick);
        }

        private void spawnBeamParticles(Vec3d startPos, Vec3d endPos) {
            Vec3d direction = endPos.subtract(startPos);
            double distance = direction.length();
            direction = direction.normalize();

            int particleCount = (int) (distance * 2);
            for (int i = 0; i < particleCount; i++) {
                double ratio = i / (double) particleCount;
                Vec3d pos = startPos.add(direction.multiply(distance * ratio));
                world.spawnParticles(
                        ParticleTypes.SOUL_FIRE_FLAME,
                        pos.x,
                        pos.y - 0.5,
                        pos.z,
                        1,
                        0.002, 0.002, 0.002,
                        0.005
                );
            }
        }

        private void affectEntitiesInBeam(Vec3d startPos, Vec3d endPos) {
            Vec3d direction = endPos.subtract(startPos).normalize();
            double beamLength = startPos.distanceTo(endPos);

            Box beamBox = new Box(startPos, endPos).expand(WIDTH);

            for (Entity entity : world.getEntitiesByClass(Entity.class, beamBox, e ->
                    e instanceof LivingEntity && e != user
            )) {
                LivingEntity target = (LivingEntity) entity;
                UUID targetId = target.getUuid();

                Vec3d targetPos = target.getBoundingBox().getCenter();
                Vec3d toTarget = targetPos.subtract(startPos);
                double dot = toTarget.dotProduct(direction);
                double t = Math.max(0, Math.min(beamLength, dot));
                Vec3d closestPoint = startPos.add(direction.multiply(t));

                if (closestPoint.distanceTo(targetPos) <= WIDTH + target.getWidth() / 2) {
                    // Apply damage
                    target.damage(world.getDamageSources().indirectMagic(user, user), DAMAGE);

                    world.spawnParticles(
                            ParticleTypes.SOUL,
                            entity.getX(),
                            entity.getY(),
                            entity.getZ(),
                            1,
                            0.1, 0.2, 0.1,
                            0.1
                    );

                    // Apply knockback
                    Vec3d knockbackDir = targetPos.subtract(startPos).normalize();
                    target.addVelocity(
                            knockbackDir.x * KNOCKBACK,
                            knockbackDir.y * KNOCKBACK,
                            knockbackDir.z * KNOCKBACK
                    );
                    target.velocityModified = true;
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int souls = stack.getOrDefault(ModDataComponentTypes.FRACTURED_SOULS, 0);
        if (!Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.homakmod.fractured_slayer.shift_hold"));
        }
        else {
            tooltip.add(Text.translatable("tooltip.homakmod.fractured_slayer.souls", souls));
            tooltip.add(Text.translatable("tooltip.homakmod.fractured_slayer.souls2"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}