package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.component.ComponentType;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.splashmilk.neoforge.SplashMilk;

import java.util.function.Supplier;

public class DataComponentRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(RegistryKeys.DATA_COMPONENT_TYPE, SplashMilk.MOD_ID);
    public static final Supplier<ComponentType<SimpleFluidContent>> MILK = DATA_COMPONENT_TYPES.registerComponentType("milk", builder->
            builder.codec(SimpleFluidContent.CODEC).packetCodec(SimpleFluidContent.STREAM_CODEC));

}
