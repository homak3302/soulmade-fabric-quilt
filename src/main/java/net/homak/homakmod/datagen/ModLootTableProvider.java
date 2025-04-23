package net.homak.homakmod.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.homak.homakmod.block.ModBlocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.POLISHED_SOUL_DEEPSLATE);
        addDrop(ModBlocks.SOUL_BRICKS);
        addDrop(ModBlocks.SOUL_TILES);
        addDrop(ModBlocks.COBBLED_SOUL_DEEPSLATE);

        // stairs
        addDrop(ModBlocks.SOUL_BRICKS_STAIRS);
        addDrop(ModBlocks.SOUL_TILES_STAIRS);
        addDrop(ModBlocks.COBBLED_SOUL_DEEPSLATE_STAIRS);
        addDrop(ModBlocks.POLISHED_SOUL_DEEPSLATE_STAIRS);

        // slabs
        addDrop(ModBlocks.POLISHED_SOUL_DEEPSLATE_SLAB, slabDrops(ModBlocks.POLISHED_SOUL_DEEPSLATE_SLAB));
        addDrop(ModBlocks.SOUL_BRICKS_SLAB, slabDrops(ModBlocks.SOUL_BRICKS_SLAB));
        addDrop(ModBlocks.SOUL_TILES_SLAB, slabDrops(ModBlocks.SOUL_TILES_SLAB));
        addDrop(ModBlocks.COBBLED_SOUL_DEEPSLATE_SLAB, slabDrops(ModBlocks.COBBLED_SOUL_DEEPSLATE_SLAB));

        // walls
        addDrop(ModBlocks.COBBLED_SOUL_DEEPSLATE_WALL);
        addDrop(ModBlocks.POLISHED_SOUL_DEEPSLATE_WALL);
        addDrop(ModBlocks.SOUL_DEEPSLATE_BRICKS_WALL);
        addDrop(ModBlocks.SOUL_DEEPSLATE_TILES_WALL);

        // plates
        addDrop(ModBlocks.COBBLED_SOUL_DEEPSLATE_PRESSURE_PLATE);
        addDrop(ModBlocks.POLISHED_SOUL_DEEPSLATE_PRESSURE_PLATE);
        addDrop(ModBlocks.SOUL_DEEPSLATE_BRICKS_PRESSURE_PLATE);
        addDrop(ModBlocks.SOUL_DEEPSLATE_TILES_PRESSURE_PLATE);
    }
}
