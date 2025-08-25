package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.client.particle.EmotionParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import plus.dragons.splashmilk.neoforge.SplashMilk;

@EventBusSubscriber(value = Dist.CLIENT, modid = SplashMilk.MOD_ID)
public class ParticleFactoryRegistry {

    @SubscribeEvent
    public static void onParticleProviderRegistration(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ParticleTypeRegistry.MILK_AREA_EFFECT.get(), EmotionParticle.AngryVillagerFactory::new);
    }
}
