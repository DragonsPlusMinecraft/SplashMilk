package plus.dragons.splashmilk.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.item.consume.UseAction;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import plus.dragons.splashmilk.PlatformUtil;

public class MilkBottle extends Item {
    public MilkBottle() {
        super(PlatformUtil.milkBottleSetting().registryKey(RegistryKey.of(Registries.ITEM.getKey(), Identifier.of("splash_milk","milk_bottle"))));
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (!world.isClient())
            user.clearStatusEffects();
        if (user instanceof PlayerEntity player) {
            if (!player.getAbilities().creativeMode) {
                stack.decrement(1);
            }
            if (stack.isEmpty()) {
                return new ItemStack(Items.GLASS_BOTTLE);
            } else {
                if (!player.getAbilities().creativeMode) {
                    player.getInventory().insertStack(new ItemStack(Items.GLASS_BOTTLE));
                }
                return stack;
            }
        } else {
            stack.decrement(1);
            return stack;
        }
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        return 10;
    }
}
