package net.homak.homakmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.homak.homakmod.item.ModItems;
import net.homak.homakmod.particle.ModParticles;
import net.homak.homakmod.particle.SoulIngotParticle;
import net.homak.homakmod.util.handler.TextureRenderHandler;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class HomakModClient implements ClientModInitializer {

    public static final String HIDDEN_PLAYER = "";
    public static boolean shouldHidePlayer = true;

    private static KeyBinding toggleRenderKey;

    @Override
    public void onInitializeClient() {
        TextureRenderHandler.register(ModItems.SOUL_HIDER);

        ParticleFactoryRegistry.getInstance().register(
                ModParticles.SOUL_INGOT_FRACTURE_PARTICLE,
                spriteSet -> new SoulIngotParticle.Factory(spriteSet)
        );

        toggleRenderKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.homakmod.toggle_visibility",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "category.homakmod.controls"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null && client.player.getGameProfile().getName().equalsIgnoreCase(HIDDEN_PLAYER)) {
                if (toggleRenderKey.wasPressed()) {
                    shouldHidePlayer = !shouldHidePlayer;
                    client.player.sendMessage(Text.literal(
                            shouldHidePlayer ? "§aHiding" : "§cShowing"
                    ), false);
                }
            }
        });
    }


}
