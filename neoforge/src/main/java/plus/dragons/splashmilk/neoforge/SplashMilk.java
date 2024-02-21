package plus.dragons.splashmilk.neoforge;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import plus.dragons.splashmilk.neoforge.registry.EntityRegistry;
import plus.dragons.splashmilk.neoforge.registry.ItemRegistry;
import plus.dragons.splashmilk.neoforge.registry.ParticleTypeRegistry;

@Mod(SplashMilk.MOD_ID)
public class SplashMilk {
    public final static String MOD_ID = "splash_milk";

    public SplashMilk(IEventBus modEventbus) {
        ItemRegistry.ITEMS.register(modEventbus);
        EntityRegistry.ENTITIES.register(modEventbus);
        ParticleTypeRegistry.PARTICLE_TYPES.register(modEventbus);

        modEventbus.addListener(ItemRegistry::addToCreativeTab);
    }
}
