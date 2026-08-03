package plus.dragons.splashmilk.neoforge.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import plus.dragons.splashmilk.item.MilkBottle;
import plus.dragons.splashmilk.item.ThrowableMilkBottle;
import plus.dragons.splashmilk.neoforge.SplashMilk;

import java.util.function.Supplier;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, SplashMilk.MOD_ID);
    public static final Supplier<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottle::new);
    public static final Supplier<Item> SPLASH_MILK_BOTTLE = ITEMS.register("splash_milk_bottle", ()-> new ThrowableMilkBottle("splash_milk_bottle"));
    public static final Supplier<Item> LINGERING_MILK_BOTTLE = ITEMS.register("lingering_milk_bottle", ()->new ThrowableMilkBottle("lingering_milk_bottle"));

    public static void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == BuiltInRegistries.CREATIVE_MODE_TAB.getValue(CreativeModeTabs.FOOD_AND_DRINKS)) {
            event.accept(MILK_BOTTLE.get());
            event.accept(SPLASH_MILK_BOTTLE.get());
            event.accept(LINGERING_MILK_BOTTLE.get());
        }
    }
}