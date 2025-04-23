package net.homak.homakmod.enchantment.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

public record SwordBreachEnchantmentEffect() implements EnchantmentEntityEffect {
    public static final MapCodec<SwordBreachEnchantmentEffect> CODEC = MapCodec.unit(SwordBreachEnchantmentEffect::new);
    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity user, Vec3d pos) {
        if (level == 1) {

        }
    }

    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
