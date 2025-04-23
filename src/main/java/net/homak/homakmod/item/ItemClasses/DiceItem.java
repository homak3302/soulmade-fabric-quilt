package net.homak.homakmod.item.ItemClasses;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Random;

public class DiceItem extends Item {
    private static final Random RANDOM = new Random();

    public DiceItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient()) {

            int roll = RANDOM.nextInt(12) + 1;

            int duration = 20 * 10;

            if (roll < 6) {
                int effectLevel = 0;
                user.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.WEAKNESS,
                        duration,
                        effectLevel,
                        false,
                        true,
                        true
                ));
                user.sendMessage(Text.translatable("item.homakmod.dice.roll.weakness", roll, effectLevel + 1), true);
            } else if (roll > 6) {
                int effectLevel = 0;
                user.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.STRENGTH,
                        duration,
                        effectLevel,
                        false,
                        true,
                        true
                ));
                user.sendMessage(Text.translatable("item.homakmod.dice.roll.strength", roll, effectLevel + 1), true);
            } else {
                user.sendMessage(Text.translatable("item.homakmod.dice.roll.neutral", roll), true);
            }

            user.getItemCooldownManager().set(this, 20 * 10);
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}
