package plus.dragons.splashmilk.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import plus.dragons.splashmilk.fabric.registry.ItemRegistry;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PotionItem;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BrewingStandBlockEntity;


@Mixin(BrewingStandBlockEntity.class)
public class MixinBrewingStandBlockEntity {
    @Inject(method = "isBrewable(Lnet/minecraft/world/item/alchemy/PotionBrewing;Lnet/minecraft/core/NonNullList;)Z", at = @At("HEAD"), cancellable = true)
    private static void injected(PotionBrewing brewingRecipeRegistry, NonNullList<ItemStack> slots, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = slots.get(3);
        if (itemStack.is(Items.MILK_BUCKET)) {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemStack2 = slots.get(i);
                if (qualifiedWaterBottle(itemStack2))
                    cir.setReturnValue(true);
            }
        }
        if (itemStack.is(Items.GUNPOWDER)) {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemStack2 = slots.get(i);
                if (itemStack2.is(ItemRegistry.MILK_BOTTLE))
                    cir.setReturnValue(true);
            }
        }

        if (itemStack.is(Items.DRAGON_BREATH)) {
            for (int i = 0; i < 3; ++i) {
                ItemStack itemStack2 = slots.get(i);
                if (itemStack2.is(ItemRegistry.SPLASH_MILK_BOTTLE))
                    cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "doBrew(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/NonNullList;)V", at = @At("HEAD"), cancellable = true)
    private static void injected(Level world, BlockPos pos, NonNullList<ItemStack> slots, CallbackInfo ci) {
        ItemStack itemStack = slots.get(3);
        if (itemStack.is(Items.MILK_BUCKET)) {
            for (int i = 0; i < 3; ++i) {
                if (qualifiedWaterBottle(slots.get(i))) {
                    if(slots.get(i).is(Items.POTION)){
                        slots.set(i, ItemRegistry.MILK_BOTTLE.getDefaultInstance());
                    } else{
                        ItemStack brewed = slots.get(i).is(Items.LINGERING_POTION) ?
                                ItemRegistry.LINGERING_MILK_BOTTLE.getDefaultInstance() :
                                ItemRegistry.SPLASH_MILK_BOTTLE.getDefaultInstance();
                        slots.set(i, brewed);
                    }
                }
            }
            slots.set(3, Items.BUCKET.getDefaultInstance());
            world.levelEvent(1035, pos, 0);
            ci.cancel();
        }

        if (itemStack.is(Items.GUNPOWDER)) {
            for (int i = 0; i < 3; ++i) {
                if (slots.get(i).is(ItemRegistry.MILK_BOTTLE))
                    slots.set(i, ItemRegistry.SPLASH_MILK_BOTTLE.getDefaultInstance());
            }
        }

        if (itemStack.is(Items.DRAGON_BREATH)) {
            for (int i = 0; i < 3; ++i) {
                if (slots.get(i).is(ItemRegistry.SPLASH_MILK_BOTTLE))
                    slots.set(i, ItemRegistry.LINGERING_MILK_BOTTLE.getDefaultInstance());
            }
        }
    }

    private static boolean qualifiedWaterBottle(ItemStack itemStack) {
        if (itemStack.getItem() instanceof PotionItem) {
            Optional<Holder<Potion>> optional = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY).potion();
            if(optional.isPresent()){
                var op = optional.get();
                return op.equals(Potions.WATER) || op.equals(Potions.MUNDANE) || op.equals(Potions.THICK) || op.equals(Potions.AWKWARD);
            }
        }
        return false;
    }

    @Inject(method = "canPlaceItem(ILnet/minecraft/world/item/ItemStack;)Z", at = @At("HEAD"), cancellable = true)
    public void injected(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot < 3 && (stack.is(ItemRegistry.MILK_BOTTLE) || stack.is(ItemRegistry.SPLASH_MILK_BOTTLE))) {
            cir.setReturnValue(true);
        }
    }
}
