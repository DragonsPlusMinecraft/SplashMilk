package plus.dragons.splashmilk.neoforge;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.ItemAccessFluidHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import plus.dragons.splashmilk.neoforge.registry.DataComponentRegistry;
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
        DataComponentRegistry.DATA_COMPONENT_TYPES.register(modEventbus);

        modEventbus.addListener(ItemRegistry::addToCreativeTab);
        modEventbus.addListener(SplashMilk::registerCapabilities);
    }

    @SubscribeEvent
    private static void milking(PlayerInteractEvent.EntityInteract event){
        if(event.getEntity().level().isClientSide()) return;
        if(event.getTarget() instanceof Cow && event.getTarget().isAlive()){
            InteractionHand hand = event.getHand();
            ItemStack itemStack = event.getEntity().getItemInHand(hand);
            if(itemStack.is(Items.GLASS_BOTTLE)){
                itemStack.shrink(1);
                event.getEntity().addItem(ItemRegistry.MILK_BOTTLE.get().getDefaultInstance());
            }
        }
    }

    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerItem(
                Capabilities.Fluid.ITEM,
                (itemStack, context) -> new ItemAccessFluidHandler(context, DataComponentRegistry.MILK.get(), 333) {
                    @Override
                    protected ItemResource update(ItemResource currentItem, int index, FluidResource fluid, int amount) {
                        if (amount == 0) {
                            return ItemResource.of(Items.GLASS_BOTTLE);
                        }
                        if (amount != capacity) {
                            return ItemResource.EMPTY;
                        }
                        return super.update(currentItem, index, fluid, amount);
                    }
                },
                ItemRegistry.MILK_BOTTLE.get()
        );
    }

}
