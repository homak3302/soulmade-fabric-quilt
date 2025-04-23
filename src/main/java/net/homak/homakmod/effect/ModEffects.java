package net.homak.homakmod.effect;

import net.homak.homakmod.HomakMod;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;

public class ModEffects {
    public static final RegistryEntry<StatusEffect> SOUL_WEAKNESS = registerStatusEffect("soul_weakness",
            new SoulWeaknessEffect(StatusEffectCategory.HARMFUL, 0x60f5fa)
                    .addAttributeModifier(EntityAttributes.GENERIC_ARMOR,
                            Identifier.of(HomakMod.MOD_ID, "soul_weakness"), -0.2f,
            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(HomakMod.MOD_ID, "soul_weakness"), -0.2f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,
                            Identifier.of(HomakMod.MOD_ID, "soul_weakness"), 1.0f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));

    public static final RegistryEntry<StatusEffect> WEIGHTED = registerStatusEffect("weighted",
            new WeightedEffect(StatusEffectCategory.HARMFUL,0x060e10)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(HomakMod.MOD_ID, "weighted"), -0.1f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_ATTACK_KNOCKBACK,
                            Identifier.of(HomakMod.MOD_ID, "weighted"), 0.2f,
                            EntityAttributeModifier.Operation.ADD_VALUE));

    public static final RegistryEntry<StatusEffect> HIDEOUT = registerStatusEffect("hideout",
            new HideoutEffect(StatusEffectCategory.BENEFICIAL,0x858585)
                    .addAttributeModifier(EntityAttributes.GENERIC_SCALE,
                            Identifier.of(HomakMod.MOD_ID, "hideout"), -0.999f,
                            EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                    .addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,
                            Identifier.of(HomakMod.MOD_ID, "hideout"), 0.02f,
                            EntityAttributeModifier.Operation.ADD_VALUE)
                    .addAttributeModifier(EntityAttributes.GENERIC_MAX_HEALTH,
                            Identifier.of(HomakMod.MOD_ID, "hideout"), -14.0f,
                            EntityAttributeModifier.Operation.ADD_VALUE));

    public static final RegistryEntry<StatusEffect> IMMUNE_TO_MAGIC = registerStatusEffect("magic_immunity",
            new HideoutEffect(StatusEffectCategory.BENEFICIAL,0x858585));


    private static RegistryEntry<StatusEffect> registerStatusEffect(String name, StatusEffect statusEffect) {
        return Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(HomakMod.MOD_ID, name), statusEffect);
    }
    public static void registerEffects() {
        HomakMod.LOGGER.info("Registering Mod Effects For " + HomakMod.MOD_ID);
    }
}
