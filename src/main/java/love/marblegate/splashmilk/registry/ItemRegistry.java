package love.marblegate.splashmilk.registry;

import love.marblegate.splashmilk.SplashMilk;
import love.marblegate.splashmilk.item.MilkBottle;
import love.marblegate.splashmilk.item.ThrowableMilkBottle;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SplashMilk.MOD_ID);
    public static final RegistryObject<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottle::new);
    public static final RegistryObject<Item> SPLASH_MILK_BOTTLE = ITEMS.register("splash_milk_bottle", ThrowableMilkBottle::new);
    public static final RegistryObject<Item> LINGERING_MILK_BOTTLE = ITEMS.register("lingering_milk_bottle", ThrowableMilkBottle::new);
}