package love.marblegate.splashmilk;

import love.marblegate.splashmilk.registry.EntityRegistry;
import love.marblegate.splashmilk.registry.ItemRegistry;
import love.marblegate.splashmilk.registry.ParticleTypeRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("splash_milk")
public class SplashMilk {
    public static String MOD_ID = "splash_milk";

    public SplashMilk() {
        // TODO add brewing & crafting(maybe?) recipe
        ItemRegistry.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        EntityRegistry.ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
        ParticleTypeRegistry.PARTICLE_TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
