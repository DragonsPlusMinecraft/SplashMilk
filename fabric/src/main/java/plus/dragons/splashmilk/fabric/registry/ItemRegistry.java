package plus.dragons.splashmilk.fabric.registry;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import plus.dragons.splashmilk.fabric.SplashMilk;
import plus.dragons.splashmilk.item.MilkBottle;
import plus.dragons.splashmilk.item.ThrowableMilkBottle;

public class ItemRegistry {
    public static final Item MILK_BOTTLE = new MilkBottle();
    public static final Item SPLASH_MILK_BOTTLE = new ThrowableMilkBottle("splash_milk_bottle");
    public static final Item LINGERING_MILK_BOTTLE = new ThrowableMilkBottle("lingering_milk_bottle");

    public static void ini() {
        register(MILK_BOTTLE, "milk_bottle");
        register(SPLASH_MILK_BOTTLE, "splash_milk_bottle");
        register(LINGERING_MILK_BOTTLE, "lingering_milk_bottle");

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register(entries -> {
            entries.accept(MILK_BOTTLE);
            entries.accept(SPLASH_MILK_BOTTLE);
            entries.accept(LINGERING_MILK_BOTTLE);
        });
    }

    private static void register(Item item, String id) {
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(SplashMilk.MOD_ID, id), item);
    }
}
