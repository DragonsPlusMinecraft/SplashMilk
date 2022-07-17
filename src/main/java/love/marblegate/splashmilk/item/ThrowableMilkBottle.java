package love.marblegate.splashmilk.item;

import love.marblegate.splashmilk.entity.MilkBottleEntity;
import love.marblegate.splashmilk.registry.ItemRegistry;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.Level;

import net.minecraft.world.item.Item.Properties;

public class ThrowableMilkBottle extends Item {
    public ThrowableMilkBottle() {
        super(new Properties().tab(CreativeModeTab.TAB_BREWING));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        boolean isLingering = itemstack.getItem().equals(ItemRegistry.LINGERING_MILK_BOTTLE.get());
        world.playSound((Player) null, player.getX(), player.getY(), player.getZ(), isLingering ? SoundEvents.LINGERING_POTION_THROW : SoundEvents.SPLASH_POTION_THROW, SoundSource.NEUTRAL, 0.5F, (float) (0.4F / (Math.random() * 0.4F + 0.8F)));

        if (!world.isClientSide) {
            MilkBottleEntity milkBottleEntity = new MilkBottleEntity(world, player);
            milkBottleEntity.setItem(itemstack);
            milkBottleEntity.shootFromRotation(player, player.xRotO, player.yRotO, -20.0F, 0.5F, 1.0F);
            world.addFreshEntity(milkBottleEntity);
        }

        if (!player.getAbilities().instabuild) {
            itemstack.shrink(1);
        }

        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
    }
}
