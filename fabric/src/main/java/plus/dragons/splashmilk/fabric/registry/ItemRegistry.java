package plus.dragons.splashmilk.fabric.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import plus.dragons.splashmilk.fabric.SplashMilk;
import plus.dragons.splashmilk.item.MilkBottle;
import plus.dragons.splashmilk.item.ThrowableMilkBottle;

public class ItemRegistry {
    public static final Item MILK_BOTTLE = new MilkBottle();
    public static final Item SPLASH_MILK_BOTTLE = new ThrowableMilkBottle();
    public static final Item LINGERING_MILK_BOTTLE = new ThrowableMilkBottle();

    public static void ini() {
        register(MILK_BOTTLE, "milk_bottle");
        register(SPLASH_MILK_BOTTLE, "splash_milk_bottle");
        register(LINGERING_MILK_BOTTLE, "lingering_milk_bottle");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(entries -> {
            entries.add(MILK_BOTTLE);
            entries.add(SPLASH_MILK_BOTTLE);
            entries.add(LINGERING_MILK_BOTTLE);
        });
    }

    private static void register(Item item, String id) {
        Registry.register(Registries.ITEM, new Identifier(SplashMilk.MOD_ID, id), item);
    }
}