package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.splashmilk.neoforge.SplashMilk;

import java.util.function.Supplier;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Registries.PARTICLE_TYPE, SplashMilk.MOD_ID);
    public static final Supplier<DefaultParticleType> MILK_AREA_EFFECT = PARTICLE_TYPES.register("milk_area_effect", () -> new DefaultParticleType(false));
}
