package net.homak.homakmod.block;

import net.fabricmc.fabric.api.block.v1.FabricBlock;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.homak.homakmod.HomakMod;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class ModBlocks {


    public static final Block SOUL_TILES = registerBlock("soul_tiles",
            new Block(AbstractBlock.Settings.create().strength(3.5f, 3.5f).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));

    public static final Block SOUL_BRICKS = (Block) registerBlock("soul_bricks",
            new Block(AbstractBlock.Settings.create().strength(3.5f, 3.5f).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));

    public static final Block POLISHED_SOUL_DEEPSLATE = (Block) registerBlock("polished_soul_deepslate",
            new Block(AbstractBlock.Settings.create().strength(3.5f, 3.5f).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));

    public static final Block COBBLED_SOUL_DEEPSLATE = (Block) registerBlock("cobbled_soul_deepslate",
            new Block(AbstractBlock.Settings.create().strength(3.5f, 3.5f).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));

    // Non-full blocks

    // Walls
    public static final Block COBBLED_SOUL_DEEPSLATE_WALL = (Block) registerBlock("cobbled_soul_deepslate_wall",
            new WallBlock(AbstractBlock.Settings.create().strength(3.5f, 3.5f).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));

    public static final Block POLISHED_SOUL_DEEPSLATE_WALL = (Block) registerBlock("polished_soul_deepslate_wall",
            new WallBlock(AbstractBlock.Settings.create().strength(3.5f, 3.5f).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));

    public static final Block SOUL_DEEPSLATE_BRICKS_WALL = (Block) registerBlock("soul_deepslate_bricks_wall",
            new WallBlock(AbstractBlock.Settings.create().strength(3.5f, 3.5f).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));

    public static final Block SOUL_DEEPSLATE_TILES_WALL = (Block) registerBlock("soul_deepslate_tiles_wall",
            new WallBlock(AbstractBlock.Settings.create().strength(3.5f, 3.5f).sounds(BlockSoundGroup.DEEPSLATE).requiresTool()));

    // stairs
    public static final Block POLISHED_SOUL_DEEPSLATE_STAIRS = (Block) registerBlock("polished_soul_deepslate_stairs",
            new StairsBlock(ModBlocks.POLISHED_SOUL_DEEPSLATE.getDefaultState(),
                    AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    public static final Block COBBLED_SOUL_DEEPSLATE_STAIRS = (Block) registerBlock("cobbled_soul_deepslate_stairs",
            new StairsBlock(ModBlocks.COBBLED_SOUL_DEEPSLATE.getDefaultState(),
                    AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    public static final Block SOUL_BRICKS_STAIRS = (Block) registerBlock("soul_bricks_stairs",
            new StairsBlock(ModBlocks.SOUL_BRICKS.getDefaultState(),
                    AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    public static final Block SOUL_TILES_STAIRS = (Block) registerBlock("soul_tiles_stairs",
            new StairsBlock(ModBlocks.SOUL_TILES.getDefaultState(),
                    AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    // slabs
    public static final Block COBBLED_SOUL_DEEPSLATE_SLAB = (Block) registerBlock("cobbled_soul_deepslate_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    public static final Block POLISHED_SOUL_DEEPSLATE_SLAB = (Block) registerBlock("polished_soul_deepslate_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    public static final Block SOUL_BRICKS_SLAB = (Block) registerBlock("soul_bricks_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    public static final Block SOUL_TILES_SLAB = (Block) registerBlock("soul_tiles_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    // plates
    public static final Block COBBLED_SOUL_DEEPSLATE_PRESSURE_PLATE = (Block) registerBlock("cobbled_soul_deepslate_pressure_plate",
            new PressurePlateBlock(BlockSetType.STONE,
                    AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    public static final Block POLISHED_SOUL_DEEPSLATE_PRESSURE_PLATE = (Block) registerBlock("polished_soul_deepslate_pressure_plate",
            new PressurePlateBlock(BlockSetType.STONE,
                    AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    public static final Block SOUL_DEEPSLATE_BRICKS_PRESSURE_PLATE = (Block) registerBlock("soul_deepslate_bricks_pressure_plate",
            new PressurePlateBlock(BlockSetType.STONE,
                    AbstractBlock.Settings.create().strength(3.5f).requiresTool()));

    public static final Block SOUL_DEEPSLATE_TILES_PRESSURE_PLATE = (Block) registerBlock("soul_deepslate_tiles_pressure_plate",
            new PressurePlateBlock(BlockSetType.STONE,
                    AbstractBlock.Settings.create().strength(3.5f).requiresTool()))


    ;

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(HomakMod.MOD_ID, name), block);
    }

   private static void registerBlockItem(String name, Block block) {
       Registry.register(Registries.ITEM, Identifier.of(HomakMod.MOD_ID, name),
               new BlockItem(block, new Item.Settings()));
   }

    public static void registerModBlocks() {

        HomakMod.LOGGER.info("Registering Mod Blocks for " + HomakMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(entries -> {
        });

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.SOUL_TILES);
            entries.add(ModBlocks.SOUL_BRICKS);
            entries.add(POLISHED_SOUL_DEEPSLATE);
            entries.add(COBBLED_SOUL_DEEPSLATE);

            // stairs
            entries.add(SOUL_BRICKS_STAIRS);
            entries.add(SOUL_TILES_STAIRS);
            entries.add(COBBLED_SOUL_DEEPSLATE_STAIRS);
            entries.add(POLISHED_SOUL_DEEPSLATE_STAIRS);

            // slabs
            entries.add(POLISHED_SOUL_DEEPSLATE_SLAB);
            entries.add(SOUL_BRICKS_SLAB);
            entries.add(SOUL_TILES_SLAB);
            entries.add(COBBLED_SOUL_DEEPSLATE_SLAB);

            // walls
            entries.add(COBBLED_SOUL_DEEPSLATE_WALL);
            entries.add(POLISHED_SOUL_DEEPSLATE_WALL);
            entries.add(SOUL_DEEPSLATE_BRICKS_WALL);
            entries.add(SOUL_DEEPSLATE_TILES_WALL);

            // plates
            entries.add(COBBLED_SOUL_DEEPSLATE_PRESSURE_PLATE);
            entries.add(POLISHED_SOUL_DEEPSLATE_PRESSURE_PLATE);
            entries.add(SOUL_DEEPSLATE_BRICKS_PRESSURE_PLATE);
            entries.add(SOUL_DEEPSLATE_TILES_PRESSURE_PLATE);
        });
    }


}
