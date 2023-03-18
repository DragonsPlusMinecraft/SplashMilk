package plus.dragons.splashmilk.registry;

import plus.dragons.splashmilk.SplashMilk;
import plus.dragons.splashmilk.item.MilkBottle;
import plus.dragons.splashmilk.item.ThrowableMilkBottle;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SplashMilk.MOD_ID);
    public static final RegistryObject<Item> MILK_BOTTLE = ITEMS.register("milk_bottle", MilkBottle::new);
    public static final RegistryObject<Item> SPLASH_MILK_BOTTLE = ITEMS.register("splash_milk_bottle", ThrowableMilkBottle::new);
    public static final RegistryObject<Item> LINGERING_MILK_BOTTLE = ITEMS.register("lingering_milk_bottle", ThrowableMilkBottle::new);

    public static void addToCreativeTab(CreativeModeTabEvent.BuildContents event){
        if(event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS){
            event.accept(MILK_BOTTLE.get());
            event.accept(SPLASH_MILK_BOTTLE.get());
            event.accept(LINGERING_MILK_BOTTLE.get());
        }
    }
}