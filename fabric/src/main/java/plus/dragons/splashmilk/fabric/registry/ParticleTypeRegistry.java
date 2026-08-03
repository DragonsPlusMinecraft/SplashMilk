package plus.dragons.splashmilk.fabric.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import plus.dragons.splashmilk.fabric.SplashMilk;

public class ParticleTypeRegistry {
    public static final SimpleParticleType MILK_AREA_EFFECT = FabricParticleTypes.simple();

    public static void ini() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath(SplashMilk.MOD_ID, "milk_area_effect"), MILK_AREA_EFFECT);
    }
}
