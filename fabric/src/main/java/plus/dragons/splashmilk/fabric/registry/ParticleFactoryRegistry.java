package plus.dragons.splashmilk.fabric.registry;

import net.minecraft.client.particle.HeartParticle;

public class ParticleFactoryRegistry {
    public static void ini() {
        net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry.getInstance().register(ParticleTypeRegistry.MILK_AREA_EFFECT, HeartParticle.AngryVillagerProvider::new);
    }
}
