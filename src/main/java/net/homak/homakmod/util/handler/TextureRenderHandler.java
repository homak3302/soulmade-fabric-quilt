package net.homak.homakmod.util.handler;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.homak.homakmod.effect.ModEffects;
import net.homak.homakmod.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TextureRenderHandler {
    private static final Identifier TEXTURE = Identifier.of("homakmod", "textures/gui/cooldown_basic.png");
    private static final Identifier COOLDOWN_TEXTURE = Identifier.of("homakmod", "textures/gui/cooldown_active.png");
    private static final int FRAME_COUNT = 4;
    private static final int X1 = 1;
    private static final int Y1 = 1;
    private static final int FRAME_SIZE = 32;
    private static final int TICKS_PER_FRAME = 40;

    private static Item targetItem;

    public static void register(Item item) {
        targetItem = item; // Set the target item
        HudRenderCallback.EVENT.register(TextureRenderHandler::onHudRender);
    }

    private static void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        ItemStack mainHandStack = client.player.getMainHandStack();
        ItemStack offHandStack = client.player.getOffHandStack();

        boolean isHoldingItem = client.player.getMainHandStack().getItem() == targetItem
                || client.player.getOffHandStack().getItem() == targetItem;

        if (!isHoldingItem) return;

        boolean isOnCooldown = client.player.getItemCooldownManager().isCoolingDown(targetItem);

        boolean isOnCooldownBooster = client.player.getItemCooldownManager().isCoolingDown(ModItems.SOUL_BOOSTER);

        boolean isf1 = client.options.hudHidden;

        boolean isEffct = client.player.hasStatusEffect(ModEffects.HIDEOUT);

        boolean isInv = false;
        for (int i = 0; i < client.player.getInventory().size(); i++) {
            ItemStack stack = client.player.getInventory().getStack(i);
            if (stack.getItem() == targetItem) {
                isInv = true;
                break;
            }
        }

        boolean hasItem = false;
        for (ItemStack item : client.player.getInventory().main) {
            if (item.getItem() == ModItems.SOUL_BOOSTER) {
                hasItem = true;
                break;
            }
        }

        Identifier currentTexture = isOnCooldown ? COOLDOWN_TEXTURE : TEXTURE;

        int currentFrame = (client.player.age / TICKS_PER_FRAME) % FRAME_COUNT;

        int screenWidth = client.getWindow().getScaledWidth();
        int screenHeight = client.getWindow().getScaledHeight();
        int frameWidth = FRAME_SIZE;


        if (isInv) {
            drawContext.drawTexture(
                    TEXTURE,
                    (screenWidth - FRAME_SIZE) / X1,  // Center X
                    (screenHeight - FRAME_SIZE) / Y1, // Center Y
                    0,
                    currentFrame * FRAME_SIZE,
                    frameWidth,
                    FRAME_SIZE,
                    frameWidth,
                    FRAME_SIZE * FRAME_COUNT
            );
        }

        if (isOnCooldown) {
            drawContext.drawTexture(
                    COOLDOWN_TEXTURE,
                    (screenWidth - FRAME_SIZE) / X1,  // Center X
                    (screenHeight - FRAME_SIZE) / Y1, // Center Y
                    0,
                    currentFrame * FRAME_SIZE,
                    frameWidth,
                    FRAME_SIZE,
                    frameWidth,
                    FRAME_SIZE * FRAME_COUNT
            );
        }
    }
}