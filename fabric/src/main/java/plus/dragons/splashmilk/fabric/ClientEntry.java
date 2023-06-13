package plus.dragons.splashmilk.fabric;

import net.fabricmc.api.ClientModInitializer;
import plus.dragons.splashmilk.fabric.registry.ParticleFactoryRegistry;
import plus.dragons.splashmilk.fabric.registry.RendererRegistry;

public class ClientEntry implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ParticleFactoryRegistry.ini();
        RendererRegistry.ini();
    }
}
