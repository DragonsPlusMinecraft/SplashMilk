package plus.dragons.splashmilk;

import plus.dragons.splashmilk.registry.EntityRegistry;
import plus.dragons.splashmilk.registry.ItemRegistry;
import plus.dragons.splashmilk.registry.ParticleTypeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("splash_milk")
public class SplashMilk {
    public static String MOD_ID = "splash_milk";

    public SplashMilk() {
        var modEventbus = FMLJavaModLoadingContext.get().getModEventBus();
        ItemRegistry.ITEMS.register(modEventbus);
        EntityRegistry.ENTITIES.register(modEventbus);
        ParticleTypeRegistry.PARTICLE_TYPES.register(modEventbus);

        modEventbus.addListener(ItemRegistry::addToCreativeTab);
    }
}