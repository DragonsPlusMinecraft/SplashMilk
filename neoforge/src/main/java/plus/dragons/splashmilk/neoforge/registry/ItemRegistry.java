package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.splashmilk.item.MilkBottle;
import plus.dragons.splashmilk.item.ThrowableMilkBottle;
import plus.dragons.splashmilk.neoforge.SplashMilk;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, SplashMilk.MOD_ID);
    public static final Supplier<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottle::new);
    public static final Supplier<Item> SPLASH_MILK_BOTTLE = ITEMS.register("splash_milk_bottle", ThrowableMilkBottle::new);
    public static final Supplier<Item> LINGERING_MILK_BOTTLE = ITEMS.register("lingering_milk_bottle", ThrowableMilkBottle::new);

    public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == Registries.ITEM_GROUP.get(ItemGroups.FOOD_AND_DRINK)) {
            event.add(MILK_BOTTLE.get());
            event.add(SPLASH_MILK_BOTTLE.get());
            event.add(LINGERING_MILK_BOTTLE.get());
        }
    }
}