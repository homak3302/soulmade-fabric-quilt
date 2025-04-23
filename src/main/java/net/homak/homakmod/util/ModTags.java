package net.homak.homakmod.util;

import net.homak.homakmod.HomakMod;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {
    public static class Blocks {

        public static final TagKey<Block> INCORRECT_FOR_ANCIENT_REMAIN_TOOL = createTag("incorrect_for_ancient_remain_tool") ;

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(HomakMod.MOD_ID, name));
    }

    public static class Items{

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(HomakMod.MOD_ID, name));
        }
    }

}}
