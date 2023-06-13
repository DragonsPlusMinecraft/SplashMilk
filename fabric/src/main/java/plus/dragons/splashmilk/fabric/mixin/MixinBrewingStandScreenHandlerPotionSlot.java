package plus.dragons.splashmilk.fabric.mixin;

import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.splashmilk.fabric.registry.ItemRegistry;

@Mixin(targets = "net.minecraft.screen.BrewingStandScreenHandler$PotionSlot")
public class MixinBrewingStandScreenHandlerPotionSlot {
    @Inject(method = "matches", at = @At("HEAD"), cancellable = true)
    private static void injected(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(ItemRegistry.MILK_BOTTLE) || stack.isOf(ItemRegistry.SPLASH_MILK_BOTTLE))
            cir.setReturnValue(true);
    }
}
