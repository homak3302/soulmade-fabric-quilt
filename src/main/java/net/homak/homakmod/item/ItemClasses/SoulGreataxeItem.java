package net.homak.homakmod.item.ItemClasses;

import net.homak.homakmod.component.ModDataComponentTypes;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.Entity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import java.util.List;

public class SoulGreataxeItem extends AxeItem {
    private static final int RADIUS = 5;
    private static final int MAX_SOULS = 20;

    public SoulGreataxeItem(ToolMaterial material, Settings settings) {
        super(material, settings);
    }

    private int getSouls(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.SOULS, 0);
    }

    private void setSouls(ItemStack stack, int amount) {
        stack.set(ModDataComponentTypes.SOULS, MathHelper.clamp(amount, 0, MAX_SOULS));
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
        int souls = getSouls(stack);

        if (!world.isClient && souls >= MAX_SOULS) {
            setSouls(stack, souls - MAX_SOULS);

            world.playSound(null, user.getBlockPos(), SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.PLAYERS, 1f, 1f);

            Vec3d pos = user.getPos();
            Box area = new Box(pos.add(-RADIUS, -RADIUS, -RADIUS), pos.add(RADIUS, RADIUS, RADIUS));

            ((ServerWorld) world).spawnParticles(ParticleTypes.SOUL, pos.x, pos.y, pos.z, 2000, RADIUS, RADIUS, RADIUS, 0.1);

            for (Entity entity : world.getEntitiesByClass(Entity.class, area, e -> e != user)) {
                if (entity instanceof LivingEntity) {
                    entity.damage(user.getDamageSources().magic(), 6.0f);
                }
            }
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        int souls = stack.getOrDefault(ModDataComponentTypes.SOULS, 0);
        if (!Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.homakmod.fractured_slayer.shift_hold"));
        }
        else {
            tooltip.add(Text.translatable("tooltip.homakmod.soul_greataxe.souls", souls));
            tooltip.add(Text.translatable("tooltip.homakmod.soul_greataxe.souls2"));
        }
        super.appendTooltip(stack, context, tooltip, type);
    }
}