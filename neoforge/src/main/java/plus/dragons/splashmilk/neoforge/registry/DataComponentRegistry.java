package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.splashmilk.neoforge.SplashMilk;

import java.util.function.Supplier;

public class DataComponentRegistry {
    public static final DeferredRegister.DataComponents DATA_COMPONENT_TYPES = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, SplashMilk.MOD_ID);
    public static final Supplier<DataComponentType<SimpleFluidContent>> MILK = DATA_COMPONENT_TYPES.registerComponentType("milk", builder->
            builder.persistent(SimpleFluidContent.CODEC).networkSynchronized(SimpleFluidContent.STREAM_CODEC));

}
