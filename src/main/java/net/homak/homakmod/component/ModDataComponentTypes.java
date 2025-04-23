package net.homak.homakmod.component;

import com.mojang.serialization.Codec;
import net.homak.homakmod.HomakMod;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final ComponentType<Integer> SOULS = register("souls", builder -> builder.codec(Codec.intRange(0, 20)));
    public static final ComponentType<Integer> FRACTURED_SOULS = register("fractured_souls", builder -> builder.codec(Codec.intRange(0, 30)));
    public static final ComponentType<BlockPos> WARP_COORDS = register("warp_coords", builder -> builder.codec(BlockPos.CODEC));
    public static final ComponentType<RegistryKey<World>> WARP_DIMENSION = register("warp_dimension", builder -> builder.codec(World.CODEC));



    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(HomakMod.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }


    public static void registerDataComponentTypes() {
        HomakMod.LOGGER.info("Registering Data Components For " + HomakMod.MOD_ID);
    }
}
