package plus.dragons.splashmilk.forge;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import plus.dragons.splashmilk.forge.registry.EntityRegistry;
import plus.dragons.splashmilk.forge.registry.ItemRegistry;
import plus.dragons.splashmilk.forge.registry.ParticleTypeRegistry;

@Mod(SplashMilk.MOD_ID)
public class SplashMilk {
    public final static String MOD_ID = "splash_milk";

    public SplashMilk() {
        var modEventbus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(modEventbus);
        EntityRegistry.ENTITIES.register(modEventbus);
        ParticleTypeRegistry.PARTICLE_TYPES.register(modEventbus);

        modEventbus.addListener(ItemRegistry::addToCreativeTab);
    }
}
