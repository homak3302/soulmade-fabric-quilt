package net.homak.homakmod.mixin;

import net.homak.homakmod.HomakMod;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin {
    @Shadow
    protected abstract void loadItemModel(ModelIdentifier id);

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/ModelLoader;loadItemModel(Lnet/minecraft/client/util/ModelIdentifier;)V", ordinal = 1))
    private void onInit(CallbackInfo ci) {
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "soul_greatsword_held")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "fractured_slayer_held")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "soul_spear_held")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "soul_greataxe_held")));
        this.loadItemModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "harbinger_glaive_held")));
    }
}