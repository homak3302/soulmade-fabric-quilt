package net.homak.homakmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.homak.homakmod.block.ModBlocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE)
                .add(ModBlocks.SOUL_BRICKS)
                .add(ModBlocks.SOUL_TILES)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE)

                .add(ModBlocks.SOUL_BRICKS_STAIRS)
                .add(ModBlocks.SOUL_TILES_STAIRS)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE_STAIRS)
                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE_STAIRS)

                .add(ModBlocks.SOUL_DEEPSLATE_BRICKS_WALL)
                .add(ModBlocks.SOUL_DEEPSLATE_TILES_WALL)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE_WALL)
                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE_WALL)

                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE_SLAB)
                .add(ModBlocks.SOUL_BRICKS_SLAB)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE_SLAB)
                .add(ModBlocks.SOUL_TILES_SLAB);

        getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE)
                .add(ModBlocks.SOUL_BRICKS)
                .add(ModBlocks.SOUL_TILES)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE)

                .add(ModBlocks.SOUL_BRICKS_STAIRS)
                .add(ModBlocks.SOUL_TILES_STAIRS)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE_STAIRS)
                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE_STAIRS)

                .add(ModBlocks.SOUL_DEEPSLATE_BRICKS_WALL)
                .add(ModBlocks.SOUL_DEEPSLATE_TILES_WALL)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE_WALL)
                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE_WALL)

                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE_SLAB)
                .add(ModBlocks.SOUL_BRICKS_SLAB)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE_SLAB)
                .add(ModBlocks.SOUL_TILES_SLAB);

        getOrCreateTagBuilder(BlockTags.WALLS)
                .add(ModBlocks.SOUL_DEEPSLATE_TILES_WALL)
                .add(ModBlocks.SOUL_DEEPSLATE_BRICKS_WALL)
                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE_WALL)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE_WALL);

        getOrCreateTagBuilder(BlockTags.STONE_PRESSURE_PLATES)
                .add(ModBlocks.COBBLED_SOUL_DEEPSLATE_PRESSURE_PLATE)
                .add(ModBlocks.POLISHED_SOUL_DEEPSLATE_PRESSURE_PLATE)
                .add(ModBlocks.SOUL_DEEPSLATE_BRICKS_PRESSURE_PLATE)
                .add(ModBlocks.SOUL_DEEPSLATE_TILES_PRESSURE_PLATE);
    }
}
