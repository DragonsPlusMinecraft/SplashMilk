package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.splashmilk.neoforge.SplashMilk;

import java.util.function.Supplier;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, SplashMilk.MOD_ID);
    public static final Supplier<SimpleParticleType> MILK_AREA_EFFECT = PARTICLE_TYPES.register("milk_area_effect", () -> new SimpleParticleType(false));
}
