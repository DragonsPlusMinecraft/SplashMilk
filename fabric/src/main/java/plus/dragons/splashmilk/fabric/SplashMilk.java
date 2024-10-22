package plus.dragons.splashmilk.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
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
            if(player.getWorld().isClient()) return ActionResult.PASS;
            if(entity instanceof CowEntity && entity.isAlive()){
                ItemStack itemStack = player.getStackInHand(hand);
                if(itemStack.isOf(Items.GLASS_BOTTLE)){
                    itemStack.decrement(1);
                    player.giveItemStack(ItemRegistry.MILK_BOTTLE.getDefaultStack());
                    return ActionResult.SUCCESS;
                }
            }
            return ActionResult.PASS;
        });
    }
}
