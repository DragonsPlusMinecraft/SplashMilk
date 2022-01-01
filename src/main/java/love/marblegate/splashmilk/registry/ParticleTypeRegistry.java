package love.marblegate.splashmilk.registry;

import love.marblegate.splashmilk.SplashMilk;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleTypeRegistry {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, SplashMilk.MOD_ID);
    public static final RegistryObject<BasicParticleType> MILK_AREA_EFFECT = PARTICLE_TYPES.register("milk_area_effect", () -> new BasicParticleType(false));
}
