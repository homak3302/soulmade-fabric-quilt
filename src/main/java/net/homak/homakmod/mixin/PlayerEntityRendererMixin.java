package net.homak.homakmod.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.homak.homakmod.HomakModClient.shouldHidePlayer;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends EntityRenderer<AbstractClientPlayerEntity> {

    @Unique private static final String HIDDEN_PLAYER = "";
    @Unique private static final float EDGE_THRESHOLD = 0.1f;

    protected PlayerEntityRendererMixin() {
        super(null);
    }

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void onRender(AbstractClientPlayerEntity player, float f, float g,
                          MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,
                          int i, CallbackInfo ci) {

        if (shouldHidePlayer && HIDDEN_PLAYER.equalsIgnoreCase(player.getGameProfile().getName())) {
            MinecraftClient client = MinecraftClient.getInstance();
            Vec3d screenPos = getScreenPosition(player, client);
            boolean isAtEdge = screenPos.x < EDGE_THRESHOLD || screenPos.x > 1 - EDGE_THRESHOLD ||
                    screenPos.y < EDGE_THRESHOLD || screenPos.y > 1 - EDGE_THRESHOLD;

            if (!isAtEdge) {
                ci.cancel();
            }
        }
    }

    @Inject(method = "render", at = @At("HEAD"))
    private void beforeRender(AbstractClientPlayerEntity player, float f, float g,
                              MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider,
                              int i, CallbackInfo ci) {
        if (HIDDEN_PLAYER.equalsIgnoreCase(player.getGameProfile().getName())) {
            this.shadowRadius = 0.0f;
        } else {
            this.shadowRadius = 0.5f;
        }
    }

    @Unique
    private Vec3d getScreenPosition(AbstractClientPlayerEntity player, MinecraftClient client) {
        Vec3d cameraPos = client.gameRenderer.getCamera().getPos();
        Vec3d playerPos = player.getPos().subtract(cameraPos);
        double distance = playerPos.length();
        double scale = 1 / Math.max(1, distance);

        return new Vec3d(
                0.5 + playerPos.x * scale * 0.1,
                0.5 - playerPos.y * scale * 0.1,
                0
        );
    }
}