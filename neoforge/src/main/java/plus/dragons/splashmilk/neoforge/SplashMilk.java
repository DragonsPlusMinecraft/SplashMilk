package plus.dragons.splashmilk.neoforge;

import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import plus.dragons.splashmilk.neoforge.registry.EntityRegistry;
import plus.dragons.splashmilk.neoforge.registry.ItemRegistry;
import plus.dragons.splashmilk.neoforge.registry.ParticleTypeRegistry;

@EventBusSubscriber(modid = SplashMilk.MOD_ID)
@Mod(SplashMilk.MOD_ID)
public class SplashMilk {
    public final static String MOD_ID = "splash_milk";

    public SplashMilk(IEventBus modEventbus) {
        ItemRegistry.ITEMS.register(modEventbus);
        EntityRegistry.ENTITIES.register(modEventbus);
        ParticleTypeRegistry.PARTICLE_TYPES.register(modEventbus);

        modEventbus.addListener(ItemRegistry::addToCreativeTab);
    }

    @SubscribeEvent
    private static void milking(PlayerInteractEvent.EntityInteract event){
        if(event.getEntity().getWorld().isClient()) return;
        if(event.getTarget() instanceof CowEntity && event.getTarget().isAlive()){
            Hand hand = event.getHand();
            ItemStack itemStack = event.getEntity().getStackInHand(hand);
            if(itemStack.isOf(Items.GLASS_BOTTLE)){
                itemStack.decrement(1);
                event.getEntity().giveItemStack(ItemRegistry.MILK_BOTTLE.get().getDefaultStack());
            }
        }
    }

}
