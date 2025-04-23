package net.homak.homakmod.item;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.homak.homakmod.HomakMod;
import net.homak.homakmod.item.ItemClasses.*;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    // Items

    public static final Item SOUL_HIDER = registerItem("soul_hider",
            new SoulHiderItem(new Item.Settings().maxCount(1)));

    public static final Item SOUL_FORCE_FIELD = registerItem("soul_force_field",
            new SoulForceFieldItem(new Item.Settings().maxCount(1)));

    public static final Item DICE_OF_FATE = registerItem("dice_of_fate",
            new DiceItem(new Item.Settings().maxCount(1)));

    public static final Item SOUL_BOOSTER = registerItem("soul_booster",
            new SoulBoosterItem(new Item.Settings().maxCount(1)));

    public static final Item UNSTABLE_SOUL_INGOT = registerItem("unstable_soul_ingot",
            new Item(new Item.Settings()));

    public static final Item SOUL_INGOT = registerItem("soul_ingot",
            new Item(new Item.Settings()));

    public static final Item SOUL_WARPER = registerItem("soul_warper",
            new SoulWarperItem(new Item.Settings().maxCount(1)));

    public static final Item SOUL_BOTTLE = registerItem("soul_bottle",
            new Item(new Item.Settings().maxCount(16)));

    public static final Item SOUL_ROCKET_LAUNCHER = registerItem("soul_rocket_launcher",
            new SoulRocketLauncherItem(new Item.Settings().maxCount(1)));

    // Swords
    public static final Item SOUL_CURVESWORD = registerItem("soul_curvesword",
            new SoulCurveswordItem(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 3, -2.4f))));

    public static final Item HARBINGER_GLAIVE = registerItem("harbinger_glaive",
            new HarbingerGlaiveItem(ToolMaterials.NETHERITE, new Item.Settings().maxCount(1)
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 1, -1.8f))));

    public static final Item SOUL_SPEAR = registerItem("soul_spear",
            new SoulSpearItem(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4, -2.6f))));

    public static final Item SOUL_GREATAXE = registerItem("soul_greataxe",
            new SoulGreataxeItem(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 5, -3.0f))));

    public static final Item FRACTURED_SLAYER = registerItem("fractured_slayer",
            new FracturedSlayerItem(ToolMaterials.NETHERITE, new Item.Settings().maxCount(1)
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 4, -2.6f))));

    public static final Item SOUL_CLEAVER = registerItem("soul_cleaver",
            new SoulCleaverItem(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 5, -3.0f))));

    public static final Item SOUL_GREATSWORD = registerItem("soul_greatsword",
            new SoulGreatswordItem(ToolMaterials.NETHERITE, new Item.Settings()
                    .attributeModifiers(SwordItem.createAttributeModifiers(ToolMaterials.NETHERITE, 5, -3.0f))));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HomakMod.MOD_ID, name), item);
    }

    public static void registerModItems() {
        HomakMod.LOGGER.info("Registering Mod Items for " + HomakMod.MOD_ID);

        // Item Groups
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(SOUL_FORCE_FIELD);
            entries.add(SOUL_CURVESWORD);
            entries.add(SOUL_CLEAVER);
            entries.add(SOUL_GREATSWORD);
            entries.add(DICE_OF_FATE);
            entries.add(FRACTURED_SLAYER);
            entries.add(SOUL_SPEAR);
            entries.add(SOUL_GREATAXE);
            entries.add(HARBINGER_GLAIVE);
        } );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(SOUL_HIDER);
            entries.add(SOUL_BOOSTER);
            entries.add(SOUL_WARPER);
            entries.add(SOUL_ROCKET_LAUNCHER);
        } );

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries -> {
            entries.add(UNSTABLE_SOUL_INGOT);
            entries.add(SOUL_INGOT);
            entries.add(SOUL_BOTTLE);
        } );
        }
    }



