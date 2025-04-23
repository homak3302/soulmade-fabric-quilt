package net.homak.homakmod.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.homak.homakmod.HomakMod;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {

    public static final SimpleParticleType SOUL_INGOT_FRACTURE_PARTICLE =
            registerParticle("soul_ingot_fracture_particle", FabricParticleTypes.simple());

    private static SimpleParticleType registerParticle(String name, SimpleParticleType particleType) {
        return Registry.register(Registries.PARTICLE_TYPE, Identifier.of(HomakMod.MOD_ID, name), particleType);
    }

    public static void registerParticles() {
        HomakMod.LOGGER.info("Registering particles for " + HomakMod.MOD_ID);
    }
}
