package plus.dragons.splashmilk.fabric.registry;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import plus.dragons.splashmilk.fabric.SplashMilk;

public class ParticleTypeRegistry {
    public static final DefaultParticleType MILK_AREA_EFFECT = FabricParticleTypes.simple();

    public static void ini() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(SplashMilk.MOD_ID, "milk_area_effect"), MILK_AREA_EFFECT);
    }
}
