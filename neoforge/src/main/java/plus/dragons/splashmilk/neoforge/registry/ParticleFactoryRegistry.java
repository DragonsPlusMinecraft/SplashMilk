package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.EmotionParticle;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ParticleFactoryRegistry {
    @SubscribeEvent
    public static void onParticleFactoryRegistration(RegisterParticleProvidersEvent event) {
        MinecraftClient.getInstance().particleManager.registerFactory(ParticleTypeRegistry.MILK_AREA_EFFECT.get(), EmotionParticle.AngryVillagerFactory::new);
    }
}
