package net.homak.homakmod.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.homak.homakmod.HomakMod;
import net.homak.homakmod.item.ModItems;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {

    @Shadow
    @Final
    private ItemModels models;

    @Shadow
    public abstract ItemModels getModels();

    @ModifyVariable(
            method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel renderItem(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack, @Local(argsOnly = true) ModelTransformationMode renderMode) {
        if (stack.getItem() == ModItems.SOUL_GREATSWORD && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "soul_greatsword")));
        }
        if (stack.getItem() == ModItems.FRACTURED_SLAYER && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "fractured_slayer")));
        }

        if (stack.getItem() == ModItems.SOUL_SPEAR && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "soul_spear")));
        }

        if (stack.getItem() == ModItems.SOUL_GREATAXE && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "soul_greataxe")));
        }

        if (stack.getItem() == ModItems.HARBINGER_GLAIVE && (renderMode == ModelTransformationMode.GUI || renderMode == ModelTransformationMode.GROUND || renderMode == ModelTransformationMode.FIXED)) {
            return getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "harbinger_glaive")));
        }


        return bakedModel;
    }

    @ModifyVariable(
            method = "getModel",
            at = @At(value = "STORE"),
            ordinal = 1
    )
    public BakedModel getHeldItemModelMixin(BakedModel bakedModel, @Local(argsOnly = true) ItemStack stack) {
        if (stack.getItem() == ModItems.SOUL_GREATSWORD) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "soul_greatsword_held")));
        }
        if (stack.getItem() == ModItems.FRACTURED_SLAYER) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "fractured_slayer_held")));
        }
        if (stack.getItem() == ModItems.SOUL_SPEAR) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "soul_spear_held")));
        }
        if (stack.getItem() == ModItems.SOUL_GREATAXE) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "soul_greataxe_held")));
        }
        if (stack.getItem() == ModItems.HARBINGER_GLAIVE) {
            return this.models.getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(HomakMod.MOD_ID, "harbinger_glaive_held")));
        }

        return bakedModel;
    }
}