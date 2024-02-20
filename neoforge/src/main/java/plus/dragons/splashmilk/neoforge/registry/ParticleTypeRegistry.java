package plus.dragons.splashmilk.forge.registry;

import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.splashmilk.forge.SplashMilk;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SplashMilk.MOD_ID);
    public static final RegistryObject<DefaultParticleType> MILK_AREA_EFFECT = PARTICLE_TYPES.register("milk_area_effect", () -> new DefaultParticleType(false));
}
