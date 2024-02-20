package plus.dragons.splashmilk.forge.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.splashmilk.forge.SplashMilk;
import plus.dragons.splashmilk.item.MilkBottle;
import plus.dragons.splashmilk.item.ThrowableMilkBottle;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SplashMilk.MOD_ID);
    public static final RegistryObject<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottle::new);
    public static final RegistryObject<Item> SPLASH_MILK_BOTTLE = ITEMS.register("splash_milk_bottle", ThrowableMilkBottle::new);
    public static final RegistryObject<Item> LINGERING_MILK_BOTTLE = ITEMS.register("lingering_milk_bottle", ThrowableMilkBottle::new);

    public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == Registries.ITEM_GROUP.get(ItemGroups.FOOD_AND_DRINK)) {
            event.accept(MILK_BOTTLE);
            event.accept(SPLASH_MILK_BOTTLE);
            event.accept(LINGERING_MILK_BOTTLE);
        }
    }
}