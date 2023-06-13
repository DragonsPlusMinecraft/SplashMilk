package plus.dragons.splashmilk.fabric;

import net.fabricmc.api.ModInitializer;
import plus.dragons.splashmilk.fabric.registry.EntityRegistry;
import plus.dragons.splashmilk.fabric.registry.ItemRegistry;
import plus.dragons.splashmilk.fabric.registry.ParticleTypeRegistry;

public class SplashMilk implements ModInitializer {
    public static final String MOD_ID = "splash_milk";

    @Override
    public void onInitialize() {
        ItemRegistry.ini();
        ParticleTypeRegistry.ini();
        EntityRegistry.ini();
    }
}
