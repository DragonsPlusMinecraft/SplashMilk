package plus.dragons.splashmilk.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.cow.Cow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import plus.dragons.splashmilk.fabric.registry.EntityRegistry;
import plus.dragons.splashmilk.fabric.registry.ItemRegistry;
import plus.dragons.splashmilk.fabric.registry.ParticleTypeRegistry;

public class SplashMilk implements ModInitializer {
    public static final String MOD_ID = "splash_milk";

    @Override
    public void onInitialize() {
        ItemRegistry.ini();
        ParticleTypeRegistry.ini();
        EntityRegistry.ini();

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult)->{
            if(player.level().isClientSide()) return InteractionResult.PASS;
            if(entity instanceof Cow && entity.isAlive()){
                ItemStack itemStack = player.getItemInHand(hand);
                if(itemStack.is(Items.GLASS_BOTTLE)){
                    itemStack.shrink(1);
                    player.addItem(ItemRegistry.MILK_BOTTLE.getDefaultInstance());
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.PASS;
        });
    }
}
