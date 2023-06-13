package plus.dragons.splashmilk.fabric.mixin;

import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.splashmilk.fabric.registry.ItemRegistry;


@Mixin(BrewingStandBlockEntity.class)
public class MixinBrewingStandBlockEntity {
    @Inject(method = "canCraft(Lnet/minecraft/util/collection/DefaultedList;)Z", at = @At("HEAD"), cancellable = true)
    private static void injected(DefaultedList<ItemStack> slots, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = slots.get(3);
        if (itemStack.isOf(Items.MILK_BUCKET)) {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemStack2 = slots.get(i);
                if (qualifiedWaterBottle(itemStack2))
                    cir.setReturnValue(true);
            }
        }
        if (itemStack.isOf(Items.GUNPOWDER)) {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemStack2 = slots.get(i);
                if (itemStack2.isOf(ItemRegistry.MILK_BOTTLE))
                    cir.setReturnValue(true);
            }
        }
        if (itemStack.isOf(Items.DRAGON_BREATH)) {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemStack2 = slots.get(i);
                if (itemStack2.isOf(ItemRegistry.SPLASH_MILK_BOTTLE))
                    cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "craft(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/collection/DefaultedList;)V", at = @At("HEAD"), cancellable = true)
    private static void injected(World world, BlockPos pos, DefaultedList<ItemStack> slots, CallbackInfo ci) {
        ItemStack itemStack = slots.get(3);
        if (itemStack.isOf(Items.MILK_BUCKET)) {
            for (int i = 0; i < 3; ++i) {
                if (qualifiedWaterBottle(slots.get(i))) {
                    ItemStack brewed = slots.get(i).isOf(Items.LINGERING_POTION) ?
                            ItemRegistry.LINGERING_MILK_BOTTLE.getDefaultStack() :
                            ItemRegistry.SPLASH_MILK_BOTTLE.getDefaultStack();
                    slots.set(i, brewed);
                }
            }
            slots.set(3, Items.BUCKET.getDefaultStack());
            world.syncWorldEvent(1035, pos, 0);
            ci.cancel();
        }

        if (itemStack.isOf(Items.GUNPOWDER)) {
            for (int i = 0; i < 3; ++i) {
                if (slots.get(i).isOf(ItemRegistry.MILK_BOTTLE))
                    slots.set(i, ItemRegistry.SPLASH_MILK_BOTTLE.getDefaultStack());
            }
        }

        if (itemStack.isOf(Items.DRAGON_BREATH)) {
            for (int i = 0; i < 3; ++i) {
                if (slots.get(i).isOf(ItemRegistry.SPLASH_MILK_BOTTLE))
                    slots.set(i, ItemRegistry.LINGERING_MILK_BOTTLE.getDefaultStack());
            }
        }
    }

    private static boolean qualifiedWaterBottle(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ThrowablePotionItem) {
            Potion potion = PotionUtil.getPotion(itemStack);
            return potion.equals(Potions.WATER) || potion.equals(Potions.MUNDANE) || potion.equals(Potions.THICK) || potion.equals(Potions.AWKWARD);
        }
        return false;
    }

    @Inject(method = "isValid(ILnet/minecraft/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    public void injected(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot < 3 && (stack.isOf(ItemRegistry.MILK_BOTTLE) || stack.isOf(ItemRegistry.SPLASH_MILK_BOTTLE))) {
            cir.setReturnValue(true);
        }
    }

}
