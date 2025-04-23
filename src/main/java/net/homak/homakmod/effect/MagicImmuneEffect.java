package net.homak.homakmod.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;

public class MagicImmuneEffect extends StatusEffect {
    public MagicImmuneEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    // This method is called every tick while the effect is active
    @Override
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        // We don't need to do anything here since we're handling damage through events
        return super.applyUpdateEffect(entity, amplifier);
    }

    public static boolean shouldCancelMagicDamage(LivingEntity entity, DamageSource source) {
        if (entity != null && entity.hasStatusEffect(ModEffects.IMMUNE_TO_MAGIC)) {
            return source.isOf(DamageTypes.MAGIC) ||
                    source.isOf(DamageTypes.INDIRECT_MAGIC) ||
                    source.isOf(DamageTypes.DRAGON_BREATH) ||
                    source.isOf(DamageTypes.SONIC_BOOM); // does nothing, was a solution at a time
        }
        return false;
    }
}
