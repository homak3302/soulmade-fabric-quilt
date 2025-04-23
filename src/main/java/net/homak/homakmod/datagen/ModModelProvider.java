package net.homak.homakmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.homak.homakmod.block.ModBlocks;
import net.homak.homakmod.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool polishedsouldeepslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.POLISHED_SOUL_DEEPSLATE);
        BlockStateModelGenerator.BlockTexturePool soulbricksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SOUL_BRICKS);
        BlockStateModelGenerator.BlockTexturePool soultilesPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.SOUL_TILES);
        BlockStateModelGenerator.BlockTexturePool cobbledsouldeepslatePool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.COBBLED_SOUL_DEEPSLATE);

        cobbledsouldeepslatePool.stairs(ModBlocks.COBBLED_SOUL_DEEPSLATE_STAIRS);
        cobbledsouldeepslatePool.slab(ModBlocks.COBBLED_SOUL_DEEPSLATE_SLAB);
        cobbledsouldeepslatePool.wall(ModBlocks.COBBLED_SOUL_DEEPSLATE_WALL);
        cobbledsouldeepslatePool.pressurePlate(ModBlocks.COBBLED_SOUL_DEEPSLATE_PRESSURE_PLATE);

        polishedsouldeepslatePool.stairs(ModBlocks.POLISHED_SOUL_DEEPSLATE_STAIRS);
        polishedsouldeepslatePool.slab(ModBlocks.POLISHED_SOUL_DEEPSLATE_SLAB);
        polishedsouldeepslatePool.wall( ModBlocks.POLISHED_SOUL_DEEPSLATE_WALL);
        polishedsouldeepslatePool.pressurePlate(ModBlocks.POLISHED_SOUL_DEEPSLATE_PRESSURE_PLATE);

        soulbricksPool.stairs(ModBlocks.SOUL_BRICKS_STAIRS);
        soulbricksPool.slab(ModBlocks.SOUL_BRICKS_SLAB);
        soulbricksPool.wall(ModBlocks.SOUL_DEEPSLATE_BRICKS_WALL);
        soulbricksPool.pressurePlate(ModBlocks.SOUL_DEEPSLATE_BRICKS_PRESSURE_PLATE);

        soultilesPool.stairs(ModBlocks.SOUL_TILES_STAIRS);
        soultilesPool.slab(ModBlocks.SOUL_TILES_SLAB);
        soultilesPool.wall(ModBlocks.SOUL_DEEPSLATE_TILES_WALL);
        soultilesPool.pressurePlate(ModBlocks.SOUL_DEEPSLATE_TILES_PRESSURE_PLATE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.SOUL_FORCE_FIELD, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_CLEAVER, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SOUL_CURVESWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SOUL_HIDER, Models.GENERATED);
        itemModelGenerator.register(ModItems.DICE_OF_FATE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_BOOSTER, Models.GENERATED);
        itemModelGenerator.register(ModItems.UNSTABLE_SOUL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_INGOT, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_BOTTLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_WARPER, Models.GENERATED);
        itemModelGenerator.register(ModItems.SOUL_ROCKET_LAUNCHER, Models.HANDHELD);

    }
}
