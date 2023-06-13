package plus.dragons.splashmilk.fabric.registry;

import net.minecraft.client.particle.EmotionParticle;

public class ParticleFactoryRegistry {
    public static void ini() {
        net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry.getInstance().register(ParticleTypeRegistry.MILK_AREA_EFFECT, EmotionParticle.AngryVillagerFactory::new);
    }
}
